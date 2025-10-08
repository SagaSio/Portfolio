export function MyShelfView(props) {
    return (
      <div>
        <div className= "MyShelf">
          MyShelf
        </div>
        <div>
          Welcome {props.userName}
        </div>
      </div>
    );
  }