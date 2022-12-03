package springTest.config.models;

import com.google.gson.Gson;

public class Book {
    public int id;
    private String title;
    private String author;
    private String description;

    public Book (int id, String title, String author, String description){
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }
   public  Book (String title, String author, String description) {
       this.title = title;
       this.author = author;
       this.description = description;
    }
    public Book() {}

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    };
}
