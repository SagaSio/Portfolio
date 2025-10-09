package example.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import example.client.catalog.CatalogItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;

import java.util.List;

import example.client.message.Message;

import javafx.scene.media.AudioClip;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ClientGUI extends Application{
    //a textarea where we will display the catalog things
    //private TextArea catalogArea = new TextArea();

    private VBox catalogContainer = new VBox(); // container for catalog items

    //sound effects
    private AudioClip clickSound;
    private MediaPlayer clickPlayer;

    private Stage primaryStage;

    public static ClientGUI instance;

    //loading the clicking sound
    private void loadClickSound(){
        try{
            URL soundURL = getClass().getResource("/example/client/sounds/appear-online.wav");
            if(soundURL!=null){
                Media media = new Media(soundURL.toString());
                clickPlayer = new MediaPlayer(media);
                clickPlayer.setVolume(1.0);
            }else{
                System.out.println("Click sound not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //playing the clicking sound
    private void playClickSound(){
        if(clickPlayer!=null){
            clickPlayer.stop();
            clickPlayer.play();
        }
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        //app starts at the login screen
        showLoginScreen(primaryStage);
        
    }


    public void showLoginScreen(Stage primaryStage){
        Stage loginStage = new Stage();
        VBox loginLayout = new VBox(10);

        //for the sound effect
        loadClickSound();

        Label loginPrompt = new Label("Enter your username: ");
        TextField usernameField = new TextField();
        Button loginButton = new Button("Login");


        //pretty
        loginLayout.setStyle("-fx-background-color: MistyRose; -fx-padding: 20px;");
        loginButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        loginLayout.setSpacing(10); // space between printButton and catalogContainer


        loginButton.setOnAction( e -> {
            //sound effect
            playClickSound();

            //getting the username
            String inputUserName = usernameField.getText().trim();
            if(!inputUserName.isEmpty()){
                Client.username = inputUserName;
                loginStage.close();
                showMainMenu(primaryStage);
            }
        });

        loginLayout.getChildren().addAll(loginPrompt, usernameField, loginButton);
        Scene loginScene = new Scene(loginLayout, 300, 150);
        loginStage.setScene(loginScene);
        loginStage.setTitle("Login");
        loginStage.show();
    }


    public void showeReader(Stage primaryStage, CatalogItem item){
        Stage eReaderStage = new Stage();
        VBox eReaderLayout = new VBox(10);

        //for the sound effect
        loadClickSound();

        TextArea pages = new TextArea();
        Button returnButton = new Button("Return to catalog");
        returnButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        pages.setWrapText(true);
        pages.setEditable(false);
        pages.setText(item.actualText);
        pages.setPrefSize(400, 600);


        //pretty
        eReaderLayout.setStyle("-fx-background-color: MistyRose; -fx-padding: 20px;");
        returnButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        eReaderLayout.setSpacing(10); // space between printButton and catalogContainer


        returnButton.setOnAction( e -> {
            //sound effect
            playClickSound();
            primaryStage.close();
            eReaderStage.close();
            showMainMenu(primaryStage);
        });

        eReaderLayout.getChildren().addAll(pages, returnButton);
        Scene eReaderScene = new Scene(eReaderLayout, 400, 600);
        eReaderStage.setScene(eReaderScene);
        eReaderStage.setTitle("eReader");
        eReaderStage.show();
    }


    public void showMainMenu(Stage primaryStage){

        //initiating sound
        loadClickSound();


        instance = this;

        ScrollPane scrollPane = new ScrollPane(catalogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: MistyRose; " + // ScrollPane outer background
                        "-fx-background-color: MistyRose;"); // Inner background);

        //we don't want users to edit the catalog
        //catalogArea.setEditable(false);
        Button printButton = new Button("Get Catalog");

        Label userLabel = new Label("Logged in as " + Client.username);
        userLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Log out");
        logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        //create vbox and scene
        VBox root = new VBox(userLabel, printButton, logoutButton, scrollPane);
        Scene scene = new Scene(root, 500, 600);

        //pretty
        root.setStyle("-fx-background-color: MistyRose; -fx-padding: 20px;");
        printButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        root.setSpacing(10); // space between printButton and catalogContainer


        //setup...
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.show();

        

        try {
            new Client().setUpNetworking();
        } catch (Exception e) {
            e.printStackTrace();
        }


        printButton.setOnAction(e -> {

            //sound effect
            playClickSound();

            System.out.println("User clicked on Get Catalog");
            String json = Client.latestCatalogJson;
            System.out.println(json);
            if (json != null) {
                displayCatalog(Client.latestCatalogJson);
            } else {
                //catalogArea.setText("Waiting for catalog data from server...");
                catalogContainer.getChildren().clear();
                catalogContainer.getChildren().add(new Label("Waiting for catalog data from server..."));
            }
        });

        logoutButton.setOnAction(e -> {
            //sound effect
            playClickSound();

            System.out.println("User clicked on log out");
            Client.username = "";
            primaryStage.close();
            showLoginScreen(primaryStage);
        });
        
    }

    public void updateCatalogArea() {
        Platform.runLater(() -> {
            displayCatalog(Client.latestCatalogJson);
        });
    }

    public void displayCatalog(String json){
        //we translate the hson to a List<CatalogItem>
        Gson gson = new Gson();
        List<CatalogItem> catalog = gson.fromJson(json, new TypeToken<List<CatalogItem>>() {}.getType());

        catalogContainer.getChildren().clear();

        for (CatalogItem item : catalog){
            // Create a label for the title with larger font size
            Label titleLabel = new Label(item.title);
            titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Increase the font size of the title

            // Create a label for item details (author, description, etc.)
            String info = item.author + "\n" +
                          item.type + "\n" + 
                          "Description: " + item.summaryDescription + "\n" +
                          "Number of copies available: " + item.numAvailableCopies + "\n" +
                          "Available: " + item.available + "\n";

            Label detailLabel = new Label(info);
            detailLabel.setWrapText(true);
            detailLabel.setMaxWidth(350);

            // Spacer to push the button to the right
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            // Create a button for the item
            Button borrowButton = new Button("Borrow");
            borrowButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            
            Button returnButton = new Button("Return");
            returnButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

            Button readButton = new Button("Read");
            readButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

            HBox buttonRow = new HBox(10);
            buttonRow.setMinWidth(200);   // prevents being squeezed
            buttonRow.setPrefWidth(200); // optional
            

            
                borrowButton.setOnAction(e -> {
                    //sound effect
                    playClickSound();

                    System.out.println("User clicked on: " + item.title);
                if (Client.instance != null) {
                    Message message = new Message("borrow", item.title, Client.username);
                    
                    String jsonToServer = gson.toJson(message);
                    Client.instance.sendToServer(jsonToServer);
                } else {
                    System.out.println("Client not initialized!");
                }
                });
                buttonRow.getChildren().add(borrowButton);
            

            boolean hasBorrowed = false;

            if(item.usersThatHaveBorrowed!=null){
                for (String user : item.usersThatHaveBorrowed){
                    if(user.equals(Client.username)){
                        hasBorrowed = true;
                        break;
                    }
                }
            }

            
            if(hasBorrowed){
                returnButton.setOnAction(e -> {
                    //sound effect
                    playClickSound();

                    Message message = new Message("return", item.title, Client.username);
                    String jsonToServer = new Gson().toJson(message);
                    Client.instance.sendToServer(jsonToServer);
                });
                buttonRow.getChildren().add(returnButton);

                readButton.setOnAction(e -> {
                    //sound effect
                    playClickSound();
                    primaryStage.close();
                    showeReader(primaryStage, item);

                    
                });
                buttonRow.getChildren().add(readButton);

                readButton.setDisable(false);
            }else{
                readButton.setDisable(true);
            }

            if(item.available.equalsIgnoreCase("no")){
                borrowButton.setDisable(true);
            }
            if(item.available.equalsIgnoreCase("yes")){
                borrowButton.setDisable(false);
            }


            

            

            // Layout the label, spacer, and button
            // Layout the title, details, spacer, and button side by side
            VBox itemInfoBox = new VBox(5, titleLabel, detailLabel);
            itemInfoBox.setMaxWidth(200);
            
            HBox itemBox = new HBox(10, itemInfoBox, buttonRow);
            itemBox.setStyle("-fx-background-color: MistyRose; -fx-border-color: white; -fx-border-width: 4px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            itemBox.setPrefHeight(220);
            itemBox.setPrefWidth(480);

            catalogContainer.setSpacing(10); // space between each catalog item

            catalogContainer.getChildren().add(itemBox);
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
