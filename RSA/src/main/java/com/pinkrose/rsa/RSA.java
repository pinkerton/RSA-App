package com.pinkrose.rsa;

import android.widget.EditText;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by pinkerton on 11/20/13.
 */
public class RSA {
    private static PublicKey pubKey;
    private static PrivateKey privKey;

    public static String encrypt(EditText message) {
        if (pubKey == null) genKeys();
        if (message.getText().toString().equals("")) return "";
        BigInteger enc = Util.strToNum(message.getText().toString());
        BigInteger dec = enc.modPow(pubKey.getPub(), pubKey.getModu());
        return dec.toString();
    }

    public static String decrypt(BigInteger enc) {
        //Log.d("MEHHH",  enc.toString());
        BigInteger raw = enc.modPow(privKey.getPriv(), pubKey.getModu());
        return Util.numToStr(raw);
    }

    public static void setKeys(String pub, String priv, String max) {
        pubKey = new PublicKey(pub, max);
        privKey = new PrivateKey(priv);
    }

    public static void genKeys() {
        BigInteger prime1 = BigInteger.probablePrime(122, new Random());
        BigInteger prime2 = BigInteger.probablePrime(126, new Random());
        // Calculate n
        BigInteger max = prime1.multiply(prime2);
        // Calculate the totient
        BigInteger qui = (prime1.subtract(BigInteger.ONE)).multiply(
                (prime2.subtract(BigInteger.ONE)));
        // Public key
        BigInteger prii = new BigInteger("65537");
        // Also public key
        pubKey = new PublicKey(prii, (max));
        // Calculate d
        privKey = new PrivateKey(prii.modInverse(qui));
    }

    public static PublicKey getPubKey() {
        return pubKey;
    }

    public static PrivateKey getPrivKey() {
        return privKey;
    }
}
