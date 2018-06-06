import React from 'react';

class ReportLostPets extends React.Component {
    render() {
        return (
            <div>
                <div className="page-content ">
                    <div className="page-header text-center plain">
                        <h1>Report a missing pet</h1>
                    </div>
                    <div>
                        <div id="report-form" className="fadeIn">
                            <div className="container content text-center">
                                <p>
                                    We are so sorry to hear that your pet is missing. We have helped to reunite
                                    thousands of pets with their families
                                    and now we are here to help you. First we need you to register your pet with us.
                                    This will enable our advanced
                                    Auto-Match system to identify any potential matches currently registered with us.
                                    This service is completely
                                    free of charge.
                                </p>
                                <p>
                                    This form can be edited later, fill in as much as you can now but don't worry if you
                                    need to come back
                                    to fill in some details, such as your pet's microchip number, later. Fields marked
                                    with a
                                    <span className="required"/> are required, in order to proceed.
                                </p>
                                <div>
                                    <h2 id="pet-type-q" className="text-center spaced">What type of pet has gone
                                        missing?</h2>
                                    <div className="text-center large-icon selectable">
                                        <label className="icon icon-cat">
                                            <input type="radio" name="" value="1"/>
                                            <span title="Cat"/>
                                        </label>
                                        <label className="icon icon-dog">
                                            <input type="radio" name="" value="2"/>
                                            <span title="Dog"/>
                                        </label>
                                        <label className="icon icon-rabbit">
                                            <input type="radio" name="" value="3"/>
                                            <span title="Rabbit"/>
                                        </label>
                                        <label className="icon icon-bird">
                                            <input type="radio" name="" value="4"/>
                                            <span title="Bird"/>
                                        </label>
                                        <label className="icon icon-ferret">
                                            <input type="radio" name="" value="5"/>
                                            <span title="Ferret"/>
                                        </label>
                                        <label className="icon icon-tortoise">
                                            <input type="radio" name="" value="6"/>
                                            <span title="Tortoise"/>
                                        </label>
                                        <label className="icon icon-horse">
                                            <input type="radio" name="" value="7"/>
                                            <span title="Horse"/>
                                        </label>
                                        <label className="icon icon-goat">
                                            <input type="radio" name="" value="9"/>
                                            <span title="Goat"/>
                                        </label>
                                        <label className="icon icon-other">
                                            <input type="radio" name="" value="8"/>
                                            <span title="Other"/>
                                        </label>
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

export default ReportLostPets;


