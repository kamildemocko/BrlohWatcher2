package scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Scrapper {
    public String getBrlohGameStatus(String uri, String city) throws Exception {
        Map<String, String> shops = getAllShops(uri);
        String requestedCity = shops.get(city);

        return (requestedCity == null) ? "" : requestedCity;
    }


    private Map<String, String> getAllShops(String uri) throws Exception {
        Connection connection = Jsoup.connect(uri).timeout(6000);
        Document document = connection.get();

        assertStatusCode(connection);

        Elements shops = document.select("div.storeInfo-item");
//        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> result = new HashMap<>();

        for (Element shop : shops){
            Element cityElement = shop.select("div.store-text>span").first();
            Element statusElement = shop.select("div.storeInfo-item-status").first();

            String city = (cityElement != null) ? cityElement.ownText() : "";
            String status = (statusElement != null) ? statusElement.ownText() : "";

            if (city.isEmpty() || status.isEmpty()) {
                continue;
            }

            result.put(city, status);
        }

        return result;
    }

    private void assertStatusCode(Connection connection) throws Exception {
        int statusCode = connection.response().statusCode();
        if (statusCode != 200) {
            throw new Exception("Status code other than 200: " + statusCode);
        }
    }
}
