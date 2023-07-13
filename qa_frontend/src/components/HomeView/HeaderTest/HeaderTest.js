import React, {useEffect, useState} from "react";
import "../../../css/HeaderTest.css"
import {Menu, Input, Avatar, Button, Space, Row, Col} from "antd";
import {FireOutlined, HomeOutlined, StarOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {getQuestions, searchQuestion} from "../../../service/QuestionService/QuestionService";
import { useNavigate } from 'react-router-dom';
import {getUser} from "../../../service/ProfileService/ProfileService";
import {logout} from "../../../service/LoginService/LoginService";
import {text} from "@milkdown/preset-commonmark";

const {Search} = Input;


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
        params.append('userId', uid);
        getUser(params,setUser);

        // console.log(user);
    },[user]);


    const handleSearch = (e) =>{
        console.log(e);

        if(e===""){
            navigate("/?page=0");
            window.location.reload();
        }else{
            // eslint-disable-next-line react-hooks/rules-of-hooks
            navigate('/?title='+e+'&page=0');
            window.location.reload();
        }
    }

    const logoutCallback = () => {
        sessionStorage.removeItem('uid');
        sessionStorage.removeItem('type');
        navigate("/login");
    }

    const handleLogout = () => {
        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        logout(params, logoutCallback);
    }

    const handleHome=()=>{

        navigate("/?page=0");
        window.location.reload();

    }

    const handleProfile=()=>{

        navigate('/profile?uid='+uid+'&menu=questions');
        window.location.reload();
    }

    const menuItems = [
        {
            label: "首页",
            key: "home",
            icon:<HomeOutlined />,
            onClick: handleHome,

        },
        {
            label: "关注",
            key: "at",
            icon: <StarOutlined />
        },
        {
            label: "热榜",
            key: "hot",
            icon: <FireOutlined />
        }
    ]
    return (
        <div  style={headerBarStyle}>
            <div data-testid="logo" style={{ fontSize: '20px' }}>My App</div>
            <Menu data-testid="menu" mode="horizontal" theme="light" style={menuStyle} items={menuItems}>
                {/*<Menu.Item key="home" icon={<HomeOutlined />}>*/}
                {/*    <Link to={"/"} >首页</Link>*/}
                {/*</Menu.Item>*/}
                {/*<Menu.Item key="follow" icon={<StarOutlined />}>*/}
                {/*    关注*/}
                {/*</Menu.Item>*/}
                {/*<Menu.Item key="hot" icon={<FireOutlined />}>*/}
                {/*    热榜*/}
                {/*</Menu.Item>*/}
            </Menu>
            <Input.Search data-testid="search" placeholder="搜索" style={searchInputStyle} onSearch={(e)=>handleSearch(e)}/>

            <div data-testid="hello" style={greetingStyle}>你好</div>
            <span style={{width: "2vw"}}></span>

            <Avatar onClick={handleProfile} data-testid="avatar" src={user.avatar}/>

            <Button type="text" data-testid="logout" onClick={handleLogout} style={{marginLeft:"2vw", fontSize:'1.2vw',}}>Log out</Button>

        </div>
    );

}

export default HeaderTest;