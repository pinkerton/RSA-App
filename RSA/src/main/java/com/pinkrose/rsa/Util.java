package com.pinkrose.rsa;

import java.math.BigInteger;

/**
 * Created by pinkerton on 11/21/13.
 */
public class Util {
    public static String numToStr(BigInteger b) {
        String s2 = "";
        int i;
        while (b.compareTo(BigInteger.ONE) > 0) {
            i = b.mod(new BigInteger("" + 256)).intValue();
            s2 = (char) i + s2;
            b = b.divide(new BigInteger("" + 256));
        }
        return s2;
    }

    public static BigInteger strToNum(String s) {
        return new BigInteger(s.getBytes());
    }
}
