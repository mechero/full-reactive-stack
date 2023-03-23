package com.thepracticaldeveloper.reactiveweb.repository.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quote")
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String book;
    private String content;

    // Empty constructor is required by the data layer and JSON de/ser
    public QuoteEntity() {
    }

    public QuoteEntity(String book, String content) {
        this.book = book;
        this.content = content;
    }

    public QuoteEntity(Long id, String book, String content) {
        this.id = id;
        this.book = book;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        QuoteEntity quote = (QuoteEntity) o;

        if (id != null ? !id.equals(quote.id) : quote.id != null)
            return false;
        if (book != null ? !book.equals(quote.book) : quote.book != null)
            return false;
        return content != null ? content.equals(quote.content) : quote.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
