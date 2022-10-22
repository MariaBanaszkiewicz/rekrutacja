package pl.akai;

public class Book {

    private String id;
    private String title;
    private String author;
    private Float rating;

    public Book(String i, String t, String a, Float r) {
        id = i;
        title = t;
        author = a;
        rating = r;
    }

    public String getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Float getRating() {
        return rating;
    }
}
