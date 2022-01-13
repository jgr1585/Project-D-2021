
import React, {PureComponent} from 'react';
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from '@mui/material';
import withStyles from '@mui/styles/withStyles';

import clsx from 'clsx';
import PropTypes from 'prop-types';

let border = 1;
let padding = 20;
const styles = (theme) => ({
    customPaperStyle: {
        webkitBoxShadow: '0px 0px 7px 0px rgba(0,0,0,0.3)',
        mozBoxShadow: '0px 0px 7px 0px rgba(0,0,0,0.3)',
        boxShadow: '0px 0px 7px 0px rgba(0,0,0,0.3)',

        width: 400 + (2 * padding) + (2 * border),
        minWidth: '302px',
        lineHeight: '24px',
        padding: '16px',

        borderRadius: '0',
        border: '1px solid #ccc !important',

        fontFamily: 'Arial, Helvetica, sans-serif !important',
        fontWeight: '400',
        letterSpacing: 'normal',
        whiteSpace: 'pre-line',
    },
    customTitleStyles: {
        '& h2': {
            fontSize: '20px !important',
            lineHeight: '22px',
            fontFamily: 'Arial, Helvetica, sans-serif !important',


            fontWeight: '400',
            letterSpacing: 'normal',
            color: '#333333',
        },
    },
    noPadding: {
        padding: '0',
    },
    marginTop: {
        marginTop: '20px',
    },
    noMargin: {
        margin: '0',
    },
    customTextStyles: {
        color: '#7a7a7a',
        fontFamily: 'Arial, Helvetica, sans-serif !important',
        fontWeight: '400',
        letterSpacing: 'normal',
        fontSize: '14px !important',
    },
});

class AlertDialog extends PureComponent {
    constructor(props) {
        super(props);
    }

    render() {
        const { classes, openDialog, onDialogClose, headerText, dialogText, onDialogOk } = this.props;

        return (
            <Dialog
                open={openDialog}
                onClose={onDialogClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                classes={{ paper: classes.customPaperStyle }}
                disableRestoreFocus={true}
            >
                <DialogTitle
                    id="alert-dialog-title"
                    className={clsx(classes.customTitleStyles, classes.noPadding)}>
                    {headerText}
                </DialogTitle>

                <DialogContent className={clsx(classes.noPadding, classes.marginTop)}>
                    <DialogContentText
                        id="alert-dialog-description"
                        className={clsx(classes.noPadding, classes.noMargin, classes.customTextStyles)}>
                        {dialogText}
                    </DialogContentText>
                </DialogContent>

                <DialogActions className={clsx(classes.noPadding, classes.marginTop)}>
                    <Button variant="contained" onClick={onDialogOk}>
                        Ok
                    </Button>
                    <Button variant="contained" onClick={onDialogClose}>
                        Cancel
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}

AlertDialog.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AlertDialog);