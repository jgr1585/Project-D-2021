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

            representativeFirstName: props.personalDetails.representativeFirstName,
            representativeLastName: props.personalDetails.representativeLastName,
            representativeStreet: props.personalDetails.representativeStreet,
            representativeZip: props.personalDetails.representativeZip,
            representativeCity: props.personalDetails.representativeCity,
            representativeCountry: props.personalDetails.representativeCountry,
            representativeMail: props.personalDetails.representativeMail,
            representativePhone: props.personalDetails.representativePhone,

            representativeCreditCardNumber: props.personalDetails.representativeCreditCardNumber,

            selectedPaymentMethod: props.personalDetails.selectedPaymentMethod,
            checkboxState: props.personalDetails.checkboxState,
        }

        this.representativePaymentMethod = new Map([
            ["cash", "Cash"],
            ["creditCard", "Credit Card"],
        ]);
    }

    repTextFieldOnChange = (value, keyRep, keyGuest) => {
        const {personalDetails} = this.props;
        const {checkboxState} = this.state;

        let stateObj = {};
        personalDetails[keyRep] = value;
        stateObj[keyRep] = value;

        if (keyGuest != null && checkboxState) {
            personalDetails[keyGuest] = value;
            stateObj[keyGuest] = value;
        }

        this.setState(stateObj);
    }

    guestIsSamePerson = (checked) => {
        const {personalDetails} = this.props;

        let stateObj = {
            checkboxState: checked,

            guestFirstName: "",
            guestLastName: "",
            guestStreet: "",
            guestZip: "",
            guestCity: "",
            guestCountry: "",
        }

        personalDetails.checkboxState = checked;

        if (checked) {
            personalDetails.guestFirstName = personalDetails.representativeFirstName;
            personalDetails.guestLastName = personalDetails.representativeFirstName;
            personalDetails.guestStreet = personalDetails.representativeFirstName;
            personalDetails.guestZip = personalDetails.representativeFirstName;
            personalDetails.guestCity = personalDetails.representativeFirstName;
            personalDetails.guestCountry = personalDetails.representativeFirstName;

            stateObj.guestFirstName = personalDetails.representativeFirstName;
            stateObj.guestLastName = personalDetails.representativeLastName;
            stateObj.guestStreet = personalDetails.representativeStreet;
            stateObj.guestZip = personalDetails.representativeZip;
            stateObj.guestCity = personalDetails.representativeCity;
            stateObj.guestCountry = personalDetails.representativeCountry;
        }

        this.setState(stateObj)
    };

    guestTextFieldOnChange = (value, key) => {
        const {personalDetails} = this.props;

        personalDetails[key] = value;

        let stateObj = {};
        stateObj[key] = value;

        this.setState(stateObj);
    }

    render() {
        const {classes} = this.props;
        const {
            representativeFirstName, representativeLastName, representativeStreet,
            representativeZip, representativeCity, representativeCountry, representativeMail,
            representativePhone, representativeCreditCardNumber, guestFirstName, guestLastName,
            guestStreet, guestZip, guestCity, guestCountry,
            selectedPaymentMethod, checkboxState
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
                                        this.repTextFieldOnChange(event.target.value, "representativeFirstName", "guestFirstName");
                                    })}
                                    value={representativeFirstName}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={6}

                                    label="Last Name"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeLastName", "guestLastName");
                                    })}
                                    value={representativeLastName}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={4}
                                    icon={"LocationCity"}

                                    label="Street"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeStreet", "guestStreet");
                                    })}
                                    value={representativeStreet}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={2}

                                    label="Zip"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeZip", "guestZip");
                                    })}
                                    value={representativeZip}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="City/Town"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeCity", "guestCity");
                                    })}
                                    value={representativeCity}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={3}

                                    label="Country"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeCountry", "guestCountry");
                                    })}
                                    value={representativeCountry}
                                    type="text"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={6}
                                    icon={"Email"}

                                    label="Email"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativeMail", null);
                                    })}
                                    value={representativeMail}
                                    type="email"
                                    variant="standard"
                                    required={true}
                                />

                                <MemoizedGridItem
                                    xs={6}
                                    icon={"Phone"}

                                    label="Phone"
                                    onChange={(event => {
                                        this.repTextFieldOnChange(event.target.value, "representativePhone", null);
                                    })}
                                    value={representativePhone}
                                    type="text"
                                    variant="standard"
                                    required={true}
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

                                                {[...this.representativePaymentMethod.keys()].map((key, index) =>
                                                    (
                                                        <MenuItem value={key}>
                                                            {this.representativePaymentMethod.get(key)}
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
                                        this.repTextFieldOnChange(event.target.value, "representativeCreditCardNumber", null);
                                    })}
                                    value={representativeCreditCardNumber}
                                    type="text"
                                    variant="standard"
                                    required={true}
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