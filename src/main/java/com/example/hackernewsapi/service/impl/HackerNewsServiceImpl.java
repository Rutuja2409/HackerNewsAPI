package com.example.hackernewsapi.service.impl;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hackernewsapi.common.AppConstants;
import com.example.hackernewsapi.common.Utils;
import com.example.hackernewsapi.exceptions.InternalServerException;
import com.example.hackernewsapi.exceptions.NoRecordFoundException;
import com.example.hackernewsapi.model.Comment;
import com.example.hackernewsapi.model.Story;
import com.example.hackernewsapi.model.TopComment;
import com.example.hackernewsapi.service.CacheService;
import com.example.hackernewsapi.service.HackerNewsApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;


@Service
public class HackerNewsServiceImpl implements HackerNewsApiService {

	@Autowired
	CacheService cacheService;

	private static Set<Story> pastStorySet = new HashSet<>();

	public List<Story> getTopStories() {
		List<Story> topStoriesList;

		if (cacheService.get(AppConstants.TOP_STORIES_CACHE_NAME) == null) {
			topStoriesList = getTopStoriesList();
			try {
				cacheService.set(AppConstants.TOP_STORIES_CACHE_NAME,
						Utils.getObjectMapper().writeValueAsString(topStoriesList));
			} catch (JsonProcessingException e) {
				throw new InternalServerException();
			}
		} else {
			try {
				topStoriesList = Utils.getObjectMapper().readValue(
						cacheService.get(AppConstants.TOP_STORIES_CACHE_NAME).toString(),
						new TypeReference<List<Story>>() {
						});
			} catch (Exception e) {
				throw new InternalServerException();
			}
		}

		return topStoriesList;

	}

	public Set<Story> getPastTopStories() {
		return pastStorySet;
	}

	public SortedSet<TopComment> getCommentsById(int storyId) {

		SortedSet<TopComment> topCommentSet = new TreeSet<>(Comparator.comparing(TopComment::getTotalComments)
				.reversed().thenComparing(TopComment::getTotalComments));

		Story story = Utils.getStory(storyId);
		if (story != null) {

			int[] comments = story.getKids();

			if (comments != null) {
				for (int commentId : comments) {

					Comment comment = Utils.getComment(commentId);
					if (comment != null) {

						int commentCount = 1 + Utils.getCommentCount(comment);

						TopComment topComment = new TopComment(comment.getText(), comment.getBy(),
								Utils.getUserAgeByName(comment.getBy()), commentCount);

						topCommentSet.add(topComment);
					}
				}
			} else {
				throw new NoRecordFoundException(AppConstants.NO_COMMENT_FOUND_FOR_THE_STORY_MESSAGE);
			}
		} else {
			throw new NoRecordFoundException(AppConstants.NO_STORY_FOUND_MESSAGE);
		}

		return topCommentSet;
	}

	private List<Story> getTopStoriesList() {

		SortedSet<Story> topStoriesSet = new TreeSet<>(
				Comparator.comparing(Story::getScore).reversed().thenComparing(Story::getScore));

		List<Integer> topStories = Utils.getTopStoryIds();
		int division = topStories.size() / 4;

		ExecutorService executor = Executors.newFixedThreadPool(4);
		CountDownLatch latch = new CountDownLatch(4);

		try {
			for (int i = 0; i < 4; i++) {
				int listSizeForThreadFrom = i * division;
				int listSizeForThreadTo = (i + 1) * division;
				executor.submit(() -> {
					addStoriesToSet(topStoriesSet, topStories.subList(listSizeForThreadFrom, listSizeForThreadTo));
					latch.countDown();
				});
			}
			//latch.await();
		} finally {
			executor.shutdown();
		}

		List<Story> topTenStories = topStoriesSet.stream().limit(10).collect(Collectors.toList());

		addToPastStorySet(topTenStories);

		return topTenStories;
	}

	private void addStoriesToSet(SortedSet<Story> topStoriesSet, List<Integer> subList) {
		for (int storyId : subList) {
			Story story = Utils.getStory(storyId);
			if (story.getType().equals(AppConstants.ITEM_TYPE_STORY)) {
				topStoriesSet.add(story);
			}
		}
	}

	private void addToPastStorySet(List<Story> topTenStories) {

		topTenStories.stream().forEach(topStory -> {
			if (pastStorySet.contains(topStory)) {
				pastStorySet.stream().filter(pastStory -> pastStory.equals(topStory)).findFirst()
						.ifPresent(story -> story.setScore(topStory.getScore()));
			} else {
				pastStorySet.add(topStory);
			}
		});
	}

}