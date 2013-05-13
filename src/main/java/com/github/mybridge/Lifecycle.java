package com.github.mybridge;

public interface Lifecycle {

    public void start() throws LifecycleException;

    public void stop() throws LifecycleException;
}
