import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import Link from "antd/es/typography/Link";
import {questionList} from "../../App";
import QuestionItem from "./QuestionItem";
import {getQuestions, searchQuestion} from "../../service/QuestionService";
import {useLocation} from "react-router";

const QuestionList =(props)=>{

    const [questions,setQuestions] =useState([]);
    const [empty,setEmpty]=useState(false);
    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const title=searchParams.get('title')


    useEffect(() => {

        if(title===null || title===""){
            // sessionStorage.setItem('uid','1');
            const params = new URLSearchParams();
            params.append('uid', sessionStorage.getItem('uid'));
            getQuestions(params,setQuestions);
            console.log(questions)

        }
       else{

            const params = new URLSearchParams();
            params.append('uid', sessionStorage.getItem('uid'));
            params.append('title', title);
            searchQuestion(params,setQuestions);

        }


    },[title]);
    useEffect(() => {


    },[questions]);



    return (

        (empty === false )? <List
            dataSource={questions}
            renderItem={(question) => (
                <QuestionItem  info={question}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />:null
    );

}

export default QuestionList;
