import "/src/style.css"
import{ useState } from 'react';



export function BookView(props){

    console.log(props.bookDetails.docs)

    const [isImageSmall, setIsImageSmall] = useState(false);

    const imgURL =  "https://covers.openlibrary.org/b/isbn/" + props.bookDetails.docs[0].isbn[0] + "-M.jpg" ;

    //fixa detta why are we using arrow functioooons
    function handleImageLoad(event) {
        if (event.target.naturalWidth < 10 || event.target.naturalHeight < 10) {
            setIsImageSmall(true);
        }
    }    

    const avgRating = props.bookDetails.docs[0].ratings_average.toFixed(1)

    function detailsToSearch(){
        return window.location.hash="#/search"
    }

    function addBook(){
        //add book to shelf (profile)
    }

    return (
        <div className = "detailsBox">
            <div className= "detailsTitle">
                {props.bookDetails.docs[0].title}
                <button className = "backButton" onClick={detailsToSearch}>Back</button>
                <button className = "backButton" onClick={addBook}>Add to Shelf</button>
            </div>
            <div className = "detailsContent"> 
                <div className = "coverImage"> 
                {isImageSmall ? (
                        <div className = "coverNotFound">Cover not found</div>
                    ) : (
                        <img src={imgURL} alt="Book Cover" onLoad={handleImageLoad} />
                    )}
                </div>
                <div className = "detailsText">
                    <div> Author: {props.bookDetails.docs[0].author_name[0] || ""} </div>
                    <div> ISBN: {props.bookDetails.docs[0].isbn[0] || ""} </div>
                    <div> First publish year: {props.bookDetails.docs[0].first_publish_year || ""} </div>
                    <div> Average rating: {avgRating} </div>
                    <div> Theme: {bookThemes() || ""} </div>
                </div>
            </div>
        </div>
    );


    function isCertainThemeCB(theme){
        return theme == "Fantasy" || theme == "Fiction" || theme == "Science fiction" || theme == "Adventure" || theme == "Young adult fiction" || theme == "History" || theme == "Romance" || theme == "Action" || theme == "Young adult works"
    }
    
    function bookThemes(){
        return props.bookDetails.docs[0].subject?.find(isCertainThemeCB) || ''
    }

}