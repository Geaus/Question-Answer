import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import QuestionItem from "./QuestionItem";
import {getQuestions, searchQuestion, searchQuestionByTag} from "../../service/QuestionService";
import {useLocation} from "react-router";

const QuestionList =(props)=>{

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const title=searchParams.get('title');
    const tag = searchParams.get('tag');
    const [reload, setReload] = useState(false);



    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [questions,setQuestions] =useState([]);
    const [empty,setEmpty]=useState(false);

    const updateQuestion = (data) =>{
        setQuestions(data);
    }
    useEffect(() => {

        if(title){
            const params = new URLSearchParams();
            params.append('uid', uid);
            console.log(title);
            params.append('keyword', title);
            searchQuestion(params, updateQuestion);


        }
        else if(tag){
            const params = new URLSearchParams();
            params.append('uid', uid);
            console.log(tag);
            params.append('tag', tag);
            searchQuestionByTag(params, setQuestions);
        }
       else{

            // sessionStorage.setItem('uid','1');
            const params = new URLSearchParams();
            params.append('uid', uid);
            getQuestions(params,setQuestions);
            console.log(questions)
        }


    },[title, tag]);

    useEffect(() => {

    },[questions]);

    useEffect(() => {
        if (new URLSearchParams(location.search).get('reload') === 'true') {
            const params = new URLSearchParams();
            params.append('uid', uid);
            getQuestions(params,setQuestions);
            console.log(questions)
            setReload(true);
        }
    }, [location.search, tag]);

    useEffect(() => {
        if (reload) {

            setReload(false); // 重置标志，避免重复加载
        }
    }, [reload]);


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
