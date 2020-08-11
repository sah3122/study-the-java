package org.example.proxy;

public class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String rent() {
        return title;
    }
}
