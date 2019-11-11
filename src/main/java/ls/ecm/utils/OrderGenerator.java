package ls.ecm.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderGenerator {
    public static String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        return "LINGS" + formatDateTime + RandomString.generateCode(13);
    }
}
