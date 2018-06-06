import React from 'react';
import {Col, ControlLabel, FormControl, FormGroup, Row} from "react-bootstrap";

const InputField = (props) => {
    return (
        <FormGroup
            controlId={props.id}
        >
            <Row>
                <Col sm={2}/>
                <Col sm={2}>
                    <ControlLabel>{props.label}:</ControlLabel>
                </Col>
                <Col sm={6}>
                    <FormControl
                        type={props.type}
                        value={props.value}
                        placeholder={`Enter ${props.id}`}
                        onChange={props.handleChange}
                    />
                </Col>
                <Col sm={2}/>
            </Row>
        </FormGroup>
    );
};

export default InputField;
