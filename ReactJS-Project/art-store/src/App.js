import { Component } from 'react';
import { Route, Link, NavLink, Redirect, Switch } from 'react-router-dom';

import LandingPage from './components/Landing-page';
// import Login from './components/Login';
// import Register from './components/Register';
// import Home from './components/Home';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      authUser: null,
    };
  }

  render() {
    return (
        <Switch>
          <Route path="/" exact component={LandingPage} />
          {/* <Route path="/home" component={Home} />
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} /> */}
        </Switch>
    );
  }
}

export default App;