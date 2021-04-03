import React, { useEffect, useState } from "react";
import firebase from "../utils/firebase";
import * as adminService from "../services/userService";

export const AuthContext = React.createContext({ currentUser: null });

export const AuthProvider = ({ children }) => {

  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    firebase.auth().onAuthStateChanged(async (user) => {
      if (user) {
        user.getIdToken(/* forceRefresh */ true)
          .then(idToken => {
            user['token'] = idToken;
          }).catch((error) => alert(error.message));
        const isAdmin = adminService.adminCheck(user);
        user['isAdmin'] = isAdmin;
      }
      setCurrentUser(user);
      console.log(user);
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