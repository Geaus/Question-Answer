import React, {Component, useEffect, useState} from 'react';
import {Card, Button, Collapse, List, Space} from 'antd';
import QuestionItem from "../QuestionItem/QuestionItem";
import {getQuestions, searchQuestion, searchQuestionByTag} from "../../../service/QuestionService/QuestionService";
import {useLocation, useParams} from "react-router";
import {LeftOutlined, RightOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";
const QuestionList =(props)=>{


    const navigate=useNavigate();

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const title=searchParams.get('title');
    const tag = searchParams.get('tag');
    const page=searchParams.get('page');


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
            params.append('page_id', 0);
            searchQuestion(params, updateQuestion);


        }
        else if(tag){
            const params = new URLSearchParams();
            params.append('uid', uid);
            console.log(tag);
            params.append('tag', tag);
            params.append('page_id', 0);
            searchQuestionByTag(params, setQuestions);
        }
       else{
            // sessionStorage.setItem('uid','1');
            const params = new URLSearchParams();
            params.append('uid', uid);
            //TODO:增加page_id的相关逻辑
            if(page===null){
                params.append('page_id', 0);
            }
            else{
                params.append('page_id', page);
            }

            getQuestions(params,setQuestions);
        }

    },[title, tag]);

    useEffect(() => {

        if(page===null){
            const params = new URLSearchParams();
            params.append('uid', uid);
            //TODO:增加page_id的相关逻辑
            if(title===null && tag===null){
                params.append('page_id', 0);
                getQuestions(params,setQuestions);
            }
            else if(title !==null && tag===null){
                params.append('page_id', 0);
                params.append('keyword', title);
                searchQuestion(params,setQuestions);
            }
            else{
                params.append('page_id', 0);
                params.append('tag', tag);
                searchQuestionByTag(params,setQuestions);
            }

        }
        else{
            const params = new URLSearchParams();
            params.append('uid', uid);
            //TODO:增加page_id的相关逻辑
            if(title===null && tag===null){
                params.append('page_id', page);
                getQuestions(params,setQuestions);
            }
            else if(title!==null && tag===null){
                params.append('page_id', page);
                params.append('keyword', title);
                searchQuestion(params,setQuestions);
            }
            else{
                params.append('page_id', page);
                params.append('tag', tag);
                searchQuestionByTag(params,setQuestions);
            }
        }

    },[page]);

    useEffect(() => {
        console.log(questions);
    },[questions]);


    const handleLeft=()=>{

        if(page===null || page==='0'){
            return;
        }
        else{

            let tmp=parseInt(page)-1;

            if(title===null && tag===null){
                navigate('/?page='+tmp);
            }
            else if(title!==null && tag===null){
                navigate('/?title=' +title+ '&page=' + tmp);
            }
            else{
                navigate('/?tag=' +tag+ '&page=' + tmp);
            }

            window.location.reload();
        }
    }

    const handleRight=()=>{

        if(page==null){

            if(title===null && tag===null){
                navigate('/?page=1');
            }
            else if(title!==null && tag===null){
                navigate('/?title=' +title+ '&page=1' );
            }
            else{
                navigate('/?tag=' +tag+ '&page=1' );
            }
            window.location.reload();

        }
        else{

            let tmp=parseInt(page)+1;

            if(title===null && tag===null){
                navigate('/?page='+tmp);
            }
            else if(title!==null && tag===null){
                navigate('/?title=' +title+ '&page=' + tmp);
            }
            else{
                navigate('/?tag=' +tag+ '&page=' + tmp);
            }

            window.location.reload();

        }

    }
    return (

        <div>

            <div>
                {
                    empty === false?<List
                        dataSource={questions}
                        renderItem={(question) => (
                            <QuestionItem  info={question}/>
                            // <Card key={question.id}>{question.title}</Card>
                        )}
                    />:null
                }
            </div>

            <div style={{margin:'auto'}}>
                <Space>
                    <Button type="text" icon={<LeftOutlined />} onClick={handleLeft}/>
                    <Button type="text" icon={<RightOutlined />} onClick={handleRight}/>
                </Space>

            </div>

        </div>

    );

}

export default QuestionList;
