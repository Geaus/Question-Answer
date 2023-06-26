import {DislikeOutlined, FormOutlined, LikeOutlined, QuestionOutlined} from "@ant-design/icons";
import {Menu} from "antd";

function ProfileMenu() {
    const items = [
        {
            label: (
                <a href="/questions">'我的提问'</a>
            ),
            key: 'questions',
            icon: <QuestionOutlined />,
        },
        {
            label: (
                <a href="/answers">'我的回答'</a>
            ),
            key: 'answers',
            icon: <FormOutlined />,
        },
        {
            label: (
                <a href="/likes">'我赞过的'</a>
            ),
            key: 'likes',
            icon: <LikeOutlined />
        },
        {
            label: (
                <a href="/dislikes">'我踩过的'</a>
            ),
            key: 'dislikes',
            icon: <DislikeOutlined />,
        }
    ];
    return (
        <Menu mode={"horizontal"} items={items}/>
    );
}
export default ProfileMenu;