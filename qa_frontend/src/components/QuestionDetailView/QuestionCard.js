import {Avatar, Button, Card, Col, Divider, List, Row, Space, Tag, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined, DislikeFilled,
    DislikeOutlined,
    LikeFilled,
    LikeOutlined, StarFilled,
    StarOutlined
} from "@ant-design/icons";
import {useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {useLocation} from "react-router";
import Answer from "./Editor";
import {getQuestion} from "../../service/QuestionService";
import QuestionItem from "../HomeView/QuestionItem";
import {feedbackQuestion} from "../../service/FeedbackService";
const { Meta } = Card;
const { Text } = Typography;

function QuestionCard(props) {


    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const id=searchParams.get('qid')
    console.log(id);

    const [question,setQuestion] =useState({tags: [],likeFlag:0,markFlag:0});
    const[answer,setAnswer] =useState(false);

    const  handleLike=()=>{

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
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
        params.append('uid', sessionStorage.getItem('uid'));
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
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', question.id);

        if(question.markFlag===0){
            params.append('value', '2');
        }
        else {
            params.append('value', '-2');
        }

        feedbackQuestion(params,setQuestion);

    }
    const handleAnswer=()=>{

        setAnswer(!answer);
    }

    useEffect(() => {

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', id);

        // eslint-disable-next-line array-callback-return
       getQuestion(params,setQuestion);

    }, [id]);


    return (
        <div>
            <Card>
                <Meta
                    title={question.title}
                    description={question.content}
                />
                <Divider />

                <Space  wrap>

                    {
                        (question.tags !== null && question.tags.length !== 0) ?
                            <List
                                dataSource={question.tags}
                                renderItem={(tag) => (
                                    <Tag >{tag.content} </Tag>
                                    // <Card key={question.id}>{question.title}</Card>
                                )}
                            />:null

                    }
                </Space>

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


                <Button
                    style={{float:'right'}}
                    onClick={handleAnswer}
                >
                    回答问题
                </Button>
            </Card>

            {
                answer&&(
                    <div >
                        <Answer setAnswer={setAnswer}/>
                    </div>

                )
            }

        </div>
    );
}
export default QuestionCard;