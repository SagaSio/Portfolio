import { BookShelfView } from "../views/bookShelfView.jsx";
import { SearchFormView } from "../views/searchFormView.jsx";
import { SearchResultsView } from "../views/searchResultsView.jsx";
import { observer } from "mobx-react-lite";



export const Search = observer(
    function SearchRender(props){
        
        
        function setSearchTextACB(theSearchQuery){
            props.model.setSearchQuery(theSearchQuery)
        }

        function searchNowACB(){
            props.model.doSearch(props.model.searchParams.query)
            console.log("the query: " + props.model.searchParams.query)
        }



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

                function seeBookDetailsACB(key){
                    props.model.setCurrentBook(key)
                }

                return <SearchResultsView
                    searchResults = {state.data || []}
                    onSeeBookDetails = {seeBookDetailsACB}
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
                return ""
            }
    
        }


        return <div><SearchFormView 
        text = {props.model.searchParams.query}
        onUserInput = {setSearchTextACB}
        onBookSearch = {searchNowACB}
        />
            <div className = "searchResultsContainer">
                {determineTheRender(props.model.searchResultsPromiseState)}
            </div>
            <BookShelfView className = "shelfBase"/>
        </div>
    }
)