import React, {Component, useState} from 'react';
import {Card, Button, Collapse, Row, Col, Space, Typography} from 'antd';
import {
    CaretDownOutlined,
    CaretUpOutlined, DeleteOutlined, DislikeFilled,
    DislikeOutlined,
    LikeFilled,
    LikeOutlined, StarFilled,
    StarOutlined
} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {feedbackQuestion} from "../../../service/FeedbackService/FeedbackService";


const { Meta } = Card;
const { Text } = Typography;

const QuestionItem =(props)=>{


    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [admin,setAdmin]=useState(sessionStorage.getItem('type')==='1');
    const [question,setQuestion]=useState(props.info);

    const [expanded,setExpanded]=useState(false);
    const [exist,setExist]=useState(true);

    const questionContent=question.content;
    const truncatedContent = questionContent.substring(0, 10) + '...';

    const  handleExpanded=()=>{
       setExpanded(!expanded);
    }

    const  handleLike=()=>{

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('qid', question.id);

        if(question.likeFlag===0||question.likeFlag===-1){
            params.append('value', '1');
        }
        else if(question.likeFlag===1){
            params.append('value', '0');
        }

        feedbackQuestion(params,setQuestion);

     }
    const handleDislike=()=>{

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('qid', question.id);

        if(question.likeFlag===1 || question.likeFlag===0){
            params.append('value', '-1');
        }
        else if(question.likeFlag===-1){
            params.append('value', '0');
        }

        feedbackQuestion(params,setQuestion);

    }
    const handleStar=()=>{

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('qid', question.id);

        if(question.markFlag===0){
            params.append('value', '2');
        }
        else {
            params.append('value', '-2');
        }

        feedbackQuestion(params,setQuestion);

     }

     const handleDelete=()=>{

        setExist(false);
     }

    return (

        exist===true? <div>
            <Card
                title={
                    <Link to={{pathname:'/ques',search:'?qid='+question.id+'&page=0'}}>
                        <div  dangerouslySetInnerHTML={{ __html: question.title }} />
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
        </div>:null

    );

}

export default QuestionItem;
