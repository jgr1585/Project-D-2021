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

import test1 from '../img/test.jpg'
import test2 from '../img/test2.jpg'

const styles = () => ({
    customTextStyle: {
        fontFamily: "Arial",
        fontSize: "14px",
    },
    customIconMarginBottom: {
        marginBottom: "16px",
    },
    gridItemPaddingTop: {
        paddingTop: '36px !important',
    },
    sectionPadding: {
        padding: '16px 0px',
    },
    stackPaddingTop: {
        paddingTop: '12px',
    },
    facilityIconColor: {
        color: "#008009 !important",
    },
});

class HotelOverview extends PureComponent {

    renderHeaderItems = (classes) => {
        return (
            <React.Fragment>
                <div>
                    <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}}>
                        <Grid container item spacing={3} className={clsx(classes.gridItemPaddingTop)}>

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
            </React.Fragment>
        )
    }

    renderCarousel = (classes) => {
        const items = [
            {
                logos: [test1, test2],
                name: "Random Name #1",
                description: "Probably the most random thing you have ever seen!"
            },
            {
                logos: [test1, test2],
                name: "Random Name #2",
                description: "Hello World!"
            }
        ];

        return (
            <Carousel>
                {items.map((item, i) =>
                    (
                        <Paper>
                            <Grid container rowSpacing={1} columnSpacing={{xs: 1, sm: 2, md: 3}}>
                                <Grid container item spacing={3}>
                                    {item.logos.map((img) =>
                                        (
                                            <Grid item xs={6}>
                                                <Item>
                                                    <img src={img} alt="logo" height={"100%"} width={"100%"}/>
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
        )
    }

    renderDescription = (classes) => {
        return (
            <React.Fragment>
                <div className={clsx(classes.customTextStyle)}>
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

                    <Stack direction="row" spacing={1} className={clsx(classes.stackPaddingTop)}>
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

                    <Stack direction="row" spacing={1} className={clsx(classes.stackPaddingTop)}>
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
        const columns = [
            {
                id: 'capacity',
                label: 'Person Capacity',
                minWidth: 100,
                align: 'left',
                format: (value) => value.toLocaleString('en-US'),
            },
            {
                id: 'roomCategory',
                label: 'Room Category',
                minWidth: 500,
                align: 'left',
                format: (value) => value.toLocaleString('en-US'),
            },
            {
                id: 'price',
                label: 'Price per night',
                minWidth: 100,
                align: 'right',
                format: (value) => value.toFixed(2),
            },
        ];

        const rows = [
            {capacity: "1", roomCategory: "Single Room", price: "N/A"},
            {capacity: "2", roomCategory: "Double Room", price: "N/A"},
            {capacity: "4", roomCategory: "Family Room", price: "N/A"},
            {capacity: "3", roomCategory: "Suite Room", price: "N/A"},
        ];

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