import {Card} from "antd";

function ProfileQuestionCard(props) {
    return (
        <Card
            title={<a href="#">{props.info.title}</a>}
        >
            <p>{props.info.content}</p>
        </Card>
    );
}
export default ProfileQuestionCard;