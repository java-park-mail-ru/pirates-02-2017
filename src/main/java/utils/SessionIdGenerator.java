package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

@SuppressWarnings("FieldCanBeLocal")
public final class SessionIdGenerator {

    private final SecureRandom random = new SecureRandom();
    private final int numBits = 130;
    private final int radix = 32;

    public String nextSessionId() {
        return new BigInteger(numBits, random).toString(radix);
    }
}