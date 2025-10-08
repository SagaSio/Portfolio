import { resolvePromise } from "/src/resolvePromise.js";
import { searchBooks } from "/src/bookSource.js";
import { auth, loginName, provider, signInWithRedirect, getRedirectResult, signInWithPopup, signOut, onAuthStateChanged, login, logout } from "./firebaseModel.js"; // Ensure correct relative path
import { set } from "firebase/database";

export const model = {
    searchParams: {},
    searchResults: {},
    searchResultsPromiseState: {},
    currentBook: null,
    currentBookPromiseState: {},
    bookShelf: {},
    
    auth, 
    getLoginName(){
        return loginName
    },
    setSearchQuery(query){
        this.searchParams.query = query;
    },

    doSearch(params){
        resolvePromise(searchBooks(params), this.searchResultsPromiseState);
    },
    login,
    logout,

    setCurrentBook(key){
        if (key && this.currentBook!=key){
            this.currentBook = key
            resolvePromise(searchBooks(this.currentBook), this.currentBookPromiseState);
        }
    },
};
