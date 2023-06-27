
import React from "react";
import {Layout, Space} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import HeaderTest from "../components/HomeView/HeaderTest";
import ProfileUserInfo from "../components/ProfileView/ProfileUserInfo";
import ProfileMenu from "../components/ProfileView/ProfileMenu";
import UserCard from "../components/HomeView/UserCard";
import QuestionList from "../components/HomeView/QuestionList";
import {questionList} from "../App";

function ProfileView(props) {
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
                   <ProfileUserInfo/>
                    <ProfileMenu/>
                </div>


                <div style={{ width:'20vw'}}>
                    <UserCard/>
                </div>

            </Content>

        </Layout>
    );
}
export default ProfileView;