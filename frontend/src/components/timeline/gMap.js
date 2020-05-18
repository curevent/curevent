import React, { Component } from 'react';
import {connect} from "react-redux";
import { withGoogleMap, GoogleMap, Marker} from 'react-google-maps';

class Map extends Component {
   render() {
   const GoogleMapExample = withGoogleMap(props => (
      <GoogleMap
        defaultCenter = { { lat: this.props.event.latitude, lng: this.props.event.longitude } }
        defaultZoom = { 13 }
      >
              <Marker
      position={{ lat: this.props.event.latitude, lng: this.props.event.longitude }}
    />
      </GoogleMap>
   ));
   return(
      <div>
        <GoogleMapExample
          containerElement={ <div style={{ height: `500px`, width: '500px' }} /> }
          mapElement={ <div style={{ height: `100%` }} /> }
        />
      </div>
   );
   }
};
const mapStateToProps = state => {
    return {
        events: state.events
    }
};

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)(Map);