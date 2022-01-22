import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {Box, Container, Grid, TextField, Typography} from "@mui/material";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import {DatePicker} from "@mui/lab";

import Item from "../grid/Item";
import HeaderItem from "../grid/HeaderItem";

const styles = () => ({
    gridItemPaddingTop: {
        paddingTop: '3px !important',
    }
});

class ChooseCategories extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            from: props.chooseCategory.from,
            until: props.chooseCategory.until,

            categorySelection: {...props.chooseCategory.categorySelection},
        }

        this.categoryControllerApi = props.categoryControllerApi;
    }

    componentDidMount() {
        this.setAvailableCategories();
    }

    availableCategories = (from, until) => {
        return new Promise((resolve, reject) => {
            this.categoryControllerApi.availableCategory(from, until, (error, data, response) => {
                if (response.statusCode === 200) {
                    resolve(data);
                } else {
                    reject(error);
                }
            });
        });
    };

    setAvailableCategories = () => {
        const {chooseCategory} = this.props;

        this.availableCategories(
            this.convertToRawDateString(chooseCategory.from),
            this.convertToRawDateString(chooseCategory.until)
        ).then((result) => {
            let categorySelection = {...this.state.categorySelection};

            for (let i = 0; i < result.length; i++) {
                let cat = result[i];

                if (cat != null) {
                    let catSel = categorySelection[cat.categoryId];

                    catSel.max = cat.numberAvailable;

                    if (catSel.value > catSel.max) {
                        catSel.value = catSel.max;
                    }
                }
            }

            chooseCategory.categorySelection = categorySelection;
            this.setState({categorySelection: categorySelection});
        });
    };

    convertToRawDateString = (dateObj) => {
        if (dateObj == null) {
            return "";
        }

        let date = dateObj.getDate();
        if (date < 10) {
            date = "0" + date.toString();
        }

        let month = dateObj.getMonth() + 1;
        if (month < 10) {
            month = "0" + month.toString();
        }

        let year = dateObj.getFullYear().toString();

        return year + "-" + month + "-" + date;
    }

    isValidDate = (date) => {
        return date instanceof Date && !isNaN(date);
    }

    render() {
        const {classes, chooseCategory, chooseCategoryError} = this.props;
        const {from, until, categorySelection} = this.state;

        return (
            <React.Fragment>
                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': {m: 1, width: '95%'},
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <Container>
                        <Grid container
                              rowSpacing={1}
                              columnSpacing={{xs: 1, sm: 2, md: 3}}
                        >
                            <Grid container item spacing={3}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant={"h6"}>
                                            Select Period
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                <Grid item xs={6} className={clsx(classes.gridItemPaddingTop)}>
                                    <Item>
                                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                                            <DatePicker
                                                label="From Date"
                                                inputFormat={"yyyy-MM-dd"}
                                                mask={"____-__-__"}
                                                value={new Date(from)}
                                                onChange={(newValue) => {
                                                    if (!this.isValidDate(newValue)) {
                                                        chooseCategory.from = "";
                                                        return;
                                                    }

                                                    chooseCategory.from = newValue;
                                                    chooseCategoryError.fromError = "";

                                                    let stateObj = {};

                                                    if (chooseCategory.from.getTime() > chooseCategory.until.getTime()) {
                                                        chooseCategory.until = newValue;
                                                        stateObj.until = newValue;
                                                    }

                                                    stateObj.from = newValue;
                                                    this.setState(stateObj);

                                                    this.setAvailableCategories();
                                                }}
                                                renderInput={
                                                    (params) => (
                                                        <TextField
                                                            {...params}
                                                            error={chooseCategoryError.fromError !== ""}
                                                        />
                                                    )
                                                }
                                            />
                                        </LocalizationProvider>
                                    </Item>
                                </Grid>

                                <Grid item xs={6} className={clsx(classes.gridItemPaddingTop)}>
                                    <Item>
                                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                                            <DatePicker
                                                label="Until Date"
                                                inputFormat={"yyyy-MM-dd"}
                                                mask={"____-__-__"}
                                                value={new Date(until)}
                                                minDate={chooseCategory.from}
                                                onChange={(newValue) => {
                                                    if (!this.isValidDate(newValue)) {
                                                        chooseCategory.until = "";
                                                        return;
                                                    }

                                                    chooseCategory.until = newValue;
                                                    chooseCategoryError.untilError = "";

                                                    this.setState({until: newValue});

                                                    this.setAvailableCategories();
                                                }}
                                                renderInput={
                                                    (params) => (
                                                        <TextField
                                                            {...params}
                                                            error={chooseCategoryError.untilError !== ""}
                                                        />
                                                    )
                                                }
                                            />
                                        </LocalizationProvider>
                                    </Item>
                                </Grid>
                            </Grid>

                            <Grid container item spacing={3}>
                                <Grid item xs={12}>
                                    <HeaderItem>
                                        <Typography variant={"h6"}>
                                            Select room categories
                                        </Typography>
                                    </HeaderItem>
                                </Grid>

                                {Object.keys(categorySelection).map((key, index) =>
                                    (
                                        <Grid item xs={3} key={index} className={clsx(classes.gridItemPaddingTop)}>
                                            <Item>
                                                {categorySelection[key].max > 0 ? (
                                                    <TextField
                                                        id="standard-number"
                                                        label={categorySelection[key].name}
                                                        type="number"
                                                        onChange={(event) => {
                                                            let categorySelection = {...this.state.categorySelection};

                                                            chooseCategory.categorySelection[key].value = event.target.value;
                                                            categorySelection[key].value = event.target.value;

                                                            if (event.target.value > 0) {
                                                                chooseCategoryError.categorySelectionError = "";
                                                            }

                                                            this.setState({categorySelection: categorySelection});
                                                        }}
                                                        InputProps={{
                                                            inputProps: {
                                                                min: 0,
                                                                max: categorySelection[key].max
                                                            }
                                                        }}
                                                        value={categorySelection[key].value}
                                                        variant="standard"

                                                        error={chooseCategoryError.categorySelectionError !== ""}
                                                        helperText={"Rooms available: " + categorySelection[key].max}
                                                    />
                                                ) : (
                                                    <TextField
                                                        id="standard-text"
                                                        label={categorySelection[key].name}
                                                        type="text"
                                                        value={"Sold out"}
                                                        variant="standard"
                                                        disabled={true}
                                                    />
                                                )}
                                            </Item>
                                        </Grid>
                                    )
                                )}
                            </Grid>
                        </Grid>
                    </Container>
                </Box>
            </React.Fragment>
        )
    }
}

ChooseCategories.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ChooseCategories);