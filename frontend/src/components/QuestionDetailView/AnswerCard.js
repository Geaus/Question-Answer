import {Avatar, Button, Card, Col, Divider, Row, Space, Tag, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined,
    DislikeOutlined,
    LikeOutlined,
    StarOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import React, {useState} from "react";
import Answer from "./Answer";
const { Meta } = Card;
const { Text } = Typography;

function AnswerCard(props) {

    const [expanded,setExpanded]=useState(false);
    const answerContent=props.info.content;
    const truncatedContent = answerContent.substring(0, 10) + '...';

    const  handleExpanded=()=>{
        setExpanded(!expanded);
    }

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

                extra={
                    expanded ? (
                        <Button type="link" onClick={handleExpanded}>
                            收起 <CaretUpOutlined />
                        </Button>
                    ) : (
                        <Button type="link" onClick={handleExpanded}>
                            展开 <CaretDownOutlined />
                        </Button>
                    )
                }
            >
                <Meta
                    avatar={<Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />}
                    title={props.info.user.userName}
                    //description={expanded ? answerContent : truncatedContent}
                />
                {expanded && (<Answer info={answerContent} />)}
                {!expanded && (<Answer info={truncatedContent} />)}
                {/*<Answer info={expanded ? answerContent : truncatedContent}/>*/}

                <Row gutter={16} style={{ marginTop: '10px' }}>
                    <Col>
                        <Space>
                            <Button icon={<LikeOutlined />} />
                            <Text type="secondary">10</Text>
                        </Space>
                    </Col>
                    <Col>
                        <Space>
                            <Button icon={<DislikeOutlined />} />
                            <Text type="secondary">5</Text>
                        </Space>
                    </Col>
                    <Col>
                        <Button icon={<UserAddOutlined />} />
                    </Col>
                </Row>

            </Card>
        </div>
    );
}
export default AnswerCard;