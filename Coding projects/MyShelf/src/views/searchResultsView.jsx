import "/src/style.css"
export function SearchResultsView(props){

    for (let i = 0; i < 10; i++){
      console.log(props.searchResults.docs[i].title + " (" + props.searchResults.docs[i].author_name[0] + ")")
    }

    return (
        <div>
              <table className="searchResults">
              <tbody>
                  {  
                    props.searchResults.docs.slice(0, 10).map(searchResultsDropCB)
                  }
                </tbody>
              </table>
        </div>
    );

    function searchResultsDropCB(res){

      function clickedResultACB(){
        props.onSeeBookDetails(res.key)
        return window.location.hash="#/details"
    }

        return <tr key={res.key} className = "resultRow" onClick = {clickedResultACB}>
                <td>{res.title}</td>
                <td className = "alignRight">{res.author_name?.[0] || "Unknown Author"}</td>
             </tr>;
      }   



 
}