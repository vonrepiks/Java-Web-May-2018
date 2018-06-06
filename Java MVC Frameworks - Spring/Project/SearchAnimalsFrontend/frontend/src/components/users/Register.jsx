import React from 'react';
import InputField from "../utility/InputField";
import {Button} from "react-bootstrap";
import axiosInstance from "../../config/axiosInstance";
// import RegisterBackground from '../images/register-background.jpeg';

import './users.css';

class Register extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                username: '',
                password: '',
                age: '',
                firstName: '',
                lastName: '',
            },
        };

        this.handleChange = this.handleChange.bind(this);
        this.registerUser = this.registerUser.bind(this);
    }

    registerUser() {
        axiosInstance
            .post('/users/register', this.state.user)
            .then(success => {
                if (success.status === 200) {
                    console.log(success)
                    this.props.history.push('/users/login');
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
                {/*<div className="register-form-background"  style={{backgroundImage: 'url('+RegisterBackground+')'}} />*/}
                <div className="register-form container">
                    <h1>Register</h1>
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
                        <InputField
                            id="age"
                            type="number"
                            label="Age"
                            value={user.age}
                            handleChange={this.handleChange}
                        />
                        <InputField
                            id="firstName"
                            type="text"
                            label="First name"
                            value={user.firstName}
                            handleChange={this.handleChange}
                        />
                        <InputField
                            id="lastName"
                            type="text"
                            label="Last name"
                            value={user.lastName}
                            handleChange={this.handleChange}
                        />
                        <Button bsStyle="primary" onClick={this.registerUser}>Register</Button>
                    </form>
                </div>
            </div>
        )
    }
}

export default Register;
