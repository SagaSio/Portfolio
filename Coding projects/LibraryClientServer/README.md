## Client Server Library

This project demonstrates a client-server application built using:
- Java sockets for communication
- JavaFX for the GUI (client side)
- JSON for catalog data (via the Gson library)
- Multithreading and synchronization on the server

<pre> ```
example/
├── client/
│ ├── catalog/ // CatalogItem class
│ ├── message/ // Message class
│ ├── lib/
│ ├── sounds/
│ ├── Client.java // Handles socket communication
│ └── ClientGUI.java // JavaFX user interface
└── server/
  ├── catalog/
  ├── lib/
  ├── message/
  ├── catalog.json
  ├── Server.java // Main server logic
  └── ClientHandler.java // One per connected client
``` </pre>
  
________________________________________________________________________

## How to run

Download the two zip files and unzip them.
Start by entering the folder.

Print this in one terminal:
java -jar Server.jar

Then print this in a second terminal (first line is optional on some computers):
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/  
java -jar Client.jar

The server reads catalog.json at startup and listens on port 4242.
The client connects to the server on 127.0.0.1:4242 and starts a login screen.

You can open multiple terminals and have a client on each. The library will update in real-time. 

________________________________________________________________________

## Communication Protocol

Clients send messages as JSON-encoded Message objects, which contain:
- type: either "borrow" or "return"
- title: the title of the item
- user: the username of the logged-in client borrowing/ returning the book

All messages are handled by Server.processRequest(), which:
- Reads the latest catalog from catalog.json
- Applies the change (borrow/return) with thread-safe synchronization
- Saves the updated catalog
- Sends the updated catalog to all connected clients
________________________________________________________________________

## Thread Safety
To handle concurrent access to shared resources:
A lock (catalogLock) is used in Server.processRequest() to ensure only one thread at a time can
modify the catalog. This prevents race conditions when multiple users try to borrow the same item

________________________________________________________________________

## How to Use the Application

Launch the client application.
On startup, you will be prompted to enter your username to log in.

After logging in:
You'll see a list of library items with:
- Title
- Author
- Type
- Summary
- Availability

Click “Get Catalog” to manually refresh the catalog from the server.

If a book is available (not all copies are borrowed), you’ll see a “Borrow” button:
Clicking it will borrow the book under your username

If you've already borrowed a book, you’ll see a “Return” button instead.
Items borrowed by others will have the “Borrow” button disabled.

Click “Log Out” to return to the login screen.
________________________________________________________________________

## Features

Features Implemented:
- Persistent catalog loaded from and saved to catalog.json
- Server handles multiple clients concurrently
- Full socket-based communication between client and server
- JavaFX GUI for browsing and interacting with the catalog
- Thread-safe borrowing with synchronization
- Catalog updates broadcast to all clients in real time
- Login screen to enter a username
- Dynamic GUI with "Borrow" and "Return" buttons based on user and availability
- ScrollPane for a clean, scrollable catalog view
- Log out button that returns user to login screen
- Buttons dynamically disable/enable depending on item state
- Sound effects
- eReader that only works if a book is borrowed
- Multiple copies of the library items. The user can borrow and return more than one copy
