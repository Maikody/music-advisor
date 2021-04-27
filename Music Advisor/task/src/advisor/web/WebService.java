package advisor.web;

import advisor.Application;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;
import java.util.*;

public class WebService {
    public static final String AUTHORIZATION_LINK =
            "/authorize?client_id=cd5b45f393874a23aeea3fba39a80547&redirect_uri=http://localhost:8080&response_type=code\n";

    private final Server server;
    private final Client client;
    private Map<String, String> categories;
    private String authorizationCode;
    private String accessToken;

    public WebService() {
        this.server = new Server();
        this.client = new Client();
        this.categories = new HashMap<>();
    }

    public void startServer() {
        server.startServer();
    }

    public boolean authorizeAccess() {
        getAuthorizationCode();
        return isAccessTokenAvailable();
    }

    private void getAuthorizationCode() {
        System.out.println("Use this link to request access code:");
        System.out.println(Application.SERVER_ADDRESS_PATH + AUTHORIZATION_LINK);
        System.out.println("waiting for code...");

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        authorizationCode = Server.AUTHORIZATION_CODE;

        while (authorizationCode.equals("")) {
            authorizationCode = Server.AUTHORIZATION_CODE;
        }
    }

    private boolean isAccessTokenAvailable() {
        String responseBody = sendRequestForAccessToken(Application.SERVER_ADDRESS_PATH, authorizationCode);
        JsonObject responseObject = JsonParser.parseString(responseBody).getAsJsonObject();

        System.out.println("response: \n" + responseBody);
        if (responseBody.matches(".*error.*")) {
            System.out.println("Authorization code not found. Try again.");
            return false;
        } else {
            accessToken = responseObject.get("access_token").getAsString();
            System.out.println(accessToken);
            System.out.println("Got the code. Return back to your program.");
            System.out.println("---SUCCESS---");
            return true;
        }
    }

    private String sendRequestForAccessToken(String serverAddress, String authorizationCode) {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.createAccessTokenRequest(serverAddress, authorizationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse.body();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void getCategories(String apiPath, String accessToken) {
        try {
            HttpResponse<String> httpResponse = client.createGETCategoriesRequest(apiPath, accessToken);
            JsonObject categoriesObject = getJsonObject(httpResponse,"categories");
            for (JsonElement item : categoriesObject.getAsJsonArray("items")) {
                String categoryName = item.getAsJsonObject().get("name").getAsString();
                categories.put(categoryName.toLowerCase(), item.getAsJsonObject().get("id").getAsString());
                System.out.println(categoryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNewReleases(String apiPath, String accessToken) {
        try {
            HttpResponse<String> httpResponse = client.createGETNewReleasesRequest(apiPath, accessToken);
            JsonObject albumObject = getJsonObject(httpResponse, "albums");

            for (JsonElement item : albumObject.getAsJsonArray("items")) {
                String albumName = item.getAsJsonObject().get("name").getAsString();
                List<String> artists = new ArrayList<>();
                for (JsonElement artist : item.getAsJsonObject().getAsJsonArray("artists")) {
                    artists.add(artist.getAsJsonObject().get("name").getAsString());
                }
                JsonElement externalUrls = item.getAsJsonObject().get("external_urls");
                String spotifyUri = externalUrls.getAsJsonObject().get("spotify").getAsString();
                System.out.println(albumName + "\n" + artists + "\n" + spotifyUri + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getFeaturedPlaylists(String apiPath, String accessToken) {
        HttpResponse<String> httpResponse = null;

        try {
            httpResponse = client.createGETFeaturedPlaylistsRequest(apiPath, accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject featuredObject = getJsonObject(httpResponse, "playlists");
        List<String> foundResults = new ArrayList<>();
        for (JsonElement item : featuredObject.getAsJsonArray("items")) {
            String name = item.getAsJsonObject().get("name").getAsString();
            JsonElement externalUrls = item.getAsJsonObject().get("external_urls");
            String spotifyUri = externalUrls.getAsJsonObject().get("spotify").getAsString();
            foundResults.add(name + "\n" + spotifyUri + "\n");
        }

        return foundResults;
    }

    public void getCategoriesPlaylists(String apiPath, String categoryRequestedByUser, String accessToken) {
        String categoryId = getCategoryId(categoryRequestedByUser);

        if (categoryId.equals("-1")) {
            System.out.println("Test unpredictable error message");
            return;
        }

        JsonObject playlistsObject = null;
        try {
            playlistsObject = getJsonObject(client.createGETPlaylistRequest(apiPath, categoryId.toLowerCase(), accessToken), "playlists");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (playlistsObject == null) {
                System.out.println("Test unpredictable error message");
                return;
            }
        }

        if (categories.containsKey(categoryRequestedByUser.toLowerCase())) {
            for (JsonElement item : playlistsObject.getAsJsonArray("items")) {
                String categoryName = item.getAsJsonObject().get("name").getAsString();
                JsonElement externalUrls = item.getAsJsonObject().get("external_urls");
                String spotifyUri = externalUrls.getAsJsonObject().get("spotify").getAsString();
                System.out.println(categoryName + "\n" + spotifyUri + "\n");
            }
        } else {
            System.out.println("Unknown category name.");
        }
    }

    private String getCategoryId(String categoryRequestedByUser) {
        return categories.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(categoryRequestedByUser))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse("-1");
    }

    private static JsonObject getJsonObject(HttpResponse<String> httpResponse, String resource) {
        if (httpResponse.statusCode() == 404) {
            return null;
        }
        String jsonResponse = httpResponse.body();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.getAsJsonObject(resource);
    }

}
