package org.acme;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/game-server")
public class GameServer {
    static final Set<Session> sessions = new HashSet<>();
    static GameLogic game;
    static Logger logger = Logger.getLogger(GameServer.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("New connection: " + session.getId());
        if (game == null) {
            game = new GameLogic();
        }
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        processMessage(message);
    }




    @OnClose
    public void onClose(Session session) {

        sessions.remove(session);
    }


    void processMessage(String message) {

        String[] parts = message.split(":");
        String action = parts[0];

        switch (action) {
            case "moveLeft":
                game.generatedMovePlayer(Direction.GAUCHE);
                break;
            case "moveRight":
                game.generatedMovePlayer(Direction.DROITE);
                break;
            case "moveUp":
                game.generatedMovePlayer(Direction.AVANT);
                break;
            case "moveDown":
                game.generatedMovePlayer(Direction.ARRIERE);
                break;
            case "reload":
                game.resetGame();
                break;
            case "start":
                game.startGame();
                startBroadcasting();
                break;

            default:
                //invoke method only conditionally
                if (logger.isLoggable(Level.WARNING))
                {
                    logger.warning(String.format("Unknown action: %s", action));
                }

        }

        game.checkForCoins(); // Vérifier les pièces après le déplacement
        updateGameState();
    }

    void startBroadcasting() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                broadcastTimeLeft();
            }
        }, 0, 1000); // Mettre à jour le temps toutes les secondes
    }

    private void updateGameState() {
        broadcastGameState();
    }


    void broadcastGameState() {
        List<Coin> coins = game.getCoins(); // Obtenez les pièces du jeu

        if (coins != null) // Vérifiez si la liste de pièces est initialisée
        {
            boolean gameOver = game.getRemainingTime() == 0 && !coins.isEmpty();
            boolean win = coins.isEmpty();
            StringBuilder gameState = new StringBuilder("gameState:" + game.getPlayer().getX() + ":" + game.getPlayer().getY() + ":" + game.getPlayer().getLives() + ":" + game.getPlayer().getScore() + ":" + game.getRemainingTime() + ":" + gameOver + ":" + win + ":");

            // Ajouter les informations sur les pièces
            for (Coin coin : coins) {
                gameState.append(coin.getX()).append(":").append(coin.getY()).append(":");
            }

            sessions.forEach(session -> {
                RemoteEndpoint.Async asyncRemote = session.getAsyncRemote(); // Appel à getAsyncRemote()
                asyncRemote.sendText(gameState.toString(), result -> {
                    if (result.getException() != null) {
                        logger.warning("Failed to send message to %s : %s".formatted(session.getId(), result.getException().getMessage()));
                    }
                });
            });
        }
    }

    void broadcastTimeLeft() {
        String message = "timeLeft:" +  game.getRemainingTime();

        sessions.forEach(session ->
                session.getAsyncRemote().sendText(message, result -> {
                    if (result.getException() != null) {
                        logger.warning("Failed to send message to %s : %s".formatted(session.getId(), result.getException().getMessage()));
                    }
                })
        );
    }




}
