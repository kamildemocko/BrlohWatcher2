import req.Req;
import scrapper.Scrapper;

import java.io.IOException;


public class Main {
    static String outOfStockStatus = "Nie je skladom";

    public static void main(String[] args) {
        Req.sendNotification("Jedi - Survivor is now available", "https://www.brloh.sk/star-wars-jedi-survivor-ps5-p43506");

        String myCity = "Košice - Atrium Optima";

        // https://www.brloh.sk/star-wars-jedi-survivor-ps5-p43506
        String jedi_survivor = "https://www.brloh.sk/Products/Product/StoreInfoItems?productId=43506&productImeiId=null&query=&latitude=null&longitude=null&inStock=false";
        boolean jedi_survivor_available = checkOnCity(jedi_survivor, myCity);
        if (jedi_survivor_available) {
            Req.sendNotification("Jedi - Survivor is now available", "https://www.brloh.sk/star-wars-jedi-survivor-ps5-p43506");
        }

        // https://www.brloh.sk/gran-turismo-7-ps5-p23717
        String gran_turismo7 = "https://www.brloh.sk/Products/Product/StoreInfoItems?productId=23717&productImeiId=null&query=&latitude=null&longitude=null&inStock=false";
        boolean grand_turismo7_available = checkOnCity(gran_turismo7, myCity);
        if (grand_turismo7_available) {
            Req.sendNotification("Gran turismo 7 is now available", "https://www.brloh.sk/gran-turismo-7-ps5-p23717");
        }
    }

    private static boolean checkOnCity(String uri, String checkedCity) {
        Scrapper scrapper = new Scrapper();

        try {
            String status = scrapper.getBrlohGameStatus(uri, checkedCity);

            if (!status.equals(outOfStockStatus)) {
                return true;
            }

        } catch (IOException ex) {
            System.out.printf("Error while getting status: %s%n", ex);

        } catch (Exception ex) {
            System.out.printf(ex.toString());
        }

        return false;
    }
}