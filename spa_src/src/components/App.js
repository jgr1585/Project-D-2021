import React, {PureComponent} from 'react';

import withStyles from '@mui/styles/withStyles';
import {AppBar, Snackbar, Typography} from '@mui/material';

import clsx from 'clsx';

import Loader from './Loader';
import Footer from './Footer';
import AppBarContent from "./AppBarContent";
import CreateBooking from "./booking/CreateBooking";
import AlertDialog from "./AlertDialog";
import HotelOverview from "./HotelOverview";

import CategoryControllerApi from '../js/openAPI/api/CategoryControllerApi';
import BookingControllerApi from '../js/openAPI/api/BookingControllerApi';

import BookingDTO from "../js/openAPI/model/BookingDTO";
import AddressDTO from "../js/openAPI/model/AddressDTO";
import RepresentativeDetailsDTO from "../js/openAPI/model/RepresentativeDetailsDTO";
import GuestDetailsDTO from "../js/openAPI/model/GuestDetailsDTO";
import {Alert} from "@mui/lab";

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
        maxHeight: "calc(100% - 120px)",
        overflow: "auto"
    },
});

class App extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            initCallsMade: false,
            initCallsError: false,

            open: false,
            openDialogSave: false,
            dialogTextSave: "",

            notificationOpen: false,
            notificationText: "Text N/A",
            notificationType: "success",

            error: "Unexpected error occurred",
        };

        this.categories = [];

        this.categoryControllerApi = new CategoryControllerApi();
        this.bookingControllerApi = new BookingControllerApi();

        this.hasChanged = false;
    }

    componentDidMount() {
        // Ajax Calls (get all necessary information about the hotel)
        this.initCalls().then(
            // success
            (result) => {
                this.categories = result;
                this.setState({initCallsMade: true});
            },
            // error
            (result) => {
                this.snackbarOpen('Request error occurred!\nPlease check your internet connection.' + result, 'error');
                this.setState({initCallsMade: true, initCallsError: true});
            }
        );
    };

    initCalls = () => {
        return new Promise((resolve, reject) => {
            this.categoryControllerApi.allCategories((error, data, response) => {
                if (response.statusCode === 200) {
                    resolve(data);
                } else {
                    reject(error)
                }
            });
        });
    };

    pushNewBooking = (bookingDTO) => {
        return new Promise((resolve, reject) => {
            this.bookingControllerApi.book(bookingDTO, (error, data, response) => {
                if (response.statusCode === 200) {
                    resolve(data);
                } else {
                    reject(error)
                }
            });
        });
    };

    createNewBooking = () => {
        this.setState({open: true});
    };

    createCategoriesFromObj = (categorySelection) => {
        let categories = {};

        if (categorySelection == null) {
            return categories;
        }

        for (let key in categorySelection) {
            categories[key] = parseInt(categorySelection[key].value);
        }

        return categories;
    };

    createRepresentativeFromObj = (personalDetails) => {
        let representative = new RepresentativeDetailsDTO();

        if (personalDetails == null) {
            return representative;
        }

        representative.firstName = personalDetails.repFirstName;
        representative.lastName = personalDetails.repLastName;
        representative.email = personalDetails.repMail;

        let address = new AddressDTO();
        address.street = personalDetails.repStreet;
        address.zip = personalDetails.repZip;
        address.city = personalDetails.repCity;
        address.country = personalDetails.repCountry;

        representative.address = address;
        representative.phone = personalDetails.repPhone;
        representative.creditCardNumber = personalDetails.repCreditCardNumber;
        representative.paymentMethod = personalDetails.selectedPaymentMethod;

        return representative;
    };

    createGuestFromObj = (personalDetails) => {
        let guest = new GuestDetailsDTO();

        if (personalDetails == null) {
            return guest;
        }

        guest.firstName = personalDetails.guestFirstName;
        guest.lastName = personalDetails.guestLastName;

        let address = new AddressDTO();
        address.street = personalDetails.guestStreet;
        address.zip = personalDetails.guestZip;
        address.city = personalDetails.guestCity;
        address.country = personalDetails.guestCountry;

        guest.address = address;

        return guest;
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
        let bookingDTO = BookingDTO.constructFromObject({
            "fromDate": bookingDetails.chooseCategory.from,
            "untilDate": bookingDetails.chooseCategory.until,
            "categories": this.createCategoriesFromObj(bookingDetails.chooseCategory.categorySelection),

            "representative": this.createRepresentativeFromObj(bookingDetails.personalDetails),
            "guest": this.createGuestFromObj(bookingDetails.personalDetails),

            "organizationId": "",
        }, null);

        this.pushNewBooking(bookingDTO).then(
            // success
            (result) => {
                this.snackbarOpen('Create booking successful!\nYou will shortly receive a confirmation email.', 'success');
                this.setState({open: false});
            },
            // error
            (result) => {
                this.snackbarOpen('Create booking failed!\nPlease check your input data and your internet connection.', 'error');
            }
        );
    };

    handleAlertDialogSaveClose = () => {
        this.setState({openDialogSave: false});
    };
    handleAlertDialogSaveOk = () => {
        this.setState({openDialogSave: false, open: false});
        this.hasChanged = false;
    };

    snackbarOpen = (notificationText, type) => {
        this.setState({ notificationOpen: true, notificationText: notificationText, notificationType: type })
    };
    snackbarClose = () => {
        this.setState({ notificationOpen: false })
    };
    snackbarContent = (classes) => {
        const { notificationOpen, notificationText, notificationType } = this.state;

        return (
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}

                open={notificationOpen}
                onClose={this.snackbarClose}
                autoHideDuration={10000}

                key={notificationText}
            >
                <Alert
                    onClose={this.snackbarClose}
                    severity={notificationType}
                    className={classes.notificationText}
                >
                    {notificationText}
                </Alert>
            </Snackbar>
        )
    };

    render() {
        const {classes} = this.props;
        const {
            anchor, open, initCallsMade, initCallsError,
            openDialogSave, dialogTextSave, error
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
                                <HotelOverview
                                    categories={this.categories}
                                />

                                {open ? (
                                    <CreateBooking
                                        anchor={anchor}
                                        open={open}

                                        categories={this.categories}

                                        categoryControllerApi={this.categoryControllerApi}

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
                {this.snackbarContent(classes)}

                <AlertDialog
                    openDialog={openDialogSave}
                    dialogText={dialogTextSave}
                    headerText={'Close window'}

                    onDialogClose={this.handleAlertDialogSaveClose}
                    onDialogOk={this.handleAlertDialogSaveOk}
                />
            </div>
        );
    }
}

export default withStyles(styles)(App);