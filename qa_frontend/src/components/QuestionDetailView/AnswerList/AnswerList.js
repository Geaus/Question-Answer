import {Avatar, Button, Card, Divider, List, Space, Tag} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined,
    DislikeOutlined, LeftOutlined,
    LikeOutlined, RightOutlined,
    StarOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import React, {useEffect, useState} from "react";
import {answerList} from "../../../App";
import AnswerCard from "../AnswerCard/AnswerCard";
import {useLocation, useParams} from "react-router";
import {getAnswers, getQuestion} from "../../../service/QuestionService/QuestionService";
import {useNavigate} from "react-router-dom";

function AnswerList(props) {

    const navigate=useNavigate();
    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);

    const id=searchParams.get('qid');
    const page=searchParams.get('page');
    console.log(page);

    const [answers,setAnswers]=useState([]);

    useEffect(() => {

        const params = new URLSearchParams();
        params.append('uid', sessionStorage.getItem('uid'));
        params.append('qid', id);
        //TODO:增加page_id的相关逻辑
        params.append('page_id', 0);

        // eslint-disable-next-line array-callback-return
       getAnswers(params,setAnswers);

    }, [id]);


    useEffect(()=>{


        if(page===null){

            const params = new URLSearchParams();
            params.append('uid', sessionStorage.getItem('uid'));
            params.append('qid', id);
            //TODO:增加page_id的相关逻辑
            params.append('page_id', 0);

            // eslint-disable-next-line array-callback-return
            getAnswers(params,setAnswers);
        }
        else{
            const params = new URLSearchParams();
            params.append('uid', sessionStorage.getItem('uid'));
            params.append('qid', id);
            //TODO:增加page_id的相关逻辑
            params.append('page_id', page);

            // eslint-disable-next-line array-callback-return
            getAnswers(params,setAnswers);
        }

    },[page]);

    const handleLeft=()=>{

        if(page===null || page==='0'){
            return;
        }
        else{

            let tmp=parseInt(page)-1;
            navigate('/ques?qid='+id+'&page='+tmp);
            window.location.reload();
        }
    }

    const handleRight=()=>{

        if(page==null){

            navigate('/ques?qid='+id+'&page=1');
            window.location.reload();

        }
        else{

            let tmp=parseInt(page)+1;
            navigate('/ques?qid='+id+'&page='+tmp);
            window.location.reload();

        }
    }

    return (

        <div>
            <List
                dataSource={answers}
                renderItem={(answer) => (
                    <AnswerCard  info={answer}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />


            <div style={{margin:'auto'}}>
                <Space>
                    <Button type="text" icon={<LeftOutlined />} onClick={handleLeft}/>
                    <Button type="text" icon={<RightOutlined />} onClick={handleRight}/>
                </Space>

            </div>

        </div>

    );
}
export default AnswerList;