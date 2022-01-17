import React, {PureComponent} from 'react';

import withStyles from '@mui/styles/withStyles';
import {AppBar, Button, Snackbar, Typography} from '@mui/material';

import clsx from 'clsx';

import Loader from './Loader';
import Footer from './Footer';
import AppBarContent from "./AppBarContent";
import CreateBooking from "./booking/CreateBooking";
import AlertDialog from "./AlertDialog";
import HotelOverview from "./HotelOverview";

const styles = theme => ({
    app: {
        position: 'fixed',
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
        flexDirection: 'column',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        flex: '0 0 auto',
        position: 'relative',
        color: '#ffffff',
        backgroundColor: '#2196F3 !important',
    },
    main: {
        maxHeight: "90%",
        overflow: "auto"
    }
});

class App extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            initCallsMade: false,
            initCallsError: false,

            open: false,
            openDialogSave: false,
            dialogTextSave: '',

            error: '404 :(',
        };

        this.hasChanged = false;
    }

    componentDidMount() {
        // Ajax Calls (get all necessary information about the hotel)
        this.initCalls().then(() => {
            this.setState({initCallsMade: true});
        });
    }

    initCalls = () => {
        return new Promise(resolve => {
            // openapi??

            resolve();
        })
    };

    createNewBooking = () => {
        this.setState({open: true});
    };

    // handle popover events! ----------------------------------------------------------------------------------------
    hasValueChanged = (hasChanged) => {
        this.hasChanged = hasChanged;
    };
    handleCreateBookingDialogClose = (event, reason) => {
        if (reason === "backdropClick") {
            return;
        }

        if (this.hasChanged) {
            this.setState({
                openDialogSave: true,
                dialogTextSave: 'Do you really want to close this window?'
            });
        } else {
            this.handleAlertDialogSaveOk();
        }
    };
    handleCreateBookingDialogOk = (bookingDetails) => {
        // TODO:: call to backend to create booking

        this.setState({open: false});
    };

    handleAlertDialogSaveClose = () => {
        this.setState({openDialogSave: false});
    };
    handleAlertDialogSaveOk = () => {
        this.setState({openDialogSave: false, open: false});
        this.hasChanged = false;
    };

    render() {
        const {classes} = this.props;
        const {
            anchor, open, initCallsMade, initCallsError,
            bookingDetails, openDialogSave, dialogTextSave, error
        } = this.state;

        return (
            <div className={clsx(classes.app)}>
                {/*check if the header param exist*/}
                <AppBar position="static" className={clsx(classes.appBar)}>
                    <AppBarContent
                        createNewBooking={this.createNewBooking}
                    />
                </AppBar>

                {/*check if init calls are made*/}
                {!initCallsMade ? (
                    <Loader/>
                ) : (
                    <React.Fragment>

                        {!initCallsError ? (
                            <main className={clsx(classes.main)}>
                                <HotelOverview/>

                                {open ? (
                                    <CreateBooking
                                        anchor={anchor}
                                        open={open}

                                        bookingDetails={bookingDetails}

                                        hasValueChanged={this.hasValueChanged}
                                        onDialogClose={this.handleCreateBookingDialogClose}
                                        onDialogOk={this.handleCreateBookingDialogOk}
                                    >

                                    </CreateBooking>
                                ) : ''}

                                <AlertDialog
                                    openDialog={openDialogSave}
                                    dialogText={dialogTextSave}
                                    headerText={'Close window'}

                                    onDialogClose={this.handleAlertDialogSaveClose}
                                    onDialogOk={this.handleAlertDialogSaveOk}
                                />
                            </main>
                        ) : (
                            <Typography gutterBottom variant="subtitle1" className={classes.error}>
                                {error}
                            </Typography>
                        )}

                        {/*add footer*/}
                        <Footer/>
                    </React.Fragment>
                )}

                {/* Notification container*/}
                {/*{this.snackbarContent(classes)}*/}
            </div>
        );
    }
}

export default withStyles(styles)(App);