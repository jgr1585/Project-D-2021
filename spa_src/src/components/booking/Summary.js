import React, {PureComponent} from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';

import {Box, Grid, List, ListItem, ListItemIcon, ListItemText, Typography} from "@mui/material";
import {
    AccountCircle,
    CreditCard,
    DateRange,
    Email, Euro, Hotel,
    LocationCity,
    Phone
} from "@mui/icons-material";
import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";

const styles = () => ({});

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

class Summary extends PureComponent {
    constructor(props) {
        super(props);
    }

    render() {
        const {classes, bookingDetails} = this.props;
        console.log(bookingDetails);

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
                        <Grid container item spacing={3}>
                            <Grid container item xs={6}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant="h6">
                                            Period
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                <Grid item xs={6}>
                                    <Item>
                                        <List>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <DateRange/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="From"
                                                    secondary={bookingDetails.chooseCategory.from.toDateString()}
                                                />
                                            </ListItem>
                                        </List>
                                    </Item>
                                </Grid>

                                <Grid item xs={6}>
                                    <Item>
                                        <List>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <DateRange/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Until"
                                                    secondary={bookingDetails.chooseCategory.until.toDateString()}
                                                />
                                            </ListItem>
                                        </List>
                                    </Item>
                                </Grid>
                            </Grid>

                            <Grid container item xs={6}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant="h6">
                                            Categories
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                {[...bookingDetails.chooseCategory.categorySelection.keys()].map((value, index) =>

                                    bookingDetails.chooseCategory.categorySelection.get(value) > 0 ?
                                        (
                                            <Grid item xs={6} key={index}>
                                                <Item>
                                                    <List>
                                                        <ListItem>
                                                            <ListItemIcon>
                                                                <Hotel/>
                                                            </ListItemIcon>
                                                            <ListItemText
                                                                primary={value}
                                                                secondary={bookingDetails.chooseCategory.categorySelection.get(value)}
                                                            />
                                                        </ListItem>
                                                    </List>
                                                </Item>
                                            </Grid>
                                        ) : ("")
                                )}
                            </Grid>
                        </Grid>

                        <Grid container item spacing={3}>
                            <Grid item xs={6}>
                                <HeaderItem>
                                    <Typography variant="h6">
                                        Guest Details
                                    </Typography>
                                </HeaderItem>
                                <Grid item xs={6}>
                                    <Item>
                                        <List>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <AccountCircle/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Name"
                                                    secondary={bookingDetails.personalDetails.guestFirstName + " " + bookingDetails.personalDetails.guestLastName}
                                                />
                                            </ListItem>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <LocationCity/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Address"
                                                    secondary={bookingDetails.personalDetails.guestStreet + ", " + bookingDetails.personalDetails.guestZip + " " + bookingDetails.personalDetails.guestCity + ", " + bookingDetails.personalDetails.guestCountry}
                                                />
                                            </ListItem>
                                        </List>
                                    </Item>
                                </Grid>
                            </Grid>

                            <Grid container item xs={6}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant="h6">
                                            Representative Details
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                <Grid item xs={6}>
                                    <Item>
                                        <List>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <AccountCircle/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Name"
                                                    secondary={bookingDetails.personalDetails.representativeFirstName + " " + bookingDetails.personalDetails.representativeLastName}
                                                />
                                            </ListItem>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <LocationCity/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Address"
                                                    secondary={bookingDetails.personalDetails.representativeStreet + ", " + bookingDetails.personalDetails.representativeZip + " " + bookingDetails.personalDetails.representativeCity + ", " + bookingDetails.personalDetails.representativeCountry}
                                                />
                                            </ListItem>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <Email/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Email"
                                                    secondary={bookingDetails.personalDetails.representativeMail}
                                                />
                                            </ListItem>
                                        </List>
                                    </Item>
                                </Grid>

                                <Grid item xs={6}>
                                    <Item>
                                        <List>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <Phone/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Phone"
                                                    secondary={bookingDetails.personalDetails.representativePhone}
                                                />
                                            </ListItem>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <Euro/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Payment"
                                                    secondary={bookingDetails.personalDetails.representativePaymentMethod}
                                                />
                                            </ListItem>
                                            <ListItem>
                                                <ListItemIcon>
                                                    <CreditCard/>
                                                </ListItemIcon>
                                                <ListItemText
                                                    primary="Credit Card Number"
                                                    secondary={bookingDetails.personalDetails.representativeCreditCardNumber}
                                                />
                                            </ListItem>
                                        </List>
                                    </Item>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Box>
            </React.Fragment>
        )
    }
}

Summary.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Summary);