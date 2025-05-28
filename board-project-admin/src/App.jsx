import './css/App.css'
import './css/Login.css'
import Login from "./components/Login.jsx";
import {AuthContext, AuthProvider} from "./components/AuthContext.jsx";
import {useContext} from "react";
import DashBoard from "./components/DashBoard.jsx";
import {BrowserRouter} from "react-router-dom";

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
      { user ?
        (<div className="body-container">
          {/* BrowserRouter : React 앱애서 URL 경로에 따라
              컴포넌트를 보여줄 수 있게 해주는 라우팅 컨테이너(최상위 부모 컴포넌트)
              -> Route, Link, NavLink, useNavigate() 등 라우팅 관련 기능을 사용하려면
              모든 라우팅 요소들을 BrowserRouter 컴포넌트로 감싸야 한다*/}
          <BrowserRouter>
            <DashBoard />
          </BrowserRouter>
        </div>)
          :
        (<div className="login-section">
          <Login />
        </div>) }
    </>
  )
}

export default App
