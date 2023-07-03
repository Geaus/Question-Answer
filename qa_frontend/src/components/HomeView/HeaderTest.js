import React, {useEffect, useState} from "react";
import "../../css/HeaderTest.css"
import {Menu, Input, Avatar, Button, Space, Row, Col} from "antd";
import {FireOutlined, HomeOutlined, StarOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {getQuestions, searchQuestion} from "../../service/QuestionService";
import { useNavigate } from 'react-router-dom';
import {getUser} from "../../service/ProfileService";

const {Search} = Input;

const menuItems = [
    {
        label: <Link to={"/"} >"首页"</Link>,
        key: "home",

    },
    {
        label: "关注",
        key: "at"
    },
    {
        label: "热榜",
        key: "hot"
    }
]
const headerBarStyle = {
    display: 'flex',
    alignItems: 'center',
    height: '60px',
    padding: '0 20px',
    backgroundColor: '#fff',
    color: '#000',
};

const menuStyle = {
    flex: 1,
    display: 'flex',
    justifyContent: 'flex-start',
};

const searchInputStyle = {
    width: '200px',
    marginLeft: '20px',
};

const greetingStyle = {
    marginLeft: '20px',
    fontWeight: 'bold',
};
const HeaderTest = () => {

    const navigate=useNavigate();

    const [uid ,setUid] =useState(sessionStorage.getItem('uid'));
    const [user,setUser]=useState({});


    useEffect(() => {

        // sessionStorage.setItem('uid','1');
        const params = new URLSearchParams();
        params.append('uid', uid);
        getUser(params,setUser);

        // console.log(user);
    },[user]);


    const handleSearch = (e) =>{
        console.log(e);

        if(e===""){
            navigate("/");
        }else{
            // eslint-disable-next-line react-hooks/rules-of-hooks
            navigate("/?title="+e);
        }


    }

    return (
        <div style={headerBarStyle}>
            <div style={{ fontSize: '20px' }}>My App</div>
            <Menu mode="horizontal" theme="light" style={menuStyle}>
                <Menu.Item key="home" icon={<HomeOutlined />}>
                    <Link to={"/"} >首页</Link>
                </Menu.Item>
                <Menu.Item key="follow" icon={<StarOutlined />}>
                    关注
                </Menu.Item>
                <Menu.Item key="hot" icon={<FireOutlined />}>
                    热榜
                </Menu.Item>
            </Menu>
            <Input.Search placeholder="搜索" style={searchInputStyle} onSearch={(e)=>handleSearch(e)}/>

            <div style={greetingStyle}>你好</div>
            <span style={{width: "2vw"}}></span>


            <Link to={{pathname:'/profile',search:'?uid='+uid}}>
               <Avatar src={user.avatar}/>
            </Link>

        </div>
    );

}

export default HeaderTest;