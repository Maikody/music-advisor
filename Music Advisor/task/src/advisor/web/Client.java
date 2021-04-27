package advisor.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.Version.*;

public class Client {

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HTTP_1_1)
            .build();

    public HttpResponse<String> createGETCategoriesRequest(String apiPath, String accessToken) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiPath + "/v1/browse/categories"))
                .GET()
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> createGETPlaylistRequest(String apiPath, String categoryId, String accessToken) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiPath + "/v1/browse/categories/" + categoryId + "/playlists"))
                .GET()
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> createGETNewReleasesRequest(String apiPath, String accessToken) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiPath + "/v1/browse/new-releases"))
                .GET()
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> createGETFeaturedPlaylistsRequest(String apiPath, String accessToken) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(apiPath + "/v1/browse/featured-playlists"))
                .GET()
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> createAccessTokenRequest(String serverAddress, String authorizationCode) throws IOException, InterruptedException {
        System.out.println("making request for access_token...");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(serverAddress + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code"
                        + "&" + authorizationCode
                        + "&redirect_uri=http://localhost:8080"
                        + "&client_id=cd5b45f393874a23aeea3fba39a80547"
                        + "&client_secret=1062d03e4528423a8108a024a3eb4bc1"))
                .build();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
