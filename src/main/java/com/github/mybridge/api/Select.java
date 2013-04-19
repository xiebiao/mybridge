package com.github.mybridge.api;

import java.util.List;

public interface Select<T> {
	T selectOne(From from, Where where, Limit limit);

	List<T> selectList(From from, Where where, Limit limit);
}
