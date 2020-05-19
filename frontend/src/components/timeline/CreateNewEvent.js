import React, {Fragment} from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import LocationPickerExample from "./newMap.js"
export const CreateNewEvent = ({event, changeStateHandler, onClose, submitHandler, changeDateHandler, handleLocationChange}) => {

    return (
        <Fragment>
            <div className="create-template-window">
                <div className="create-event-container">
                    <div className="ct-title">
                        <div className="ct-title-message">Create event</div>
                        <button className="close-button" onClick={onClose}/>
                    </div>
                    <div className="field">
                        <div className="template-layout">title:</div>
                        <input
                            id="title"
                            value={event.title}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">description:</div>
                        <textarea
                            id="description"
                            value={event.description}
                            className="template-input template-textarea"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">duration:</div>
                        <input
                            id="duration"
                            type="number"
                            placeholder="Time your event in minutes"
                            value={event.duration}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">date:</div>
                        <DatePicker
                            id="date"
                            selected={event.date}
                            className="template-input"
                            onChange={changeDateHandler}
                            showTimeSelect
                            dateFormat="Pp"
                        />
                    </div>
                    {/* <div className="field"> */}
                        <div className="map-popup">
                        <LocationPickerExample
                        onChange={handleLocationChange}
                        />
                        </div>
                    {/* </div> */}
                    <div className="template-window-buttons">
                        <button className="auth-button" onClick={submitHandler}>Create</button>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};
