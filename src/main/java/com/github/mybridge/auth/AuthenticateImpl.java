package com.github.mybridge.auth;

import java.security.MessageDigest;

public class AuthenticateImpl implements Authenticate {

    private String serverUser;
    private String serverPassword;

    public AuthenticateImpl(String serverUser, String serverPassword) {
        this.serverUser = serverUser;
        this.serverPassword = serverPassword;
    }

    @Override
    public boolean checkAuth(String user, byte[] password) {
        if (!serverUser.equals(user)) {
            return false;
        }
        if (password.length == 0 && serverPassword.length() == 0) {
            return true;
        }
        byte[] sPass;
        try {
            sPass = encodePassword(serverPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (password.length != sPass.length) {
            return false;
        }
        for (int i = 0; i < sPass.length; i++) {
            if (password[i] != sPass[i]) {
                return false;
            }
        }
        return true;
    }

    private byte[] encodePassword(String password) throws Exception {
        MessageDigest md;
        byte[] seed = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 };
        md = MessageDigest.getInstance("SHA-1");
        byte[] passwordHashStage1 = md.digest(password.getBytes("ASCII"));
        md.reset();
        byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();
        md.update(seed);
        md.update(passwordHashStage2);
        byte[] toBeXord = md.digest();
        int numToXor = toBeXord.length;
        for (int i = 0; i < numToXor; i++) {
            toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
        }
        return toBeXord;
    }

}
