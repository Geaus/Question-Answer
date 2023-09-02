
import React, {useEffect, useReducer} from 'react';
import {Button, DatePicker, Space} from 'antd';
import "./App.css"
import HomeView from "./view/HomeView/HomeView";
import ProfileView from "./view/ProfileView/ProfileView";
import {Route, Routes} from "react-router";
import {BrowserRouter} from "react-router-dom";
import QuestionDetailView from "./view/QuestionDetailView/QuestionDetailView";
import LoginView from "./view/LoginView/LoginView";
import Answer from "./components/QuestionDetailView/Editor/Editor";

// export const URL='http://localhost:8080';
export const URL='http://localhost/api';

class App extends React.Component {

    render() {

        return (

            <BrowserRouter>
                <Routes>

                    <Route path="/" element={<HomeView />} />
                    <Route path="/ques" element={<QuestionDetailView />} />
                    <Route path="/profile" element={<ProfileView />} />
                    <Route path="/login" element={<LoginView/>}/>

                </Routes>
            </BrowserRouter>


        );
    }

}
export default App;