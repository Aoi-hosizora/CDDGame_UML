package com.oosad.cddgame.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassUtil {

    /**
     * SHA1 加密密码
     * @param PlainPassWord
     * @return
     */
    public static String EncryptPassWord(String PlainPassWord) {
        return getSHA1(PlainPassWord);
    }

    /**
     * 判断密码是否一样
     * @param PlainPassWord
     * @param EncryptedPassWord
     * @return
     */
    public static boolean ComparePassWord(String PlainPassWord, String EncryptedPassWord) {
        return getSHA1(PlainPassWord).equals(EncryptedPassWord);
    }

    private static String getSHA1(String data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return "";
        }

        byte[] b = data.getBytes();
        md.update(b);

        byte[] b2 = md.digest();
        int len = b2.length;

        String str = "0123456789abcdef";
        char[] ch = str.toCharArray();

        char[] chs = new char[len*2];

        for(int i=0,k=0;i<len;i++) {
            byte b3 = b2[i];

            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

        return new String(chs);
    }

}
