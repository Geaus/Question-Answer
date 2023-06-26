import React from 'react';
import { Layout, Card, Button, List } from 'antd';
import { UserOutlined, SolutionOutlined } from '@ant-design/icons';
import "../css/HeaderTest.css"

import {questionList} from '../App'
import HeaderTest from "./HeaderTest";

const { Header, Content, Footer } = Layout;

const HomePage = () => {

    const questionList = [
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
        {
            id: 9,
            title: '问题9',
            content:'12345678901234567890'
        },
        {
            id: 10,
            title: '问题10',
            content:'12345678901234567890'
        },
        {
            id: 11,
            title: '问题11',
            content:'12345678901234567890'
        },
        {
            id: 12,
            title: '问题12',
            content:'12345678901234567890'
        },
        {
            id: 13,
            title: '问题13',
            content:'12345678901234567890'
        },
        {
            id: 14,
            title: '问题14',
            content:'12345678901234567890'
        },

    ];

    return (
        <Layout style={{width: '100%'}}>
            <Header style={{height: '8vh', padding: 0}}>
                <HeaderTest />
            </Header>
            <Content
                style={{
                    display: 'flex',
                    padding: '8px',
                }}
            >
                <div
                    style={{
                        flex: 1,
                        marginRight: '8px',
                        borderRight: '1px solid #e8e8e8',
                    }}
                >
                    <List
                        dataSource={questionList}
                        renderItem={(question) => (
                            <div style={{paddingBottom: '1vw'}}>
                                <Card key={question.id}>{question.title}</Card>
                            </div>
                        )}
                    />
                </div>
                <div style={{ width: 300 }}>
                    <Card>
                        <Button type="primary" icon={<UserOutlined />} block>
                            个人主页
                        </Button>
                        <Button type="primary" icon={<SolutionOutlined />} block>
                            回答问题
                        </Button>
                    </Card>
                </div>
            </Content>
            <Footer>Footer</Footer>
        </Layout>
    );
};

export default HomePage;
