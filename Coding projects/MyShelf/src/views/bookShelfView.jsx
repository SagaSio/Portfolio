import "/src/style.css"



export function BookShelfView(props){

    const bookTitles = [
        "To Kill a Mockingbird",
        "1984",
        "The Great Gatsby",
        "The Catcher in the Rye",
        "Pride and Prejudice",
        "The Lord of the Rings",
        "Harry Potter and the Sorcerer’s Stone",
        "The Hobbit",
        "Moby Dick",
        "War and Peace",
        "Jane Eyre",
        "Brave New World",
        "The Adventures of Huckleberry Finn",
        "Wuthering Heights",
        "Animal Farm",
        "Crime and Punishment",
        "The Kite Runner",
        "The Hunger Games",
        "The Da Vinci Code",
        "The Alchemist",
        "Les Misérables",
        "Fahrenheit 451"
      ];
      
    return (
        <div>
            <div className = "shelfTop">{displayBooks()}</div>
            <div className = "shelfEdge"></div>
        </div>
    )

      

    function displayBooks() {
        const books = [];
        for (let i = 0; i < 22; i++) {
            books.push(
                <div
                    key={i}
                    className="book"
                    style={{ "--book-index": i }}
                >
                    {bookTitles[i]}
                </div>
            );
        }
        return books;
    }
}