
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
export const questionList = [
    {
        id: 1,
        title: '问题1',
        content:'12345678901234567890'
    },
    {
        id: 2,
        title: '问题2',
        content:'12345678901234567890'
    },
    {
        id: 3,
        title: '问题3',
        content:'12345678901234567890'
    },
    {
        id: 4,
        title: '问题4',
        content:'12345678901234567890'
    },
    {
        id: 5,
        title: '问题5',
        content:'12345678901234567890'
    },
    {
        id: 6,
        title: '问题6',
        content:'12345678901234567890'
    },
    {
        id: 7,
        title: '问题7',
        content:'12345678901234567890'
    },
    {
        id: 8,
        title: '问题8',
        content:'12345678901234567890'
    },


];

export const answerList = [
    {
        id: 1,
        title: '回答1',
        content:'12345678901234567890'
    },
    {
        id: 2,
        title: '回答2',
        content:'12345678901234567890'
    },
    {
        id: 3,
        title: '回答3',
        content:'12345678901234567890'
    },
    {
        id: 4,
        title: '回答4',
        content:'12345678901234567890'
    },
    {
        id: 5,
        title: '回答5',
        content:'12345678901234567890'
    },
    {
        id: 6,
        title: '回答6',
        content:'12345678901234567890'
    },

];

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