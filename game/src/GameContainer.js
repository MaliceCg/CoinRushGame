import React from 'react';
import getRotation from './utils';

const GameContainer = ({ x, y, score, coins, direction }) => {
  console.log('direction', direction);
  const rotation = getRotation(direction);
  console.log('rotation', rotation);

  return (
    <div className="game-container">
      <div
        className="player"
        style={{
          left: `${x}px`,
          top: `${y}px`,
          transform: `rotate(${rotation.rotation}deg) scaleX(${rotation.scaleX})`,
        }}
      ></div>
      {coins.map((coin, index) => (
        <div key={index} className="coin" style={{ left: `${coin.x}px`, top: `${coin.y}px` }}></div>
      ))}
      
    
    </div>
  );
};

export default GameContainer;
