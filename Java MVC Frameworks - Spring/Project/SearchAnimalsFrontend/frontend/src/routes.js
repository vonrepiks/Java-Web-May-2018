import React from 'react';
import {Switch, Route} from 'react-router-dom';
import Home from './components/home/Home';
import Register from './components/users/Register';
import Login from './components/users/Login.jsx';
import AllUsers from './components/users/AllUsers';
import SearchPets from './components/pets/search/SearchPets';
import SearchLostPets from './components/pets/search/SearchLostPets';
import SearchFoundPets from './components/pets/search/SearchFoundPets';
import ReportPet from "./components/pets/report/ReportPet";
import ReportLostPets from "./components/pets/report/ReportLostPets";
import ReportFoundPets from "./components/pets/report/ReportFoundPets";

const Routes = (props) => (
    <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/users/login" component={Login}/>
        <Route path="/users/register" component={Register}/>
        <Route path="/users/all" component={AllUsers}/>
        <Route exact path="/search" component={SearchPets}/>
        <Route path="/search/lost" component={SearchLostPets}/>
        <Route path="/search/found" component={SearchFoundPets}/>
        <Route exact path="/report" component={ReportPet}/>
        <Route path="/report/lost" component={ReportLostPets}/>
        <Route path="/report/found" component={ReportFoundPets}/>
    </Switch>
);

export default Routes;
