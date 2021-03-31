import { Component } from 'react';
import firebase from '../../firebase';

class Logout extends Component {
    componentDidMount() {
        firebase.auth().signOut()
        this.props.history.push('/')
      }
}

export default Logout