package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtils {
    public static String extractIdFromLink(String link) {
        String pattern = "(?<=p)(\\d+)$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(link);

        if (!matcher.find()) {
            return "";
        }

        return matcher.group();
    }

    public static String getCheckLink(String uid) {
        final String CHECK_LINK_TEMPLATE = "https://www.brloh.sk/Products/Product/StoreInfoItems?productId=%ID%";

        return CHECK_LINK_TEMPLATE.replace("%ID%", uid);
    }
}
