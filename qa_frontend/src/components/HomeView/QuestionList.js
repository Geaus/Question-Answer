import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import QuestionItem from "./QuestionItem";
import {getQuestions, searchQuestion} from "../../service/QuestionService";
import {useLocation} from "react-router";

const QuestionList =(props)=>{

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const title=searchParams.get('title')
    const [reload, setReload] = useState(false);



    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [questions,setQuestions] =useState([]);
    const [empty,setEmpty]=useState(false);

    useEffect(() => {

        if(title===null ||title ==="" ){
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

        }


    },[title]);

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
    }, [location.search]);

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
