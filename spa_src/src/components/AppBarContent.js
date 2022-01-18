import React, {PureComponent} from 'react'
import {Button, Grid, Toolbar} from '@mui/material';
import {BookOnline} from '@mui/icons-material';

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import logo from '../img/appBarHotelLogo.png'

const styles = () => ({
    appTitle: {
        fontSize: '18px',
        fontFamily: 'Arial, sans-serif',
    },
    customizeToolbar: {
        minHeight: '85px',
        paddingRight: 0,
    },
    createBookingBtnStyle: {
        padding: "10px",
        float: "right",
        marginRight: "20px",
        backgroundColor: "rgba(0, 0, 0, 0.20)",
    }
});

class AppBarContent extends PureComponent {
    render() {
        const {classes} = this.props;

        return (
            <Toolbar className={clsx(classes.customizeToolbar)}>
                <Grid container
                      rowSpacing={1}
                      columnSpacing={{xs: 1, sm: 2, md: 3}}
                >
                    <Grid container item spacing={3}>
                        <Grid item xs={6}>
                            <img src={logo} alt="logo" height={40}/>
                        </Grid>

                        <Grid item xs={6}>
                            <Button
                                color={"inherit"}
                                startIcon={<BookOnline/>}
                                onClick={() => this.props.createNewBooking()}
                                className={clsx(classes.createBookingBtnStyle)}
                            >
                                Create booking
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </Toolbar>
        )
    }
}

AppBarContent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AppBarContent);