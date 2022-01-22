import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';

import {Box, Container, Grid, Typography} from "@mui/material";
import {
    AccountCircle,
    CreditCard,
    DateRange,
    Email, Euro, Hotel,
    LocationCity,
    Phone
} from "@mui/icons-material";

import HeaderItem from "../grid/HeaderItem";
import Item from "../grid/Item"

import clsx from "clsx";

const styles = () => ({
    minHeightPaddingTop: {
        paddingTop: "0px !important"
    },
    descriptiveHeaderText: {
        fontWeight: "bold"
    },
    descriptiveText: {
        paddingTop: "3px",
        textAlign: "left",
    },
    descriptiveSpacing: {
        paddingLeft: "10px",
        paddingRight: "10px",
    },
});

class Summary extends PureComponent {

    render() {
        const {classes, bookingDetails} = this.props;

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
                    <Container>
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

                                    <Grid container item xs={12}>
                                        <Grid container item xs={12}>
                                            <Grid item xs={6}>
                                                <Item>
                                                    <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                                                        <DateRange/>

                                                        <div
                                                            className={clsx(classes.descriptiveHeaderText, classes.descriptiveSpacing)}
                                                        >
                                                            <span>From</span>
                                                        </div>
                                                    </Box>
                                                </Item>
                                            </Grid>

                                            <Grid item xs={6}>
                                                <Item>
                                                    <div className={clsx(classes.descriptiveText)}>
                                                        {bookingDetails.chooseCategory.until.toDateString()}
                                                    </div>
                                                </Item>
                                            </Grid>
                                        </Grid>



                                        


                                        <Grid container item xs={12}>
                                            <Grid item xs={1}>
                                                <DateRange/>
                                            </Grid>
                                            <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                                Until
                                            </Grid>
                                            <Grid item xs={7}>
                                                {bookingDetails.chooseCategory.until.toDateString()}
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Grid>


                                <Grid container item xs={6}>
                                    <Grid item xs={12}>
                                        <HeaderItem>
                                            <Typography variant="h6">
                                                Guest Details
                                            </Typography>
                                        </HeaderItem>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <AccountCircle/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Name
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.guestFirstName + " " + bookingDetails.personalDetails.guestLastName}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <LocationCity/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Address
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.guestStreet + ", " + bookingDetails.personalDetails.guestZip + " " + bookingDetails.personalDetails.guestCity + ", " + bookingDetails.personalDetails.guestCountry}
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>

                            <Grid container item spacing={3}>
                                <Grid container item xs={6}>
                                    <Grid item xs={12}>
                                        <HeaderItem>
                                            <Typography variant="h6">
                                                Categories
                                            </Typography>
                                        </HeaderItem>
                                    </Grid>

                                    {Object.keys(bookingDetails.chooseCategory.categorySelection).map((key, index) =>

                                        bookingDetails.chooseCategory.categorySelection[key].value > 0 ?
                                            (
                                                <Grid container item xs={12} key={index}>
                                                    <Grid item xs={1}>
                                                        <Hotel/>
                                                    </Grid>
                                                    <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                                        {bookingDetails.chooseCategory.categorySelection[key].name}
                                                    </Grid>
                                                    <Grid item xs={7}>
                                                        {bookingDetails.chooseCategory.categorySelection[key].value}
                                                    </Grid>
                                                </Grid>
                                            ) : ("")
                                    )}
                                </Grid>

                                <Grid container item xs={6}>
                                    <Grid item xs={12}>
                                        <HeaderItem>
                                            <Typography variant="h6">
                                                Representative Details
                                            </Typography>
                                        </HeaderItem>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <AccountCircle/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Name
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.repFirstName + " " + bookingDetails.personalDetails.repLastName}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <LocationCity/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Address
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.repStreet + ", " + bookingDetails.personalDetails.repZip + " " + bookingDetails.personalDetails.repCity + ", " + bookingDetails.personalDetails.repCountry}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <Email/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Email
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.repMail}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <Phone/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Phone
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.repPhone}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <Euro/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Payment
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.selectedPaymentMethod}
                                        </Grid>
                                    </Grid>

                                    <Grid container item xs={12}>
                                        <Grid item xs={1}>
                                            <CreditCard/>
                                        </Grid>
                                        <Grid item xs={4} className={clsx(classes.descriptiveText)}>
                                            Credit Card Number
                                        </Grid>
                                        <Grid item xs={7}>
                                            {bookingDetails.personalDetails.repCreditCardNumber}
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Container>
                </Box>
            </React.Fragment>
        )
    }
}

Summary.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Summary);