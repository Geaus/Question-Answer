import {DislikeOutlined, FormOutlined, LikeOutlined, QuestionOutlined} from "@ant-design/icons";
import {List, Menu} from "antd";
import {useState} from "react";
import QuestionCard from "./QuestionCard";
import AnswerCard from "./AnswerCard";

function ProfileMenu() {
    const [currentMenuItem, setCurrentMenuItem] = useState('questions'); // 初始选中的菜单项，默认为 'menu1'

    const handleMenuClick = (e) => {
        setCurrentMenuItem(e.key);
    };

    let content;

    // 根据当前选中的菜单项，设置不同的内容
    if (currentMenuItem === 'questions') {
        content =
            <List>
                <QuestionCard />
                <QuestionCard />
            </List>
    } else if (currentMenuItem === 'answers') {
        content =
            <List>
                <AnswerCard />
                <AnswerCard />
            </List>
    } else if (currentMenuItem === 'likes') {
        content =
            <List>
                <QuestionCard />
                <QuestionCard />
            </List>
    } else if (currentMenuItem === 'dislikes') {
        content =
            <List>
                <QuestionCard />
                <QuestionCard />
            </List>
    }
    const items = [
        {
            label: (
                <a>'我的提问'</a>
            ),
            key: 'questions',
            icon: <QuestionOutlined />,
        },
        {
            label: (
                <a>'我的回答'</a>
            ),
            key: 'answers',
            icon: <FormOutlined />,
        },
        {
            label: (
                <a>'我赞过的'</a>
            ),
            key: 'likes',
            icon: <LikeOutlined />
        },
        {
            label: (
                <a>'我踩过的'</a>
            ),
            key: 'dislikes',
            icon: <DislikeOutlined />,
        }
    ];
    return (
        <div>
            <Menu
                onClick={handleMenuClick}
                selectedKeys={[currentMenuItem]}
                mode={"horizontal"}
                items={items}
            />
            {content}
        </div>
    );
}
export default ProfileMenu;