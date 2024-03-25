package org.acme;

import java.time.Duration;
import java.time.Instant;

public class GameTimer {
    private Instant startTime;
    private Duration timeLimit;
    private boolean isRunning;

    public GameTimer(long seconds) {
        this.timeLimit = Duration.ofSeconds(seconds);
        this.isRunning = false;
    }

    public void start() {
        this.startTime = Instant.now();
        this.isRunning = true;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public long getRemainingTime() {
        if (!isRunning) {
            return 0;
        }
        Instant currentTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, currentTime);
        Duration remainingTime = timeLimit.minus(elapsedTime);

        return remainingTime.isNegative() ? 0 : remainingTime.getSeconds();
    }

    public void reload() {
        start();
    }
    public void stop() {
        this.isRunning = false;
    }
}
