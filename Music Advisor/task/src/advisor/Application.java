package advisor;

import advisor.controller.Controller;

import java.util.Scanner;

public class Application {

    private static final String SPOTIFY_ACCESS_SERVER_DEFAULT = "https://accounts.spotify.com";
    private static final String SPOTIFY_API_PATH_DEFAULT = "https://api.spotify.com";
    private static final int PAGINATION_DEFAULT_SIZE = 5;
    public static String SERVER_ADDRESS_PATH;
    public static String SPOTIFY_API_PATH;
    public static int PAGINATION_SIZE;

    private static final Controller controller = new Controller();

    public static void run(String[] args) {
        checkCLIArguments(args);

        printMenu();

        while (true) {
            controller.getUserRequest();
        }
    }

    private static void printMenu() {
        System.out.println("---WELCOME IN VIBY MUSIC ADVISOR APP---\n");
        System.out.println("Choose one of the following options:");
        System.out.println("* AUTH - to authorize your access to the app");
        System.out.println("* NEW - to print Spotify's new releases");
        System.out.println("* FEATURED - to print Spotify's featured playlists");
        System.out.println("* CATEGORIES - to print Spotify's music categories");
        System.out.println("* PLAYLISTS ... - to print Spotify's playlists of the chosen category");
        System.out.println("* EXIT - to exit the app");
    }

    private static void checkCLIArguments(String[] args) {
        SERVER_ADDRESS_PATH = getSpotifyAccessServerAddress(args);
        SPOTIFY_API_PATH = getSpotifyApiPath(args);
        PAGINATION_SIZE = getPaginationSize(args);
    }

    private static int getPaginationSize(String[] args) {
        int pageSize = PAGINATION_DEFAULT_SIZE;
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("--?page")) {
                pageSize = Integer.parseInt(args[i + 1]);
            }
        }
        return pageSize;
    }

    private static String getSpotifyAccessServerAddress(String[] args) {
        String spotifyServer = SPOTIFY_ACCESS_SERVER_DEFAULT;
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("--?access")) {
                spotifyServer = args[i + 1];
            }
        }
        return spotifyServer;
    }

    private static String getSpotifyApiPath(String[] args) {
        String spotifyApi = SPOTIFY_API_PATH_DEFAULT;
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("--?resource")) {
                spotifyApi = args[i + 1];
            }
        }
        return spotifyApi;
    }
}
