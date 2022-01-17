import React, {PureComponent} from 'react';

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({
    centered: {
        position: 'absolute',
        bottom: 0,
    },
    positioningFooter: {
        display: 'block',
        minWidth: '80%',
        flexWrap: 'wrap',
        margin: '8px',
        marginBottom: 0,
    },
    newDashboardBtn: {
        padding: '2px 8px !important',
        fontSize: '14px !important',
        borderRadius: '0 !important',
        textTransform: 'inherit !important',
    },
    btnFooterMarginLeft: {
        marginLeft: '0px !important',
    },
});

class Footer extends PureComponent {
    render() {
        const {classes} = this.props;

        return (
            <footer className={clsx(classes.centered)}>
                <div className={clsx(classes.positioningFooter)}>

                </div>
            </footer>
        );
    }
}

Footer.propTypes = {
    classes: PropTypes.object.isRequired,
    onOpenSettings: PropTypes.func,
    onCloseSettings: PropTypes.func,
    onCreate: PropTypes.func,
};

export default withStyles(styles)(Footer);