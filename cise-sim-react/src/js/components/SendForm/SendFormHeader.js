import React from 'react';
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '16px auto',
        maxWidth: 800,
        overflowY: 'scroll',
        maxHeight: 800,
    },
    header: {
        backgroundColor: "lightgrey"
    },
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },

});


const sendFormHeader = (props)  => {

    const {classes} = props;

    return (
        <TableContainer component={Paper} className={classes.header} >
            <Table size="small" aria-label="a dense table">
                <TableBody>
                    <TableRow>
                        <TableCell>
                            <Typography variant="h6" component="h1" align={"left"}>
                                Create Message
                            </Typography>
                        </TableCell>

                        <TableCell align={"right"}>
                            <button type="button" className="close" data-dismiss="create-message" onClick={props.onclose}>&times;</button>
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default withStyles(styles)(sendFormHeader);