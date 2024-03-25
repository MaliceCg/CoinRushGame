import React from 'react';

const StartGame = ({ handleStartGame }) => (
  <div className="start">
    <h2>Start game !</h2>
    <button onClick={handleStartGame}>Start</button>
  </div>
);

export default StartGame;
