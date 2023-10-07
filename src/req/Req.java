package req;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Req {
    public static void sendNotification(String message, String link) {
        try {
            final URI NTFY_URI = new URI("https://ntfy.sh/dkdkdk12345");

            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(NTFY_URI)
                    .POST(HttpRequest.BodyPublishers.ofString(message))
                    .header("Content-type", "text/plain")
                    .header("Action", "view, Open link, " + link)
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.discarding());

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
