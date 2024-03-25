package org.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinTest {

    @Test
        void testCoinInitialization() {
            int x = 10;
            int y = 15;
            int expectedWidth = 20;
            int expectedHeight = 20;

            Coin coin = new Coin(x, y);

            assertEquals(x, coin.getX());
            assertEquals(y, coin.getY());
            assertEquals(expectedWidth, coin.getWidth());
            assertEquals(expectedHeight, coin.getHeight());
        }
    }
