import React from 'react'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import LoginPage from './login-components/login-container.js'
import AdminPage from './admin-components/admin-container.js';
import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import ClientPage from './client-components/client-container.js';

class App extends React.Component {
    render() {
        return (
            <div className={styles.back}>
                <Router>
                    <div>
                        <NavigationBar />
                        <Switch>
                            <Route exact path='/' component={Home} />
                            <Route exact path='/login' component={LoginPage} />
                            <Route exact path='/admin' component={AdminPage} /> 
                            <Route exact path='/error' component={ErrorPage} />
                            <Route exact path='/client' component={ClientPage} />
                            <Route render={() => <ErrorPage />} />
                        </Switch>
                    </div>
                </Router>
            </div>
        )
    }
}

export default App
