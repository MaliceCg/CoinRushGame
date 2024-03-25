package org.acme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    private GameTimer gameTimer;

    @BeforeEach
    void setup() {
        gameTimer = new GameTimer(10);
    }

    @Test
    void testGetRemainingTimeWhenNotRunning() {
        assertEquals(0, gameTimer.getRemainingTime());
    }

    @Test
    void testGetRemainingTimeWhenJustStarted() {
        gameTimer.start();
        assertTrue(gameTimer.getRemainingTime() >= 9);
    }

    @Test
    void testGetRemainingTimeAfterElapsedTime() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        gameTimer.start();
        Instant startTime = Instant.now();
        latch.await(5, TimeUnit.SECONDS);
        long remainingTime = gameTimer.getRemainingTime();
        assertTrue(remainingTime >= 4 && remainingTime <= 5);
        assertTrue(Duration.between(startTime, Instant.now()).getSeconds() >= 4 && Duration.between(startTime, Instant.now()).getSeconds() <= 5);
    }

    @Test
    void testGetRemainingTimeWhenTimeLimitExceeded() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        gameTimer.start();
        latch.await(11, TimeUnit.SECONDS);

        assertEquals(0, gameTimer.getRemainingTime());
    }

    @Test
    void testIsRunningWhenNotStarted() {
        assertFalse(gameTimer.isRunning());
    }

    @Test
    void testIsRunningWhenStarted() {
        gameTimer.start();
        assertTrue(gameTimer.isRunning());
    }

    @Test
    void testIsRunningWhenStopped() {
        gameTimer.start();
        gameTimer.stop();
        assertFalse(gameTimer.isRunning());
    }

    @Test
    void testReload() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        gameTimer.start();
        // Attendre 5 secondes
        latch.await(5, TimeUnit.SECONDS);
        gameTimer.reload();
        assertTrue(gameTimer.getRemainingTime() >= 9);
    }
}