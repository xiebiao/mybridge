package com.github.mybridge;


public interface Lifecycle {

	public void init() throws LifecycleException;

	public void start() throws LifecycleException;

	public void stop() throws LifecycleException;

	public void destroy() throws LifecycleException;
}
