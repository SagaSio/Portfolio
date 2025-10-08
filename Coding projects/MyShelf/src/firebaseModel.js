import { initializeApp } from "firebase/app";

/* you will find 2 imports in firebaseModel, add the configuration and instantiate the app and database: */
import {firebaseConfig} from "/src/firebaseConfig.js";
import { getDatabase, ref, set } from "firebase/database"; // Import getDatabase, ref, and set

import { getAuth, GoogleAuthProvider, onAuthStateChanged, signInWithPopup, getRedirectResult, signInWithRedirect,  signOut } from "firebase/auth";


const app = initializeApp(firebaseConfig)
const db = getDatabase(app)

/*  PATH is the “root” Firebase path. NN is your TW2_TW3 group number */
// const PATH="dinnerModel11";

// set(ref(db, PATH+"/test"), "dummy");

// Initialize Firebase Authentication
const auth = getAuth(app);
//console.log(auth);

// Initialize Google Auth Provider
const provider = new GoogleAuthProvider();


function login() {
    signInWithPopup(auth, provider).catch((error) => {
        console.error("Login error:", error);
        alert(`Login failed: ${error.message}`);
    });}

function logout() {
    signOut(auth).catch((error) => {
        console.error("Logout error:", error);
        alert(`Logout failed: ${error.message}`);
    });
}
let loginName = "";
function initializeAuthListener(model) {
    onAuthStateChanged(auth, (firebaseUser) => {
        console.log("Auth state changed:", firebaseUser);
        if (firebaseUser) {
            model.user = {
                uid: firebaseUser.uid,
                displayName: firebaseUser.displayName,
                email: firebaseUser.email,
            };
            loginName = model.user.displayName;
            console.log("loginName: " + loginName);
        } else {
            model.user = null;
        }
    });
}



export { app, db, auth, provider, loginName, onAuthStateChanged, signInWithPopup, 
    signInWithRedirect , getRedirectResult,  signOut, login, logout, initializeAuthListener };
