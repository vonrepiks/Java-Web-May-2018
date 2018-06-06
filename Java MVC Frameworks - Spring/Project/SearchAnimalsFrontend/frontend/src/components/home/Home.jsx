import React from 'react';
import HomeBackgroundVideo from "../utility/HomeBackgroundVideo";

import './home.css';

class Home extends React.Component {
    render() {
        return (
            <div className="full-video">
                <HomeBackgroundVideo />
                <section className="video-overlay">
                    <div className="outer">
                        <div className="dynamic-row">
                            <div className="dynamic-content">
                                <h1>The BGs largest missing pet organisation and free database</h1>
                            </div>
                        </div>
                        <div className="text-center">
                            <a href="https://www.animalsearchuk.co.uk/report/lost" className="btn btn-default">I
                                have <b>lost</b> my pet</a>
                            <a href="https://www.animalsearchuk.co.uk/report/found" className="btn btn-orange">I
                                have <b>found</b> a pet</a>
                        </div>
                    </div>
                    <div className="pull-down">
                        <p className="smaller hide">Scroll Down</p>
                        <p><i className="fa fa-chevron-down animated bounceAndPause infinite"></i></p>
                    </div>
                </section>
            </div>
        );
    }
}

export default Home;
