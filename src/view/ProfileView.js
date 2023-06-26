import ProfileMenu from "../component/ProfileMenu";
import {Card, Layout, Space} from "antd";
import ProfileUserInfo from "../component/ProfileUserInfo";
import HeaderTest from "../component/HeaderTest";
import React from "react";
import {Content, Header} from "antd/es/layout/layout";
import {Footer} from "antd/es/modal/shared";
import UserCard from "../component/UserCard";

function ProfileView(props) {
    return (
        <Layout style={{width: '100%'}}>
            <Header style={{height: '8vh', padding: 0}}>
                <HeaderTest />
            </Header>
            <Content>
                <Space direction={"vertical"} align={"start"} style={{width: "75%"}}>
                    <ProfileUserInfo />
                    <ProfileMenu />
                </Space>
                <Space>
                    <UserCard />
                </Space>
            </Content>
        </Layout>
    );
}
export default ProfileView;