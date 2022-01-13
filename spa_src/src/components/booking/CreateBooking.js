import React, {PureComponent} from 'react'

import {
    Button,
    DialogActions,
    DialogContent,
    DialogTitle,
    Popover,
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
    customDialogTitle: {
        background: theme.palette.primary.light,
        height: '32px',
        padding: '0px',
        position: 'sticky',
        top: 0,
        zIndex: 100,
        fontFamily: 'Arial, Helvetica, sans-serif',
    },
    noPaddingTop: {
        paddingTop: '0',
    },
    customTypography: {
        padding: '6px ' + marginNum + 'px', //top, right, bottom, left
        color: theme.color.white,
        fontSize: '14px !important',
        marginTop: '0px',
        fontFamily: 'Arial, Helvetica, sans-serif',
    },
    greyBackground: {
        backgroundColor: '#f7f7f7',
    },
    customPaddingLeft: {
        paddingLeft: '16px',
    },
});

class CreateBooking extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            activeStep: 0,
        }

        this.bookingDetails = props.bookingDetails;
        if (this.bookingDetails == null) {
            this.bookingDetails = {
                from: "",
                until: "",
                categorySelection: new Map([
                    ["single", 0],
                    ["double", 0],
                    ["multi", 0],
                    ["suite", 0],
                ]),

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
                representativePaymentMethod: 0,
            };
        }

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
                this.props.onPopoverOk(this.bookingDetails);
            }
        }
    };

    renderChooseCategories = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <Typography title={'Step 1 - Properties'} variant={"h6"}/>

                <ChooseCategories
                    // props for choose categories
                >
                </ChooseCategories>
            </React.Fragment>
        );
    };

    renderPersonalDetails = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <Typography title={'Step 1 - Properties'} variant={"h6"}/>

                <PersonalDetails
                    // props for personal details
                >
                </PersonalDetails>
            </React.Fragment>
        );
    };

    renderSummary = (classes, bookingDetails) => {
        return (
            <React.Fragment>
                <Typography title={'Step 1 - Properties'} variant={"h6"}/>

                <Summary
                    // props for summary
                >
                </Summary>
            </React.Fragment>
        );
    };

    renderDialogActions = (classes, activeStep) => {
        return (
            <DialogActions className={clsx(classes.noPaddingTop, classes.positionBottom)}>
                <Button variant="contained" onClick={this.goBack} disabled={activeStep === 0}>
                    Back
                </Button>

                <Button variant="contained" onClick={() => this.save(activeStep)}>
                    {activeStep === 2 ? 'Create' : 'Next'}
                </Button>

                <Button variant="contained" onClick={this.props.onPopoverClose}>
                    Cancel
                </Button>
            </DialogActions>
        );
    };

    render() {
        const {classes, open} = this.props;
        const {activeStep} = this.state;

        return (
            <Popover open={open}
                     onClose={() => this.props.onPopoverClose(this.bookingDetails)}
                     onKeyDown={this.onKeyDown}

                     disableRestoreFocus={true}
            >
                <DialogTitle className={clsx(classes.customDialogTitle)}>
                    <Typography className={clsx(classes.customTypography)}>
                        Create booking
                    </Typography>
                </DialogTitle>

                <DialogContent>
                    <Typography variant={"h6"}/>

                    <Stepper activeStep={activeStep}
                             className={clsx(classes.greyBackground)}
                    >
                        {this.steps.map((label, index) => (
                            <Step key={label}
                                  completed={false}
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

                    <div style={{paddingBottom: '8px', width: 680}}>
                        {activeStep === 0 ? (
                            this.renderChooseCategories(classes, this.bookingDetails)
                        ) : (activeStep === 1 ? (
                            this.renderPersonalDetails(classes, this.bookingDetails)
                        ) : (
                            this.renderSummary(classes, this.bookingDetails)))}
                    </div>

                    {this.renderDialogActions(classes, activeStep)}
                </DialogContent>
            </Popover>
        )
    }
}

CreateBooking.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CreateBooking);