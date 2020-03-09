import React, { Component } from 'react';

class Search extends Component {
    render() {
        return (
            <div className="search-container">
                <input className="search-input" type="text"/>
                <div className="search-img"/>
            </div>
        );
    }
}

export default Search;