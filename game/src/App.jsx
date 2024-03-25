import React, { useEffect } from 'react';
import './App.css';
import GameContainer from './GameContainer';
import StartGame from './StartGame';
import TryAgain from './TryAgain';
import useWebSocket from './useWebSocket';

const App = () => {
  const {
    x,
    y,
    score,
    coins,
    timeLeft,
    gameOver,
    gameStarted,
    win,
    direction, // Ajoutez cette ligne
    handleStartGame,
    handleTryAgain,
    handleKeyDown,
  } = useWebSocket();

  useEffect(() => {
    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [handleKeyDown]);

  return (
    <div className="App">
    <div className='Header'>
      <h1>Coin Rush</h1>
      <div className="timer">
        Time left: {timeLeft}
      </div>
      <div className="score">
        Score: {score}
      </div>
    </div>
    <div className="divider"></div> {/* Ajoutez cette ligne */}
    {!gameStarted && <StartGame handleStartGame={handleStartGame} />}
    {gameStarted && (
      <>
        <GameContainer
          x={x}
          y={y}
          score={score}
          coins={coins}
          direction={direction} // Ajoutez cette ligne
        />
        {gameOver && <TryAgain handleTryAgain={handleTryAgain} />}
        {win && <TryAgain handleTryAgain={handleTryAgain} win={true} />}
      </>
    )}
  </div>
  );
};

export default App;
