package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.NoResultException;
import ru.urvanov.virtualpets.server.dao.MachineWithDrinksDao;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

public class MachineWithDrinksDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private MachineWithDrinksDao machineWithDrinksDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind1() {
        MachineWithDrinks drink = machineWithDrinksDao.findById(1)
                .orElseThrow();
        assertNotNull(drink);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind2() {
        MachineWithDrinks drink = machineWithDrinksDao.findFullById(1)
                .orElseThrow();
        assertNotNull(drink);
        assertNotNull(drink.getMachineWithDrinksCost());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind3() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(-1);
        assertTrue(drink.isEmpty());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind4() {
        Optional<MachineWithDrinks> machineWithDrinks =  machineWithDrinksDao.findFullById(-1);
        assertTrue(machineWithDrinks.isEmpty());
    }
}
