import React, {Component, useState} from 'react';
import {Card, Button, Collapse, Row, Col, Space, Typography} from 'antd';
import {
    CaretDownOutlined,
    CaretUpOutlined, DislikeFilled,
    DislikeOutlined,
    LikeFilled,
    LikeOutlined, StarFilled,
    StarOutlined
} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {feedbackQuestion} from "../../service/FeedbackService";


const { Meta } = Card;
const { Text } = Typography;

const QuestionPage =(props)=>{


    const [expanded,setExpanded]=useState(false);
    const [question,setQuestion]=useState(props.info);

    const questionContent=question.content;
    const truncatedContent = questionContent.substring(0, 10) + '...';

    const  handleExpanded=()=>{
       setExpanded(!expanded);
    }

    const  handleLike=()=>{

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', question.id);
        params.append('value', '1');
        feedbackQuestion(params,setQuestion);

     }
    const handleDislike=()=>{

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', question.id);
        params.append('value', '-1');
        feedbackQuestion(params,setQuestion);

    }
    const handleStar=()=>{

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', question.id);
        params.append('value', '2');
        feedbackQuestion(params,setQuestion);

     }


    return (
        <Card
            title={
                <Link to={{pathname:'/ques',search:'?qid='+question.id}}>
                    {question.title}
                </Link>
            }
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
            {question.user.userName+' : '}
            {expanded ? questionContent : truncatedContent}

            <Row gutter={16} style={{ marginTop: '10px' }}>
                <Col>
                    <Space>

                        {
                            (question.likeFlag===1) ?
                                <Button icon={<LikeFilled />} onClick={handleLike}/>
                                : <Button icon={<LikeOutlined />} onClick={handleLike}/>
                        }
                        <Text type="secondary">{question.like}</Text>
                    </Space>
                </Col>
                <Col>
                    <Space>
                        {
                            (question.likeFlag===-1) ?
                                <Button icon={<DislikeFilled/>} onClick={handleDislike}/>
                                : <Button icon={<DislikeOutlined/>} onClick={handleDislike}/>
                        }
                        <Text type="secondary">{question.dislike}</Text>

                    </Space>
                </Col>
                <Col>
                    <Space>
                        {
                            (question.markFlag===1) ?
                                <Button icon={<StarFilled/>} onClick={handleStar}/>
                                : <Button icon={<StarOutlined/>} onClick={handleStar}/>
                        }
                        <Text type="secondary">{question.mark}</Text>

                    </Space>
                </Col>
            </Row>

        </Card>
    );

}

export default QuestionPage;
