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




// import React, { Component } from 'react';
// import {connect} from "react-redux";
// import { withGoogleMap, GoogleMap, Marker} from 'react-google-maps';
// import { compose, withProps } from "recompose"
// import { withScriptjs} from "react-google-maps"

// const MyMapComponent = compose(
//   withProps({
//     googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyCs9SeoatW4IPFxRRAPmGPAkrmnhr55dzM&libraries=geometry,drawing,places",
//     loadingElement: <div style={{ height: `100%` }} />,
//     containerElement: <div style={{ height: `400px` }} />,
//     mapElement: <div style={{ height: `100%` }} />,
//   }),
//   withScriptjs,
//   withGoogleMap
// )((props) =>
//   <GoogleMap
//     defaultZoom={8}
//     defaultCenter={{ lat: -34.397, lng: 150.644 }}
//   >
//     {props.isMarkerShown && <Marker position={{ lat: -34.397, lng: 150.644 }} onClick={props.onMarkerClick} />}
//   </GoogleMap>
// );

// class Map extends React.PureComponent {
//   state = {
//     isMarkerShown: false,
//   }

//   componentDidMount() {
//     this.delayedShowMarker()
//   }

//   delayedShowMarker = () => {
//     setTimeout(() => {
//       this.setState({ isMarkerShown: true })
//     }, 3000)
//   }

//   handleMarkerClick = () => {
//     this.setState({ isMarkerShown: false })
//     this.delayedShowMarker()
//   }

//   render() {
//     return (
//       <MyMapComponent
//         isMarkerShown={this.state.isMarkerShown}
//         onMarkerClick={this.handleMarkerClick}
//       />
//     )
//   }
// }

// const mapStateToProps = state => {
//     return {
//         events: state.events
//     }
// };

// const mapDispatchToProps = {

// };

// export default connect(mapStateToProps, mapDispatchToProps)(Map);