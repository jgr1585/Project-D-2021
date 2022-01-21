import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from "clsx";

import {Box, Checkbox, Container, FormControlLabel, Grid, MenuItem, Select, TextField, Typography} from "@mui/material";
import {AccountCircle, Email, EuroSymbol, LocationCity, Payment, Phone} from "@mui/icons-material";

import Item from "../grid/Item";
import HeaderItem from "../grid/HeaderItem";

const styles = () => ({
    dropDownPaddingTop: {
        paddingTop: "28px !important"
    },
});

const MemoizedGridItem = React.memo((props) => {

    return (
        <Grid item xs={props.xs}>
            <Item>

                {props.icon != null ? (
                    <Box sx={{display: 'flex', alignItems: 'flex-end'}}>

                        {props.icon === "AccountCircle" ? (
                            <AccountCircle sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                        ) : ("")}

                        {props.icon === "LocationCity" ? (
                            <LocationCity sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                        ) : ("")}

                        {props.icon === "Email" ? (
                            <Email sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                        ) : ("")}

                        {props.icon === "Phone" ? (
                            <Phone sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                        ) : ("")}

                        {props.icon === "Payment" ? (
                            <Payment sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                        ) : ("")}

                        <TextField
                            label={props.label}
                            onChange={props.onChange}
                            value={props.value}
                            type={props.type}
                            variant={props.variant}
                            required={props.required}
                            disabled={props.disabled != null && props.disabled}

                            error={props.errorText !== ""}
                            helperText={props.errorText}
                        />
                    </Box>
                ) : (
                    <TextField
                        label={props.label}
                        onChange={props.onChange}
                        value={props.value}
                        type={props.type}
                        variant={props.variant}
                        required={props.required}
                        disabled={props.disabled != null && props.disabled}

                        error={props.errorText !== ""}
                        helperText={props.errorText}
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
                                    xs={6}
                                    icon={"AccountCircle"}

                                    label="First Name"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repFirstName", "guestFirstName");
                                    })}
                                    value={repFirstName}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repFirstNameError}
                                />

                                <MemoizedGridItem
                                    xs={6}

                                    label="Last Name"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repLastName", "guestLastName");
                                    })}
                                    value={repLastName}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repLastNameError}
                                />

                                <MemoizedGridItem
                                    xs={4}
                                    icon={"LocationCity"}

                                    label="Street"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repStreet", "guestStreet");
                                    })}
                                    value={repStreet}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repStreetError}
                                />

                                <MemoizedGridItem
                                    xs={2}

                                    label="Zip"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repZip", "guestZip");
                                    })}
                                    value={repZip}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repZipError}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="City/Town"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCity", "guestCity");
                                    })}
                                    value={repCity}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repCityError}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="Country"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCountry", "guestCountry");
                                    })}
                                    value={repCountry}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repCountryError}
                                />

                                <MemoizedGridItem
                                    xs={6}
                                    icon={"Email"}

                                    label="Email"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repMail", null);
                                    })}
                                    value={repMail}
                                    type="email"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repMailError}
                                />

                                <MemoizedGridItem
                                    xs={6}
                                    icon={"Phone"}

                                    label="Phone"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repPhone", null);
                                    })}
                                    value={repPhone}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    errorText={personalDetailsError.repPhoneError}
                                />

                                <Grid item xs={6} className={clsx(classes.dropDownPaddingTop)}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <EuroSymbol sx={{color: 'action.active', mr: 1, my: 1.5}}/>
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
                                    xs={6}
                                    icon={"Payment"}

                                    label="Credit Card Number"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "repCreditCardNumber", null);
                                    })}
                                    value={repCreditCardNumber}
                                    type="text"
                                    variant="standard"
                                    required={true}

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

                                    <Grid item xs={4}>
                                        <Item>
                                            <FormControlLabel
                                                label="rep is same Person"
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
                                    xs={6}
                                    icon={"AccountCircle"}

                                    label="First Name"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestFirstName");
                                    })}
                                    value={guestFirstName}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestFirstNameError}
                                />

                                <MemoizedGridItem
                                    xs={6}

                                    label="Last Name"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestLastName");
                                    })}
                                    value={guestLastName}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestLastNameError}
                                />

                                <MemoizedGridItem
                                    xs={4}
                                    icon={"LocationCity"}

                                    label="Street"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestStreet");
                                    })}
                                    value={guestStreet}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestStreetError}
                                />

                                <MemoizedGridItem
                                    xs={2}

                                    label="Zip"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestZip");
                                    })}
                                    value={guestZip}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestZipError}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="City/Town"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestCity");
                                    })}
                                    value={guestCity}
                                    type="text"
                                    variant="standard"
                                    required={true}

                                    disabled={checkboxState}
                                    errorText={personalDetailsError.guestCityError}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="Country"
                                    onChange={(event => {
                                        this.guestTextFieldOnChange(event.target.value, "guestCountry");
                                    })}
                                    value={guestCountry}
                                    type="text"
                                    variant="standard"
                                    required={true}

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