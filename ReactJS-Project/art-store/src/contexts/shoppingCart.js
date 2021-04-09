import React, { useEffect, useState, useContext } from "react";
import * as ordersService from '../services/ordersService';
import { AuthContext } from './authentication';


export const CartContext = React.createContext({
  cart: [],
});

export const CartProvider = ({ children }) => {

  const { currentUser } = useContext(AuthContext);
  const [cart, setCart] = useState([]);


  useEffect(() => {
    if (currentUser) {
      if (!currentUser.token) return;
      // if(currentUser){
      ordersService.create(currentUser.token, currentUser.email)
        .then(res => setCart(res));
      // } 
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