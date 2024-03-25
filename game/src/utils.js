export default function getRotation(direction) {
    let rotation = 0;
    let scaleX = 1;
  
    switch (direction) {
      case 'up':
        rotation = 270;
        break;
      case 'down':
        rotation = 90;
        break;
      case 'left':
        rotation = 0;
        scaleX = -1; // Inverser l'image
        break;
      case 'right':
        rotation = 0;
        break;
      default:
        break;
    }
  
    return { rotation, scaleX };
  }
  