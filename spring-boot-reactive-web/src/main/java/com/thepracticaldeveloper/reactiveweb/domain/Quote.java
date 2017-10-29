package com.thepracticaldeveloper.reactiveweb.domain;

public final class Quote {

    private String id;
    private String book;
    private String content;

    // Empty constructor is needed for Jackson to deserialize JSON
    public Quote() {
    }

    public Quote(String id, String book, String content) {
        this.id = id;
        this.book = book;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getContent() {
        return content;
    }

}
