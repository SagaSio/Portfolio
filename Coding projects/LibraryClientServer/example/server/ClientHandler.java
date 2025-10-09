package example.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class ClientHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    protected ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            fromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            toClient = new PrintWriter(this.clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //when the client connects we use this method to send the catalog
    private void sendCatalogToClient(){
        //creates a gson instance. prettyPrinting makes it easier to read
        Gson gson = new GsonBuilder().create();
        //gets the catalog and turns that Java object into a JSON string
        String json = gson.toJson(server.getCatalog());
        //send the json to the client
        sendToClient(json);
    }



    protected void sendToClient(String string) {
        System.out.println("Sending to client: " + string);
        toClient.println(string);
        toClient.flush();
    }

    @Override
    public void run() {
        String input;
        //immediately after connecting, get the catalog
        sendCatalogToClient();
        try {
            while ((input = fromClient.readLine()) != null) {
                //System.out.println("From client: " + input);
                server.processRequest(input);
                sendCatalogToClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
