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
            <Content
                style={{
                    display: 'flex',
                    padding: '8px',
                }}
            >
                <Space direction={"vertical"} align={"center"}>
                    <Card>
                        <ProfileUserInfo />
                    </Card>
                    <Card>
                        <ProfileMenu />
                    </Card>
                </Space>
                <Space>
                    <UserCard />
                </Space>
            </Content>
            <Footer>Footer</Footer>
        </Layout>
    );
}
export default ProfileView;