package com.luis.ravegram.model;

import java.util.ArrayList;
import java.util.List;

public class Results<T> {

	private List<T> data = null;
	private long total = 0;

	public Results() {
		this.data = new ArrayList<T>();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}