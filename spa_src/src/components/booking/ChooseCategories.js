import React, {PureComponent} from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {Container, TextField} from "@mui/material";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import StaticDatePicker from '@mui/lab/StaticDatePicker';
import {DatePicker} from "@mui/lab";

const styles = () => ({});

class ChooseCategories extends PureComponent {
    onDateSelectChange(value, chooseCategory) {

    }

    render() {
        const {classes, chooseCategory} = this.props;

        return (
            <React.Fragment>
                <Container maxWidth="sm">

                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                            label="From Date"
                            value={new Date()}
                            onChange={(newValue) => {
                                this.onDateSelectChange(newValue, chooseCategory);
                            }}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </LocalizationProvider>

                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                            label="Todo Date"
                            value={new Date()}
                            onChange={(newValue) => {
                                this.onDateSelectChange(newValue, chooseCategory);
                            }}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </LocalizationProvider>
                </Container>
            </React.Fragment>
        )
    }
}

ChooseCategories.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ChooseCategories);