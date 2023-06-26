import './App.css';
import './css/HeaderTest.css';
import HomePage from "./view/homePage";
import ProfileView from "./view/ProfileView";
import {BrowserRouter, Route, Router, Routes} from "react-router-dom";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />}/>
          <Route path="/profile" element={<ProfileView />}/>
        </Routes>
      </BrowserRouter>
  );
}
export default App;
