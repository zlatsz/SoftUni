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
    console.log(currentUser);
    if (!currentUser) return;
   
    ordersService.create(currentUser.za, currentUser.email)
      .then(res => setCart(res))
      .catch((error) => alert(error.message));
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