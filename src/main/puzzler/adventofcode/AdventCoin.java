package puzzler.adventofcode;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 28/03/2016
 */
public class AdventCoin {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        Scanner scanner = new Scanner(System.in);
        String hashBase = scanner.next();

        System.out.println(hashBase);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (getMD5Hash(hashBase + i)) {
                System.out.println(i);
                break;
            }
        }

    }

    public static boolean getMD5Hash(String text) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        return hashtext.length() <= 26;
    }
}
