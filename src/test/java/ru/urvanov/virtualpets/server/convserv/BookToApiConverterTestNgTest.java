package ru.urvanov.virtualpets.server.convserv;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ru.urvanov.virtualpets.server.dao.domain.Book;


/**
 * Тесты для {@link BookToApiConverter}.
 */
public class BookToApiConverterTestNgTest {

    private static final float HIDDEN_OBJECTS_GAME_DROP_RATE = 0;
    private static final int BOOKCASE_ORDER = 0;
    private static final int BOOKCASE_LEVEL = 1;
    private static final String BOOK_ID = "DESTINY_BOOK";

    /**
     * Простейший пример теста TestNG.
     */
    @Test
    void convert() {
        // Экземпляр тестируемого класса
        BookToApiConverter converter = new BookToApiConverter();
        
        // Подготовка тестовых данных
        Book source = new Book(
                BOOK_ID,
                BOOKCASE_LEVEL,
                BOOKCASE_ORDER,
                HIDDEN_OBJECTS_GAME_DROP_RATE);
        
        // Подготовка ожидаемого результата
        var expected = new ru.urvanov.virtualpets.server.controller.api
                .domain.Book(
                        BOOK_ID,
                        BOOKCASE_LEVEL,
                        BOOKCASE_ORDER);
        
        // Вызов тестируемого метода
        var actual = converter.convert(source);
        
        // Проверка результата
        assertEquals(expected,  actual);
    }

}
