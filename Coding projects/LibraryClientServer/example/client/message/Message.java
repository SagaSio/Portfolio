package example.client.message;

public class Message {
    public String type;    // "borrow"
    public String title;   // the book title
    public String user;    // the username

    public Message(String type, String title, String user) {
        this.type = type;
        this.title = title;
        this.user = user;
    }
}
