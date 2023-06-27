import React from "react";
import "../../css/HeaderTest.css"
import {Menu, Input, Avatar, Button, Space, Row, Col} from "antd";
import {FireOutlined, HomeOutlined, StarOutlined} from "@ant-design/icons";

const {Search} = Input;

const menuItems = [
    {
        label: "首页",
        key: "home"
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

const HeaderTest = () => {
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

    return (
        <div style={headerBarStyle}>
            <div style={{ fontSize: '20px' }}>My App</div>
            <Menu mode="horizontal" theme="light" style={menuStyle}>
                <Menu.Item key="home" icon={<HomeOutlined />}>
                    首页
                </Menu.Item>
                <Menu.Item key="follow" icon={<StarOutlined />}>
                    关注
                </Menu.Item>
                <Menu.Item key="hot" icon={<FireOutlined />}>
                    热榜
                </Menu.Item>
            </Menu>
            <Input.Search placeholder="搜索" style={searchInputStyle} />
            <div style={greetingStyle}>你好</div>
            <span style={{width: "2vw"}}></span>
            <Avatar src="https://example.com/avatar.png" />
        </div>
    );
        // return (
            // <div className={"header-area-limit"}>
            //     <div className={"header-logo-part"} >
            //         <span>Logo</span>
            //     </div>
            //     <div className={"header-menu-part"}>
            //         <Menu items={menuItems} mode="horizontal"></Menu>
            //     </div>
            //     <div className={"header-search-part"}>
            //         <Search className={"header-search-input"} />
            //     </div>
            //     <div className={"header-person-part"}>
            //         <Avatar className={"header-avatar-part"} size={"default"}/>
            //         <span className={"header-welcome-font"}>你好，用户</span>
            //     </div>
            // </div>
        // );
}

export default HeaderTest;