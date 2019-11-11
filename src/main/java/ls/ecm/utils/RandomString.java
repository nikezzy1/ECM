package ls.ecm.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;


public class RandomString {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String generate(int count, String target) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(target.charAt(RANDOM.nextInt(target.length())));
        }
        return sb.toString();
    }

    public static String generateChar(int count) {
        return generate(count, ALPHABET);
    }

    public static String generateCode(int count) {
        return generate(count, NUMBERS);
    }

    /*
    time-based uuid
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 百分之probability的可能
     */
    public static boolean getRandom(int probability) {
        int min = 1;
        int max = 100;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min < probability;
    }

    public static String generateFileName() {
        return generateUUID() + generateChar(4) + ".txt";
    }
}
