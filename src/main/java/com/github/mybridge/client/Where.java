package com.github.mybridge.client;

import java.util.List;

import com.github.mybridge.Pair;

public class Where {
	private List<Pair<String, String>> pairs;

	public Where(List<Pair<String, String>> pairs) {
		this.pairs = pairs;
	}

	/**
	 * 暂时不实现
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = this.pairs.size();
		for (int i = 0; i < size; i++) {
			// sb.append(this.pairs.get(i).getKey()+"=")
		}
		return null;
	}

}