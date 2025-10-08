export function LoginView(props) {
    const { authState, user, onAuthButtonClick } = props;
  
    let content;
    content = (
      <div>
        <div className = "LoginContainer">
          <div className= "MyShelfLogin">
            MyShelf
          </div>
        </div>
        <div>
          <h2 className = "LoginSubtext">Your Own Virtual Bookshelf</h2>
        </div>
        <div className="LoginButtonContainer">
          <button className = "LoginButton" onClick={onAuthButtonClick}>
            Log in with Google
          </button>
        </div>
      </div>
      );
  
    return (
      <div>
        
        <div id="app">
          {content}
        </div>
        
      </div>
    );
  }
  