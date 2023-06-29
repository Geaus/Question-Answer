import {
    DislikeOutlined,
    FormOutlined,
    LikeOutlined,
    QuestionOutlined,
    StarFilled, StarOutlined,
    UserAddOutlined
} from "@ant-design/icons";
import {List, Menu} from "antd";
import React, {useEffect, useState} from "react";
import ProfileAnswerCard from "./ProfileAnswerCard";
import ProfileQuestionCard from "./ProfileQuestionCard";
import ProfileUserCard from "./ProfileUserCard";
import {
    getAnswered,
    getAsked, getDislikedAnswer,
    getDislikedQuestion, getFollowed,
    getLikedAnswer,
    getLikedQuestion, getMarkedQuestion
} from "../../service/ProfileService";
import {useLocation} from "react-router";


function ProfileMenu() {

    const location=useLocation();
    const searchParams=new URLSearchParams(location.search);
    const uid=searchParams.get('uid')

    const self= uid===sessionStorage.getItem('uid');
    console.log(self)

    const [currentMenuItem,  setCurrentMenuItem] = useState('questions'); // 初始选中的菜单项，默认为 'menu1'

    const [questions,setQuestions] =useState([]);
    const [answers,setAnswers] =useState([]);

    const [follows,setFollows]=useState([]);

    useEffect(() => {


        const params = new URLSearchParams();
        params.append('uid', uid);

        if (currentMenuItem === 'questions') {
            getAsked(params,setQuestions);
        }
        else if (currentMenuItem === 'answers') {
            getAnswered(params,setAnswers);
        }
        else if (currentMenuItem === 'likeQuestion') {
            getLikedQuestion(params,setQuestions);
        }
        else if (currentMenuItem === 'likeAnswer') {
            getLikedAnswer(params,setAnswers);
        }
        else if (currentMenuItem === 'dislikeQuestion') {
            getDislikedQuestion(params,setQuestions);
        }
        else if (currentMenuItem === 'dislikeAnswer') {
            getDislikedAnswer(params,setAnswers);
        }
        else if (currentMenuItem === 'subscribe') {
            getFollowed(params,setFollows);
        }
        else if (currentMenuItem === 'star') {
            getMarkedQuestion(params,setQuestions);
        }

        console.log(questions)


    },[currentMenuItem,uid]);


    const handleMenuClick = (e) => {
        setCurrentMenuItem(e.key);

    };

    let content;

    // 根据当前选中的菜单项，设置不同的内容
    if (currentMenuItem === 'questions') {
        content =
            <List
                dataSource={questions}
                renderItem={(question) => (
                    <ProfileQuestionCard  info={question}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />

    }
    else if (currentMenuItem === 'answers') {
        content =
            <List
                dataSource={answers}
                renderItem={(answer) => (
                    <ProfileAnswerCard  info={answer}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }
    else if (currentMenuItem === 'likeQuestion') {
        content =
            <List
                dataSource={questions}
                renderItem={(question) => (
                    <ProfileQuestionCard  info={question}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }
    else if (currentMenuItem === 'likeAnswer') {
        content =
            <List
                dataSource={answers}
                renderItem={(answer) => (
                    <ProfileAnswerCard  info={answer}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }
    else if (currentMenuItem === 'dislikeQuestion') {
        content =
            <List
                dataSource={questions}
                renderItem={(question) => (
                    <ProfileQuestionCard  info={question}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }
    else if (currentMenuItem === 'dislikeAnswer') {
        content =
            <List
                dataSource={answers}
                renderItem={(answer) => (
                    <ProfileAnswerCard  info={answer}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }
    else if (currentMenuItem === 'subscribe') {
        content =

                self&&(
                <List
                    dataSource={follows}
                    renderItem={(follow) => (
                        <ProfileUserCard  info={follow}/>
                        // <Card key={question.id}>{question.title}</Card>
                    )}
                />
              );


    }
    else if (currentMenuItem === 'star') {
        content =
            <List
                dataSource={questions}
                renderItem={(question) => (
                    <ProfileQuestionCard  info={question}/>
                    // <Card key={question.id}>{question.title}</Card>
                )}
            />
    }

    const items = [
        {
            // eslint-disable-next-line no-mixed-operators
            label: self&&(
                <a>我的提问</a>
            ) || !self&&( <a>ta的提问</a>),

            key: 'questions',
            icon: <QuestionOutlined />,
        },
        {
            label: self&&(
                <a>我的回答</a>
            ) || !self&&( <a>ta的回答</a>),
            key: 'answers',
            icon: <FormOutlined />,
        },
        {
            label: self&&(
                <a>我赞过的</a>
            ) || !self&&( <a>ta赞过的</a>),
            key: 'likes',
            icon: <LikeOutlined />,
            children: [
                {
                    label:self&&(
                        <a>我赞过的问题</a>
                    ) || !self&&( <a>ta赞过的问题</a>),
                    key:'likeQuestion'
                },
                {
                    label:self&&(
                        <a>我赞过的回答</a>
                    ) || !self&&( <a>ta赞过的回答</a>),
                    key:'likeAnswer'
                }
            ]
        },
        {
            label: self&&(
                <a>我踩过的</a>
            ) || !self&&( <a>ta踩过的</a>),
            key: 'dislikes',
            icon: <DislikeOutlined />,
            children: [
                {
                    label:self&&(
                        <a>我踩过的问题</a>
                    ) || !self&&( <a>ta踩过的问题</a>),
                    key:'dislikeQuestion'
                },
                {
                    label: self&&(
                        <a>我踩过的回答</a>
                    ) || !self&&( <a>ta踩过的回答</a>),
                    key:'dislikeAnswer'
                }
            ]
        },

        self&&{
            label: (
                <a>我的关注</a>
            ),
            key: 'subscribe',
            icon: <UserAddOutlined />,
        },
        {
            label:self&&(
                <a>我收藏的</a>
            ) || !self&&( <a>ta收藏的</a>),
            key: 'star',
            icon: <StarOutlined />
        }
    ];
    return (
        <div>
            <Menu
                onClick={handleMenuClick}
                selectedKeys={[currentMenuItem]}
                mode={"horizontal"}
                items={items}
            />
            {content}
        </div>
    );
}
export default ProfileMenu;