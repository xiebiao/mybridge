package com.github.mybridge.engine;

import com.github.mybridge.Lifecycle;

public interface Engine extends Lifecycle{	
	
	public DatabaseServer getServer(String database);
}
