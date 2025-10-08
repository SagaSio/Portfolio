import "/src/style.css"
export function SearchFormView(props){
    
    function inputBoxACB(evt){
        props.onUserInput(evt.target.value);
    }

    function handleKeyPressACB(evt){
        if (evt.key === "Enter") {
            props.onBookSearch();
        }
    }

    function handleSearchPressACB(){
        props.onBookSearch();
    }

    return (
        <div>
            
              <input 
                className = "searchInput"
                value = {props.text || ""} 
                placeholder = {"Book title or author"} 
                onChange = {inputBoxACB}
                onKeyDown={handleKeyPressACB}
              ></input>
              <button
                className = "searchButton"
                onClick = {handleSearchPressACB}>
                Search  
              </button>
        </div>



    );

}