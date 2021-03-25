import { Component } from 'react';
import { Route, Switch } from 'react-router-dom';

import LandingPage from './components/Landing-page';
import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';
import AddProducts from "./components/AddProducts";
import Profiles from "./components/Profiles";
import AddPosts from "./components/AddPosts";
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
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <Route path="/home" exact component={Home} />
          <Route path="/add-products" exact component={AddProducts} />
          <Route path="/profiles" exact component={Profiles} />
          <Route path="/add-posts" exact component={AddPosts} />
        </Switch>
    );
  }
}

export default App;