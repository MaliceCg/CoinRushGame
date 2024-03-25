import React from 'react';

const TryAgain = ({ handleTryAgain, win }) => (
  <div className={win ? 'win' : 'game-over'}>
    <h2>{win ? 'You win!' : 'Game Over!'}</h2>
    <p>{win ? 'Congratulations, you have collected all the coins!' : 'You didn\'t collect all the coins in time.'}</p>
    <button onClick={handleTryAgain}>Try Again</button>
  </div>
);

export default TryAgain;
