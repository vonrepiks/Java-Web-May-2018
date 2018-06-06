import React from 'react';
import axiosInstance from "../../config/axiosInstance";

import './users.css';

class AllUsers extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            allUsers: '',
        };
    }

    componentDidMount() {
        axiosInstance
            .get('/users/all')
            .then(success => {
                if (success.status === 200) {
                    console.log(success.data);
                }
            })
            .catch((err) => {
                if (err) {
                    console.log(err);
                }
            });
    }

    render() {
        return (
            <div className="all-users">
                <h1>All Users</h1>
            </div>
        );
    }
}

export default AllUsers;
