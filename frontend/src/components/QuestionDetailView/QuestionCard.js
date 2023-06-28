import {Avatar, Button, Card, Col, Divider, List, Row, Space, Tag, Typography} from "antd";
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import {useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {useLocation} from "react-router";
import Answer from "./Editor";
import {getQuestion} from "../../service/QuestionService";
import QuestionItem from "../HomeView/QuestionItem";
const { Meta } = Card;
const { Text } = Typography;

function QuestionCard(props) {


    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const id=searchParams.get('qid')


    console.log(id);
    const [question,setQuestion] =useState({});
    const[answer,setAnswer] =useState(false);

    function handleLike() {

    }
    function handleDislike() {

    }
    function handleStar() {

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

                    <List
                        dataSource={question.tags}
                        renderItem={(tag) => (
                            <Tag >{tag.content} </Tag>
                            // <Card key={question.id}>{question.title}</Card>
                        )}
                    />

                </Space>

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
                        <Button icon={<StarOutlined />} />
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