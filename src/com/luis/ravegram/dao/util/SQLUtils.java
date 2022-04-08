package com.luis.ravegram.dao.util;

import java.util.List;

public class SQLUtils {

	public static final String toIN(List<Long> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<ids.size()-1;i++) {
			sb.append(ids.get(i)).append(",");
		}
		sb.append(ids.get(ids.size()-1));
		return sb.toString(); 
	}
}
