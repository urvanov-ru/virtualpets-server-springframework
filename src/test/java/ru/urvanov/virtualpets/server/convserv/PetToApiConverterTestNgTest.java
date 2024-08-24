package ru.urvanov.virtualpets.server.convserv;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.urvanov.virtualpets.server.controller.api.domain.PetInfo;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetType;


/**
 * Тесты для {@link PetToApiConverter}.
 */
class PetToApiConverterTestNgTest {


    private static final Integer PET1_ID = 1;
    private static final String PET1_NAME = "Vasya";
    private static final PetType PET1_TYPE = PetType.CAT;
    
    private static final Integer PET2_ID = 2;
    private static final String PET2_NAME = "Котик";
    private static final PetType PET2_TYPE = PetType.CAT;

    @DataProvider
    static Object[][] convertDataProvider() {
        return new Object[][] {
            {PET1_ID, PET1_NAME, PET1_TYPE},
            {PET2_ID, PET2_NAME, PET2_TYPE}
        };
    }
    
    @Test(dataProvider = "convertDataProvider")
    
    void convert(Integer petId, String petName, PetType petType) {
        PetToApiConverter converter = new PetToApiConverter();
        
        Pet source = new Pet();
        source.setId(petId);
        source.setName(petName);
        source.setPetType(petType);
        
        var expected = new PetInfo(
                        petId,
                        petName,
                        petType);
        
        var actual = converter.convert(source);
        
        assertEquals(expected,  actual);
    }

}
