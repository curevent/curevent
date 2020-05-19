import React, {Fragment} from "react";
import DatePicker from "react-datepicker";

export const CreateEventsByTemplate = ({template, changeStateHandler, onClose, submitHandler, changeDateHandler}) => {

    return (
        <Fragment>
            <div className="create-template-window">
                <div className="create-event-container">
                    <div className="ct-title">
                        <div className="ct-title-message">Create events by template</div>
                        <button className="close-button" onClick={onClose}/>
                    </div>
                    <div className="field">
                        <div className="template-layout">Repeat type:</div>
                        <select
                            size="1"
                            id="repeatType"
                            className="template-input"
                            onChange={changeStateHandler}
                            value={template.repeatType}
                        >
                            <option value="NONE">NONE</option>
                            <option value="DAILY">DAILY</option>
                            <option value="WEEKLY">WEEKLY</option>
                            <option value="MONTHLY">MONTHLY</option>
                            <option value="ANNUALLY">ANNUALLY</option>
                        </select>
                    </div>
                    <div className="field">
                        <div className="template-layout">Repeat interval:</div>
                        <input
                            id="repeatInterval"
                            type="number"
                            placeholder="Repeat interval for events"
                            value={template.repeatInterval}
                            className="template-input"
                            onChange={changeStateHandler}
                            autoComplete="off"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">Start date:</div>
                        <DatePicker
                            id="date"
                            selected={template.date}
                            className="template-input"
                            onChange={date => changeDateHandler(date, "start")}
                            showTimeSelect
                            dateFormat="Pp"
                        />
                    </div>
                    <div className="field">
                        <div className="template-layout">End date:</div>
                        <DatePicker
                            id="endDate"
                            selected={template.endDate}
                            className="template-input"
                            onChange={date => changeDateHandler(date, "end")}
                            showTimeSelect
                            dateFormat="Pp"
                        />
                    </div>
                    <div className="template-window-buttons">
                        <button className="auth-button" onClick={submitHandler}>Create</button>
                    </div>
                </div>
            </div>
        </Fragment>
    );
};
