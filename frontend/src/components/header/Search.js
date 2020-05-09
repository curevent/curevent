import React, {useState} from "react";
import SearchMenu from "./SearchMenu";

export const Search = () => {

    const [search, setSearch] = useState();

    const [menuVisible, setMenuVisible] = useState(false);

    const toggleMenu = (event) => {
        event.preventDefault();
        setMenuVisible(prev => (!prev));
    };

    const submitHandler = (event) => {
        event.preventDefault();
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
            {menuVisible && <SearchMenu/>}
        </div>
    );
};
