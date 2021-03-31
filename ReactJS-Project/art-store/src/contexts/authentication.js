import React, { useEffect, useState } from "react";
import firebase from "../firebase";
import * as adminService from "../services/adminService";

export const AuthContext = React.createContext();

export const AuthProvider = ({ children }) => {

  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    firebase.auth().onAuthStateChanged(async (user) => {
      if (user) {
        const isAdmin = adminService.adminCheck(user);
        user['isAdmin'] = isAdmin;
      }
      setCurrentUser(user)
    });
  }, []);



  return (
    <AuthContext.Provider
      value={{
        currentUser
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};