package com.example.hackernewsapi.model;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Story implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String by;
	private int score;
	private int time;
	private String title;
	private String url;

	public Story() {
	};

	@JsonIgnore
	private int[] kids;

	@JsonIgnore
	private String type;

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int[] getKids() {
		return kids;
	}

	public void setKids(int[] kids) {
		this.kids = kids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Story [by=" + by + ", score=" + score + ", time=" + time + ", title=" + title + ", url=" + url
				+ ", kids=" + Arrays.toString(kids) + ", type=" + type + "]";
	}

}
