import {Avatar, Button, Card, Divider, Space, Tag} from "antd";
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

function AnswerCard(props) {

    const [expanded,setExpanded]=useState(false);
    const answerContent=props.info.content;
    const truncatedContent = answerContent.substring(0, 10) + '...';

    const  handleExpanded=()=>{
        setExpanded(!expanded);
    }

    function handleLike() {

    }
    function handleDislike() {

    }
    function handleSubscribe() {

    }
    return (
        <div>
            <Card
                size={"small"}
                actions={[
                    <LikeOutlined key="like" onClick={handleLike}/>,
                    <DislikeOutlined key="dislike" onClick={handleDislike}/>,
                    <UserAddOutlined key="subscribe" onClick={handleSubscribe}/>
                ]}

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
                <Meta
                    avatar={<Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />}
                    title={props.info.title}
                    description={expanded ? answerContent : truncatedContent}
                />


            </Card>
        </div>
    );
}
export default AnswerCard;