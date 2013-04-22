package com.github.mybridge.client;

import java.util.List;

import com.github.mybridge.api.example.User;

public class QuerySelect implements Select<User> {

	@Override
	public User selectOne(From from, Where where, Limit limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selectList(From from, Where where, Limit limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public static final void main(String args[]) {
		QuerySelect q = new QuerySelect();
		From from = new From("user");

	}

}
