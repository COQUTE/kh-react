import './css/App.css'
import './css/Login.css'
import Login from "./components/Login.jsx";
import {AuthContext, AuthProvider} from "./components/AuthContext.jsx";
import {useContext} from "react";
import DashBoard from "./components/DashBoard.jsx";

// 컴포넌트 분리하여 하위 컴포넌트에서 useContext 이용하기
function App() {
  return (
      <AuthProvider>
        <AppComponent />
      </AuthProvider>
  )
}

function AppComponent() {
  // 로그인을 했다면 DashBoard 렌더링
  // 로그인을 안했다면 Login 렌더링
  // -> 조건 : 로그인 여부
  // ->       로그인을 했는지 안했는지를 기억해줄 상태값(user)
  // ->       user에는 로그인한 사람에 대한 정보가 세팅.
  // ->       user는 AuthContext 안에 있다!
  // ->       ContextAPI를 이요하여 렌더링 조건처리 하면 된다!
  const {user} = useContext(AuthContext);
  // ContextAPI 내부에 user만 사용

  return (
      <>
        {
          user ?
            (<div className="body-container">
              <DashBoard />
             </div>)
              :
            (<div className="login-section">
              <Login />
             </div>)
        }
      </>

  )
}

export default App
