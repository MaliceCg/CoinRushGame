package org.acme;

public class Player extends GameObject {

    private int lives;
    private int score;

    public Player(int x, int y) {
        super(x, y, 50, 50);
        lives = 3;
        score = 0;
    }

    public void moveLeft() {
        x -= 10;
    }

    public void moveRight() {
        x += 10;
    }

    public void moveUp() {
        y-= 10;
    }

    public void moveDown() {
        y+= 10;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void takeDamage() {
        lives--;
    }
}