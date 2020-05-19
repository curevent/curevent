import React, {useState} from "react";
import {SearchMenu} from "./SearchMenu";
import {findUser} from "../../redux/services/UserService";

export const Search = () => {

    const [users, setUsers] = useState();

    const [search, setSearch] = useState();

    const [menuVisible, setMenuVisible] = useState(false);

    const enableMenu = (event) => {
        event.preventDefault();
        setMenuVisible(true);
    };

    const disableMenu = (event) => {
        event.preventDefault();
        setMenuVisible(false);
    };

    const submitHandler = (event) => {
        event.preventDefault();
        let wordsArray = [];
        if (search !== undefined) {
            wordsArray = search.split(" ");
        }

        for (let counter = 0; counter < wordsArray.length; counter++) {
            if (wordsArray[counter] === "") {
                wordsArray.splice(counter, 1);
                counter--
            }
        }

        if (wordsArray.length === 1) {
            findUser(wordsArray[0], null, null).then(response => {
                setUsers(response);
            })
        } else if (wordsArray.length === 2) {
            findUser(null, wordsArray[0], wordsArray[1]).then(response => {
                setUsers(response);
            })
        } else {
            setUsers(null);
        }

        enableMenu(event);
    };

    const enterKeyHandler = (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            submitHandler(event);
        }
    };

    const changeInputHandler = event => {
        event.persist();
        setSearch(event.target.value);
    };

    return (
        <div className="search-container">
            <div className="search-icon" onClick={submitHandler}/>
            <input
                className="search-input"
                onKeyPress={enterKeyHandler}
                value={search}
                onChange={changeInputHandler}
                placeholder="Enter username or full name"
            />
            {menuVisible && <SearchMenu onClose={disableMenu} users={users}/>}
        </div>
    );
};
