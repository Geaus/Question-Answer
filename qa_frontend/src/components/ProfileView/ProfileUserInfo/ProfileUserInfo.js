import {UserOutlined} from "@ant-design/icons";
import {Avatar, Descriptions, Space} from "antd";
import {useEffect, useState} from "react";
import {getQuestions} from "../../../service/QuestionService/QuestionService";
import {getUser} from "../../../service/ProfileService/ProfileService";
import {useLocation} from "react-router";

function ProfileUserInfo(props) {

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const userId=searchParams.get('uid')
    const uid = sessionStorage.getItem('uid')

    const [user,setUser]=useState({});

    useEffect(() => {

        // sessionStorage.setItem('uid','1');
        const params = new URLSearchParams();
        params.append('userId', userId);
        params.append('uid', uid);

        getUser(params,setUser);

        // console.log(user);
    },[]);


    return (
        <div style={{padding: '50px'}}>
            <Space direction="horizontal" size={40}>
                <Avatar size={128}  src={user.avatar} />
                <Descriptions title="User Info">
                    <Descriptions.Item label="UserName">{user.userName}</Descriptions.Item>
                    <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
                </Descriptions>
            </Space>
        </div>
    );
}
export default ProfileUserInfo;