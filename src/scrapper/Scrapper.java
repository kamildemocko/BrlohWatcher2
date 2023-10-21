package scrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import records.ShopData;

import java.util.HashMap;
import java.util.Map;

public class Scrapper {
    public ShopData getOneShop(String uriProduct, String uriShops, String city) throws Exception {
        Map<String, ShopData> shops = getAllShops(uriProduct, uriShops);

        if (!shops.containsKey(city)) {
            return new ShopData("", "");
        }

        return shops.get(city);
    }


    private Map<String, ShopData> getAllShops(String uriProduct, String uriShops) throws Exception {
        Map<String, ShopData> result = new HashMap<>();

        Connection connectionProdct = Jsoup.connect(uriProduct).timeout(6000);
        Connection connectionShops = Jsoup.connect(uriShops).timeout(6000);
        Document documentProduct = connectionProdct.get();
        Document documentShops = connectionShops.get();

        assertStatusCode(connectionProdct);
        assertStatusCode(connectionShops);

        Element titleElement = documentProduct.select("h1.detail-title-mobile").first();
        String title = (titleElement != null) ? titleElement.ownText() : "";
        if (title.isEmpty()) {
            throw new UnsupportedOperationException("Title not found on web page, possibly wrong webpage");
        }

        Elements shops = documentShops.select("div.storeInfo-item");
        for (Element shop : shops){
            Element cityElement = shop.select("div.store-text>span").first();
            Element statusElement = shop.select("div.storeInfo-item-status").first();

            String city = (cityElement != null) ? cityElement.ownText() : "";
            String status = (statusElement != null) ? statusElement.ownText() : "";

            if (city.isEmpty() || status.isEmpty()) {
                continue;
            }

            ShopData resList = new ShopData(title, status);
            result.put(city, resList);
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
