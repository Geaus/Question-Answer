import {Avatar, Button, Card, Divider, List, Space, Tag} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined,
    DislikeOutlined,
    LikeOutlined,
    StarOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import React, {useEffect, useState} from "react";
import {answerList} from "../../App";
import AnswerCard from "./AnswerCard";
import {useLocation, useParams} from "react-router";
import {getAnswers, getQuestion} from "../../service/QuestionService";

function AnswerList(props) {

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const id=searchParams.get('qid')

    const [answers,setAnswers]=useState([]);

    useEffect(() => {

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', id);

        // eslint-disable-next-line array-callback-return
       getAnswers(params,setAnswers);

    }, [id]);

    return (

        <List
            dataSource={answers}
            renderItem={(answer) => (
                <AnswerCard  info={answer}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />

    );
}
export default AnswerList;