package ru.urvanov.virtualpets.server.convserv;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import ru.urvanov.virtualpets.server.dao.domain.Book;


/**
 * Тесты для {@link BookToApiConverter}.
 */
class BookToApiConverterTestNgTest {

    private static final float HIDDEN_OBJECTS_GAME_DROP_RATE = 0;
    private static final int BOOKCASE_ORDER = 0;
    private static final int BOOKCASE_LEVEL = 1;
    private static final String BOOK_ID = "DESTINY_BOOK";

    @Test
    void convert() {
        BookToApiConverter converter = new BookToApiConverter();
        
        Book source = new Book(
                BOOK_ID,
                BOOKCASE_LEVEL,
                BOOKCASE_ORDER,
                HIDDEN_OBJECTS_GAME_DROP_RATE);
        
        var expected = new ru.urvanov.virtualpets.server.controller.api
                .domain.Book(
                        BOOK_ID,
                        BOOKCASE_LEVEL,
                        BOOKCASE_ORDER);
        
        var actual = converter.convert(source);
        
        assertEquals(expected,  actual);
    }

}
