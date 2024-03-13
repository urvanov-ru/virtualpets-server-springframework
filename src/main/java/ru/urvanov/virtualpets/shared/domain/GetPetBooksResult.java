package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetPetBooksResult implements Serializable {

    private static final long serialVersionUID = -5317436753255391619L;
    
    private List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    
}
