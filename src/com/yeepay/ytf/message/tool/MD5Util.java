package com.yeepay.ytf.message.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String encrypt(String str) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("md5");
		 try {
			md.update(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        byte sign[] = md.digest();

         StringBuilder buf = new StringBuilder();
         for (int i = 0; i < sign.length; i++) {
        	  	int j = sign[i] & 0xff ;
                  if (j < 16)
                       buf.append("0");
                 buf.append(Integer.toHexString(j));

         }
		return buf.toString();
	}
}
