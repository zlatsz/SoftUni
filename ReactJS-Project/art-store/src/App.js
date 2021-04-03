import { Route, Switch } from 'react-router-dom';
import { AuthProvider } from './contexts/authentication';
import { CartProvider } from './contexts/shoppingCart';
import LandingPage from './components/Landing-page';
import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';
import AddProducts from "./components/AddProducts";
import Profiles from "./components/Profiles";
import AddPosts from "./components/AddPosts";
import Products from "./components/Products/CategoryView";
import ProductDetails from "./components/Product/ProductDetails";
import EditProduct from "./components/Product/EditProduct";
import Blog from "./components/Blog";
import PostDetails from "./components/Post/PostDetails";
import Cart from "./components/Cart/";
import './App.css';

const App = () => {
  return (
    <AuthProvider>
      <Switch>
        <Route path="/" exact component={LandingPage} />
        <Route path="/login" component={Login} />
        <Route path="/register" component={Register} />
        <CartProvider>
          <Route path="/home" exact component={Home} />
          <Route path="/add-products" exact component={AddProducts} />
          <Route path="/profiles" exact component={Profiles} />
          <Route path="/add-posts" exact component={AddPosts} />
          <Route path="/products/:category" component={Products} />
          <Route path="/product/details/:productId" exact component={ProductDetails} />
          <Route path="/product/edit/:productId" exact component={EditProduct} />
          <Route path="/blog" exact component={Blog} />
          <Route path="/blog/:postId" exact component={PostDetails} />
          <Route path="/cart/:orderId" exact component={Cart} />
        </CartProvider>
      </Switch>
    </AuthProvider>
  );
}

export default App;