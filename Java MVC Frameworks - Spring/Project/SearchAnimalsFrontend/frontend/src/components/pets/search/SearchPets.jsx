import React from 'react';
import { Link } from 'react-router-dom';

import '../pets.css';

const SearchPets = () => {
    return (
        <div>
            <div className="page-content ">
                <section className="page-header">
                    <h1>View missing or found pets</h1>
                </section>
                <section className="container-fluid big-container content">
                    <div className="row">
                        <div className="col-xs-12">
                            <h2 className="title text-center no-margin">What pets would you like to view?</h2>
                        </div>
                    </div>
                </section>
                <section className="section bg-grey">
                    <div className="container-fluid big-container content">
                        <div className="row">
                            <div className="choice col-xs-12 col-sm-6 col-md-4 col-md-offset-1">
                                <div>
                                    <h3><b>Missing</b>Pets</h3>
                                    <p>
                                        Browse pets reported missing by their owner.
                                    </p>
                                    <Link to="/search/lost" className="btn btn-primary">
                                        View Missing
                                    </Link>
                                </div>
                            </div>
                            <div className="clearfix visible-xs-block col-xs-12 gap-top-half"/>
                            <div className="col-xs-12 col-sm-6 col-md-4 col-md-offset-2 choice">
                                <div>
                                    <h3><b>Found</b> Pets</h3>
                                    <p>
                                        Look through pets reported found by members of the public
                                    </p>
                                    <Link to="/search/found" className="btn btn-primary">
                                        View Found
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    )
};

export default SearchPets;
