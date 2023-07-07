
import {Card, Layout, List, message, Space} from "antd";
import QuestionCard from "../../components/QuestionDetailView/QuestionCard/QuestionCard";
import HeaderTest from "../../components/HomeView/HeaderTest/HeaderTest";
import UserCard from "../../components/HomeView/UserCard/UserCard";
import React, {useEffect} from "react";
import {Content, Header} from "antd/es/layout/layout";
import AnswerList from "../../components/QuestionDetailView/AnswerList/AnswerList";
import {useNavigate} from "react-router-dom";


function QuestionDetailView(props) {

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
                   <QuestionCard/>

                   <div>

                       <AnswerList/>

                   </div>

                </div>

                <div style={{ width:'20vw' }}>
                    <UserCard/>
                </div>
            </Content>


        </Layout>
    );
}

export default QuestionDetailView;