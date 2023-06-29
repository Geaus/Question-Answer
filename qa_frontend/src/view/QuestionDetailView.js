
import {Card, Layout, List, Space} from "antd";
import QuestionCard from "../components/QuestionDetailView/QuestionCard";
import HeaderTest from "../components/HomeView/HeaderTest";
import {answerList, questionList} from "../App";
import QuestionItem from "../components/HomeView/QuestionItem";
import UserCard from "../components/HomeView/UserCard";
import React from "react";
import {Footer} from "antd/es/modal/shared";
import {Content, Header} from "antd/es/layout/layout";
import AnswerList from "../components/QuestionDetailView/AnswerList";


function QuestionDetailView(props) {
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

                       {/*<List*/}
                       {/*    dataSource={answerList}*/}
                       {/*    renderItem={(answer) => (*/}
                       {/*        <AnswerCard  info={answer}/>*/}
                       {/*        // <Card key={question.id}>{question.title}</Card>*/}
                       {/*    )}*/}
                       {/*/>*/}
                       <AnswerList info={answerList}/>

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