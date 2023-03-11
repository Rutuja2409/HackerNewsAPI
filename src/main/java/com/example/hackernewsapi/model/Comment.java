package com.example.hackernewsapi.model;

import java.io.Serializable;
import java.util.Arrays;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	private String by;
	private int id;
	private int parent;
	private int[] kids;
	private String text;
	private int time;

	public Comment() {
	};

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int[] getKids() {
		return kids;
	}

	public void setKids(int[] kids) {
		this.kids = kids;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Comment [by=" + by + ", id=" + id + ", parent=" + parent + ", kids=" + Arrays.toString(kids) + ", text="
				+ text + ", time=" + time + "]";
	}

}
