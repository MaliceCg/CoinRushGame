package org.acme;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    protected Player player;
    private List<Coin> coins;
    private int width;
    private int height;
    GameTimer gameTimer;
    SecureRandom random = new SecureRandom(); // Compliant for security-sensitive use cases

    public GameLogic() {
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
    }
    public void startGame() {
        player = new Player(0, 0);
        coins = new ArrayList<>();
        width = 1270;
        height = 520;
        gameTimer = new GameTimer(25); //
        gameTimer.start();
        generateRandomCoins(10); //
    }


    public long getRemainingTime() {
        if (gameTimer != null) { // Vérifier si gameTimer est initialisé
            return gameTimer.getRemainingTime();
        }
        return 0; // Retourner une valeur par défaut si gameTimer n'est pas initialisé
    }


    public void resetGame() {
        player = new Player(0, 0);
        coins.clear();
        generateRandomCoins(10); // Générer des pièces aléatoirement
        gameTimer.reload();
    }

    public void generateRandomCoins(int numberOfCoins) {

        for (int i = 0; i < numberOfCoins; i++) {
            int x = random.nextInt(width - 20) + 10; // Assurez-vous que les pièces ne sont pas trop près du bord
            int y = random.nextInt(height - 20) + 10;

            addCoin(x, y);
        }
    }

    public void addCoin(int x, int y) {
        Coin coin = new Coin(x, y);
        coins.add(coin);
    }

    public void checkForCoins() {
        if (coins != null) { // Vérifier si coins est initialisé
            List<Coin> coinsToRemove = new ArrayList<>();
            if (coins.isEmpty()) {
                // Le joueur a gagné
                gameTimer.stop();
                // Autres actions pour déclarer le joueur gagnant
            }

            for (Coin coin : coins) {
                if (player.getBounds().intersects(coin.getBounds())) {
                    player.setScore(player.getScore() + 50);
                    coinsToRemove.add(coin);
                }
            }

            coins.removeAll(coinsToRemove);
        }
    }


    public Player getPlayer() {
        return player;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void generatedMovePlayer(Direction direction) {
        if (player != null) {
            if (direction == Direction.GAUCHE) {
                player.moveLeft();
            }            else if (direction == Direction.DROITE) {
                player.moveRight();
            }            else if (direction == Direction.AVANT) {
                player.moveUp();
            }            else if (direction == Direction.ARRIERE) {
                player.moveDown();
            }            checkForCoins();
        }
    }
}
