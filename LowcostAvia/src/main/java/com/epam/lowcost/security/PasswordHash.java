package com.epam.lowcost.security;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * Created by Виктория on 27.02.2016.
 */
public class PasswordHash {

    public static String getSecurePassword(String passwordToHash) {
        String generatedPassword = DigestUtils.md5Hex(passwordToHash);
        return generatedPassword;
    }
}
