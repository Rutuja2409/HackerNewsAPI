package com.example.hackernewsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TopComment {

	private String text;
	private String by;
	private int age;
	@JsonIgnore
	private int totalComments;

	public TopComment() {
	}

	public TopComment(String text, String by, int age, int totalComments) {
		super();
		this.text = text;
		this.by = by;
		this.age = age;
		this.totalComments = totalComments;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	@Override
	public String toString() {
		return "TopComment [text=" + text + ", by=" + by + ", age=" + age + ", totalComments=" + totalComments + "]";
	}

}
