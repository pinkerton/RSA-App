import java.nio.ByteBuffer;
import java.io.UnsupportedEncodingException;

/**
 * Class containing methods used in the RSA algorithm for encryption
 * and decryption.
 * p = large prime
 * q = large prime
 * n = pq, public
 * m = the original message
 * M = message encoded as blocks of integers
 * C = cipertext
 * e = encryption exponent, relatively prime to (p-1)(q-1), public
 * d = decryption exponent, 1 / (e mod (p-1)(q-1))
 */
class RSA {

  /**
   * Encrypt the encoded message to generate the ciphertext using the equation:
   * C = M^e mod n
   */
  public static void encrypt(Message message, PublicKey pub) {
    long[] m = message.getEncodedMessage();
    for (int i = 0; i < m.length; i++) {
      //System.out.println(((int) Math.pow(m[i], pub.e))); => 2147483647 (max int)
      m[i] = ((long) Math.pow(m[i], pub.e)) % pub.n;
      //System.out.println("New: " + m[i]); => suggests that this might be working?
    }
    message.setEncodedMessage(m);
  }

  /**
   * Decrypt the ciphertext into an encoded message using the equation:
   * m = C^d mod n
   */
  public static void decrypt(Message message, PrivateKey priv) {
    long[] m = message.getEncodedMessage();
    for (int i = 0; i < m.length; i++) {
      m[i] = ((long) Math.pow(m[i], priv.d)) % priv.n;
    }
    message.setEncodedMessage(m);
  }
}

abstract class Key {
  protected int n;
  protected int z; // Euler's totient

  public Key(int p, int q) {
    this.n = p * q;
    this.z = (p - 1) * (q - 1);
  }
}

class PublicKey extends Key {
  protected int e; // between 3 and n-l, coprime to p-1, q-1

  public PublicKey(int p, int q) {
    super(p, q);
    this.e = 7;
  }
}

class PrivateKey extends Key {
  protected int d; // computed from e, p, q

  public PrivateKey(int p, int q) {
    super(p, q);
  }
}

class Message {
  private long[] encodedMessage;

  public Message(String message) {
    this.encodedMessage = toIntArray(message);
    // http://stackoverflow.com/a/13747854
    // convert int[] to byte[] for encrypted message after applying algo
    // we might be doing this wrong => http://stackoverflow.com/q/7619058
  }

  private long[] toIntArray(String message) {
    byte[] bytes = null;
    long[] ints = null;
    try {
      bytes = message.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.out.println("Bad encoding");
    }
    ints = new long[bytes.length];

    for (int i = 0; i < bytes.length; i++) {
      ints[i] = (long) bytes[i];
    }
    return ints;
  }

  public long[] getEncodedMessage() {
    return encodedMessage;
  }

  public void setEncodedMessage(long[] encodedMessage) {
    this.encodedMessage = encodedMessage;
  }

  public String toString() {
    String s = "";
    for (long i : encodedMessage) {
      s += (i + "\t" + (char)i + "\n");
    }
    return s;
  }
}

public class Tester {
  public static void main(String[] args) {
    int p = 3;
    int q = 11;
    PublicKey pub = new PublicKey(p, q);
    PrivateKey priv = new PrivateKey(p, q);

    Message message = new Message("Cryptography is fun!");
    System.out.println(message);

    RSA.encrypt(message, pub);
    System.out.println(message);
  }
}
