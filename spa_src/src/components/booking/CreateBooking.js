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

const styles = (theme) => ({
    noPaddingTop: {
        paddingTop: '0',
    },
    greyBackground: {
        backgroundColor: '#f7f7f7',
    },

    customTypography: {
        padding: '6px 8px',
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
    customBoxShadow: {
        boxShadow: "0px 2px 4px -1px rgb(0 0 0 / 10%), 0px 4px 5px 0px rgb(0 0 0 / 0%), 0px 1px 10px 0px rgb(0 0 0 / 15%)",
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

    stepperContentHeight: {
        maxHeight: "calc(70vh - 200px)",
        overflow: "auto",
    },

    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
});

class CreateBooking extends PureComponent {
    static SECONDS_OF_DAY = 60 * 60 * 24;

    static MONTHS = {
        "JANUARY": 0,
        "FEBRUARY": 1,
        "MARCH": 2,
        "APRIL": 3,
        "MAY": 4,
        "JUNE": 5,
        "JULY": 6,
        "AUGUST": 7,
        "SEPTEMBER": 8,
        "OCTOBER": 9,
        "NOVEMBER": 10,
        "DECEMBER": 11,
    };

    constructor(props) {
        super(props);

        this.state = {
            activeStep: 0,

            stepValidationError: false,

            chooseCategoryError: {
                fromError: "",
                untilError: "",
                categorySelectionError: "",
            },

            personalDetailsError: {
                guestFirstNameError: "",
                guestLastNameError: "",
                guestStreetError: "",
                guestZipError: "",
                guestCityError: "",
                guestCountryError: "",

                repFirstNameError: "",
                repLastNameError: "",
                repStreetError: "",
                repZipError: "",
                repCityError: "",
                repCountryError: "",
                repMailError: "",
                repPhoneError: "",
                repCreditCardNumberError: "",
            }
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

        let today = new Date();
        let weekTimeRange = 7 * CreateBooking.SECONDS_OF_DAY * 1000;
        this.bookingDetails = {
            chooseCategory: {
                from: new Date(today.getTime() + weekTimeRange),
                until: new Date(today.getTime() + (weekTimeRange * 2)),
                categorySelection: categorySelection,
            },
            personalDetails: {
                guestFirstName: "",
                guestLastName: "",
                guestStreet: "",
                guestZip: "",
                guestCity: "",
                guestCountry: "",

                repFirstName: "",
                repLastName: "",
                repStreet: "",
                repZip: "",
                repCity: "",
                repCountry: "",
                repMail: "",
                repPhone: "",
                repCreditCardNumber: "",

                selectedPaymentMethod: "Cash",
                checkboxState: false,
            }
        };

        this.categories = this.props.categories;

        this.steps = ['Choose categories', 'Personal details', 'Summary'];
    }

    goBack = () => {
        const {activeStep} = this.state;

        this.setState({activeStep: activeStep - 1, stepValidationError: false});
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

                if (this.validateChooseCategories()) {

                    if (index === 1) {
                        this.setState({activeStep: index});
                    } else {
                        if (this.validatePersonalDetails()) {
                            this.setState({activeStep: index});
                        }
                    }
                }
            } else if (activeStep === 1) {
                if (this.validatePersonalDetails()) {
                    this.setState({activeStep: index});
                }
            }
        }
    };

    validateChooseCategories = () => {
        let chooseCategoryError = {...this.state.chooseCategoryError};
        let chooseCategory = this.bookingDetails.chooseCategory;

        let stepValidationError = false;

        if (chooseCategory.from === "") {
            chooseCategoryError.fromError = "Cannot be empty";
            stepValidationError = true;
        }
        if (chooseCategory.until === "") {
            chooseCategoryError.untilError = "Cannot be empty";
            stepValidationError = true;
        }

        let selectedCategories = 0;
        for (let key in chooseCategory.categorySelection) {
            let cat = chooseCategory.categorySelection[key];

            if (cat != null) {
                selectedCategories += cat.value;
            }
        }

        if (selectedCategories <= 0) {
            chooseCategoryError.categorySelectionError = "Take at least one category";
            stepValidationError = true;
        }

        if (stepValidationError) {
            this.setState({chooseCategoryError: chooseCategoryError, stepValidationError: true});

            return false;
        }

        return true;
    };

    validatePersonalDetails = () => {
        let personalDetailsError = {...this.state.personalDetailsError};
        let personalDetails = this.bookingDetails.personalDetails;

        let stepValidationError = false;
        for (let key in personalDetails) {
            if (key === "selectedPaymentMethod" || key === "checkboxState" || (personalDetails.checkboxState && key.includes("guest"))) {
                continue;
            }

            let errKey = key + "Error";
            if (personalDetails[key] === "") {
                personalDetailsError[errKey] = "Cannot be empty";
                stepValidationError = true;
            } else {
                personalDetailsError[errKey] = "";
            }
        }

        if (stepValidationError) {
            this.setState({personalDetailsError: personalDetailsError, stepValidationError: true});

            return false;
        }

        return true;
    };

    save = (activeStep, index) => {
        if (activeStep === 0) {
            if (this.validateChooseCategories()) {
                this.goNext();
            }
        } else if (activeStep === 1) {
            if (this.validatePersonalDetails()) {
                this.goNext();
            }
        } else {
            if (this.validateChooseCategories() && this.validatePersonalDetails()) {
                this.props.onDialogOk(this.bookingDetails);
            }
        }
    };

    seasonPricedCategorySelection = (from, categories, categorySelection) => {
        let stayStart = from.getMonth();

        for (let key in categories) {
            let cat = categories[key];

            if (cat != null) {
                if (categorySelection[cat.id] != null) {
                    categorySelection[cat.id].unitPrice = 0;
                }

                if (cat.priceList != null) {
                    for (let i = 0; i < cat.priceList.length; i++) {
                        let priceItem = cat.priceList[i];

                        if (priceItem != null) {
                            let seasonStart = CreateBooking.MONTHS[priceItem.from];
                            let seasonEnd = CreateBooking.MONTHS[priceItem.to];

                            if (seasonStart > seasonEnd) {
                                if (stayStart >= seasonStart || stayStart <= seasonEnd) {
                                    categorySelection[cat.id].unitPrice = priceItem.price;
                                }
                            } else {
                                if (stayStart >= seasonStart && stayStart <= seasonEnd) {
                                    categorySelection[cat.id].unitPrice = priceItem.price;
                                }
                            }
                        }
                    }
                }
            }
        }

        return categorySelection;
    };

    renderChooseCategories = (classes, chooseCategory) => {
        const {chooseCategoryError} = this.state;

        return (
            <React.Fragment>
                <ChooseCategories
                    categoryControllerApi={this.props.categoryControllerApi}
                    chooseCategory={chooseCategory}
                    chooseCategoryError={chooseCategoryError}
                >
                </ChooseCategories>
            </React.Fragment>
        );
    };

    renderPersonalDetails = (classes, personalDetails) => {
        const {personalDetailsError} = this.state;

        return (
            <React.Fragment>
                <PersonalDetails
                    personalDetails={personalDetails}
                    personalDetailsError={personalDetailsError}
                >
                </PersonalDetails>
            </React.Fragment>
        );
    };

    renderSummary = (classes, bookingDetails) => {
        let chooseCategory = bookingDetails.chooseCategory;

        let fromUnixTs = Math.round(chooseCategory.from.getTime() / 1000);
        let untilUnixTs = Math.round(chooseCategory.until.getTime() / 1000);

        let nights = Math.round(Math.abs(untilUnixTs - fromUnixTs) / CreateBooking.SECONDS_OF_DAY);

        chooseCategory.categorySelection = this.seasonPricedCategorySelection(chooseCategory.from, this.categories, chooseCategory.categorySelection);

        return (
            <React.Fragment>
                <Summary
                    bookingDetails={bookingDetails}
                    nights={nights}
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
                             className={clsx(classes.greyBackground, classes.customPadding, classes.customMargin, classes.customBoxShadow)}
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

                    <div className={clsx(classes.stepperContentHeight)}>
                        {activeStep === 0 ? (
                            this.renderChooseCategories(classes, this.bookingDetails.chooseCategory)
                        ) : (activeStep === 1 ? (
                            this.renderPersonalDetails(classes, this.bookingDetails.personalDetails)
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