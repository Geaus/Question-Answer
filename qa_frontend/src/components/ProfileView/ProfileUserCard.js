import {Avatar, Button, Card, Space} from "antd";
import Meta from "antd/es/card/Meta";
import {DeleteOutlined, UserDeleteOutlined, UserOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {changeFollow} from "../../service/FeedbackService";

function ProfileUserCard(props) {

    const [follow,setFollow]=useState(props.info);
    const [exist,setExist]=useState(true);
    const handleUnfollow=()=>{

        const params = new URLSearchParams();
        params.append('uid_1', sessionStorage.getItem('uid'));
        params.append('uid_2',follow.id);
        params.append('value', '0');

        changeFollow(params,setExist);
    }

    useEffect(() => {


    },[exist]);
    return (

        exist===true?
        <Card>

            <Link to={{pathname:'/profile',search:'?uid='+follow.id}}>
                <Meta
                    avatar={<Avatar size={64} icon={<UserOutlined />} />}
                    title={follow.userName}
                />
            </Link>


            <Button
                type="text"
                size={"large"}
                icon={<UserDeleteOutlined />}
                style={{ position: 'absolute', top: 10, right: 10 }}
                onClick={handleUnfollow}
            />
        </Card>:null
    );
}
export default ProfileUserCard;