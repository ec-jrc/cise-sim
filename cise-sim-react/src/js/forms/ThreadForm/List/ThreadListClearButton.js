import {Button, withStyles} from '@material-ui/core';
import React, {Component} from 'react';
import {observer} from 'mobx-react';
import VisibilityOffRoundedIcon from '@material-ui/icons/VisibilityOffRounded';
import {buttonSizeSmall, fontSizeExtraSmall} from "../../../layouts/Font";

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
        maxHeight: buttonSizeSmall,
        fontSize:fontSizeExtraSmall
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    leftIcon: {
        marginRight: theme.spacing(1),
        width:"20%",
        marginLeft: theme.spacing(0)
    },
});

@observer
class MsgClearButton extends Component {

    constructor(props) {
        super(props);
    }

    isDisabled() {
        return !this.getMessageStore().historyMsgList.length > 0;
    }

    clear () {
        this.getMessageStore().clearHistory();
        this.getMessageStore().updateThreadWithBody([]);
    }

    render() {
        const {classes} = this.props;

        let disabled;
        let visibility;
        if (this.getMessageStore()){
            disabled =this.isDisabled();
            visibility = null;
        }
        else {
            disabled = null;
            visibility = 'hidden';
        }

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={() => this.clear()}
                disabled={disabled}
                style={{visibility:visibility}}
            >
                <VisibilityOffRoundedIcon className={classes.leftIcon}/>
                Clear List
            </Button>
        )
    }

    getMessageStore() {
        return this.props.messageStore;
    }
}

export default withStyles(styles)(MsgClearButton)