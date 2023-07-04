import React, {useEffect} from 'react';
import { Layout, Card, Button, List } from 'antd';
import { UserOutlined, SolutionOutlined } from '@ant-design/icons';
import {questionList} from '../App'
import HeaderTest from "../components/HomeView/HeaderTest";
import QuestionCard from "../components/HomeView/QuestionItem";
import UserCard from "../components/HomeView/UserCard";
import QuestionItem from "../components/HomeView/QuestionItem";
import QuestionList from "../components/HomeView/QuestionList";
import {getQuestions} from "../service/QuestionService";
import { useNavigate } from "react-router-dom";
const { Header, Content, Footer } = Layout;

const HomeView = () => {


    const navigate=useNavigate();

    useEffect(() => {

       const uid=sessionStorage.getItem('uid');
       console.log(uid);
        if(uid===null){
            navigate("/login")
        }

    },[]);


    return (

        <Layout>
            <Header style={{backgroundColor:'white'}}>
                <HeaderTest />
            </Header>
            <Content
                style={{
                    display: 'flex',
                    height: '100vh',
                    width:'80vw',
                    margin: '0 auto',
                    paddingTop:'3vh'
                }}
            >
                <div
                    style={{
                        flex: 1,
                        overflow: 'auto',
                        borderRight: '1px solid #e8e8e8',

                    }}
                >
                    <QuestionList/>
                </div>


                <div style={{ width:'20vw'}}>
                  <UserCard/>
                </div>

            </Content>

        </Layout>
    );
};

export default HomeView;
