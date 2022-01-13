
import React, { PureComponent } from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({

});

class Summary extends PureComponent {
    render() {
        const { classes } = this.props;

        return (
            <React.Fragment>

            </React.Fragment>
        )
    }
}

Summary.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Summary);