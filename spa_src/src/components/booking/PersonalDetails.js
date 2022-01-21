import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from "clsx";

import {Box, Checkbox, Container, FormControlLabel, Grid, MenuItem, Select, TextField, Typography} from "@mui/material";
import {AccountCircle, Email, EuroSymbol, LocationCity, Payment, Phone} from "@mui/icons-material";

import Item from "../grid/Item";
import HeaderItem from "../grid/HeaderItem";

const styles = () => ({
    checkboxPaddingTop: {
        paddingTop: "6px !important"
    },
    dropDownPaddingTop: {
        paddingTop: "9px !important"
    },
    minHeightPaddingTop: {
        minHeight: "70px",
        paddingTop: "0px !important"
    },
    customIconPos: {
        marginBottom: "37px",
    },
    customSelectIconPos: {
        marginBottom: "17px",
    },
});

const MemoizedGridItem = React.memo((props) => {
    const {
        classProp, xs, icon,
        label, onChange, value, type, size, disabled,
        errorText
    } = props;

    return (
        <Grid
            item
            xs={xs}
            className={clsx(classProp.minHeightPaddingTop)}
        >
            <Item>

                {icon != null ? (
                    <Box sx={{display: 'flex', alignItems: 'flex-end'}}>

                        {icon === "AccountCircle" ? (
                            <AccountCircle
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classProp.customIconPos)}
                            />
                        ) : ("")}

                        {icon === "LocationCity" ? (
                            <LocationCity
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classProp.customIconPos)}
                            />
                        ) : ("")}

                        {icon === "Email" ? (
                            <Email
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classProp.customIconPos)}
                            />
                        ) : ("")}

                        {icon === "Phone" ? (
                            <Phone
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classProp.customIconPos)}
                            />
                        ) : ("")}

                        {icon === "Payment" ? (
                            <Payment
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classProp.customIconPos)}
                            />
                        ) : ("")}

                        <TextField
                            className={clsx(classProp.minHeightPaddingTop)}

                            label={label}
                            onChange={onChange}
                            value={value}
                            type={type}
                            size={size}

                            variant="standard"
                            required={true}

                            disabled={disabled || false}

                            error={errorText !== ""}
                            helperText={errorText}
                        />
                    </Box>
                ) : (
                    <TextField
                        className={clsx(classProp.minHeightPaddingTop)}

                        label={label}
                        onChange={onChange}
                        value={value}
                        type={type}
                        size={size}

                        variant="standard"
                        required={true}

                        disabled={disabled || false}

                        error={errorText !== ""}
                        helperText={errorText}
                    />
                )}

            </Item>
        </Grid>
    );
}, (prevProps, nextProps) => {

    let isSame = true;

    if (isSame && nextProps.value != null) {
        isSame = nextProps.value === prevProps.value
    }
    if (isSame && nextProps.disabled != null) {
        isSame = nextProps.disabled === prevProps.disabled
    }
    if (isSame && nextProps.errorText != null) {
        isSame = nextProps.errorText === prevProps.errorText
    }

    if (isSame && nextProps.xs != null) {
        isSame = nextProps.xs === prevProps.xs
    }
    if (isSame && nextProps.icon != null) {
        isSame = nextProps.icon === prevProps.icon
    }

    return isSame;
});

class PersonalDetails extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            guestFirstName: props.personalDetails.guestFirstName,
            guestLastName: props.personalDetails.guestLastName,
            guestStreet: props.personalDetails.guestStreet,
            guestZip: props.personalDetails.guestZip,
            guestCity: props.personalDetails.guestCity,
            guestCountry: props.personalDetails.guestCountry,

            repFirstName: props.personalDetails.repFirstName,
            repLastName: props.personalDetails.repLastName,
            repStreet: props.personalDetails.repStreet,
            repZip: props.personalDetails.repZip,
            repCity: props.personalDetails.repCity,
            repCountry: props.personalDetails.repCountry,
            repMail: props.personalDetails.repMail,
            repPhone: props.personalDetails.repPhone,

            repCreditCardNumber: props.personalDetails.repCreditCardNumber,

            selectedPaymentMethod: props.personalDetails.selectedPaymentMethod,
            checkboxState: props.personalDetails.checkboxState,
        }

        this.personalDetailsError = props.personalDetailsError;

        this.repPaymentMethod = new Map([
            ["Cash", "Cash"],
            ["CreditCard", "Credit Card"],
        ]);
    }

    repTextFieldOnChange = (value, keyRep, keyGuest) => {
        let errKeyRep = keyRep + "Error";
        let errKeyGuest = keyGuest + "Error";

        const {personalDetails, personalDetailsError} = this.props;
        const {checkboxState} = this.state;

        let isValValid = value !== "";

        let stateObj = {};
        personalDetails[keyRep] = value;
        stateObj[keyRep] = value;

        if (keyGuest != null && checkboxState) {
            personalDetails[keyGuest] = value;
            stateObj[keyGuest] = value;

            if (isValValid) {
                personalDetailsError[errKeyGuest] = "";
            }
        }

        if (isValValid) {
            personalDetailsError[errKeyRep] = "";
        }

        this.setState(stateObj);
    }

    guestIsSamePerson = (checked) => {
        const {personalDetails, personalDetailsError} = this.props;

        let stateObj = {
            checkboxState: checked,
        }

        personalDetails.checkboxState = checked;

        if (checked) {
            personalDetails.guestFirstName = personalDetails.repFirstName;
            personalDetails.guestLastName = personalDetails.repLastName;
            personalDetails.guestStreet = personalDetails.repStreet;
            personalDetails.guestZip = personalDetails.repZip;
            personalDetails.guestCity = personalDetails.repCity;
            personalDetails.guestCountry = personalDetails.repCountry;

            stateObj.guestFirstName = personalDetails.repFirstName;
            stateObj.guestLastName = personalDetails.repLastName;
            stateObj.guestStreet = personalDetails.repStreet;
            stateObj.guestZip = personalDetails.repZip;
            stateObj.guestCity = personalDetails.repCity;
            stateObj.guestCountry = personalDetails.repCountry;

            personalDetailsError.guestFirstNameError = "";
            personalDetailsError.guestLastNameError = "";
            personalDetailsError.guestStreetError = "";
            personalDetailsError.guestZipError = "";
            personalDetailsError.guestCityError = "";
            personalDetailsError.guestCountryError = "";
        } else {
            personalDetails.guestFirstName = "";
            personalDetails.guestLastName = "";
            personalDetails.guestStreet = "";
            personalDetails.guestZip = "";
            personalDetails.guestCity = "";
            personalDetails.guestCountry = "";

            stateObj.guestFirstName = "";
            stateObj.guestLastName = "";
            stateObj.guestStreet = "";
            stateObj.guestZip = "";
            stateObj.guestCity = "";
            stateObj.guestCountry = "";
        }

        this.setState(stateObj)
    };

    guestTextFieldOnChange = (value, key) => {
        let errKey = key + "Error";

        const {personalDetails, personalDetailsError} = this.props;

        personalDetails[key] = value;

        let stateObj = {};
        stateObj[key] = value;

        if (value !== "") {
            personalDetailsError[errKey] = "";
        }

        this.setState(stateObj);
    }

    render() {
        const {classes, personalDetailsError} = this.props;
        const {
            repFirstName, repLastName, repStreet,
            repZip, repCity, repCountry, repMail,
            repPhone, repCreditCardNumber, guestFirstName, guestLastName,
            guestStreet, guestZip, guestCity, guestCountry,
            selectedPaymentMethod, checkboxState,
        } = this.state;

        return (
            <React.Fragment>
                <Container>
                    <Box
                        component="form"
                        sx={{
                            '& .MuiTextField-root': {m: 1, width: '95%'},
                        }}
                        noValidate
                        autoComplete="off"
                    >
                        <Grid container
                              rowSpacing={1}
                              columnSpacing={{xs: 1, sm: 2, md: 3}}
                        >

                            <Grid container item spacing={2}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant={"h6"}>
                                            Representative Data
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}
                                    icon={"AccountCircle"}

                                    label="First Name"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repFirstName", "guestFirstName");
                                    })}
                                    value={repFirstName}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repFirstNameError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}

                                    label="Last Name"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repLastName", "guestLastName");
                                    })}
                                    value={repLastName}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repLastNameError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={4}
                                    icon={"LocationCity"}

                                    label="Street"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repStreet", "guestStreet");
                                    })}
                                    value={repStreet}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repStreetError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={2}

                                    label="Zip"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repZip", "guestZip");
                                    })}
                                    value={repZip}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repZipError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={3}

                                    label="City/Town"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCity", "guestCity");
                                    })}
                                    value={repCity}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repCityError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={3}

                                    label="Country"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCountry", "guestCountry");
                                    })}
                                    value={repCountry}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repCountryError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}
                                    icon={"Email"}

                                    label="Email"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repMail", null);
                                    })}
                                    value={repMail}
                                    type="email"
                                    size="small"

                                    errorText={personalDetailsError.repMailError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}
                                    icon={"Phone"}

                                    label="Phone"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repPhone", null);
                                    })}
                                    value={repPhone}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repPhoneError}
                                />

                                <Grid item xs={6} className={clsx(classes.dropDownPaddingTop)}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <EuroSymbol
                                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                                className={clsx(classes.customSelectIconPos)}
                                            />
                                            <Select
                                                label="Payment"
                                                onChange={(event => {
                                                    this.repTextFieldOnChange(event.target.value, "selectedPaymentMethod", null);
                                                })}
                                                value={selectedPaymentMethod}
                                                variant="standard"
                                                sx={{width: "100%", my: 1.5, textAlign: "left"}}
                                            >

                                                {[...this.repPaymentMethod.keys()].map((key, index) =>
                                                    (
                                                        <MenuItem value={key}>
                                                            {this.repPaymentMethod.get(key)}
                                                        </MenuItem>
                                                    )
                                                )}
                                            </Select>
                                        </Box>
                                    </Item>
                                </Grid>

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}
                                    icon={"Payment"}

                                    label="Credit Card Number"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCreditCardNumber", null);
                                    })}
                                    value={repCreditCardNumber}
                                    type="text"
                                    size="small"

                                    errorText={personalDetailsError.repCreditCardNumberError}
                                />


                                <Grid container item spacing={2}>
                                    <Grid item xs={12}>
                                        <HeaderItem>
                                            <Typography variant={"h6"}>
                                                Guest Data
                                            </Typography>
                                        </HeaderItem>
                                    </Grid>

                                    <Grid item xs={4} className={clsx(classes.checkboxPaddingTop)}>
                                        <Item>
                                            <FormControlLabel
                                                label="Representative is same Person"
                                                control={
                                                    <Checkbox checked={checkboxState}
                                                              onChange={(event) => {
                                                                  this.guestIsSamePerson(event.target.checked)
                                                              }}
                                                    />
                                                }
                                            />
                                        </Item>
                                    </Grid>
                                </Grid>

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}
                                    icon={"AccountCircle"}

                                    label="First Name"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestFirstName");
                                    })}
                                    value={guestFirstName}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestFirstNameError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={6}

                                    label="Last Name"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestLastName");
                                    })}
                                    value={guestLastName}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestLastNameError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={4}
                                    icon={"LocationCity"}

                                    label="Street"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestStreet");
                                    })}
                                    value={guestStreet}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestStreetError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={2}

                                    label="Zip"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestZip");
                                    })}
                                    value={guestZip}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestZipError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={3}

                                    label="City/Town"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestCity");
                                    })}
                                    value={guestCity}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestCityError}
                                />

                                <MemoizedGridItem
                                    classProp={classes}

                                    xs={3}

                                    label="Country"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestCountry");
                                    })}
                                    value={guestCountry}
                                    type="text"
                                    size="small"

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestCountryError}
                                />

                            </Grid>
                        </Grid>
                    </Box>
                </Container>
            </React.Fragment>
        )
    }
}

PersonalDetails.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PersonalDetails);