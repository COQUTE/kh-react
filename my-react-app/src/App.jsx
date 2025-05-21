import './App.css'
import {useState} from "react";
import Exam2 from "./components/Exam2.jsx";
import Exam3 from "./components/Exam3.jsx";
import Exam4 from "./components/Exam4.jsx";
import Exam5 from "./components/Exam5.jsx";

function App() {
  // 상태(state)
  const [showExam, setShowExam] = useState(true);

  return (
    // js 주석
    // <></> : fragment (html 역할 X)

    /*
    jsx 주석
    <>
      <h1>Hello World</h1>
      <h1>Hello World</h1>
    </>
     */

    // <>
    //   <button onClick={() => setShowExam(!showExam)}>클릭</button>
    //
    //   {/* showExam이 true면 화면에 Exam1 컴포넌트 호출하여 렌더링함 */}
    //   {showExam && <Exam2 jiho={"hello"} test={"world"} />}
    // </>

    // <Exam3/>
    // <Exam4/>
    <Exam5/>
  );
}

export default App
