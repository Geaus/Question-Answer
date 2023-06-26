import React, {Component, useState} from 'react';
import {Card, Button, Collapse, List} from 'antd';
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import Link from "antd/es/typography/Link";
import {questionList} from "../../App";
import QuestionItem from "./QuestionItem";

const QuestionList =(props)=>{


    return (
        <List
            dataSource={props.info}
            renderItem={(question) => (
                <QuestionItem  info={question}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />
    );

}

export default QuestionList;
