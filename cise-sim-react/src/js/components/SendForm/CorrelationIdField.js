import React from 'react';
import {observer} from 'mobx-react';
import {TextField} from '@material-ui/core';
import Tooltip from '@material-ui/core/Tooltip';
import PropTypes from 'prop-types';

@observer
export default class CorrelationIdField extends React.Component {

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.props.store.correlationId = event.target.value;
    }

    render() {
        return (
            <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
                <TextField
                    name="correlationId"
                    label="Correlation Id"
                    fullWidth={true}
                    color="primary"
                    variant="outlined"
                    value={this.props.store.correlationId}
                    onChange={this.handleChange}
                />
            </Tooltip>
        )
    }
}

CorrelationIdField.propTypes = {
    store: PropTypes.object.isRequired,
};