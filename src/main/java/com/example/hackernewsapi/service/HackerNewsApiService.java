package com.example.hackernewsapi.service;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.example.hackernewsapi.model.Story;
import com.example.hackernewsapi.model.TopComment;

public interface HackerNewsApiService {

	List<Story> getTopStories();

	SortedSet<TopComment> getCommentsById(int storyId);

	Set<Story> getPastTopStories();
}
