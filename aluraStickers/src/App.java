import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Fazer uma conexão HTPP e buscar os top 250 filmes
        String url = "https://api.mocki.io/v2/549a5d8b";
        URI adress = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(adress).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> moviesList = parser.parse(body);

        // Exibir e manipular os dados
        var generator = new StickersGenerator();

        for (Map<String, String> movie : moviesList) {

            String imageUrl = movie.get("image");
            String title = movie.get("title");

            InputStream inputStream = new URL(imageUrl).openStream();
            String archiveName = title + ".png";

            generator.create(inputStream, archiveName);

            System.out.println(title);
            System.out.println();
        }
    }
}
