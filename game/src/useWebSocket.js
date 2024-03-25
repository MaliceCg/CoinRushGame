import { useEffect, useState } from 'react';
import { gameContainerHeight, gameContainerWidth } from './constants';

const useWebSocket = () => {
  // États
  const [socket, setSocket] = useState(null);
  const [x, setX] = useState(0);
  const [y, setY] = useState(0);
  const [score, setScore] = useState(0);
  const [lives, setLives] = useState(3);
  const [coins, setCoins] = useState([]);
  const [timeLeft, setTimeLeft] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [gameStarted, setGameStarted] = useState(false);
  const [win, setWin] = useState(false);
  const [direction, setDirection] = useState('right');

  // Gestionnaires d'événements
  const handleStartGame = () => {
    socket.send("start");
    setGameStarted(true);
  };

  const handleTryAgain = () => {
    window.location.reload();
    socket.send("reload");
  };

  const handleKeyDown = (event) => {
    if (gameOver || win || !gameStarted) {
      return;
    }

    const key = event.key;

    // Move player
    switch (key) {
      case "ArrowLeft":
        if (x > 0) {
          setDirection('left');
          socket.send("moveLeft");
        }
        break;
      case "ArrowRight":
        if (x < gameContainerWidth - 50) {
          setDirection('right');
          socket.send("moveRight");
        }
        break;
      case "ArrowUp":
        if (y > 0) {
          setDirection('up');
          socket.send("moveUp");
        }
        break;
      case "ArrowDown":
        if (y < gameContainerHeight - 50) {
          setDirection('down');
          socket.send("moveDown");
        }
        break;
      default:
        break;
    }
  };

  // useEffect pour la connexion WebSocket
  useEffect(() => {
    // Établir la connexion WebSocket
    const newSocket = new WebSocket('ws://localhost:8080/game-server');
    newSocket.onopen = () => {
      console.log('WebSocket connected');
      setSocket(newSocket);
    };

    // Gérer la réception des messages WebSocket
    newSocket.onmessage = (event) => {
      console.log('WebSocket message:', event.data);
      const data = event.data.split(':');
      if (data[0] === 'gameState') {
        const newX = parseInt(data[1]);
        const newY = parseInt(data[2]);
        const newScore = parseInt(data[4]);
        const newLives = parseInt(data[3]);
        const newTimeLeft = parseInt(data[5]);
        const newGameOver = data[6] === 'true';
        const newWin = data[7] === 'true';
        const newCoins = [];
        for (let i = 8; i < data.length; i += 2) {
          const x = parseInt(data[i]);
          const y = parseInt(data[i + 1]);
          //pour que la dernier piece disparaissent aussi
          if (!isNaN(x) && !isNaN(y)) {
            newCoins.push({ x, y });
          }
        }

        console.log(newCoins);
        setX(newX);
        setY(newY);
        setScore(newScore); // Mettre à jour l'état du score
        setLives(newLives);
        setCoins(newCoins);
        setTimeLeft(newTimeLeft);
        setGameOver(newGameOver);
        setWin(newWin);
      }
      else if (data[0] === 'timeLeft') {
        const newTimeLeft = parseInt(data[1]);
        setTimeLeft(newTimeLeft);
      }
    };

    // Gérer les erreurs WebSocket
    newSocket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    return () => {
      // Fermer la connexion WebSocket lors du démontage du composant
      if (socket) {
        socket.close();
      }
    };
  }, []);

  return {
    x,
    y,
    score,
    lives,
    coins,
    direction,
    timeLeft,
    gameOver,
    gameStarted,
    win,
    handleStartGame,
    handleTryAgain,
    handleKeyDown,
  };
};

export default useWebSocket;
