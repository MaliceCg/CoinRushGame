package org.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testDirectionValues() {
        assertEquals("AVANT", Direction.AVANT.name());
        assertEquals("GAUCHE", Direction.GAUCHE.name());
        assertEquals("DROITE", Direction.DROITE.name());
        assertEquals("ARRIERE", Direction.ARRIERE.name());
    }

    @Test
    void testDirectionValueOf() {
        assertEquals(Direction.AVANT, Direction.valueOf("AVANT"));
        assertEquals(Direction.GAUCHE, Direction.valueOf("GAUCHE"));
        assertEquals(Direction.DROITE, Direction.valueOf("DROITE"));
        assertEquals(Direction.ARRIERE, Direction.valueOf("ARRIERE"));
    }
    @Test
    void testDirectionValueOfInvalid() {
        try {
            Direction.valueOf("INVALID");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Le comportement attendu est que l'exception soit levée
        }
    }
}