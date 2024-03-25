package org.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerInitialization() {
        Player player = new Player(0, 0);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        assertEquals(50, player.getWidth());
        assertEquals(50, player.getHeight());
        assertEquals(3, player.getLives());
        assertEquals(0, player.getScore());
    }

    @Test
    void testPlayerMovement() {
        Player player = new Player(0, 0);
        player.moveRight();
        assertEquals(10, player.getX());
        assertEquals(0, player.getY());

        player.moveLeft();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

        player.moveUp();
        assertEquals(0, player.getX());
        assertEquals(-10, player.getY());

        player.moveDown();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void testPlayerTakeDamage() {
        Player player = new Player(0, 0);
        player.takeDamage();
        assertEquals(2, player.getLives());

        player.takeDamage();
        assertEquals(1, player.getLives());

        player.takeDamage();
        assertEquals(0, player.getLives());
    }

    @Test
    void testPlayerScore() {
        Player player = new Player(0, 0);
        player.setScore(100);
        assertEquals(100, player.getScore());
    }
    @Test
    void testPlayerLives() {
        Player player = new Player(0, 0);
        player.setLives(2);
        assertEquals(2, player.getLives());
    }

}