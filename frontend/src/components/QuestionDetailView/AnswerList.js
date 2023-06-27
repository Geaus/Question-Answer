import {Avatar, Button, Card, Divider, List, Space, Tag} from "antd";
import {
    CaretDownOutlined,
    CaretUpOutlined,
    DislikeOutlined,
    LikeOutlined,
    StarOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import React, {useState} from "react";
import {answerList} from "../../App";
import AnswerCard from "./AnswerCard";

function AnswerList(props) {

    return (

        <List
            dataSource={props.info}
            renderItem={(answer) => (
                <AnswerCard  info={answer}/>
                // <Card key={question.id}>{question.title}</Card>
            )}
        />

    );
}
export default AnswerList;