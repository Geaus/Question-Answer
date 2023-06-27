import {Button, Card, Divider, Space, Tag} from "antd";
import Meta from "antd/es/card/Meta";
import {DislikeOutlined, LikeOutlined, StarOutlined} from "@ant-design/icons";
import {useParams} from "react-router";
import {useEffect, useState} from "react";
import {questionList} from "../../App";
import Answer from "./Editor";

function QuestionCard(props) {


    const { id } = useParams();
    console.log(id);
    const [question,setQuestion] =useState({
        id:0,
        title:'',
        content:''
    });
    const[answer,setAnswer] =useState(false);

    function handleLike() {

    }
    function handleDislike() {

    }
    function handleStar() {

    }

    const handleAnswer=()=>{

        setAnswer(!answer);
    }
    useEffect(() => {

        // eslint-disable-next-line array-callback-return
        questionList.map((item) =>{
                if(item.id.toString()===id.toString()){
                    setQuestion(item);
                }
        }
        );

    }, [id]);


    return (
        <div>
            <Card
                size={"default"}
                actions={[
                    <LikeOutlined key="like" onClick={handleLike}/>,
                    <DislikeOutlined key="dislike" onClick={handleDislike}/>,
                    <StarOutlined key="start" onClick={handleStar}/>
                ]}
            >
                <h1>{question.title}</h1>
                <p1>{question.content}</p1>

                <Divider />
                <Space size={[0, 3]} wrap>
                    <Tag>magenta</Tag>
                    <Tag>red</Tag>
                    <Tag>volcano</Tag>
                </Space>

                <Button
                    style={{float:'right'}}
                    onClick={handleAnswer}
                >
                    回答问题
                </Button>
            </Card>


            {
                answer&&(
                    <div >
                        <Answer/>
                    </div>

                )
            }

        </div>
    );
}
export default QuestionCard;