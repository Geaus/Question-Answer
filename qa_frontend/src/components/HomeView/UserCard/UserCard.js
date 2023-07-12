import {Button, Card, Col, Divider, Input, Modal, Row, Space, Statistic, Tag} from "antd";
import {
    ArrowDownOutlined,
    ArrowUpOutlined,
    DislikeOutlined,
    FormOutlined,
    LikeOutlined,
    QuestionOutlined
} from "@ant-design/icons";

import Meta from "antd/es/card/Meta";
import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {askQuestion, getQuestions, listTag} from "../../../service/QuestionService/QuestionService";
// 点赞数量 点踩数量 提问数量 回答数量
function UserCard() {

    const navigate = useNavigate();

    const [uid,setUid]=useState(sessionStorage.getItem('uid'));

    const [open,setOpen]=useState(false);

    const [questionTitle, setQuestionTitle] = useState('');
    const [questionContent, setQuestionContent] = useState('');
    const [selectedTags, setSelectedTags] = useState([]); // 上方已选择的标签
    const [unselectedTags, setUnselectedTags] = useState([]); // 下方未选择的标签

    const handleAddTagToQuestion = (tag) => {
        const updatedSelectedTags = [...selectedTags, tag];
        const updatedUnselectedTags = unselectedTags.filter((t) => t !== tag);
        setSelectedTags(updatedSelectedTags);
        setUnselectedTags(updatedUnselectedTags);
    };

    const handleRemoveTag = (tag) => {

        const updatedSelectedTags =  selectedTags.filter((t) => t !== tag);
        const updatedUnselectedTags =[...unselectedTags, tag];
        setSelectedTags(updatedSelectedTags);
        setUnselectedTags(updatedUnselectedTags);
    };

    const handleAsk=()=>{

        const callback=(data)=>{
            navigate("/ques?qid="+data.id);
        }

        const title = questionTitle;
        const content = questionContent;

        const params = new URLSearchParams();
        params.append('uid', uid);
        params.append('title',title);
        params.append('content', content);

        askQuestion(params,selectedTags,callback);

        setOpen(false);
        setSelectedTags([]);
        listTag(setUnselectedTags);
        setQuestionTitle('');
        setQuestionContent('');



    }

    useEffect(() => {

        // sessionStorage.setItem('uid','1');

        listTag(setUnselectedTags);

    },[]);

    return (
        <div>

            <div>
                <Card
                    title="用户中心"
                    size={"default"}

                >
                    <Space direction={"vertical"}>
                        <Meta title={"统计数据"}/>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Statistic title="提问数量" value={1} prefix={<QuestionOutlined />} suffix={"次"}/>
                            </Col>
                            <Col span={12}>
                                <Statistic title="回答数量" value={1} prefix={<FormOutlined />} suffix="次" />
                            </Col>
                        </Row>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Statistic title="点赞数量" value={1} prefix={<LikeOutlined />} suffix={"次"}/>
                            </Col>
                            <Col span={12}>
                                <Statistic title="点踩数量" value={1} prefix={<DislikeOutlined />} suffix="次" />
                            </Col>
                        </Row>
                        <Meta title={"您可以"} />
                        <Row>
                            <Col span={12}>
                                <Button type="primary"  onClick={()=>{setOpen(true)}}>去提问！</Button>
                            </Col>
                            <Col span={12}>

                                <Link to={{pathname:'/profile',search:'?uid='+uid}}>
                                    <Button  >查看我的主页</Button>
                                </Link>

                            </Col>
                        </Row>
                    </Space>
                </Card>
            </div>

            <div>
                <Modal
                    visible={open}
                    onOk={handleAsk}
                    onCancel={() => { setOpen(false) }}
                >
                    <Input
                        placeholder="问题标题"
                        value={questionTitle}
                        onChange={(e) => { setQuestionTitle(e.target.value) }}
                    />
                    <Input.TextArea
                        placeholder="问题内容"
                        value={questionContent}
                        onChange={(e) => { setQuestionContent(e.target.value) }}
                        autoSize={{ minRows: 3, maxRows: 6 }}
                    />
                    <div>
                        {selectedTags.map((tag) => (
                            <Tag
                                data-testId="ask-select-key"
                                key={tag.id}
                                closable
                                onClose={() => handleRemoveTag(tag)}
                            >
                                {tag.content}
                            </Tag>
                        ))}
                    </div>
                    <Divider/>
                    <div>
                        {unselectedTags.map((tag) => (
                            <Tag
                                data-testId="ask-unselect-key"
                                key={tag.id}
                                onClick={() => handleAddTagToQuestion(tag)} // 添加点击事件
                            >
                                {tag.content}
                            </Tag>
                        ))}
                    </div>
                </Modal>

            </div>

        </div>
    );
}
export default UserCard;