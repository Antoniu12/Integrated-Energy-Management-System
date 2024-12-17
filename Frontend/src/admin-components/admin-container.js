import React from 'react';
import * as API_ADMIN from './api/admin-api';
import { Button } from 'reactstrap';
import ClientTable from './components/client-components/client-table';
import ClientModal from './components/client-components/client-modal';
import DeviceTable from './components/device-components/device-table';
import DeviceModal from './components/device-components/device-modal';
import AdminChatModal from './components/chat-components/chat-widget';

class AdminPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            devices: [],
            showUserModal: false,
            showDeviceModal: false,
            newUser: { id: '', name: '', password: '', age: '', role: '', address: '' },
            newDevice: { id: '', userId: '', description: '', address: '', energyConsumption: '' },
            viewDevicesForUser: [],
            isChatOpen: false, // Added chat visibility state
            selectedUserForChat: null, // Added state for the selected user for chat
        };
    }

    componentDidMount() {
        const userRole = localStorage.getItem('role');
        if (userRole !== 'admin') {
            alert('Unauthorized access');
            this.props.history.push('/login');
            return;
        }
        this.fetchUsers();
        this.fetchDevices();
    }

    fetchUsers = () => {
        const token = localStorage.getItem('token');
        API_ADMIN.getPersons(token, (response) => this.setState({ users: response }));
    };

    fetchDevices = () => {
        API_ADMIN.getDevices((response) => this.setState({ devices: response }));
    };

    fetchDevicesForUser = (userId) => {
        const token = localStorage.getItem('token');
        API_ADMIN.getDevicesByUserId(userId, token, (response) =>
            this.setState({ viewDevicesForUser: response })
        );
    };

    toggleUserModal = () =>
        this.setState((prevState) => ({ showUserModal: !prevState.showUserModal }));

    toggleDeviceModal = () =>
        this.setState((prevState) => ({ showDeviceModal: !prevState.showDeviceModal }));

    handleUserSubmit = () => {
        const { newUser } = this.state;
        const token = localStorage.getItem('token');
        if (newUser.id) {
            API_ADMIN.updatePerson(newUser, token, () => {
                this.fetchUsers();
                this.resetUserForm();
            });
        } else {
            API_ADMIN.postPerson(newUser, token, () => {
                this.fetchUsers();
                this.resetUserForm();
            });
        }
    };

    handleDeviceSubmit = () => {
        const { newDevice } = this.state;
        if (newDevice.id) {
            API_ADMIN.updateDevice(newDevice, () => {
                this.fetchDevices();
                this.resetDeviceForm();
            });
        } else {
            API_ADMIN.postDevice(newDevice, () => {
                this.fetchDevices();
                this.resetDeviceForm();
            });
        }
    };

    resetUserForm = () =>
        this.setState({
            showUserModal: false,
            newUser: { id: '', name: '', password: '', age: '', role: '', address: '' },
        });

    resetDeviceForm = () =>
        this.setState({
            showDeviceModal: false,
            newDevice: { id: '', userId: '', description: '', address: '', energyConsumption: '' },
        });

    handleDeleteUser = (userId) => {
        const token = localStorage.getItem('token');
        API_ADMIN.deletePerson(userId, token, () => {
            this.fetchUsers();
            this.fetchDevices();
        });
    };

    handleDeleteDevice = (deviceId) => {
        API_ADMIN.deleteDevice(deviceId, this.fetchDevices);
    };

    editUser = (user) => this.setState({ newUser: user, showUserModal: true });

    editDevice = (device) => this.setState({ newDevice: device, showDeviceModal: true });

    handleSelectUserForChat = (userId) => {
        console.log(`Selected user for chat: ${userId}`);
        this.setState({ selectedUserForChat: userId, isChatOpen: true });
    };

    closeChat = () => {
        this.setState({ isChatOpen: false, selectedUserForChat: null });
    };

    render() {
        const {
            users,
            devices,
            showUserModal,
            showDeviceModal,
            newUser,
            newDevice,
            viewDevicesForUser,
            isChatOpen,
            selectedUserForChat,
        } = this.state;

        return (
            <div>
                <h1>Admin Dashboard</h1>

                <h2>Clients</h2>
                <Button
                    color="primary"
                    onClick={() =>
                        this.setState({
                            newUser: { id: '', name: '', password: '', age: '', role: '', address: '' },
                            showUserModal: true,
                        })
                    }
                >
                    Add Client
                </Button>
                <ClientTable
                    users={users}
                    onEditUser={this.editUser}
                    onDeleteUser={this.handleDeleteUser}
                    onViewDevices={this.fetchDevicesForUser}
                    onSelectUserForChat={this.handleSelectUserForChat} // Added for chat selection
                />

                <h2>All Devices</h2>
                <Button
                    color="primary"
                    onClick={() =>
                        this.setState({
                            newDevice: {
                                id: '',
                                userId: '',
                                description: '',
                                address: '',
                                energyConsumption: '',
                            },
                            showDeviceModal: true,
                        })
                    }
                >
                    Add Device
                </Button>
                <DeviceTable
                    devices={devices}
                    onEditDevice={this.editDevice}
                    onDeleteDevice={this.handleDeleteDevice}
                />

                <h3>Devices for Selected User</h3>
                <DeviceTable devices={viewDevicesForUser} />

                <ClientModal
                    isOpen={showUserModal}
                    toggle={this.toggleUserModal}
                    user={newUser}
                    onChange={(field, value) =>
                        this.setState({ newUser: { ...newUser, [field]: value } })
                    }
                    onSave={this.handleUserSubmit}
                />

                <DeviceModal
                    isOpen={showDeviceModal}
                    toggle={this.toggleDeviceModal}
                    device={newDevice}
                    onChange={(field, value) =>
                        this.setState({ newDevice: { ...newDevice, [field]: value } })
                    }
                    onSave={this.handleDeviceSubmit}
                />

                {/* Chat Modal */}
                {isChatOpen && (
                    <AdminChatModal
                        selectedUser={selectedUserForChat}
                        isOpen={isChatOpen}
                        onClose={this.closeChat}
                    />
                )}
            </div>
        );
    }
}

export default AdminPage;
