package APIViaCEP.atv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumirCep {
    public static void main(String[] args) {

        // Identificação
        System.out.println("Aluno: Thierry Alves");
        System.out.println("RA: 0023636");
        System.out.println("-------------------------");

        String cep = "72504400";
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                    .uri(URI.create(url)) // informa endpoint para consulta
                    .GET()                // pede os dados do site
                    .build();             // finaliza o pedido

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("Erro ao consultar o CEP" + e.getMessage());
        }
    }
}
