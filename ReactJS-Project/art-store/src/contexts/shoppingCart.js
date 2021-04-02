import React, { useEffect, useState, useContext} from "react";
import * as userService from '../services/userService';
import { AuthContext } from './authentication';


export const CartContext = React.createContext({
    cart: [],
});

export const CartProvider = ({ children }) => {

    const { currentUser } = useContext(AuthContext);
    const [cart, setCart] = useState([]);

    useEffect(() => {
      if(currentUser){
        userService.create(currentUser.email)
        .then(res => setCart(res));
      }
      }, [currentUser]);
  
    return (
      <CartContext.Provider
        value={{
            cart
        }}
      >
        {children}
      </CartContext.Provider>
    );
  };