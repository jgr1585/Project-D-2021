import React, {PureComponent} from 'react'
import {Button} from '@mui/material';

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({

});

class HotelOverview extends PureComponent {
    render() {
        const {classes} = this.props;

        return (
            <React.Fragment>
                <Button variant="contained"
                        onClick={() => this.props.createNewBooking()}
                >
                    Create booking
                </Button>
            </React.Fragment>
        )
    }
}

HotelOverview.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(HotelOverview);