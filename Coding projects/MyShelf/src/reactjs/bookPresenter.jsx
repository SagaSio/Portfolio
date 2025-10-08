import { BookView } from "../views/bookView.jsx";
import { observer } from "mobx-react-lite";

export const Book = observer(
    function BookRender(props){

        function determineTheRender(state){
            if (state.promise && 
                !state.data &&
                !state.error
            ){
                return <img className="loadingImage" src="https://media.giphy.com/media/WGOaDgpnLdfPSpwm69/giphy.gif?cid=ecf05e47auzwz515tn5wxhxvonca6k0isa8yhfj7h9hbjsvs&ep=v1_stickers_search&rid=giphy.gif&ct=ts" />;
            }
            if (state.promise && 
                state.data &&
                !state.error
            ){

                return <BookView
                    bookDetails = {state.data || []}
                />
            }
            if (state.promise && 
                !state.data &&
                state.error
            ){
                return <div>{state.error}</div>
            }
            if (!state.promise && 
                !state.data &&
                !state.error
            ){
                return "No data"
            }
    
        }

        return <div>
        {determineTheRender(props.model.currentBookPromiseState)}
        </div>
    }
)