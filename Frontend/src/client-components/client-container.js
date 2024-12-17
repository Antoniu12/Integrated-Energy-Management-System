import React from 'react';
import * as API_CLIENT from './api/client-api';
import ClientTable from './commons/client-components/client-table';
import ClientModal from './commons/client-components/client-modal';
import DeviceTable from './commons/device-components/device-table';
import UserChatModal from './commons/chat-components/chat-widget';


class ClientPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            client: { id: '', name: '', age: '', address: '', role: '', password: '' },
            devices: [],
            showModal: false,
            socket: null,
            stompClient: null,
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    componentDidMount() {
        const userRole = localStorage.getItem('role'); 
        if (userRole !== 'client') {
            alert('Unauthorized access');
            this.props.history.push('/login');
            return;
        }

        this.fetchClientData();
        this.fetchClientDevices();

        this.connectWebSocket();  
    }

    componentWillUnmount() {
        this.disconnectWebSocket();  
    }

    fetchClientData = () => {
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('token');
        API_CLIENT.getClientData(userId, token, (response) => {
            this.setState({ client: response });
        });
    }

    fetchClientDevices = () => {
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('token');
        API_CLIENT.getClientDevices(userId, token, (response) => {
            this.setState({ devices: response });
        });
    }

    handleClientUpdate = () => {
        const { client } = this.state;
        const token = localStorage.getItem('token');
        API_CLIENT.updateClientData(client, token, () => {
            this.setState({ showModal: false });
            this.fetchClientData(); 
        });
    }

    toggleModal = () => {
        this.setState(prevState => ({ showModal: !prevState.showModal }));
    }

    handleInputChange = (field, value) => {
        this.setState(prevState => ({
            client: {
                ...prevState.client,
                [field]: value,
            }
        }));
    }

    connectWebSocket = () => {
        const userId = localStorage.getItem('userId');
        const socketUrl = `ws://localhost:8082/ws/energyNotifications?userId=${userId}`;
    
        const socket = new WebSocket(socketUrl);
    
        socket.onopen = () => {
            console.log('WebSocket connection established for user:', userId);
        };
    
        socket.onmessage = (event) => {
            const message = event.data; 
            console.log('Received message:', message);
        
            alert(message);
        };
    
        socket.onclose = (event) => {
            console.log('WebSocket connection closed:', event);
        };
    
        socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };
    
        this.setState({ socket });
    };
    

    disconnectWebSocket = () => {
        const { stompClient } = this.state;
        if (stompClient) {
            stompClient.deactivate(() => {
                console.log('Disconnected');
            });
        }
    }

    render() {
        const { client, devices, showModal } = this.state;

        return (
            <div>
                <h1>Client Dashboard</h1>
                
                <ClientTable client={client} openModal={this.toggleModal} />
                
                <ClientModal
                    isOpen={showModal}
                    client={client}
                    toggle={this.toggleModal}
                    handleChange={this.handleInputChange}
                    handleSave={this.handleClientUpdate}
                />

                <DeviceTable devices={devices} />

                <UserChatModal userId={localStorage.getItem('userId')} />
            </div>
        );
    }
}

export default ClientPage;
