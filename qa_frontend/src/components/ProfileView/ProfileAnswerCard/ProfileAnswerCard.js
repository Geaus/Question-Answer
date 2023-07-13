import {Card} from "antd";
import gfm from 'remark-gfm';
import ReactMarkdown from 'react-markdown';
import React from "react";
import remarkMath from "remark-math";
import rehypeKatex from "rehype-katex";
function ProfileAnswerCard(props) {
    return (
        <Card title={<a href="src/components/ProfileView#">{props.info.question.title}</a>}>
            <ReactMarkdown remarkPlugins={[gfm, remarkMath]}
                           rehypePlugins={[rehypeKatex]}>{props.info.content}
            </ReactMarkdown>
        </Card>
    );
}
export default ProfileAnswerCard;