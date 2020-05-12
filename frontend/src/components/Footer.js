import {connect} from "react-redux";
import React from "react";
import '../css/footer.css'

const Footer = ({navStatus}) => {

    return (
        <div className="footer-container">
            {navStatus.page}
        </div>
    );
};

const mapStateToProps = state => {
    return {
        navStatus: state.nav.status,
    }
};

export default connect(mapStateToProps, null)(Footer);
