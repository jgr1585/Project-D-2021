import React, {PureComponent} from 'react'

import {
    Button, Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Step,
    StepLabel,
    Stepper,
    Typography
} from "@mui/material";
import withStyles from '@mui/styles/withStyles';

import PropTypes from 'prop-types';
import clsx from 'clsx';

import ChooseCategories from "./ChooseCategories";
import PersonalDetails from "./PersonalDetails";
import Summary from "./Summary";

let marginNum = 8;
const styles = (theme) => ({
    noPaddingTop: {
        paddingTop: '0',
    },
    greyBackground: {
        backgroundColor: '#f7f7f7',
    },

    customTypography: {
        padding: '6px ' + marginNum + 'px', //top, right, bottom, left
        color: theme.color.white,
        fontSize: '14px !important',
        marginTop: '0px',
        fontFamily: 'Arial, Helvetica, sans-serif',
    },
    customPaddingLeft: {
        paddingLeft: '16px',
    },
    customPadding: {
        paddingTop: '10px',
        paddingBottom: '10px'
    },
    customMargin: {
        marginBottom: '10px',
    },

    customDialogPaper: {
        minHeight: '70vh',
        maxHeight: '70vh',
    },
    customDialogTitle: {
        background: theme.palette.primary.light,
        height: '32px',
        padding: '0px',
        position: 'sticky',
        top: 0,
        zIndex: 100,
        fontFamily: 'Arial, Helvetica, sans-serif',
    },
    customDialogActionPadding: {
        padding: '20px 24px',
    },

    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
});

class CreateBooking extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            activeStep: 0,
        }

        this.categoryControllerApi = props.categoryControllerApi;

        let categorySelection = {};
        for (let i = 0; i < props.categories.length; i++) {
            let cat = props.categories[i];

            if (cat != null) {
                categorySelection[cat.id] = {
                    name: cat.title,
                    max: "5",
                    value: "0",
                };
            }
        }

        this.bookingDetails = {
            chooseCategory: {
                from: new Date(),
                until: new Date(),
                categorySelection: categorySelection,
            },
            personalDetails: {
                guestFirstName: "",
                guestLastName: "",
                guestStreet: "",
                guestZip: "",
                guestCity: "",
                guestCountry: "",

                representativeFirstName: "",
                representativeLastName: "",
                representativeStreet: "",
                representativeZip: "",
                representativeCity: "",
                representativeCountry: "",
                representativeMail: "",
                representativePhone: "",

                representativeCreditCardNumber: "",
                representativePaymentMethod: new Map([
                    ["cash", "Cash"],
                    ["creditCard", "Credit Card"],
                ]),
                selectedPaymentMethod: "cash",

                checkboxState: false,
            }
        };

        this.steps = ['Choose categories', 'Personal details', 'Summary'];
    }

    goBack = () => {
        const {activeStep} = this.state;

        this.setState({activeStep: activeStep - 1});
    };
    goNext = () => {
        const {activeStep} = this.state;

        this.setState({activeStep: activeStep + 1});
    };

    checkNextStep = (index) => {
        const {activeStep} = this.state;

        if (activeStep > index) {
            this.setState({activeStep: index});
        } else {
            if (activeStep === 0) {

                if (this.validateChooseCategories(this.bookingDetails)) {

                    if (index === 1) {
                        this.setState({activeStep: index});
                    } else {
                        if (this.validatePersonalDetails(this.bookingDetails)) {
                            this.setState({activeStep: index});
                        }
                    }
                }
            } else if (activeStep === 1) {
                if (this.validatePersonalDetails(this.bookingDetails)) {
                    this.setState({activeStep: index});
                }
            }
        }
    };

    validateChooseCategories = () => {
        // TODO:: implement validation
        return true;
    };

    validatePersonalDetails = () => {
        // TODO:: implement validation
        return true;
    };

    save = (activeStep) => {
        if (activeStep === 0) {
            if (this.validateChooseCategories(this.bookingDetails)) {
                this.goNext();
            }
        } else if (activeStep === 1) {
            if (this.validatePersonalDetails(this.bookingDetails)) {
                this.goNext();
            }
        } else {
            if (this.validateChooseCategories(this.bookingDetails) && this.validatePersonalDetails(this.bookingDetails)) {
                this.props.onDialogOk(this.bookingDetails);
            }
        }
    };

    renderChooseCategories = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <ChooseCategories
                    categoryControllerApi={this.props.categoryControllerApi}
                    chooseCategory={bookingDetails.chooseCategory}
                >
                </ChooseCategories>
            </React.Fragment>
        );
    };

    renderPersonalDetails = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <PersonalDetails
                    personalDetails={bookingDetails.personalDetails}
                >
                </PersonalDetails>
            </React.Fragment>
        );
    };

    renderSummary = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <Summary
                    bookingDetails={bookingDetails}
                >
                </Summary>
            </React.Fragment>
        );
    };

    renderDialogActions = (classes, activeStep) => {
        return (
            <DialogActions className={clsx(classes.positionBottom, classes.customDialogActionPadding)}>
                <Button variant="contained" onClick={this.goBack} disabled={activeStep === 0}>
                    Back
                </Button>

                <Button variant="contained" onClick={() => this.save(activeStep)}>
                    {activeStep === 2 ? 'Create' : 'Next'}
                </Button>

                <Button variant="contained" onClick={this.props.onDialogClose}>
                    Cancel
                </Button>
            </DialogActions>
        );
    };

    render() {
        const {classes, open} = this.props;
        const {activeStep} = this.state;

        return (
            <Dialog open={open}
                    onClose={this.props.onDialogClose}
                    onKeyDown={this.onKeyDown}

                    maxWidth={"xl"}
                    fullWidth={true}

                    disableRestoreFocus={true}

                    classes={{paper: classes.customDialogPaper}}

                    disableBackdropClick
                    disableEscapeKeyDown
            >
                <DialogTitle className={clsx(classes.customDialogTitle)}>
                    <Typography className={clsx(classes.customTypography)}>
                        Create booking
                    </Typography>
                </DialogTitle>

                <DialogContent>
                    <Typography variant={"h6"}/>

                    <Stepper activeStep={activeStep}
                             className={clsx(classes.greyBackground, classes.customPadding, classes.customMargin)}
                    >
                        {this.steps.map((label, index) => (
                            <Step key={label}
                                  completed={index < activeStep}
                            >
                                <StepLabel StepIconProps={{classes: {root: classes.icon}}}
                                           onClick={() => {
                                               this.checkNextStep(index);
                                           }} style={{padding: 0}}
                                >
                                    {label}
                                </StepLabel>
                            </Step>
                        ))}
                    </Stepper>

                    <div>
                        {activeStep === 0 ? (
                            this.renderChooseCategories(classes, this.bookingDetails)
                        ) : (activeStep === 1 ? (
                            this.renderPersonalDetails(classes, this.bookingDetails)
                        ) : (
                            this.renderSummary(classes, this.bookingDetails)
                        ))}
                    </div>
                </DialogContent>

                {this.renderDialogActions(classes, activeStep)}
            </Dialog>
        )
    }
}

CreateBooking.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CreateBooking);