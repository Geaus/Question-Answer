import React, {Component, useState} from 'react';
import { Card, Button, Collapse } from 'antd';
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import Link from "antd/es/typography/Link";

const QuestionPage =(props)=>{

    const [expanded,setExpanded]=useState(false);

    const  handleExpanded=()=>{
       setExpanded(!expanded);
    }


    const  handleLike=()=>{

     }
    const handleDislike=()=>{

    }
    const handleStar=()=>{

     }
    const questionContent=props.info.content;
    const truncatedContent = questionContent.substring(0, 10) + '...';

    return (
        <Card
            title={
                <a href={`/ques/${props.info.id}`}
                >
                    {props.info.title}
                </a>
            }
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
            actions={[
                <LikeOutlined key="like" onClick={handleLike}/>,
                <DislikeOutlined key="dislike" onClick={handleDislike}/>,
                <StarOutlined key="start" onClick={handleStar}/>
            ]}
        >
            {expanded ? questionContent : truncatedContent}
        </Card>
    );

}

export default QuestionPage;
