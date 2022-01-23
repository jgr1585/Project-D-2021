import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {
    Box,
    Container,
    Grid, Paper,
    TextField,
    Typography
} from "@mui/material";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import {DatePicker} from "@mui/lab";

import Item from "../grid/Item";
import HeaderItem from "../grid/HeaderItem";
import Carousel from "react-material-ui-carousel";

function importAll(r) {
    let images = {};
    r.keys().forEach((item, index) => {
        images[item.replace('./', '')] = r(item);
    });
    return images
}

const images = importAll(require.context('../../img/hotel_choose_categories', false, /\.(png|jpe?g|svg)$/));

const styles = () => ({
    gridItemPaddingTop: {
        paddingTop: '3px !important',
    },
    sectionPaddingTop: {
        paddingTop: '24px',
    },
    descriptionStyles: {
        textAlign: 'left',
        whiteSpace: 'break-spaces',
    },
    innerCarouselStyles: {
        paddingTop: '50px !important',
        minHeight: '400px',
    },
    customNavBtnWrapperStyles: {
        position: "absolute",
        backgroundColor: "transparent",
        '&:hover': {
            '& $button': {
                backgroundColor: "transparent !important",
                opacity: "1 !important",
                color: "#666666 !important"
            }
        }
    },
    customNavBtnStyles: {
        position: "relative",
        color: "#666666",
        backgroundColor: "transparent",
        top: "calc(50% - 20px) !important",
        fontSize: "30px",
        transition: "200ms",
        cursor: "pointer",
        '&:hover': {
            opacity: "1 !important"
        },
    },
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
    };

    isValidDate = (date) => {
        return date instanceof Date && !isNaN(date);
    };

    renderDatePicker = (classes) => {
        const {chooseCategory, chooseCategoryError, hasValueChanged} = this.props;
        const {from, until} = this.state;

        return (
            <React.Fragment>
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

                                        hasValueChanged(true);

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

                                        hasValueChanged(true);

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
            </React.Fragment>
        )
    };

    renderCategories = (classes) => {
        const {chooseCategory, chooseCategoryError, hasValueChanged} = this.props;
        const {categorySelection} = this.state;

        return (
            <React.Fragment>
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

                                                hasValueChanged(true);

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
            </React.Fragment>
        )
    };

    renderInnerCarousel = (classes, logos) => {
        return (
            <React.Fragment>
                <div className={clsx(classes.sectionPaddingTop)}>
                    <Carousel
                        autoPlay={false}
                        animation={"slide"}
                        indicators={false}
                        navButtonsAlwaysVisible={true}

                        navButtonsProps={{className: clsx(classes.customNavBtnStyles)}}
                        navButtonsWrapperProps={{className: clsx(classes.customNavBtnWrapperStyles)}}


                    >
                        {logos.map((img, index) =>
                            (
                                <img src={img} alt="logo" height={280} width={445}/>
                            )
                        )}
                    </Carousel>
                </div>
            </React.Fragment>
        )
    };

    renderCarousel = (classes) => {
        const items = [
            {
                title: "Single Room",
                descriptionHeader: "Relax, live and experience!",
                description: "In our bright and spacious single rooms your holidays will turn out to be real break. " +
                    "In these rooms you will be able to really recharge. " +
                    "Garage at the entrance of the village (500m from the hotel) or parking space near the hotel." +
                    "\n\nApprox. 19m²",
                logos: [
                    images["hotelChooseCategories_single_1.jpg"], images["hotelChooseCategories_single_2.jpg"],
                    images["hotelChooseCategories_single_3.jpg"]
                ],
            },
            {
                title: "Double Room",
                descriptionHeader: "Immerse yourself in a sense of security, take your time and enjoy!",
                description: "Superior room with cosy sitting area, balcony, shower, WC, flat-screen TV, WiFi, radio, " +
                    "alarm clock, hair-dryer and safe. Garage at the entrance of the village (500m from the hotel) or parking space near the hotel." +
                    "\n\nApprox. 25m²",
                logos: [
                    images["hotelChooseCategories_double_1.jpg"], images["hotelChooseCategories_double_2.jpg"],
                    images["hotelChooseCategories_double_3.jpg"], images["hotelChooseCategories_double_4.jpg"]
                ],
            },
            {
                title: "Family Room",
                descriptionHeader: "Design in every corner!",
                description: "Our new Montana family rooms have been cleverly designed down " +
                    "to the last detail and offer plenty of space for your holidays. There you can really feel at home." +
                    "\n" +
                    "Modern two-room suites with a difference. The open wooden roof structure, " +
                    "the Bretz designer sofa as well as the fine cloths and the cosy loggia make " +
                    "these suites truly unique. Our new Montana junior suites feature an open living-cum-bedroom and " +
                    "a separate small room (with a 1.60m-wide bed or a bunk bed). They are equipped with a stylish " +
                    "sitting area, a bathroom with walk-in shower, a separate WC, a flat screen TV, WiFi, an alarm clock, " +
                    "a hair-dryer, a minibar and a safe. Parking space in the Muirengarage – 70m from the hotel." +
                    "\n\nApprox. 45m²",
                logos: [
                    images["hotelChooseCategories_family_1.jpg"], images["hotelChooseCategories_family_2.jpg"],
                    images["hotelChooseCategories_family_3.jpg"], images["hotelChooseCategories_family_4.jpg"],
                    images["hotelChooseCategories_family_5.jpg"], images["hotelChooseCategories_family_6.jpg"]
                ],
            },
            {
                title: "Suite Room",
                descriptionHeader: "Romantic alpine-style flair!",
                description: "New, luxurious and spacious suites with a difference. Just relax, live and enjoy. Our large " +
                    "and very modern suites offer plenty of space for your relaxing holidays. Real matured timber, " +
                    "stone walls, an open fireplace and a beautiful terrace create a romantic ambiance to feel at home in. " +
                    "The generous suite also features a cosy sitting area, a spacious bathroom with shower and WC, a " +
                    "flat-screen TV, WiFi, a radio, a hair-dryer, a minibar and a safe.  Parking space in the hotel’s garage." +
                    "\n\nApprox. 45 – 50m²",
                logos: [
                    images["hotelChooseCategories_suite_1.jpg"], images["hotelChooseCategories_suite_2.jpg"],
                    images["hotelChooseCategories_suite_3.jpg"], images["hotelChooseCategories_suite_4.jpg"]
                ],
            },
        ];

        return (
            <React.Fragment>
                <div className={clsx(classes.sectionPaddingTop)}>
                    <Carousel
                        interval={6000}
                        navButtonsAlwaysInvisible={true}
                    >
                        {items.map((item, index) =>
                            (
                                <Paper>
                                    <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}} key={index}>
                                        <Grid container item spacing={3} key={index}>
                                            <Grid item xs={6} key={index}>
                                                <HeaderItem>
                                                    <Typography variant={"h6"}>
                                                        {item.title}
                                                    </Typography>
                                                </HeaderItem>

                                                <br/>

                                                <Item className={clsx(classes.descriptionStyles)}>
                                                    <Typography>
                                                        {item.descriptionHeader}
                                                    </Typography>
                                                </Item>

                                                <Item className={clsx(classes.descriptionStyles)}>
                                                    <p>
                                                        {item.description}
                                                    </p>
                                                </Item>
                                            </Grid>

                                            <Grid item xs={6} key={index} className={clsx(classes.innerCarouselStyles)}>
                                                <Item>
                                                    {this.renderInnerCarousel(classes, item.logos)}
                                                </Item>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Paper>
                            )
                        )}
                    </Carousel>
                </div>
            </React.Fragment>
        )
    };

    render() {
        const {classes} = this.props;

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

                            {this.renderDatePicker(classes)}

                            {this.renderCategories(classes)}

                        </Grid>

                        {this.renderCarousel(classes)}
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