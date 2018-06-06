import React from 'react';
import Routes from "./routes";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";

import './App.css';

const App = () => {
    return (
        <div className="App">
            <Header/>
            <div className="body">
                <Routes/>
            </div>
            <Footer/>
        </div>
    );
};

export default App;
