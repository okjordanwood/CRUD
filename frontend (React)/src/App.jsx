import Home from "./common/Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import GroupList from "./components/GroupList";
import GroupEdit from "./components/GroupEdit";
import logo from "./assets/react.svg";
import "./App.css";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/groups" exact={true} element={<GroupList />} />
        <Route path="/groups/:id" element={<GroupEdit />} />
      </Routes>
    </Router>
  );
};

export default App;
