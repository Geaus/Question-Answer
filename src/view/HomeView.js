import React from 'react';
import { Layout, Card, Button, List } from 'antd';
import { UserOutlined, SolutionOutlined } from '@ant-design/icons';
import {questionList} from '../App'
import HeaderTest from "../components/HomeView/HeaderTest";
import QuestionCard from "../components/HomeView/QuestionItem";
import UserCard from "../components/HomeView/UserCard";
import QuestionItem from "../components/HomeView/QuestionItem";
import QuestionList from "../components/HomeView/QuestionList";

const { Header, Content, Footer } = Layout;

const HomeView = () => {

    return (
        <Layout>
            <Header style={{height: '8vh', padding: 0}}>
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
                    <QuestionList info={questionList}/>
                </div>


                <div style={{ width:'20vw'}}>
                  <UserCard/>
                </div>

            </Content>

        </Layout>
    );
};

export default HomeView;
