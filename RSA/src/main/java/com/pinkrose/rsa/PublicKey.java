package com.pinkrose.rsa;

import java.math.BigInteger;

/**
 * Created by pinkerton on 11/20/13.
 */
public class PublicKey extends Key {
    private BigInteger pub;
    private BigInteger modu;
    private String mod;

    PublicKey(BigInteger exp, BigInteger mod) {
        //key = numToStr(exp);
        this.mod = Util.numToStr(mod);
        pub = exp;
        modu = (mod);
    }

    PublicKey(String exp, String mod) {
        //key = exp;
        this.mod = (mod);
        pub = new BigInteger(exp);
        modu = Util.strToNum(mod);
    }

    public BigInteger getModu() {
        return modu;
    }

    public BigInteger getPub() {
        return pub;
    }

    public String toString() {
        return pub.toString();
    }

    public String mToString() {
        return mod;
    }
}
