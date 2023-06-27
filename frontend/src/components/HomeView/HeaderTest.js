import React from "react";
import "../../css/HeaderTest.css"
import {Menu, Input, Avatar, Button} from "antd";

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
        return (
            <div className={"header-area-limit"}>
                <div className={"header-logo-part"} >
                    <span>Logo</span>
                </div>
                <div className={"header-menu-part"}>
                    <Menu items={menuItems} mode="horizontal"></Menu>
                </div>
                <div className={"header-search-part"}>
                    <Search className={"header-search-input"} />
                </div>
                <div className={"header-button-part"}>
                    <Button className={"header-question-button"} type={"primary"}>提问</Button>
                </div>
                <div className={"header-person-part"}>
                    <Avatar className={"header-avatar-part"} size={"default"}/>
                    <span className={"header-welcome-font"}>你好，用户</span>
                </div>
            </div>
        )
}

export default HeaderTest;