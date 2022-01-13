
import React, { PureComponent } from 'react';
import withStyles from '@mui/styles/withStyles';
import { AppBar, Snackbar } from '@mui/material';
import clsx from 'clsx';

import Loader from './loader';
import Footer from './footer';
import AppBarContent from "./AppBarContent";

const styles = theme => ({
    app: {
        position: 'fixed',
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
        flexDirection: 'column',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        flex: '0 0 auto',
        position: 'relative',
        color: '#ffffff',
        backgroundColor: '#2196F3 !important',
    },
});

class App extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            initCallsMade: true,
            initCallsError: false,
        };
    }

    componentDidMount() {
        // Ajax Calls (get all necessary information about the hotel)
        this.initCalls();
    }

    initCalls = () => {
        // openapi??
    };


    render() {
        const { classes } = this.props;

        return (
            <div className={clsx(classes.app)}>
                {/*check if the header param exist*/}
                <AppBar position="static" className={clsx(classes.appBar)}>
                    <AppBarContent />
                </AppBar>


                {/*check if init calls are made*/}
                {!this.state.initCallsMade ? (
                    <Loader />
                ) : (
                    <React.Fragment>
                        <main>

                        </main>

                        {/*add footer*/}
                        <Footer/>
                    </React.Fragment>
                )}

                {/* Notification container*/}
                {/*{this.snackbarContent(classes)}*/}
            </div>
        );
    }
}

export default withStyles(styles)(App);