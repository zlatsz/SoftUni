import firebase from "firebase/app";
import "firebase/auth";
import "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyDeOQ2G6uKZEWG659FSpvsTsiHwv8Td1P4",
  authDomain: "reactjs-532f1.firebaseapp.com",
  projectId: "reactjs-532f1",
  databaseURL: "https://reactjs-532f1-default-rtdb.firebaseio.com/",
  storageBucket: "reactjs-532f1.appspot.com",
  messagingSenderId: "198128184220",
  appId: "1:198128184220:web:8fcced89fa96d655deb659"
};

if(!firebase.apps.length){
firebase.initializeApp(firebaseConfig);
}

export default firebase;
// export const auth = firebase.auth();

