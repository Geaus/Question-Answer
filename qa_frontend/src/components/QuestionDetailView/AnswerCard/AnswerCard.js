import {Avatar, Button, Card, Col, Row, Space, Typography} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined, DeleteOutlined, DislikeFilled,
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
import {changeFollow, feedbackAnswer, feedbackQuestion} from "../../../service/FeedbackService/FeedbackService";
import {banUser, deleteAnswer, getQuestions} from "../../../service/QuestionService/QuestionService";
import {useNavigate} from "react-router-dom";
const { Text } = Typography;

function AnswerCard(props) {

    const navigate = useNavigate();

    const [uid,setUid]=useState(sessionStorage.getItem('uid'))
    const [answer,setAnswer]=useState(props.info);
    const [followFlag,setFollowFlag]=useState(props.info.followFlag);
    const [user,setUser]=useState(props.info.user);
    const [exist,setExist]=useState(true);
    const [admin,setAdmin]=useState(sessionStorage.getItem('type')==='1');

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

    const  handleLike=()=>{

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('aid', answer.id);

        if(answer.likeFlag===0||answer.likeFlag===-1){
            params.append('value', '1');
        }
        else if(answer.likeFlag===1){
            params.append('value', '0');
        }

        feedbackAnswer(params,setAnswer);

    }
    const handleDislike=()=>{

        const params = new URLSearchParams();
        params.append('uid',uid);
        params.append('aid', answer.id);

        if(answer.likeFlag===1 || answer.likeFlag===0){
            params.append('value', '-1');
        }
        else if(answer.likeFlag===-1){
            params.append('value', '0');
        }

        feedbackAnswer(params,setAnswer);

    }

    const handleSubscribe=()=> {

        const params = new URLSearchParams();
        params.append('uid_1', uid);
        params.append('uid_2',user.id);

        let tmp = answer;
        if(followFlag===0){
            params.append('value', '1');
        }
        else{
            params.append('value', '0');
        }

         changeFollow(params,setFollowFlag);
    }

    const handleDelete=()=>{

        const params = new URLSearchParams();
        params.append('aid', answer.id);
<<<<<<< HEAD
        params.append('uid', sessionStorage.getItem('uid'));
=======

>>>>>>> Gao
        deleteAnswer(params,setExist(false));

    }

    const handleBan=()=>{

        const params = new URLSearchParams();
<<<<<<< HEAD
        params.append('userId',user.id);
        params.append('uid', sessionStorage.getItem('uid'))
=======
        params.append('uid',user.id);
>>>>>>> Gao

        banUser(params,setUser);

    }
    return (
        exist===true?<div>
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
                    avatar={<Avatar src={user.avatar} />}
                    title={
                        <div>
                            {user.type===-1 ? '该用户已被封禁' :user.userName}
                            {
                                admin===true&&user.type!==1&&user.type!==-1?
                                    <Button onClick={handleBan} style={{float:'right'}}>封禁</Button>
                                    :null
                            }
                        </div>
                    }

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

                            !self && (followFlag === 1 ? (
                                <Button icon={<MinusCircleFilled />} onClick={handleSubscribe} />
                            ) : (
                                <Button icon={<PlusCircleOutlined />} onClick={handleSubscribe} />
                            ))

                        }

                    </Col>
                    <Col>
                        <Space>
                            {
                                (admin===true) ?
                                    <Button icon={<DeleteOutlined/>} onClick={handleDelete}/>
                                    : null
                            }

                        </Space>
                    </Col>
                </Row>

            </Card>
        </div>:null
    );
}
export default AnswerCard;