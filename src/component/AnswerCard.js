import {Avatar, Button, Card, Divider, Space, Tag} from "antd";
import {DislikeOutlined, LikeOutlined, StarOutlined, UserAddOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";

function AnswerCard(props) {
    function handleLike() {

    }
    function handleDislike() {

    }
    function handleSubscribe() {

    }
    return (
        <div>
            <Card
                size={"small"}
                actions={[
                    <LikeOutlined key="like" onClick={handleLike}/>,
                    <DislikeOutlined key="dislike" onClick={handleDislike}/>,
                    <UserAddOutlined key="subscribe" onClick={handleSubscribe}/>
                ]}
            >
                <Meta
                    avatar={<Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />}
                    title="props.userInfo.username"
                    description="props.userInfo.answer"
                />
            </Card>
        </div>
    );
}
export default AnswerCard;