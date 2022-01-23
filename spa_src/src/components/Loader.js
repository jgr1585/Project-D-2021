import React, {PureComponent} from 'react';
import {SemipolarLoading} from 'react-loadinggg';
import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

const styles = () => ({
    loaderContent: {
        textAlign: 'center !important',
    },
    loaderMessageWrapper: {
        marginTop: '6em',
    },
    loaderMessage: {
        color: '#3056a8',
    },
    myText: {
        fontFamily: 'Arial, Helvetica, sans-serif',
        fontSize: '14px !important',
    },
});

class Loader extends PureComponent {
    render() {
        const {classes, text} = this.props;

        return (
            <div className={clsx(classes.loaderContent)}>
                <SemipolarLoading color="#3056a8"/>
                <div className={clsx(classes.loaderMessageWrapper)}>
                    <span className={clsx(classes.loaderMessage, classes.myText)}>
                        {text}
                    </span>
                </div>
            </div>
        );
    }
}

Loader.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Loader);