import { MyShelfView } from "../views/myShelfView.jsx";
import { observer } from "mobx-react-lite";

const Shelf = observer(function ShelfRender(props) {
    
    function getLoginNameACB(){
        return props.model.getLoginName()
    }
    return <MyShelfView userName = {getLoginNameACB()} />;
});

export { Shelf };
