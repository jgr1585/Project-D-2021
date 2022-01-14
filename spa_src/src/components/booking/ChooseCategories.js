
import React, { PureComponent } from 'react'


import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {TextField} from "@mui/material";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import StaticDatePicker from '@mui/lab/StaticDatePicker';

const styles = () => ({

});

class ChooseCategories extends PureComponent {
    onDateSelectChange(value, chooseCategory) {

    }

    render() {
        const { classes, chooseCategory } = this.props;

        return (
            <React.Fragment>

                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <StaticDatePicker
                        orientation="landscape"
                        openTo="day"
                        value={new Date()}
                        onChange={(newValue) => {
                            this.onDateSelectChange(newValue, chooseCategory);
                        }}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>

            </React.Fragment>
        )
    }
}

ChooseCategories.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ChooseCategories);