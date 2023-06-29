import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import Link from "antd/es/typography/Link";
import {questionList} from "../../App";
import QuestionItem from "./QuestionItem";
import {getQuestions} from "../../service/QuestionService";

const QuestionList =(props)=>{

    const [questions,setQuestions] =useState([]);


    useEffect(() => {

        // sessionStorage.setItem('uid','1');
        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        getQuestions(params,setQuestions);
        console.log(questions)

    },[]);


    return (
        <List
            dataSource={questions}
            renderItem={(question) => (
                <QuestionItem  info={question}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />
    );

}

export default QuestionList;
