package com.luis.ravegram.model;

import java.util.ArrayList;
import java.util.List;

public class Results<T> {

	private List<T> data = null;
	private Integer total = 0;

	public Results() {
		this.data = new ArrayList<T>();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}