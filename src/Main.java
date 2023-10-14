import records.Game;
import req.Req;
import scrapper.Scrapper;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static final Game[] games = {
//            new Game(
//                    "Jedi - survivor",
//                    "https://www.brloh.sk/star-wars-jedi-survivor-ps5-p43506",
//                    "https://www.brloh.sk/Products/Product/StoreInfoItems?productId=43506&productImeiId=null&query=&latitude=null&longitude=null&inStock=false",
//                    "Košice - Atrium Optima"
//            ),
            new Game(
                    "Gran Turismo 7",
                    "https://www.brloh.sk/gran-turismo-7-ps5-p23717",
                    "https://www.brloh.sk/Products/Product/StoreInfoItems?productId=23717&productImeiId=null&query=&latitude=null&longitude=null&inStock=false",
                    "Košice - Atrium Optima"
            )
    };

    public static void main(String[] args) {
        Arrays.asList(games).forEach(game -> {
            System.out.printf("Checking game %s in city %s.\n", game.name(), game.city());
            boolean gameAvailable = checkOnCity(game.checkLink(), game.city());
            if (gameAvailable) {
                System.out.printf("Found different status for game %s\n", game.name());
                Req.sendNotification(game.name(), game.link());
            }

        });
    }

    private static boolean checkOnCity(String uri, String checkedCity) {
        final String OUT_OF_STOCK_STATUS = "Nie je skladom";

        Scrapper scrapper = new Scrapper();
        boolean returnStatus = false;

        try {
            String status = scrapper.getBrlohGameStatus(uri, checkedCity);

            if (!status.equals(OUT_OF_STOCK_STATUS)) {
                returnStatus = true;
            }

        } catch (IOException ex) {
            System.out.printf("Error while getting status: %s%n", ex);

        } catch (Exception ex) {
            System.out.printf(ex.toString());
        }

        return returnStatus;
    }
}