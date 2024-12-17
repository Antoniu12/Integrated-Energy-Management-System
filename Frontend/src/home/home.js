import React from 'react';

import BackgroundImg from '../commons/images/app_background.jpg';

import {Button, Container, Jumbotron} from 'reactstrap';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "700px",
    backgroundImage: `url(${BackgroundImg})`
};
const textStyle = {color: 'white', };

class Home extends React.Component {

    componentDidMount() {
        localStorage.clear()
    }
    
    render() {

        return (

            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <h1 className="display-3" style={textStyle}>Integrated Energy Management Platform</h1>
                        <p className="lead" style={textStyle}> <b>Enabling real time energy consumption and devices</b> </p>
                        <hr className="my-2"/>
                        <p  style={textStyle}> <b>This assignment represents the first module of the distributed software system "Integrated
                            Energy Management Platform assistance that represents the final project
                            for the Distributed Systems course. </b> </p>
                        <p className="lead">
                            <Button color="primary" onClick={() => window.open('https://dsrl.eu/courses/sd/')}>Learn
                                More</Button>
                        </p>
                    </Container>
                </Jumbotron>

            </div>
        )
    };
}

export default Home
