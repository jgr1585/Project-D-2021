import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {Box, Grid, TextField, Typography} from "@mui/material";
import Paper from '@mui/material/Paper';
import {styled} from '@mui/material/styles';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import {DatePicker} from "@mui/lab";

const styles = () => ({
    gridItemPaddingTop: {
        paddingTop: '3px !important',
    }
});

const HeaderItem = styled(Paper)(({theme}) => ({
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'left',
    color: theme.palette.text.secondary,
    boxShadow: 'none',
}));

const Item = styled(Paper)(({theme}) => ({
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    boxShadow: 'none',
}));

class ChooseCategories extends PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            from: props.chooseCategory.from,
            until: props.chooseCategory.until,
        }
    }

    render() {
        const {classes, chooseCategory} = this.props;
        const {from, until} = this.state;

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
                                                this.setState({from: newValue});
                                                chooseCategory.from = newValue;
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
                                                this.setState({until: newValue});
                                                chooseCategory.until = newValue;
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

                            {[...chooseCategory.categorySelection.keys()].map((value, index) => (
                                <Grid item xs={3} key={index} className={clsx(classes.gridItemPaddingTop)}>
                                    <Item>
                                        <TextField
                                            id="standard-number"
                                            label={value}
                                            type="number"
                                            onChange={(event) => {
                                                chooseCategory.categorySelection.set(value, event.target.value);
                                            }}
                                            InputProps={{ inputProps: { min: 0, max: 5 } }}
                                            defaultValue={chooseCategory.categorySelection.get(value)}
                                            variant="standard"
                                            required={true}
                                        />
                                    </Item>
                                </Grid>
                            ))}

                            {/*<Grid item xs={3}>*/}
                            {/*    <Item>*/}
                            {/*        <TextField*/}
                            {/*            id="standard-number"*/}
                            {/*            label="Single Room"*/}
                            {/*            type="number"*/}
                            {/*            InputLabelProps={{*/}
                            {/*                shrink: true,*/}
                            {/*            }}*/}
                            {/*            variant="standard"*/}
                            {/*        />*/}
                            {/*    </Item>*/}
                            {/*</Grid>*/}
                            {/*<Grid item xs={3}>*/}
                            {/*    <Item>*/}
                            {/*        <TextField*/}
                            {/*            id="standard-number"*/}
                            {/*            label="Double Room"*/}
                            {/*            type="number"*/}
                            {/*            InputLabelProps={{*/}
                            {/*                shrink: true,*/}
                            {/*            }}*/}
                            {/*            variant="standard"*/}
                            {/*        />*/}
                            {/*    </Item>*/}
                            {/*</Grid>*/}
                        </Grid>
                    </Grid>
                </Box>
            </React.Fragment>
        )
    }
}

ChooseCategories.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ChooseCategories);