package com.pinkrose.rsa;

import java.math.BigInteger;

/**
 * Created by pinkerton on 11/20/13.
 */
public class PrivateKey extends Key {
    private BigInteger priK;
    private String key;

    PrivateKey(String priv) {
        key = priv;
        priK = Util.strToNum(priv);
    }

    PrivateKey(BigInteger priv) {
        key = Util.numToStr(priv);
        priK = (priv);
    }

    public BigInteger getPriv() {
        return priK;
    }

    public String toString() {
        return key;
    }
}
