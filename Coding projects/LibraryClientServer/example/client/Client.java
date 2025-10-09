package example.client;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import example.client.message.Message;

class Client {

    private static String host = "127.0.0.1";
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Scanner consoleInput = new Scanner(System.in);

    //default user
    public static String username = "Guest";

    //store the latest catalog entry
    public static String latestCatalogJson = null;

    //To make your running Client accessible from anywhere
    public static Client instance;

    // public static void main(String[] args) {
    //     try {
    //         new Client().setUpNetworking();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public void setUpNetworking() throws Exception {
        //We want to access Client.instance.sendToServer(...) from anywhere,
        //so we add a static instance of the client.
        instance = this; 

        @SuppressWarnings("resource")
        Socket socket = new Socket(host, 4242);
        System.out.println("Connecting to... " + socket);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream());

        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String input;
                try {
                    while ((input = fromServer.readLine()) != null) {
                        System.out.println("From server: " + input);
                        //store the input into latestCatalogJson

                        processRequest(input);
                    }

                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // String input = consoleInput.nextLine();
                    // String[] variables = input.split(",");
                    // Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
                    // GsonBuilder builder = new GsonBuilder();
                    // Gson gson = builder.create();
                    // sendToServer(gson.toJson(request));
                }
            }
        });

        readerThread.start();
        writerThread.start();
    }

    protected void processRequest(String input) {
        // Save the JSON to a variable you can access from the GUI
        latestCatalogJson = input;

        // Notify GUI
        if (ClientGUI.instance != null) {
            ClientGUI.instance.updateCatalogArea();
        }
    }

    protected void sendToServer(String string) {
        System.out.println("Sending to server: " + string);
        toServer.println(string);
        toServer.flush();
    }

}
