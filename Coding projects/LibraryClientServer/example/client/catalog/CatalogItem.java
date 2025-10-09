package example.client.catalog;

import java.util.List;

public class CatalogItem {
    //item type, title, author, pages, summary description

    public String type;
    public String title;
    public String author; 
    public String summaryDescription;
    public List<String> usersThatHaveBorrowed;
    public String available;
    public String actualText;

    public int pages;
    public int numAvailableCopies;

    public CatalogItem() {
        // empty constructor needed for Gson
    }

    public CatalogItem(String type, String title, String author, String summaryDescription,  List<String> usersThatHaveBorrowed, int pages, String available, String actualText, int numAvailableCopies){
        this.type = type;
        this.title = title;
        this.author = author;
        this.summaryDescription = summaryDescription;
        this.usersThatHaveBorrowed = usersThatHaveBorrowed;
        this.pages = pages;
        this.available = available;
        this.actualText = actualText;
        this.numAvailableCopies = numAvailableCopies;
    }


}
