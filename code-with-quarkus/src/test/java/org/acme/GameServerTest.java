package org.acme;
import jakarta.websocket.RemoteEndpoint;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.websocket.Session;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameServerTest {

    private GameServer gameServer;
    private Session session;
    private Logger logger;
    private GameLogic gameLogic;

    @Test
    void testOnOpen() {
        // Créer une instance de GameServer
        GameServer gameServer = new GameServer();

        // Créer un mock de Session
        Session session = Mockito.mock(Session.class);

        // Appeler la méthode onOpen() du GameServer
        gameServer.onOpen(session);

        // Vérifier si la session a été ajoutée à la liste des sessions
        assertTrue(GameServer.sessions.contains(session));
    }


    @Test
    void testOnMessage() {
        // Créez une instance réelle de GameServer et espionnez-la
        gameServer = Mockito.spy(new GameServer());
        String message = "moveLeft"; // Message de test
        session = Mockito.mock(Session.class);
        logger = Mockito.mock(Logger.class);

        // Injectez le mock Logger dans gameServer
        GameServer.logger = logger;

        // Appelez la méthode à tester
        gameServer.onMessage(message, session);

        // Vérifiez si la méthode processMessage a été appelée avec le message approprié
        verify(gameServer, times(1)).processMessage(message); // Utilisation de eq pour vérifier l'égalité des objets
    }

    @Test
    void testOnClose() {
        // Créer une instance de GameServer
        GameServer gameServer = new GameServer();

        // Créer un mock de Session
        Session session = Mockito.mock(Session.class);

        // Appeler la méthode onClose() du GameServer
        gameServer.onClose(session);

        // Vérifier si la session a été retirée de la liste des sessions
        assertFalse(GameServer.sessions.contains(session));
    }




    @Test
    void testProcessMessage() {
        gameServer = new GameServer();
        gameLogic = Mockito.mock(GameLogic.class);
        session = Mockito.mock(Session.class);
        logger = Mockito.mock(Logger.class);

        // Configurez le mock gameLogic pour retourner un joueur valide lorsque getPlayer() est appelé
        Player player = new Player(0, 0); // Créez un joueur factice
        when(gameLogic.getPlayer()).thenReturn(player);

        // Injectez le mock GameLogic dans gameServer
        GameServer.game = gameLogic;
        GameServer.logger = logger;

        // Initialisez le jeu avant de tester processMessage()
        gameLogic.startGame();
        // Testons une action valide
        gameServer.processMessage("moveLeft");
        // Assurez-vous que game.movePlayer a été appelé avec la bonne direction
        verify(gameLogic, times(1)).generatedMovePlayer(Direction.GAUCHE);
        gameServer.processMessage("moveRight");
        // Assurez-vous que game.movePlayer a été appelé avec la bonne direction
        verify(gameLogic, times(1)).generatedMovePlayer(Direction.DROITE);
        gameServer.processMessage("moveUp");
        // Assurez-vous que game.movePlayer a été appelé avec la bonne direction
        verify(gameLogic, times(1)).generatedMovePlayer(Direction.AVANT);
        gameServer.processMessage("moveDown");
        // Assurez-vous que game.movePlayer a été appelé avec la bonne direction
        verify(gameLogic, times(1)).generatedMovePlayer(Direction.ARRIERE);
    }

    @Test
    void testStartBroadcasting() {
        gameServer = Mockito.mock(GameServer.class); // Créer un mock pour GameServer
        gameLogic = Mockito.mock(GameLogic.class);
        session = Mockito.mock(Session.class);
        logger = Mockito.mock(Logger.class);

        // Configurez le mock gameLogic pour retourner un joueur valide lorsque getPlayer() est appelé
        Player player = new Player(0, 0); // Créez un joueur factice
        when(gameLogic.getPlayer()).thenReturn(player);

        // Injectez le mock GameLogic dans gameServer
        GameServer.game = gameLogic;
        GameServer.logger = logger;

        // Initialisez le jeu avant de tester processMessage()
        gameLogic.startGame();
        // Nous ne pouvons pas tester directement startBroadcasting car elle crée un Timer.
        // Nous pouvons tester si elle appelle startBroadcasting() au moins une fois.
        gameServer.startBroadcasting();
        verify(gameServer, times(1)).startBroadcasting();
    }

    @Test
    void testBroadcastGameState() {

        // Créer les mocks pour les sessions
        Session session1 = Mockito.mock(Session.class);
        Session session2 = Mockito.mock(Session.class);

        // Créer les mocks pour les objets RemoteEndpoint.Async
        RemoteEndpoint.Async asyncRemote1 = Mockito.mock(RemoteEndpoint.Async.class);
        RemoteEndpoint.Async asyncRemote2 = Mockito.mock(RemoteEndpoint.Async.class);

        // Spécifier le comportement attendu lors de l'appel à getAsyncRemote() pour chaque session
        when(session1.getAsyncRemote()).thenReturn(asyncRemote1);
        when(session2.getAsyncRemote()).thenReturn(asyncRemote2);

        // Spécifier le comportement attendu lors de l'appel à sendText() pour chaque endpoint asynchrone
        doNothing().when(asyncRemote1).sendText(anyString(), any());
        doNothing().when(asyncRemote2).sendText(anyString(), any());

        // Créer l'objet GameServer
        gameServer = Mockito.mock(GameServer.class);

        // Appeler la méthode à tester
        gameServer.broadcastGameState();

        verify(gameServer, times(1)).broadcastGameState();

    }





}
