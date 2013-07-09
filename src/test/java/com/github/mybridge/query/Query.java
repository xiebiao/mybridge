package com.github.mybridge.query;

public class Query extends Base {

	public static void main(String[] args) throws Exception {
		int count = 1;
		Query q = new Query();
		while (count-- > 0) {
			q.query(count);
		}
	}
}
