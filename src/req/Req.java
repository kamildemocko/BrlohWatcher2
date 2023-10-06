package req;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Req {
    public static void sendNotification(String message, String link) {
        try {
            URI uri = new URI("https://ntfy.sh/dkdkdk12345");

            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(message))
                    .header("Content-type", "text/plain")
                    .header("Action", "view, Open link, " + link)
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.out.println(response.statusCode());

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
