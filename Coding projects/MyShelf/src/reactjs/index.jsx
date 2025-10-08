import { createRoot } from "react-dom/client";
import { ReactRoot } from './ReactRoot.jsx';
import { observable } from "mobx";
import { initializeAuthListener } from "../firebaseModel.js";


import { model } from '../shelfModel.js';


const reactiveModel= observable(model);

//initiating the root component
const rootJSX= <ReactRoot model={reactiveModel} />

import "/src/firebaseModel.js"
initializeAuthListener(reactiveModel);


// mount the app in the page DIV with the id "root":

createRoot(document.getElementById("root")).render(rootJSX);



