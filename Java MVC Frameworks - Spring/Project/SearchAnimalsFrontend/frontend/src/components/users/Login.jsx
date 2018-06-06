import React from 'react';
import {Link} from "react-router-dom";
import { Button } from "react-bootstrap";
import InputField from "../utility/InputField";
import axiosInstance from "../../config/axiosInstance";
// import LoginBackground from '../images/login-background.jpeg';

import './users.css';

class Login extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                username: '',
                password: '',
            },
        };

        this.handleChange = this.handleChange.bind(this);
        this.loginUser = this.loginUser.bind(this);
    }

    loginUser() {
        axiosInstance
            .post('/users/login', this.state.user)
            .then(success => {
                if (success.status === 200) {
                    this.props.history.push('/home');
                }
            })
            .catch((err) => {
                console.log(err);
            })
    }

    handleChange(e) {
        const {user} = this.state;
        const id = e.currentTarget.id;
        const value = e.currentTarget.value;
        user[id] = value;
        this.setState({
            user,
        });
    }

    render() {
        const {user} = this.state;

        return (
            <div>
                {/*<div className="login-form-background" style={{backgroundImage: 'url(' + LoginBackground + ')'}}/>*/}
                <div className="login-form container">
                    <Link to="/users/register">Don't yet have an account? Create one here</Link>
                    <h1>Login</h1>
                    <form>
                        <InputField
                            id="username"
                            type="text"
                            label="Username"
                            value={user.username}
                            handleChange={this.handleChange}
                        />
                        <InputField
                            id="password"
                            type="password"
                            label="Password"
                            value={user.password}
                            handleChange={this.handleChange}
                        />
                        <Button bsStyle="primary" onClick={this.loginUser}>Login</Button>
                    </form>
                </div>
            </div>
        )
    }
}

export default Login;
