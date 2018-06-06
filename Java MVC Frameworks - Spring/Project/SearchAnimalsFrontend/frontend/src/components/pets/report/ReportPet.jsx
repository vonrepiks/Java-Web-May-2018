import React from 'react';
import {Link} from "react-router-dom";

class ReportPet extends React.Component {
    render() {
        return (
            <div>
                <div className="page-content ">
                    <div className="page-header">
                        <h1>Report a pet</h1>
                        <img src="https://www.animalsearchuk.co.uk/assets/images/headers/report.png" alt="" />
                    </div>
                    <div className="container-fluid big-container content">
                        <div className="row">
                            <div className="col-xs-12">
                                <h2 className="title text-center no-margin">What would you like to report?</h2>
                            </div>
                        </div>
                    </div>
                    <div className="section bg-grey">
                        <div className="listing container-fluid big-container content">
                            <div className="row">
                                <div className="col-md-4 col-sm-6 col-lg-3 col-lg-offset-half choice">
                                    <div>
                                        <h3>
                                            <Link to="/report/lost" className="plain">
                                                Missing
                                            </Link>
                                        </h3>
                                        <p>
                                            I am the owner of a missing pet.
                                        </p>
                                        <Link to="/report/lost" className="btn btn-primary">
                                            Report Missing
                                        </Link>
                                    </div>
                                </div>
                                <div className="clearfix visible-xs-block gap-top-half"/>
                                <div className="col-md-4 col-sm-6 col-lg-3 col-lg-offset-1 choice">
                                    <div>
                                        <h3>
                                            <Link to="/report/found" className="plain">
                                                Found
                                            </Link>
                                        </h3>
                                        <p>
                                            I have found someones pet
                                        </p>
                                        <Link to="/report/found" className="btn btn-primary">
                                            Report Found
                                        </Link>
                                    </div>
                                </div>
                                <div
                                    className="clearfix visible-xs-block visible-sm-block col-xs-12 gap-top-half"/>
                                <div className="col-md-4 col-lg-3 col-lg-offset-1 col-xs-12 choice">
                                    <div>
                                        <h3>
                                            <Link to="/pre-register" className="plain">
                                                Pre-Register
                                            </Link>
                                        </h3>
                                        <p>
                                            Pre-register my pet who is safe at home.
                                        </p>
                                        <Link to="/pre-register" className="btn btn-primary">
                                            Pre-Register
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ReportPet;
