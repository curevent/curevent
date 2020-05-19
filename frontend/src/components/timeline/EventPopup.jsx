import React from 'react';
import '../../css/EventPopup.css';
import Map from './gMap';



export default function EventPopup({ event, onClose }) {
  const { comments, description, duration, time, title } = event;
  

  return (
    <div className="event-popup">
      <div className="popup-body">
        <div className="popup-time">
          <span>{time}</span>
          <span>Duration: {duration}m</span>
          <button onClick={onClose}>x</button>
        </div>
        <h1>{title}</h1>
        <h3>{description}</h3>
        <div className="popup-comments">
          {comments === null && (
            <>
              <h4>Comments:</h4>
              {comments.map(({ description }, i) => (
                <p key={String(i)}>{description}</p>
              ))}
            </>
          )}
            </div>
            { (!(event.latitude === null) || !(event.longitude === null)) && (
                  <div className="popup-map">
                  <Map event={event}/>
        </div>)}
      </div>
    </div>
  );
}

