package example.server;

import example.server.message.Message;
import example.server.catalog.CatalogItem;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.reflect.TypeToken;




class Server {

    private List<CatalogItem> catalog;
    final String CATALOGLOCATION = "example/server/catalog.json";

    //lock object
    private final Object catalogLock = new Object();

    public static void main(String[] args) {
        new Server().runServer();
    }


    private void loadCatalogFromFile(){
        try{
            //creating a Gson object.
            //Gson is the library we use to convert JSON ↔ Java objects.
            //like a translator: JSON ↔ Java.
            Gson gson = new Gson();
            //reads the raw JSON content 
            FileReader reader = new FileReader(CATALOGLOCATION);
            //read this JSON and turn it into a List<CatalogItem>
            catalog = gson.fromJson(reader, new TypeToken<List<CatalogItem>>(){}.getType());
            System.out.println("Catalog loaded");
        }
        catch(Exception e){
            System.out.println("Failed to load catalog");
            catalog = new ArrayList<>();
        }
    }


    protected List<CatalogItem> getCatalog(){
        return catalog;
    }





    private void runServer() {

        loadCatalogFromFile();
        try {
            setUpNetworking();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private List<ClientHandler> handlers = new ArrayList<>();

    private void setUpNetworking() throws Exception {
        @SuppressWarnings("resource")
        ServerSocket serverSock = new ServerSocket(4242);
        while (true) {
            Socket clientSocket = serverSock.accept();
            System.out.println("Connecting to... " + clientSocket);

            ClientHandler handler = new ClientHandler(this, clientSocket);
            this.handlers.add(handler);

            Thread t = new Thread(handler);
            t.start();
        }
    }

    protected void processRequest(String input) {

        System.out.println("Received from client: " + input);

        Gson gson = new Gson();
        Message message = gson.fromJson(input, Message.class);

        //When the user borrows a book
        try{

            synchronized(catalogLock){
            
            //reads the raw JSON content 
            FileReader reader = new FileReader(CATALOGLOCATION);
            //read this JSON and turn it into a List<CatalogItem>
            catalog = gson.fromJson(reader, new TypeToken<List<CatalogItem>>(){}.getType());
            reader.close();

            

            if("borrow".equalsIgnoreCase(message.type)){
                for(CatalogItem  item: catalog){
                    if(item.title.equals(message.title)){
                        item.numAvailableCopies = item.numAvailableCopies-1;
                        if(item.numAvailableCopies==0){
                            item.available="No";
                        }
                        
                        item.usersThatHaveBorrowed.add(message.user);
                        break;
                    }
                }
            }

            if("return".equalsIgnoreCase(message.type)){
                for(CatalogItem  item: catalog){
                    if(item.title.equals(message.title)){
                        item.numAvailableCopies = item.numAvailableCopies+1;
                        if(item.numAvailableCopies!=0){
                            item.available="Yes";
                        }
                        item.usersThatHaveBorrowed.remove(message.user);
                        break;
                    }
                }
            }
            

            FileWriter writer = new FileWriter(CATALOGLOCATION);
            gson.toJson(catalog, writer);
            writer.flush();
            writer.close();

            System.out.println("Catalog updated");


            //sending the updated catalog to the server

            String jsonCatalog = gson.toJson(catalog);

            for (ClientHandler handler : handlers) {
                handler.sendToClient(jsonCatalog);
            }

        }



        } catch(Exception e){

        }
        
        //String output = "Output";
        //Gson gson = new Gson();
        //Message message = gson.fromJson(input, Message.class);
        // try {            
        //     for (ClientHandler handler : handlers) {
        //         handler.sendToClient(output);
        //     }

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
