import React, {PureComponent} from 'react'
import {Typography, Toolbar} from '@mui/material';
import {KingBed} from "@mui/icons-material";

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({
    appTitle: {
        fontSize: '18px',
        fontFamily: 'Arial, sans-serif',
    },
    customizeToolbar: {
        minHeight: '85px',
        paddingRight: 0,
    },
});

class AppBarContent extends PureComponent {
    render() {
        const {classes} = this.props;

        return (
            <Toolbar className={clsx(classes.customizeToolbar)}>
                <Typography
                    variant="h6"
                    noWrap
                    component="div"
                    sx={{mr: 2, display: {xs: 'none', md: 'flex'}}}
                >
                    <KingBed fontSize={"large"}/>
                </Typography>

                <Typography className={clsx(classes.appTitle)} variant="h6" color="inherit">
                    Hotel Schwarz
                </Typography>
            </Toolbar>
        )
    }
}

AppBarContent.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(AppBarContent);