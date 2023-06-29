import {Avatar, Button, Card, Col, Row, Space, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined, DislikeFilled,
    DislikeOutlined, LikeFilled,
    LikeOutlined, MinusCircleFilled, MinusCircleOutlined, PlusCircleFilled, PlusCircleOutlined,
    UserAddOutlined, UserDeleteOutlined
} from "@ant-design/icons";
import React, {useEffect, useState} from "react";
import gfm from 'remark-gfm';
import ReactMarkdown from 'react-markdown';
import remarkMath from "remark-math";
import rehypeKatex from 'rehype-katex'
import Meta from "antd/es/card/Meta";
import {changeFollow, feedbackAnswer, feedbackQuestion} from "../../service/FeedbackService";
import {getQuestions} from "../../service/QuestionService";
const { Text } = Typography;

function AnswerCard(props) {

    const [uid,setUid]=useState(sessionStorage.getItem('uid'))

    const [answer,setAnswer]=useState(props.info);
    const [follow,setFollow]=useState(props.info.followFlag);
    const self= uid.toString()===answer.user.id.toString();
    console.log(self)

    const [expanded,setExpanded]=useState(false);


    const answerContent=answer.content;
    const newlineIndex = answerContent.indexOf('\n');
    const truncatedContent = newlineIndex !== -1 ? answerContent.substring(0, newlineIndex) + '...' : answerContent;



    const  handleExpanded=()=>{
        setExpanded(!expanded);
        console.log(answerContent);
    }

    function handleLike() {

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('aid', answer.id);
        params.append('value', '1');
        feedbackAnswer(params,setAnswer);

    }
    function handleDislike() {

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('aid', answer.id);
        params.append('value', '-1');
        feedbackAnswer(params,setAnswer);
    }


    function handleSubscribe() {

        const params = new URLSearchParams();
        params.append('uid_1', sessionStorage.getItem('uid'));
        params.append('uid_2',answer.user.id);

        let tmp = answer;
        if(follow===0){
            params.append('value', '1');
        }
        else{
            params.append('value', '0');
        }

         changeFollow(params,setFollow);
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
                            {
                                (answer.likeFlag===1)?
                                    <Button icon={<LikeFilled />} onClick={handleLike}/>
                                    : <Button icon={<LikeOutlined />} onClick={handleLike}/>
                            }
                            <Text type="secondary">{answer.like}</Text>
                        </Space>
                    </Col>
                    <Col>
                        <Space>
                            {
                                (answer.likeFlag===-1)?
                                    <Button icon={<DislikeFilled />} onClick={handleDislike}/>
                                    : <Button icon={<DislikeOutlined />} onClick={handleDislike}/>
                            }
                            <Text type="secondary">{answer.dislike}</Text>
                        </Space>
                    </Col>
                    <Col>

                        {

                            !self && (follow === 1 ? (
                                <Button icon={<MinusCircleFilled />} onClick={handleSubscribe} />
                            ) : (
                                <Button icon={<PlusCircleOutlined />} onClick={handleSubscribe} />
                            ))

                        }

                    </Col>
                </Row>

            </Card>
        </div>
    );
}
export default AnswerCard;