import {UserOutlined} from "@ant-design/icons";
import {Avatar, Descriptions, Space} from "antd";

function ProfileUserInfo(props) {
    return (
        <div style={{padding: '50px'}}>
            <Space direction="horizontal" size={40}>
                <Avatar size={128} icon={<UserOutlined />} />
                <Descriptions title="User Info">
                    <Descriptions.Item label="UserName">Zhou Maomao</Descriptions.Item>
                    <Descriptions.Item label="Telephone">1810000000</Descriptions.Item>
                    <Descriptions.Item label="Live">Hangzhou, Zhejiang</Descriptions.Item>
                    <Descriptions.Item label="Remark">empty</Descriptions.Item>
                    <Descriptions.Item label="Address">
                        No. 18, Wantang Road, Xihu District, Hangzhou, Zhejiang, China
                    </Descriptions.Item>
                </Descriptions>
            </Space>
        </div>
    );
}
export default ProfileUserInfo;