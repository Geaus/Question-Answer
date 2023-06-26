import {Card, Divider, Space, Tag} from "antd";
import Meta from "antd/es/card/Meta";
import {DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";

function QuestionCard(props) {
    function handleLike() {

    }
    function handleDislike() {

    }
    function handleStar() {

    }
    return (
        <div>
            <Card
                size={"default"}
                actions={[
                    <LikeOutlined key="like" onClick={handleLike}/>,
                    <DislikeOutlined key="dislike" onClick={handleDislike}/>,
                    <StarOutlined key="start" onClick={handleStar}/>
                ]}
            >
                <h1>如何评价……</h1>
                <p1>描述具体问题描述具体问题描述具体问题描述具体问题描述具体问题描述具体问题描述具体问题</p1>
                <Divider />
                <Space size={[0, 3]} wrap>
                    <Tag>magenta</Tag>
                    <Tag>red</Tag>
                    <Tag>volcano</Tag>
                </Space>
            </Card>
        </div>
    );
}
export default QuestionCard;