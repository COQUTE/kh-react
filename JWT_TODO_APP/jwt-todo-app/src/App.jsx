import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MyPage, {MyPagePw} from "./components/MyPage.jsx";
import LoginPage from "./components/LoginPage.jsx";

function App() {
  
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/myPage" element={<MyPage />} />
        <Route path="/myPage/editPw" element={<MyPagePw />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
