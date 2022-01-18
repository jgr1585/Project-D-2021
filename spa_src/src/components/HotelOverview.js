import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {
    Box,
    Container,
    Grid,
    Paper,
    Chip,
    Rating,
    Typography,
    Table,
    TableHead,
    TableRow,
    TableCell, TableBody, Stack
} from '@mui/material';
import Carousel from 'react-material-ui-carousel'

import {LocationOn, Pets, Spa, LocalParking, FamilyRestroom, Wifi, LocalBar, SmokeFree} from '@mui/icons-material';

import Item from "./grid/Item";
import HeaderItem from "./grid/HeaderItem";

function importAll(r) {
    let images = {};
    r.keys().forEach((item, index) => {
        images[item.replace('./', '')] = r(item);
    });
    return images
}

const images = importAll(require.context('../img', false, /\.(png|jpe?g|svg)$/));

const styles = () => ({
    customTextStyle: {
        fontFamily: "Arial",
        fontSize: "14px",
    },
    customIconMarginBottom: {
        marginBottom: "16px",
    },
    sectionPadding: {
        padding: '12px 0px',
    },
    stackPadding: {
        padding: '6px 0px',
    },
    facilityIconColor: {
        color: "#008009 !important",
    },
});

class HotelOverview extends PureComponent {

    renderHeaderItems = (classes) => {
        return (
            <React.Fragment>
                <div className={clsx(classes.sectionPadding)}>
                    <div>
                        <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}}>
                            <Grid container item spacing={3}>

                                <Grid item xs={6}>
                                    <HeaderItem>
                                        <Rating name="read-only" value={4} readOnly max={4}/>
                                    </HeaderItem>
                                </Grid>

                                <Grid item xs={6}>
                                    <HeaderItem>
                                        <Stack direction="row" spacing={1}>
                                            <Chip label={"Airport shuttle"}/>
                                            <Chip label={"At the ski slope"}/>
                                            <Chip label={"Free parking"}/>
                                        </Stack>
                                    </HeaderItem>
                                </Grid>

                            </Grid>
                        </Grid>
                    </div>

                    <div>
                        <Box sx={{display: 'flex', alignItems: 'flex-end'}}>
                            <LocationOn
                                sx={{color: 'action.active', mr: 1, my: 1.5}}
                                className={clsx(classes.customIconMarginBottom)}
                            />

                            <p>
                                Zürs 126, 6763 Zürs am Arlberg, Austria
                            </p>
                        </Box>
                    </div>
                </div>
            </React.Fragment>
        )
    }

    renderCarousel = (classes) => {
        const items = [
            {
                logos: [images["hotelOverview_1.jpg"], images["hotelOverview_2.jpg"]],
            },
            {
                logos: [images["hotelOverview_3.jpg"], images["hotelOverview_4.jpg"]],
            },
            {
                logos: [images["hotelOverview_5.jpg"], images["hotelOverview_6.jpg"]],
            },
            {
                logos: [images["hotelOverview_7.jpg"], images["hotelOverview_8.jpg"]],
            },
            {
                logos: [images["hotelOverview_9.jpg"], images["hotelOverview_10.jpg"]],
            },
            {
                logos: [images["hotelOverview_11.jpg"], images["hotelOverview_12.jpg"]],
            },
            {
                logos: [images["hotelOverview_13.jpg"], images["hotelOverview_14.jpg"]],
            },
            {
                logos: [images["hotelOverview_15.jpg"], images["hotelOverview_16.jpg"]],
            },
            {
                logos: [images["hotelOverview_17.jpg"], images["hotelOverview_18.jpg"]],
            },
            {
                logos: [images["hotelOverview_19.jpg"], images["hotelOverview_20.jpg"]],
            },
            {
                logos: [images["hotelOverview_21.jpg"], images["hotelOverview_22.jpg"]],
            },
            {
                logos: [images["hotelOverview_23.jpg"], images["hotelOverview_24.jpg"]],
            },
            {
                logos: [images["hotelOverview_25.jpg"], images["hotelOverview_26.jpg"]],
            },
        ];

        return (
            <div className={clsx(classes.sectionPadding)}>
                <Carousel>
                    {items.map((item, index) =>
                        (
                            <Paper>
                                <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}} key={index}>
                                    <Grid container item spacing={3} key={index}>
                                        {item.logos.map((img, index) =>
                                            (
                                                <Grid item xs={6} key={index}>
                                                    <Item>
                                                        <img src={img} alt="logo" height={370} width={550}/>
                                                    </Item>
                                                </Grid>
                                            )
                                        )}
                                    </Grid>
                                </Grid>
                            </Paper>
                        )
                    )}
                </Carousel>
            </div>
        )
    }

    renderDescription = (classes) => {
        return (
            <React.Fragment>
                <div className={clsx(classes.customTextStyle, classes.sectionPadding)}>
                    <p>
                        Hotel Schwarz is a modern 4-star hotel centrally located in the
                        middle of Zürs, the cradle of alpine skiing.
                    </p>
                    <p>
                        Enjoy skiing with the highest comfort: the ski slopes end directly in front of
                        the front door, ski lifts and cable car are 150m away from the house.
                        Also the meeting point of the ski school and the ski kindergarten are right next to the
                        hotel.
                    </p>
                    <p>
                        Let the day begin comfortably with the delicious breakfast buffet before you
                        you enjoy the wonderful ski slopes and deep snow around Zürs.
                        Before dinner in the hotel restaurant, you can still relax in the sauna or steam room
                        steam bath, use the fitness room or enjoy a massage in the wellness center of Hotel
                        Schwarz.
                    </p>
                </div>
            </React.Fragment>
        )
    }

    renderHotelFacilities = (classes) => {
        return (
            <React.Fragment>
                <div className={clsx(classes.sectionPadding)}>
                    <Typography variant={"h6"}>
                        Most Popular Facilities
                    </Typography>

                    <Stack direction="row" spacing={1} className={clsx(classes.stackPadding)}>
                        <Chip icon={<Pets className={clsx(classes.facilityIconColor)}/>}
                              label="Pets allowed"
                              variant="outlined"
                        />
                        <Chip icon={<Spa className={clsx(classes.facilityIconColor)}/>}
                              label="Spa & Wellness Center"
                              variant="outlined"
                        />
                        <Chip icon={<LocalParking className={clsx(classes.facilityIconColor)}/>}
                              label="Parking included"
                              variant="outlined"
                        />
                        <Chip icon={<FamilyRestroom className={clsx(classes.facilityIconColor)}/>}
                              label="Family room"
                              variant="outlined"
                        />
                    </Stack>

                    <Stack direction="row" spacing={1} className={clsx(classes.stackPadding)}>
                        <Chip icon={<Wifi className={clsx(classes.facilityIconColor)}/>}
                              label="Wifi included"
                              variant="outlined"
                        />
                        <Chip icon={<SmokeFree className={clsx(classes.facilityIconColor)}/>}
                              label="Non smoking room"
                              variant="outlined"
                        />
                        <Chip icon={<LocalBar className={clsx(classes.facilityIconColor)}/>}
                              label="Bar"
                              variant="outlined"
                        />
                    </Stack>
                </div>
            </React.Fragment>
        )
    }

    renderCategories = (classes) => {
        const {categories} = this.props;

        const columns = [
            {
                id: 'roomCategory',
                label: 'Room Category',
                minWidth: 200,
                align: 'left',
                format: (value) => value.toLocaleString('en-US'),
            },
            {
                id: 'description',
                label: 'Description',
                minWidth: 500,
                align: 'left',
                format: (value) => value.toLocaleString('en-US'),
            },
            {
                id: 'summerPrice',
                label: 'Summer Price',
                minWidth: 100,
                align: 'right',
                format: (value) => value.toFixed(2),
            },
            {
                id: 'winterPrice',
                label: 'Winter Price',
                minWidth: 100,
                align: 'right',
                format: (value) => value.toFixed(2),
            },
        ];
        const rows = [];

        let seasonPrice = (category, seasonName) => {
            let price = 0;

            for (let i = 0; i < category.priceList.length; i++) {
                let priceList = category.priceList[i];

                if (priceList != null && priceList.name === seasonName) {
                    price = priceList.price;
                    break;
                }
            }

            return price;
        };

        for (let i = 0; i < categories.length; i++) {
            let cat = categories[i];

            if (cat != null) {

                rows.push({
                    roomCategory: cat.title,
                    description: cat.description,
                    summerPrice: seasonPrice(cat, "Summer") + " €",
                    winterPrice: seasonPrice(cat, "Winter") + " €",
                });
            }
        }

        return (
            <React.Fragment>
                <div className={clsx(classes.sectionPadding)}>
                    <Typography variant={"h6"}>
                        Categories
                    </Typography>

                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                {columns.map((column) => (
                                    <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{minWidth: column.minWidth}}
                                    >
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.map((row) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                        {columns.map((column) => {
                                            const value = row[column.id];
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    {column.format && typeof value === 'number'
                                                        ? column.format(value)
                                                        : value}
                                                </TableCell>
                                            );
                                        })}
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                </div>
            </React.Fragment>
        )
    }

    render() {
        const {classes} = this.props;

        return (
            <React.Fragment>
                <Container>

                    {this.renderHeaderItems(classes)}

                    {this.renderCarousel(classes)}

                    {this.renderDescription(classes)}

                    {this.renderHotelFacilities(classes)}

                    {this.renderCategories(classes)}

                </Container>
            </React.Fragment>
        )
    }
}

HotelOverview.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(HotelOverview);