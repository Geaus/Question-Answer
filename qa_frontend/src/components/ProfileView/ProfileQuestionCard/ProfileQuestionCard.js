import {Card} from "antd";

function ProfileQuestionCard(props) {
    return (
        <Card
            title={<a href="src/components/ProfileView#">{props.info.title}</a>}
        >
            <p>{props.info.content}</p>
        </Card>
    );
}
export default ProfileQuestionCard;