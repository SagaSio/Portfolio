import { MyShelfView } from '../views/myShelfView.jsx'; 
import { Search } from './searchPresenter.jsx';
import { Login } from './loginPresenter.jsx'; 
import { Book } from './bookPresenter.jsx';
import { Shelf } from './myShelfPresenter.jsx';
import { observer } from "mobx-react-lite";
import "../style.css"

import {  createHashRouter,  RouterProvider } from "react-router-dom";

const ReactRoot = observer(
    function ReactRoot(props) {

        function makeRouter(model){
            return createHashRouter([
            {
                path: "/",
                element: <Search model={props.model} />,
            },
            {
                path: "/search",
                element: <Search model={props.model} />,
            },
            {
                path: "/details",
                element: <Book model={props.model} />,
            },       
        ])
        }

        
/*
    if (props.model.user != null) {
        return (
            <div className = "background">
                <Shelf model = {props.model}/>
                <Search model={props.model} />
                <Book model={props.model} />
            </div>
        );
        
    } else {
        
        return <Login model={props.model} />
        
    }
*/



function determineTheRender(){
    return (
        <div>
            <div><Shelf model = {props.model}/></div>
            <RouterProvider router={makeRouter(props.model)}/>
        </div>
    );
    
}


if (props.model.user != null) {
    return (
        <div>{determineTheRender(props.model)}</div>
    );
    
} else {
    
    return <Login model={props.model} />
    
}
});



export { ReactRoot };
