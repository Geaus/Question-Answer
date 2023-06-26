import {Button, Card, Col, Row, Space, Statistic} from "antd";
import {
    DislikeOutlined,
    FormOutlined,
    LikeOutlined,
    QuestionOutlined
} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
// 点赞数量 点踩数量 提问数量 回答数量
function UserCard() {
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
                            <Button type="primary" size={"large"} href={"createQuestion"}>去提问！</Button>
                        </Col>
                        <Col span={12}>
                            <Button size={"large"} href={"profile"}>查看我的主页</Button>
                        </Col>
                    </Row>
                </Space>
            </Card>
        </div>
    );
}
export default UserCard;