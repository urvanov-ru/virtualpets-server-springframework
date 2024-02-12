/**
 * 
 */
package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

/**
 * @author fedya
 *
 */
public class GetPetBooksResult implements Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = -5317436753255391619L;
    private boolean[] books;

    public boolean[] getBooks() {
        return books;
    }

    public void setBooks(boolean[] books) {
        this.books = books;
    }
    
    

}
