import ProfileMenu from "../components/ProfileMenu";
import {Card, Space} from "antd";
import ProfileUserInfo from "../components/ProfileUserInfo";
import {Divider} from "antd/lib";

function ProfileView(props) {
    return (
        <Space direction={"vertical"} align={"center"}>
            <Card>
                <ProfileUserInfo />
            </Card>
            <Card>
                <ProfileMenu />
            </Card>
        </Space>
    );
}
export default ProfileView;