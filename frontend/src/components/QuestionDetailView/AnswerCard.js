import {Avatar, Button, Card, Col, Row, Space, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined,
    DislikeOutlined,
    LikeOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import React, {useState} from "react";
import gfm from 'remark-gfm';
import ReactMarkdown from 'react-markdown';
import remarkMath from "remark-math";
import rehypeKatex from 'rehype-katex'
import Meta from "antd/es/card/Meta";
const { Text } = Typography;

function AnswerCard(props) {

    const [expanded,setExpanded]=useState(false);
    const answerContent=props.info.content;
    const newlineIndex = answerContent.indexOf('\n');
    const truncatedContent = newlineIndex !== -1 ? answerContent.substring(0, newlineIndex) + '...' : answerContent;
    const  handleExpanded=()=>{
        setExpanded(!expanded);
        console.log(answerContent);
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
                {expanded && (<ReactMarkdown remarkPlugins={[gfm, remarkMath]} rehypePlugins={[rehypeKatex]}>{answerContent}</ReactMarkdown>)}
                {!expanded && (<ReactMarkdown remarkPlugins={[gfm, remarkMath]} rehypePlugins={[rehypeKatex]}>{truncatedContent}</ReactMarkdown>)}

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