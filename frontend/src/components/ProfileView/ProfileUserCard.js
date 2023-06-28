import {Avatar, Button, Card, Space} from "antd";
import Meta from "antd/es/card/Meta";
import {DeleteOutlined, UserDeleteOutlined, UserOutlined} from "@ant-design/icons";

function ProfileUserCard(props) {
    return (
        <Card>
            <Meta
                avatar={<Avatar size={64} icon={<UserOutlined />} />}
                title="Username"
            />
            <Button
                type="text"
                size={"large"}
                icon={<DeleteOutlined />}
                style={{ position: 'absolute', top: 10, right: 10 }}
            />
        </Card>
    );
}
export default ProfileUserCard;