import React, { Component } from 'react';
import LocationPicker from 'react-location-picker';
import {connect} from "react-redux";


/* Default position */
const defaultPosition = {
  lat: 59.57,
  lng: 30.1
};


class LocationPickerExample extends Component {
  constructor (props) {
    super(props);

    this.state = {
      position: {
         lat: 0,
         lng: 0
      }
    };
    // this.handleLocationChange = this.handleLocationChange.bind(this);
  }

  // handleLocationChange ({ position}) {
  //   this.setState({ position});
  // }handleLocationChange

  render () {
    return (
      <div>
        <div>
          <LocationPicker
            containerElement={ <div style={ {height: '70%'} } /> }
            mapElement={ <div style={ {height: '300px'} } /> }
            defaultPosition={defaultPosition}
            onChange={this.props.onChange}
          />
        </div>
      </div>
    )
  }
}


const mapStateToProps = state => {
  return {
      userInfo: state.currentUser.userInfo,
  }
};

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)(LocationPickerExample);
