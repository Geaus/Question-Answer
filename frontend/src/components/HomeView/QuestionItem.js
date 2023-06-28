import React, {Component, useState} from 'react';
import {Card, Button, Collapse, Row, Col, Space, Typography} from 'antd';
import {CaretDownOutlined, CaretUpOutlined, DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";


const { Meta } = Card;
const { Text } = Typography;

const QuestionPage =(props)=>{


    const [expanded,setExpanded]=useState(false);
    const  handleExpanded=()=>{
       setExpanded(!expanded);
    }
    const questionContent=props.info.content;
    const truncatedContent = questionContent.substring(0, 10) + '...';


    const  handleLike=()=>{

     }
    const handleDislike=()=>{

    }
    const handleStar=()=>{

     }


    return (
        <Card
            title={
                <Link to={{pathname:'/ques',search:'?qid='+props.info.id}}>
                    {props.info.title}
                </Link>
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

        >
            {props.info.user.userName+' : '}
            {expanded ? questionContent : truncatedContent}

            <Row gutter={16} style={{ marginTop: '10px' }}>
                <Col>
                    <Space>
                        <Button icon={<LikeOutlined />} />
                        <Text type="secondary">10</Text>
                    </Space>
                </Col>
                <Col>
                    <Space>
                        <Button icon={<DislikeOutlined />} />
                        <Text type="secondary">5</Text>
                    </Space>
                </Col>
                <Col>
                    <Button icon={<StarOutlined />} />
                </Col>
            </Row>

        </Card>
    );

}

export default QuestionPage;
