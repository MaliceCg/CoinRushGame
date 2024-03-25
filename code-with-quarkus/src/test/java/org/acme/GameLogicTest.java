package org.acme;

        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    @Test
    void testStartGame() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        assertNotNull(gameLogic.getPlayer());
        assertEquals(0, gameLogic.getPlayer().getX());
        assertEquals(0, gameLogic.getPlayer().getY());
        assertEquals(10, gameLogic.getCoins().size());
        assertEquals(600, gameLogic.getWidth());
        assertEquals(400, gameLogic.getHeight());
        assertTrue(gameLogic.getGameTimer().isRunning());
    }

    @Test
    void testMovePlayer() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        gameLogic.generatedMovePlayer(Direction.DROITE);
        assertEquals(10, gameLogic.getPlayer().getX());
        assertEquals(0, gameLogic.getPlayer().getY());

        gameLogic.generatedMovePlayer(Direction.GAUCHE);
        assertEquals(0, gameLogic.getPlayer().getX());
        assertEquals(0, gameLogic.getPlayer().getY());

        gameLogic.generatedMovePlayer(Direction.AVANT);
        assertEquals(0, gameLogic.getPlayer().getX());
        assertEquals(-10, gameLogic.getPlayer().getY());

        gameLogic.generatedMovePlayer(Direction.ARRIERE);
        assertEquals(0, gameLogic.getPlayer().getX());
        assertEquals(0, gameLogic.getPlayer().getY());
    }

    @Test
    void testResetGame() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        gameLogic.getPlayer().setX(100);
        gameLogic.getPlayer().setY(100);
        gameLogic.getCoins().clear();
        gameLogic.getGameTimer().stop();

        gameLogic.resetGame();

        assertNotNull(gameLogic.getPlayer());
        assertEquals(0, gameLogic.getPlayer().getX());
        assertEquals(0, gameLogic.getPlayer().getY());
        assertEquals(10, gameLogic.getCoins().size());
        assertTrue(gameLogic.getGameTimer().isRunning());
    }

    @Test
    void testGenerateRandomCoins() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        gameLogic.getCoins().clear();
        gameLogic.generateRandomCoins(5);

        assertEquals(5, gameLogic.getCoins().size());
    }

    @Test
    void testAddCoin() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        gameLogic.getCoins().clear();
        gameLogic.addCoin(100, 100);

        assertEquals(1, gameLogic.getCoins().size());
        assertEquals(100, gameLogic.getCoins().get(0).getX());
        assertEquals(100, gameLogic.getCoins().get(0).getY());
    }

    @Test
    void testCheckForCoins() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        gameLogic.getCoins().clear();
        gameLogic.checkForCoins();

        assertFalse(gameLogic.getGameTimer().isRunning());
    }
    @Test
    void testGetRemainingTime() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        assertEquals(19, gameLogic.getRemainingTime());
    }
    @Test
    void testGetRemainingTimeWhenGameTimerIsNull() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.gameTimer = null; // Set gameTimer to null for this test case

        assertEquals(0, gameLogic.getRemainingTime());
    }

    @Test
    void testCheckForCoinsWhenPlayerCollectsCoin() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.startGame();

        // Place the player at the same position as a coin
        gameLogic.getPlayer().setX(gameLogic.getCoins().get(0).getX());
        gameLogic.getPlayer().setY(gameLogic.getCoins().get(0).getY());

        gameLogic.checkForCoins();

        // Verify that the player's score has increased and the number of coins has decreased
        assertEquals(50, gameLogic.getPlayer().getScore());
        assertEquals(9, gameLogic.getCoins().size());
    }





    @Test
    void testCheckForCoinsWhenPlayerDoesNotCollectCoin() {
        GameLogic gameLogic = new GameLogic();

        gameLogic.startGame();

        // Vérifiez que le score du joueur n'a pas changé et que le nombre de pièces reste le même
        gameLogic.checkForCoins();
        assertEquals(0, gameLogic.getPlayer().getScore());
        assertEquals(10, gameLogic.getCoins().size());
    }



}