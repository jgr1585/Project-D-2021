
import React, { PureComponent } from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import {Box, Checkbox, FormControlLabel, Grid, TextField, Typography} from "@mui/material";
import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import {AccountCircle, LocationCity, Email, Phone, EuroSymbol, Payment} from "@mui/icons-material";

const styles = () => ({

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
    }

    render() {
        const {personalDetails} = this.props;

        return (
            <React.Fragment>
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
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <AccountCircle sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="First Name"
                                            onChange={(event => {
                                                personalDetails.representativeFirstName.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={6}>
                                <Item>
                                    <TextField
                                        label="Last Name"
                                        onChange={(event => {
                                            personalDetails.representativeLastName.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>

                            <Grid item xs={4}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <LocationCity sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Street"
                                            onChange={(event => {
                                                personalDetails.representativeStreet.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={2}>
                                <Item>
                                    <TextField
                                        label="Zip"
                                        onChange={(event => {
                                            personalDetails.representativeZip.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>
                            <Grid item xs={3}>
                                <Item>
                                    <TextField
                                        label="City/Town"
                                        onChange={(event => {
                                            personalDetails.representativeCity.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>
                            <Grid item xs={3}>
                                <Item>
                                    <TextField
                                        label="Country"
                                        onChange={(event => {
                                           personalDetails.representativeCountry.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>

                            <Grid item xs={6}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <Email sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Email"
                                            onChange={(event => {
                                               personalDetails.representativeMail.set(event.target.value);
                                            })}
                                            type="email"
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={6}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <Phone sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Phone"
                                            onChange={(event => {
                                               personalDetails.representativePhone.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>

                            <Grid item xs={6}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <EuroSymbol sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Payment"
                                            onChange={(event => {
                                               personalDetails.representativePaymentMethod.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={6}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <Payment sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Credit Card Number"
                                            onChange={(event => {
                                               personalDetails.representativeCreditCardNumber.set(event.target.value);
                                            })}
                                            variant="standard"
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

                                <Grid item xs={3}>
                                    <Item>
                                        <FormControlLabel control={<Checkbox defaultChecked={true}/>} label="Representative is same Person" />
                                    </Item>
                                </Grid>
                            </Grid>

                            <Grid item xs={6}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <AccountCircle sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="First Name"
                                            onChange={(event => {
                                               personalDetails.guestFirstName.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={6}>
                                <Item>
                                    <TextField
                                        label="Last Name"
                                        onChange={(event => {
                                           personalDetails.guestLastName.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>

                            <Grid item xs={4}>
                                <Item>
                                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                                        <LocationCity sx={{ color: 'action.active', mr: 1, my: 1.5 }} />
                                        <TextField
                                            label="Street"
                                            onChange={(event => {
                                               personalDetails.guestStreet.set(event.target.value);
                                            })}
                                            variant="standard"
                                        />
                                    </Box>
                                </Item>
                            </Grid>
                            <Grid item xs={2}>
                                <Item>
                                    <TextField
                                        label="Zip"
                                        onChange={(event => {
                                           personalDetails.guestZip.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>
                            <Grid item xs={3}>
                                <Item>
                                    <TextField
                                        label="City/Town"
                                        onChange={(event => {
                                           personalDetails.guestCity.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>
                            <Grid item xs={3}>
                                <Item>
                                    <TextField
                                        label="Country"
                                        onChange={(event => {
                                           personalDetails.guestCountry.set(event.target.value);
                                        })}
                                        variant="standard"
                                    />
                                </Item>
                            </Grid>

                        </Grid>
                    </Grid>
                </Box>
            </React.Fragment>
        )
    }
}

PersonalDetails.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PersonalDetails);