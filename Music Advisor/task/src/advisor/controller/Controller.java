package advisor.controller;

import advisor.Application;
import advisor.web.WebService;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Controller {
    private static final String FEATURED = "FEATURED";
    private static final String NEW = "NEW";
    private static final String CATEGORIES = "CATEGORIES";
    private static final String PLAYLISTS = "PLAYLISTS";
    private static final String AUTH = "AUTH";
    private static final String EXIT = "EXIT";

    private static final Scanner scanner = new Scanner(System.in);

    private final WebService service;
    private String accessToken;
    private boolean authorized;

    public Controller() {
        this.service = new WebService();
        this.authorized = false;
        service.startServer();
    }

    public void getUserRequest() {
        String[] requestArray = scanner.nextLine().split(" ");
        String userRequest = requestArray[0].toUpperCase();
        String categoryRequestedByUser = getCategory(requestArray);

        if (!authorized && !userRequest.equals(AUTH) && !userRequest.equals(EXIT)) {
            System.out.println("Please, provide access for application.");
            return;
        }

        switch (userRequest) {
            case AUTH:
                authorized = service.authorizeAccess();
                accessToken = service.getAccessToken();
                break;
            case NEW:
                service.getNewReleases(Application.SPOTIFY_API_PATH, accessToken);
                break;
            case FEATURED:
                List<String> featuredPlaylists = service.getFeaturedPlaylists(Application.SPOTIFY_API_PATH, accessToken);
                printPaginatedResult(featuredPlaylists);
                break;
            case CATEGORIES:
                service.getCategories(Application.SPOTIFY_API_PATH, accessToken);
                break;
            case PLAYLISTS:
                service.getCategoriesPlaylists(Application.SPOTIFY_API_PATH, categoryRequestedByUser, accessToken);
                break;
            case EXIT:
                System.out.println("---GOODBYE!---");
                System.exit(0);
                break;
        }
    }

    private static String getCategory(String[] requestArray) {
        StringBuilder category = new StringBuilder();

        for (int i = 1; i < requestArray.length; i++) {
            category.append(requestArray[i].toLowerCase());
            category.append(" ");
        }

        return category.toString().trim();
    }

    private void printPaginatedResult(List<String> results) {
        int numberOfAllPages = results.size() / Application.PAGINATION_SIZE;
        int pageCounter = 1;

        List<List<String>> listOfLists = getListOfLists(results);
        ListIterator<List<String>> iterator = listOfLists.listIterator();

        iterator.next().forEach(System.out::println);
        System.out.println("---PAGE " + pageCounter + " OF " + numberOfAllPages + "---");

        String moveDirection = scanner.nextLine();
        boolean goingForward = true;

        while (moveDirection.equalsIgnoreCase("PREV") || moveDirection.equalsIgnoreCase("NEXT")) {
            switch (moveDirection.toUpperCase()) {
                case "NEXT":
                    if (iterator.hasNext()) {
                        pageCounter = pageCounter == numberOfAllPages ? pageCounter : ++pageCounter;
                        if (goingForward) {
                            iterator.next().forEach(System.out::println);
                            System.out.println("---PAGE " + pageCounter + " OF " + numberOfAllPages + "---");
                        } else {
                            iterator.next();
                            if (iterator.hasNext()) {
                                iterator.next().forEach(System.out::println);
                                System.out.println("---PAGE " + pageCounter + " OF " + numberOfAllPages + "---");
                                goingForward = true;
                            } else {
                                System.out.println("No more pages");
                                goingForward = false;
                            }
                        }
                    } else {
                        System.out.println("No more pages");
                        goingForward = false;
                    }
                    break;
                case "PREV":
                    if (iterator.hasPrevious()) {
                        pageCounter = pageCounter == 1 ? pageCounter : --pageCounter;
                        if (goingForward) {
                            iterator.previous();
                            if (iterator.hasPrevious()) {
                                iterator.previous().forEach(System.out::println);
                                System.out.println("---PAGE " + pageCounter + " OF " + numberOfAllPages + "---");
                                goingForward = false;
                            } else {
                                System.out.println("No more pages");
                                goingForward = true;
                            }
                        } else {
                            iterator.previous().forEach(System.out::println);
                            System.out.println("---PAGE " + pageCounter + " OF " + numberOfAllPages + "---");
                        }
                    } else {
                        System.out.println("No more pages");
                        goingForward = true;
                    }
                    break;
            }
            moveDirection = scanner.nextLine();
        }
    }

    private List<List<String>> getListOfLists(List<String> results) {
        List<List<String>> listOfLists = new ArrayList<>();
        for (int i = 0; i < results.size(); i += Application.PAGINATION_SIZE) {
            listOfLists.add(results.subList(i, (i + Application.PAGINATION_SIZE) > results.size() ? results.size()- 1:(i + Application.PAGINATION_SIZE)));
        }
        return listOfLists;
    }

}
