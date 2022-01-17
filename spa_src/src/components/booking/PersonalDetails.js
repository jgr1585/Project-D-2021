import React, {PureComponent} from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import {Box, Checkbox, Container, FormControlLabel, Grid, MenuItem, Select, TextField, Typography} from "@mui/material";
import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import {AccountCircle, Email, EuroSymbol, LocationCity, Payment, Phone} from "@mui/icons-material";
import clsx from "clsx";

const styles = () => ({
    dropDownPaddingTop: {
        paddingTop: "28px !important"
    },
    displayNone: {
        display: "none",
    }
});

const HeaderItem = styled(Paper)(({theme}) => ({
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'left',
    color: theme.palette.text.secondary,
    boxShadow: 'none',
}));

const Item = styled(Paper)(({theme}) => ({
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    boxShadow: 'none',
}));

class PersonalDetails extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            selectedPaymentMethod: props.personalDetails.selectedPaymentMethod,
            checkboxState: props.personalDetails.checkboxState,
        }
    }

    render() {
        const {personalDetails, classes} = this.props;
        const {selectedPaymentMethod, checkboxState} = this.state;

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


                                <Grid item xs={6}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <AccountCircle sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="First Name"
                                                onChange={(event => {
                                                    personalDetails.representativeFirstName = event.target.value;

                                                    if (personalDetails.checkboxState) {
                                                        personalDetails.guestFirstName = event.target.value;
                                                    }
                                                })}
                                                defaultValue={personalDetails.representativeFirstName}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={6}>
                                    <Item>
                                        <TextField
                                            label="Last Name"
                                            onChange={(event => {
                                                personalDetails.representativeLastName = event.target.value;

                                                if (personalDetails.checkboxState) {
                                                    personalDetails.guestLastName = event.target.value;
                                                }
                                            })}
                                            defaultValue={personalDetails.representativeLastName}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>

                                <Grid item xs={4}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <LocationCity sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="Street"
                                                onChange={(event => {
                                                    personalDetails.representativeStreet = event.target.value;

                                                    if (personalDetails.checkboxState) {
                                                        personalDetails.guestStreet = event.target.value;
                                                    }
                                                })}
                                                defaultValue={personalDetails.representativeStreet}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={2}>
                                    <Item>
                                        <TextField
                                            label="Zip"
                                            onChange={(event => {
                                                personalDetails.representativeZip = event.target.value;

                                                if (personalDetails.checkboxState) {
                                                    personalDetails.guestZip = event.target.value;
                                                }
                                            })}
                                            defaultValue={personalDetails.representativeZip}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>
                                <Grid item xs={3}>
                                    <Item>
                                        <TextField
                                            label="City/Town"
                                            onChange={(event => {
                                                personalDetails.representativeCity = event.target.value;

                                                if (personalDetails.checkboxState) {
                                                    personalDetails.guestCity = event.target.value;
                                                }
                                            })}
                                            defaultValue={personalDetails.representativeCity}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>
                                <Grid item xs={3}>
                                    <Item>
                                        <TextField
                                            label="Country"
                                            onChange={(event => {
                                                personalDetails.representativeCountry = event.target.value;

                                                if (personalDetails.checkboxState) {
                                                    personalDetails.guestCountry = event.target.value;
                                                }
                                            })}
                                            defaultValue={personalDetails.representativeCountry}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>

                                <Grid item xs={6}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <Email sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="Email"
                                                onChange={(event => {
                                                    personalDetails.representativeMail = event.target.value;
                                                })}
                                                defaultValue={personalDetails.representativeMail}
                                                type="email"
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={6}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <Phone sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="Phone"
                                                onChange={(event => {
                                                    personalDetails.representativePhone = event.target.value;
                                                })}
                                                defaultValue={personalDetails.representativePhone}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>

                                <Grid item xs={6} className={clsx(classes.dropDownPaddingTop)}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <EuroSymbol sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <Select
                                                label="Payment"
                                                onChange={(event => {
                                                    this.setState({selectedPaymentMethod: event.target.value})
                                                    personalDetails.selectedPaymentMethod = event.target.value;
                                                })}
                                                value={selectedPaymentMethod}
                                                variant="standard"
                                                sx={{width: "100%", my: 1.5, textAlign: "left"}}
                                            >

                                                {[...personalDetails.representativePaymentMethod.keys()].map((value, index) =>
                                                    (
                                                        <MenuItem value={value}>
                                                            {personalDetails.representativePaymentMethod.get(value)}
                                                        </MenuItem>
                                                    )
                                                )}
                                            </Select>
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={6}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <Payment sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="Credit Card Number"
                                                onChange={(event => {
                                                    personalDetails.representativeCreditCardNumber = event.target.value;
                                                })}
                                                defaultValue={personalDetails.representativeCreditCardNumber}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>


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
                                                                  let state = event.target.checked;

                                                                  if (state) {
                                                                      personalDetails.guestFirstName = personalDetails.representativeFirstName;
                                                                      personalDetails.guestLastName = personalDetails.representativeLastName;
                                                                      personalDetails.guestStreet = personalDetails.representativeStreet;
                                                                      personalDetails.guestZip = personalDetails.representativeZip;
                                                                      personalDetails.guestCity = personalDetails.representativeCity;
                                                                      personalDetails.guestCountry = personalDetails.representativeCountry;
                                                                  } else {
                                                                      personalDetails.guestFirstName = "";
                                                                      personalDetails.guestLastName = "";
                                                                      personalDetails.guestStreet = "";
                                                                      personalDetails.guestZip = "";
                                                                      personalDetails.guestCity = "";
                                                                      personalDetails.guestCountry = "";
                                                                  }

                                                                  this.setState({checkboxState: state})
                                                                  personalDetails.checkboxState = state;
                                                              }}
                                                    />
                                                }
                                            />
                                        </Item>
                                    </Grid>
                                </Grid>

                                <Grid item xs={6} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <AccountCircle sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="First Name"
                                                onChange={(event => {
                                                    personalDetails.guestFirstName = event.target.value;
                                                })}
                                                defaultValue={personalDetails.guestFirstName}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={6} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <TextField
                                            label="Last Name"
                                            onChange={(event => {
                                                personalDetails.guestLastName = event.target.value;
                                            })}
                                            defaultValue={personalDetails.guestLastName}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>

                                <Grid item xs={4} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                            <LocationCity sx={{color: 'action.active', mr: 1, my: 1.5}}/>
                                            <TextField
                                                label="Street"
                                                onChange={(event => {
                                                    personalDetails.guestStreet = event.target.value;
                                                })}
                                                defaultValue={personalDetails.guestStreet}
                                                variant="standard"
                                                required={true}
                                            />
                                        </Box>
                                    </Item>
                                </Grid>
                                <Grid item xs={2} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <TextField
                                            label="Zip"
                                            onChange={(event => {
                                                personalDetails.guestZip = event.target.value;
                                            })}
                                            defaultValue={personalDetails.guestZip}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>
                                <Grid item xs={3} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <TextField
                                            label="City/Town"
                                            onChange={(event => {
                                                personalDetails.guestCity = event.target.value;
                                            })}
                                            defaultValue={personalDetails.guestCity}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>
                                <Grid item xs={3} className={personalDetails.checkboxState ? classes.displayNone : ""}>
                                    <Item>
                                        <TextField
                                            label="Country"
                                            onChange={(event => {
                                                personalDetails.guestCountry = event.target.value;
                                            })}
                                            defaultValue={personalDetails.guestCountry}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>

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