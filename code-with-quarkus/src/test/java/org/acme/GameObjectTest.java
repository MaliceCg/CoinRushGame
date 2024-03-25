package org.acme;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    @Test
    void testGameObjectInitialization() {
        int x = 10;
        int y = 15;
        int width = 20;
        int height = 25;

        GameObject obj = new GameObject(x, y, width, height) {
            // Classe anonyme pour contourner le caractère abstrait de GameObject
        };

        assertEquals(x, obj.getX());
        assertEquals(y, obj.getY());
        assertEquals(width, obj.getWidth());
        assertEquals(height, obj.getHeight());
    }

    @Test
    void testGameObjectSetters() {
        int x = 10;
        int y = 15;
        int width = 20;
        int height = 25;

        GameObject obj = new GameObject(0, 0, 0, 0) {
            // Classe anonyme pour contourner le caractère abstrait de GameObject
        };

        obj.setX(x);
        obj.setY(y);

        assertEquals(x, obj.getX());
        assertEquals(y, obj.getY());
    }

    @Test
    void testGameObjectGetBounds() {
        int x = 10;
        int y = 15;
        int width = 20;
        int height = 25;

        GameObject obj = new GameObject(x, y, width, height) {
            // Classe anonyme pour contourner le caractère abstrait de GameObject
        };

        Rectangle bounds = obj.getBounds();
        assertEquals(x, bounds.x);
        assertEquals(y, bounds.y);
        assertEquals(width, bounds.width);
        assertEquals(height, bounds.height);
    }
}