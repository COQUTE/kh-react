// 상태 끌어올리기(State lifting up) : 자식 컴포넌트의 상태를 부모에게 끌어올려 부모가 이용 가능하도록 해주는 것

// 부모 컴포넌트
import {useState} from "react";

const Exam4 = () => {

  // 상태
  // 자식 컴포넌트의 상태 id, pw를 부모로 끌어올려 작성
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  // 자식 컴포넌트의 상태 변경 함수도 부모로 끌어올려 작성
  // id 상태를 업데이트 해주는 함수
  const onChangeId = e => {
    setId(e.target.value);
  };

  // pw라는 상태를 업데이트 해주는 함수
  const onChangePw = e => {
    setPw(e.target.value);
  };

  return (
    <div>
      <Id onChangeId={onChangeId}/>
      <Pw onChangePw={onChangePw}/>
      <div>
        {/* 자식이 가진 id, pw라는 상태값을 부모 컴포넌트가 알 방법이 없음!
         -> 부모 컴포넌트로 자식의 상태, 함수를 끌어올려 사용 */}
        <button disabled={id.trim().length === 0 || pw.trim().length === 0} type="submit">Login</button>
      </div>
    </div>
  );
};

// 자식 컴포넌트 Id
const Id = ({onChangeId}) => {

  return (
      <div>
        <label>ID : </label>
        {/* props에 담긴 onChangeId 라는 key에 매핑된 value 값(함수) 사용 */}
        <input type="text" onChange={onChangeId}/>
      </div>
  );
};

// 자식 컴포넌트 Pw
const Pw = ({onChangePw}) => {

  return (
      <div>
        <label>PW : </label>
        <input type="text" onChange={onChangePw}/>
      </div>
  );
};

export default Exam4;