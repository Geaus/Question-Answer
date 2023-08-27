import {Card} from "antd";
import gfm from 'remark-gfm';
import ReactMarkdown from 'react-markdown';
import React from "react";
import remarkMath from "remark-math";
import rehypeKatex from "rehype-katex";
import {Link} from "react-router-dom";
function ProfileAnswerCard(props) {
    return (
        <Card title={
            <Link to={{pathname: '/ques', search: '?qid='+props.info.question.id}}>
                <a>{props.info.question.title}</a>
            </Link>
            }
        >
            <ReactMarkdown remarkPlugins={[gfm, remarkMath]}
                           rehypePlugins={[rehypeKatex]}>{props.info.content}
            </ReactMarkdown>
        </Card>
    );
}
export default ProfileAnswerCard;