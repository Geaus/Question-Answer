import {UserOutlined} from "@ant-design/icons";
import {Avatar, Space} from "antd";

function ProfileUserInfo(props) {
    return (
        <Space direction="vertical" size={16} align={"center"}>
            <Avatar size={128} icon={<UserOutlined />} />
            <h1>props.username</h1>
        </Space>
    );
}
export default ProfileUserInfo;