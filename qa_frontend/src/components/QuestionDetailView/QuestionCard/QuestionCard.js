import {Avatar, Button, Card, Col, Divider, List, Row, Space, Tag, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined, DeleteOutlined, DislikeFilled,
    DislikeOutlined,
    LikeFilled,
    LikeOutlined, StarFilled,
    StarOutlined
} from "@ant-design/icons";
import {useParams} from "react-router";
import React, {useEffect, useState} from "react";
import {useLocation} from "react-router";
import Answer from "../Editor/Editor";
import {
    banUser,
    deleteQuestion,
    getQuestion,
    getQuestions,
    searchQuestion, searchQuestionByTag
} from "../../../service/QuestionService/QuestionService";
import QuestionItem from "../../HomeView/QuestionItem/QuestionItem";
import {feedbackQuestion} from "../../../service/FeedbackService/FeedbackService";
import {Link, useNavigate} from "react-router-dom";
const { Meta } = Card;
const { Text } = Typography;

function QuestionCard(props) {

    const navigate = useNavigate();

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const id=searchParams.get('qid')

    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [question,setQuestion] =useState({user:{},tags: [],likeFlag:0,markFlag:0});
    const [user,setUser]=useState({});
    const [exist,setExist]=useState(true);
    const [admin,setAdmin]=useState(sessionStorage.getItem('type')==='1');
    console.log(admin);
    const [showEditor,setShowEditor] =useState(false);

    useEffect(() => {

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('qid', id);

        const callback=(data)=>{

            setQuestion(data);
            setUser(data.user);
        }
        // eslint-disable-next-line array-callback-return
        getQuestion(params,callback);

    }, [id]);

    useEffect(() => {
        const interval = setInterval(() => {
            const params = new URLSearchParams();
            params.append('uid', uid);
            params.append('qid', id);

            const callback=(data)=>{

                setQuestion(data);
                setUser(data.user);
            }
            // eslint-disable-next-line array-callback-return
            getQuestion(params,callback);
        }, 3000);
        return () => clearInterval(interval);
    }, [])

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
    const handleShowEditor=()=>{

        setShowEditor(!showEditor);

    }

   const handleDelete=()=>{

       const params = new URLSearchParams();
       params.append('qid', question.id);
       params.append('uid', sessionStorage.getItem('uid'));

       const callback=()=>{
           navigate("/");
       }
       deleteQuestion(params,callback);
   }

   const handleBan=()=>{

       const params = new URLSearchParams();
       params.append('userId',user.id);
       params.append('uid', uid);
       banUser(params,setUser);

   }


    return (
        exist===true ?<div>
            <Card>

                <Meta
                    avatar={<Avatar src={user.avatar} />}
                    title={
                        <div>
                            { user.type===-1 ? '该用户已被封禁' :user.userName}
                            {

                                admin===true&&user.type!==1&&user.type!==-1?
                                <Button onClick={handleBan} style={{float:'right'}}>封禁</Button>
                                    :null
                            }
                        </div>
                    }
                />

                <br/>

                <Meta
                    title={question.title}
                    description={question.content}
                    //description={expanded ? answerContent : truncatedContent}
                />
                <br/>

                <Space  wrap>

                    {
                        (question.tags !== null && question.tags.length !== 0) ?
                            <List
                                dataSource={question.tags}
                                renderItem={(tag) => (
                                    <Link to={`/?tag=${tag.id}&page=0`}>
                                        <Tag data-testid="question-tag" color="#1677ff">{tag.content}</Tag>
                                    </Link>
                                )}
                            />:null

                    }
                </Space>

                <Row gutter={16} style={{ marginTop: '10px' }}>
                    <Col>
                        <Space>

                            {
                                (question.likeFlag===1) ?
                                    <Button data-testId="like-question-button" icon={<LikeFilled />} onClick={handleLike}/>
                                    : <Button data-testId="like-question-button" icon={<LikeOutlined />} onClick={handleLike}/>
                            }
                            <Text data-testId="like-question-count" type="secondary">{question.like}</Text>
                        </Space>
                    </Col>
                    <Col>
                        <Space>
                            {
                                (question.likeFlag===-1) ?
                                    <Button data-testId="dislike-question-button" icon={<DislikeFilled/>} onClick={handleDislike}/>
                                    : <Button data-testId="dislike-question-button" icon={<DislikeOutlined/>} onClick={handleDislike}/>
                            }
                            <Text data-testId="dislike-question-count" type="secondary">{question.dislike}</Text>

                        </Space>
                    </Col>
                    <Col>
                        <Space>
                            {
                                (question.markFlag===1) ?
                                    <Button data-testId="mark-question-button" icon={<StarFilled/>} onClick={handleStar}/>
                                    : <Button data-testId="mark-question-button" icon={<StarOutlined/>} onClick={handleStar}/>
                            }
                            <Text data-testId="mark-question-count" type="secondary">{question.mark}</Text>

                        </Space>
                    </Col>
                    <Col>
                        <Space>
                            {
                                (admin===true) ?
                                    <Button data-testId="delete-question-button" icon={<DeleteOutlined/>} onClick={handleDelete}/>
                                    : null
                            }
                        </Space>
                    </Col>
                </Row>

                {
                    showEditor ?
                        <Button
                        style={{float:'right'}}
                        onClick={handleShowEditor}
                        >
                            取消回答
                        </Button>
                        :<Button
                            style={{float:'right'}}
                            onClick={handleShowEditor}
                        >
                            回答问题
                        </Button>
                }
            </Card>

            {
                showEditor&&(
                    <div >
                        <Answer setAnswer={setShowEditor}/>
                    </div>

                )
            }

        </div>:null
    );
}
export default QuestionCard;