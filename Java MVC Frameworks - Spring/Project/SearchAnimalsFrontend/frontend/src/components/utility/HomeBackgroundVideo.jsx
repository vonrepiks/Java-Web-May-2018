import React from 'react';

import './homeBackgroundVideo.css'

const HomeBackgroundVideo = () => {
    return (
        <div className="video-container">
            <video className="home-video" autoPlay="autoplay" loop="loop" preload="auto">
                <source type="video/mp4" src="https://www.animalsearchuk.co.uk/assets/video/home.mp4"/>
                <source type="video/webm" src="https://www.animalsearchuk.co.uk/assets/video/home.webm"/>
            </video>
        </div>
    )
};

export default HomeBackgroundVideo;