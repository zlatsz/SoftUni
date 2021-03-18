import { Component } from 'react';
import { Route, Link, NavLink, Redirect, Switch } from 'react-router-dom';

import Header from './components/landing-page/Header';
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
      <div className=".app">
        <Header />
      </div>
    );
  }
}

export default App;