
import React, {useEffect} from "react";
import {Layout, message, Space} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import HeaderTest from "../../components/HomeView/HeaderTest/HeaderTest";
import ProfileUserInfo from "../../components/ProfileView/ProfileUserInfo/ProfileUserInfo";
import ProfileMenu from "../../components/ProfileView/ProfileMenu/ProfileMenu";
import {useNavigate} from "react-router-dom";

function ProfileView(props) {

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
                    width:'75vw',
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


            </Content>

        </Layout>
    );
}
export default ProfileView;