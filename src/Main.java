import config.Filepaths;
import utils.Files;
import records.ShopData;
import req.Req;
import scrapper.Scrapper;
import utils.GeneralUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<String> games;
        Map<String, String> config = getConfig();
        if (config.isEmpty()) {
            return;
        }

        try {
            games = Files.getUrlsFromTextFile(new File(Filepaths.games_files));

        } catch (FileNotFoundException ex) {
            System.out.printf("Nastala chyba pri ziskavani dat z txt suboru: %S%n", ex);
            return;
        }

        games.forEach(gameLink -> {
            System.out.printf("Checking link %s in city %s.\n", gameLink, config.get("moje_mesto"));

            String linkId = GeneralUtils.extractIdFromLink(gameLink);
            if (linkId.isEmpty()) {
                System.out.printf("Zly link: %s, nenajdene ID%n", gameLink);
                return;
            }

            String checkGameLink = GeneralUtils.getCheckLink(linkId);

            ShopData shopCityData = getCityData(gameLink, checkGameLink, config.get("moje_mesto"));
            if (shopCityData.title().isEmpty() || shopCityData.status().isEmpty()) {
                System.out.printf("Nenajdeny obchod v meste %s, skoontroluj spravnost", config.get("moje_mesto"));
                return;
            }

            boolean gameAvailable = !shopCityData.status().equals(config.get("nie_je_skladom_web_text"));
            if (gameAvailable) {
                System.out.printf("Najdena zmena status pre hru %s\n", shopCityData.title());
                Req.sendNotification(shopCityData.title(), gameLink);
            }
            else {
                System.out.println("Hra v statuse 'Nie je skladom'");
            }
        });
    }

    private static Map<String, String> getConfig() {
        try {
            return Files.getIniFile(new File(Filepaths.config));

        } catch (FileNotFoundException ex) {
            return new HashMap<>();
        }

    }

    private static ShopData getCityData(String uriProduct, String uriShops, String requestedCity) {
        Scrapper scrapper = new Scrapper();
        ShopData result = new ShopData("", "");

        try {
            return scrapper.getOneShop(uriProduct, uriShops, requestedCity);

        } catch (IOException ex) {
            System.out.printf("Chyba ziskania status: %s%n", ex);
            return result;

        } catch (UnsupportedOperationException ex) {
            System.out.printf("Stranka nespravna alebo nedostupna: %s%n", ex);
            return result;

        } catch (Exception ex) {
            System.out.printf(ex.toString());
            return result;
        }
    }

}