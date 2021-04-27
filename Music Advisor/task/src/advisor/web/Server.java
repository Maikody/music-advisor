package advisor.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;
    public static volatile String AUTHORIZATION_CODE = "";

    public void startServer() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress("localhost",8080), 0);
            server.start();
            System.out.println("Server started at port 8080");
            createContext();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    private void createContext() {
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = exchange.getRequestURI().getQuery();
                if (response != null && response.startsWith("code")) {
                    AUTHORIZATION_CODE = response;
                    System.out.println("code received: " + AUTHORIZATION_CODE);
                    server.stop(10);
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                }
            }
        });
//        while (AUTHORIZATION_CODE.equals("")) {
//            try {
//                new Thread().sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        server.stop(1);

    }

}
