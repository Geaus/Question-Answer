import {UserOutlined} from "@ant-design/icons";
import {Avatar, Descriptions, Space} from "antd";
import {useEffect, useState} from "react";
import {getQuestions} from "../../service/QuestionService";
import {getUser} from "../../service/ProfileService";

function ProfileUserInfo(props) {

    const [user,setUser]=useState({});

    useEffect(() => {

        // sessionStorage.setItem('uid','1');
        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));

        getUser(params,setUser);

        // console.log(user);
    },[user]);


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