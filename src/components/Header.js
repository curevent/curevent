import React from 'react';
import '../css/components/Header.css'
import Search from "./objects/Search";

function Header() {
    return (
        <div className="header-container">
            <div className="header-logo">Product Name</div>
            <div className="header-nav">
                <div className="header-element">Home</div>
                <div className="header-element">Panel</div>
                <div className="header-element">Social</div>
            </div>
            <Search/>
        </div>
    );
}

export default Header;