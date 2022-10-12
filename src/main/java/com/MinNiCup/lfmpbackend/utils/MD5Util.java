package com.MinNiCup.lfmpbackend.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  MD5工具
 */
public class MD5Util {

    private static final String SALT = "*fdsvkljd&SHDFEKfEW03";

    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue + SALT).getBytes()));
    }
}
