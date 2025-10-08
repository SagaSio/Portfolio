import { LoginView } from "../views/loginView.jsx";
import { observer } from "mobx-react-lite";

const Login = observer(function LoginRender(props) {
  const user = props.model.auth.currentUser

  function handleAuthButtonClick() {
    if (user) {
        props.model.logout();
    } else {
        props.model.login();    }
  }

  // Determine the authentication state
  let authState; // 'loading', 'notLoggedIn', 'loggedIn'

  if (user === undefined) {
    authState = 'loading';
  } else if (user === null) {
    authState = 'notLoggedIn';
  } else {
    authState = 'loggedIn';
  }

  // Prepare data to pass to the view
  const viewProps = {
    authState: authState,
    user: user,
    onAuthButtonClick: handleAuthButtonClick,
  };

  // Return the view component with prepared data
  return <LoginView {...viewProps} />;
});

export { Login };
