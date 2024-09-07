package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
public class MachineWithDrinksDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private MachineWithDrinksDao machineWithDrinksDao;

    @Test
    public void testFind1() {
        MachineWithDrinks drink = machineWithDrinksDao.findById(1)
                .orElseThrow();
        assertNotNull(drink);
    }

    @Test
    public void testFind2() {
        MachineWithDrinks drink = machineWithDrinksDao.findFullById(1)
                .orElseThrow();
        assertNotNull(drink);
        assertNotNull(drink.getMachineWithDrinksCosts());
    }

    @Test
    public void testFind3() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(-1);
        assertTrue(drink.isEmpty());
    }

    @Test
    public void testFind4() {
        Optional<MachineWithDrinks> machineWithDrinks =  machineWithDrinksDao.findFullById(-1);
        assertTrue(machineWithDrinks.isEmpty());
    }
}
