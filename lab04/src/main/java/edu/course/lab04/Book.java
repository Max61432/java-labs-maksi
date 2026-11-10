package edu.course.lab04;

import java.util.Objects;

public final class Book {
    private final String isbn;
    private final String title;

    public Book(String isbn, String title) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN не может быть пустым");
        }
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // проверка на идентичность
        if (o == null || getClass() != o.getClass()) return false; // проверка на null и тип
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn); // сравниваем ТОЛЬКО isbn
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn); // хеш тоже только по isbn
    }
}