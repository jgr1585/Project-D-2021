
import React, { PureComponent } from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({

});

class PersonalDetails extends PureComponent {
    render() {
        const { classes } = this.props;

        return (
            <React.Fragment>

            </React.Fragment>
        )
    }
}

PersonalDetails.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PersonalDetails);