import React from 'react';
import LoginModal from './components/login-modal'; 
import * as API_USERS from './api/login-api';

class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modalOpen: true,
            error: null,
        };
    }

    handleLogin = (user) => {
        API_USERS.logIn(user, (response, status) => {
            if (status === 200 && response.role !== 'denied' && response.userId !== 'denied') {
                // Store user data in localStorage
                localStorage.setItem('userId', response.userId);
                localStorage.setItem('role', response.role);
                localStorage.setItem('token', response.token);

                const role = response.role;
                console.log(role);

                if (role === 'admin') {
                    this.props.history.push('/admin');
                } else if (role === 'client') {
                    this.props.history.push('/client');
                }
            } else {
                const errorMessage = response.error || 'Invalid login';
                this.setState({ error: errorMessage });
            }
        });
    }

    closeModal = () => {
        this.setState({ modalOpen: false });
    }

    render() {
        return (
            <div>
                <LoginModal
                    isOpen={this.state.modalOpen}
                    onLogin={this.handleLogin}
                    onClose={this.closeModal}
                    error={this.state.error}
                />
            </div>
        );
    }
}

export default LoginPage;
