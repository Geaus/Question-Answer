import {Avatar, Button, Card, Space} from "antd";
import Meta from "antd/es/card/Meta";
import {DeleteOutlined, UserDeleteOutlined, UserOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";

function ProfileUserCard(props) {
    return (
        <Card>

            <Link to={{pathname:'/profile',search:'?uid='+props.info.id}}>
                <Meta
                    avatar={<Avatar size={64} icon={<UserOutlined />} />}
                    title={props.info.userName}
                />
            </Link>


            <Button
                type="text"
                size={"large"}
                icon={<UserDeleteOutlined />}
                style={{ position: 'absolute', top: 10, right: 10 }}
            />
        </Card>
    );
}
export default ProfileUserCard;