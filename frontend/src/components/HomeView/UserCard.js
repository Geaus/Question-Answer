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
import Link from "antd/es/typography/Link";
import {useState} from "react";
// 点赞数量 点踩数量 提问数量 回答数量
function UserCard() {


    const [open,setOpen]=useState(false);
    const [questionTitle, setQuestionTitle] = useState('');
    const [questionContent, setQuestionContent] = useState('');
    const [questionTags, setQuestionTags] = useState([]);


    const handleAsk=()=>{

        const title = questionTitle;
        const content = questionContent;
        const tags = questionTags;

    }

    const handleRemoveTag = (index) => {
        const updatedTags = [...questionTags];
        updatedTags.splice(index, 1);
        setQuestionTags(updatedTags);
    };


    return (
        <div>
            <Card
                title="用户中心"
                size={"default"}
                hoverable={true}
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
                            <Button type="primary" size={"large"} onClick={()=>{setOpen(true)}}>去提问！</Button>
                        </Col>
                        <Col span={12}>

                            <Link to={"/profile"}>
                                <Button size={"large"} href={"/profile"}>查看我的主页</Button>
                            </Link>

                        </Col>
                    </Row>
                </Space>
            </Card>


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
                <Input
                    placeholder="输入标签，按回车添加"
                    value={''}
                    onPressEnter={(e) => {
                        const tagValue = e.target.value.trim();
                        if (tagValue !== '') {
                            setQuestionTags([...questionTags, tagValue]);
                            e.target.value = '';
                        }
                    }}
                />
                <div>
                    {questionTags.map((tag, index) => (
                        <Tag key={index} closable onClose={() => handleRemoveTag(index)}>
                            {tag}
                        </Tag>
                    ))}
                </div>
            </Modal>



        </div>
    );
}
export default UserCard;