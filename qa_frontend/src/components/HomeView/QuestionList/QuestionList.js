import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import QuestionItem from "../QuestionItem/QuestionItem";
import {getQuestions, searchQuestion} from "../../../service/QuestionService/QuestionService";
import {useLocation, useParams} from "react-router";
const QuestionList =(props)=>{



    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const title=searchParams.get('title')
    console.log(title);

    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [questions,setQuestions] =useState([]);
    const [empty,setEmpty]=useState(false);
    const [count,setCount]=useState(0);

    useEffect(() => {

        if(title==="" || title===null){
            // sessionStorage.setItem('uid','1');
            const params = new URLSearchParams();
            params.append('uid', uid);
            getQuestions(params,setQuestions);
            console.log(questions)
        }
        else{
            const params = new URLSearchParams();
            params.append('uid', uid);
            params.append('title', title);
            searchQuestion(params,setQuestions);
            console.log(questions)
        }

    },[title]);


    return (

        empty === false? <List
            dataSource={questions}
            renderItem={(question) => (
                <QuestionItem  info={question}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />:null
    );

}

export default QuestionList;
