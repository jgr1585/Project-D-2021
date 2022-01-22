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
        return new Promise((resolve) => {
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

                resolve(result);
            });
        });
    };

    convertToRawDateString = (dateObj) => {
        if (dateObj == null) {
            return "";
        }

        let date = dateObj.getDate().toString();

        let month = dateObj.getMonth() + 1;
        if (month < 10) {
            month = "0" + month.toString();
        }

        let year = dateObj.getFullYear().toString();

        return year + "-" + month + "-" + date;
    }

    render() {
        const {classes, chooseCategory} = this.props;
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
                                                    chooseCategory.from = newValue;

                                                    this.setAvailableCategories().then(() => {
                                                        this.setState({from: newValue});
                                                    });
                                                }}
                                                renderInput={(params) => <TextField {...params} />}
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
                                                onChange={(newValue) => {
                                                    chooseCategory.until = newValue;

                                                    this.setAvailableCategories().then(() => {
                                                        this.setState({until: newValue});
                                                    });
                                                }}
                                                renderInput={(params) => <TextField {...params} />}
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
                                                <TextField
                                                    id="standard-number"
                                                    label={categorySelection[key].name}
                                                    type="number"
                                                    onChange={(event) => {
                                                        let categorySelection = {...this.state.categorySelection};

                                                        chooseCategory.categorySelection[key].value = event.target.value;
                                                        categorySelection[key].value = event.target.value;

                                                        this.setState({categorySelection: categorySelection});
                                                    }}
                                                    InputProps={{inputProps: {min: 0, max: categorySelection[key].max}}}
                                                    value={categorySelection[key].value}
                                                    variant="standard"
                                                    required={true}
                                                />
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