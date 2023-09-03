import React, {Component, useEffect, useReducer, useRef, useState} from 'react';
import {Card, Button, Collapse, List, Space, message} from 'antd';
import QuestionItem from "../QuestionItem/QuestionItem";
import {getQuestions, searchQuestion, searchQuestionByTag, getHotQuestion} from "../../../service/QuestionService/QuestionService";
import {useLocation, useParams} from "react-router";
import {LeftOutlined, RightOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";

const HotQuestionList = (props) => {
    const navigate=useNavigate();
    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const page=searchParams.get('page');
    const [uid,setUid]=useState(sessionStorage.getItem('uid'));
    const [questions,setQuestions] =useState([]);
    const [empty,setEmpty]=useState(false);

    const updateQuestion = (data) =>{
        if(data.length === 0 && page != null && page !== "0") {
            handleLeft();
            message.error("当前为最后一页");
        }
        else setQuestions(data);
    }

    useEffect(() => {
        if(page===null){
            const params = new URLSearchParams();
            params.append('uid', uid);
            params.append('page_id', 0);
            getHotQuestion(params,updateQuestion);
        }
        else{
            const params = new URLSearchParams();
            params.append('uid', uid);
            params.append('page_id', page);
            getHotQuestion(params,updateQuestion);
        }
    },[page]);

    useEffect(() => {
        console.log(questions);
    },[questions]);

    useEffect(() => {
        const interval = setInterval(() => {
            if(page===null){
                const params = new URLSearchParams();
                params.append('uid', uid);
                params.append('page_id', 0);
                getHotQuestion(params,updateQuestion);
            }
            else{
                const params = new URLSearchParams();
                params.append('uid', uid);
                params.append('page_id', page);
                getHotQuestion(params,updateQuestion);
            }
        }, 3000);
        return () => clearInterval(interval);
    }, [])


    const handleLeft=()=>{

        if(page===null || page==='0'){
            return;
        }
        else{
            let tmp=parseInt(page)-1;
            navigate('/hot?page=' + tmp);
            window.location.reload();
        }
    }

    const handleRight=()=>{

        if(page==null){
            navigate('/hot?page=1');
            window.location.reload();
        }
        else{
            let tmp=parseInt(page) + 1;
            navigate('/hot?page=' + tmp);
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
                            <div>
                                <QuestionItem  info={question}/>
                            </div>
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

export default HotQuestionList;
