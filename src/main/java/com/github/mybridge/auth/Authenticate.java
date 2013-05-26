package com.github.mybridge.auth;

public interface Authenticate {

    public boolean checkAuth(String user, byte[] password);
}
