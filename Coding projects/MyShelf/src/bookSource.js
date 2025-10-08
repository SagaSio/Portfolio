export function searchBooks(searchParams){

    const urlParams = new URLSearchParams();

    if (searchParams) {
        urlParams.append("q", searchParams);
    }

    const url = "https://openlibrary.org/search.json?" + urlParams;

    function gotResponseACB(response){
        if (!response.ok){
            throw new Error("Error! Status: " + response.status)
        }
        return response.json();
    }

    function returnResultACB(data){
        console.log(data)
        return data
    }

    function handleErrorACB(error){
        console.error("Error fetching the data:", error);
    }

    return fetch(url).then(gotResponseACB).then(returnResultACB).catch(handleErrorACB)
}


