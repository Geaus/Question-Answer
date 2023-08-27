import {Card} from "antd";
import {Link} from "react-router-dom";

function ProfileQuestionCard(props) {
    return (
        <Card
            title={
            <Link to={{pathname: '/ques', search: '?qid='+props.info.id}}>
                <a>{props.info.title}</a>
            </Link>
        }
        >
            <p>{props.info.content}</p>
        </Card>
    );
}
export default ProfileQuestionCard;