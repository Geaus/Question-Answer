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
import {Link} from "react-router-dom";
import {getQuestions} from "../../service/QuestionService";
// 点赞数量 点踩数量 提问数量 回答数量
function UserCard() {

    const [uid,setUid]=useState(0);

    const [open,setOpen]=useState(false);
    const [questionTitle, setQuestionTitle] = useState('');
    const [questionContent, setQuestionContent] = useState('');

    const [selectedTags, setSelectedTags] = useState([]); // 上方已选择的标签
    const [unselectedTags, setUnselectedTags] = useState(['Tag1', 'Tag2', 'Tag3', 'Tag4']); // 下方未选择的标签

    const handleAddTagToQuestion = (tag) => {
        const updatedSelectedTags = [...selectedTags, tag];
        const updatedUnselectedTags = unselectedTags.filter((t) => t !== tag);
        setSelectedTags(updatedSelectedTags);
        setUnselectedTags(updatedUnselectedTags);
    };

    const handleRemoveTag = (index) => {
        const removedTag = selectedTags[index];
        const updatedSelectedTags = selectedTags.filter((_, i) => i !== index);
        setSelectedTags(updatedSelectedTags);
        setUnselectedTags([...unselectedTags, removedTag]); // 修改这一行
    };

    const handleAsk=()=>{

        const title = questionTitle;
        const content = questionContent;

    }

    useEffect(() => {

        sessionStorage.setItem('uid','1');
        const uid=sessionStorage.getItem('uid');
        setUid(uid);


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


                                <Link to={`/profile/${uid}`}>
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
                        {selectedTags.map((tag, index) => (
                            <Tag
                                key={index}
                                closable
                                onClose={() => handleRemoveTag(index)}
                            >
                                {tag}
                            </Tag>
                        ))}
                    </div>
                    <Divider/>
                    <div>
                        {unselectedTags.map((tag, index) => (
                            <Tag
                                key={index}
                                onClick={() => handleAddTagToQuestion(tag)} // 添加点击事件

                            >
                                {tag}
                            </Tag>
                        ))}
                    </div>
                </Modal>

            </div>

        </div>
    );
}
export default UserCard;