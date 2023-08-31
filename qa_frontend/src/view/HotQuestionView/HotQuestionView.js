import React, {useEffect} from 'react';
import {Layout, Card, Button, List, message} from 'antd';
import HeaderTest from "../../components/HomeView/HeaderTest/HeaderTest";
import UserCard from "../../components/HomeView/UserCard/UserCard";
import QuestionList from "../../components/HomeView/QuestionList/QuestionList";
import { useNavigate } from "react-router-dom";
import HotQuestionList from "../../components/HomeView/HotQuestionList/HotQuestionList";
const { Header, Content, Footer } = Layout;

const HotQuestionView = () => {


    const navigate=useNavigate();

    useEffect(() => {

        const uid=sessionStorage.getItem('uid');
        if(uid===null){
            message.error("请登录");
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
                    <HotQuestionList />
                </div>


                <div style={{ width:'20vw'}}>
                    <UserCard />
                </div>

            </Content>

        </Layout>
    );
};

export default HotQuestionView;
