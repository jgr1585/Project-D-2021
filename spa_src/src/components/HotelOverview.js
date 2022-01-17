import React, {PureComponent} from 'react'

import withStyles from '@mui/styles/withStyles';
import PropTypes from 'prop-types';
import clsx from 'clsx';

import {Button, Container, Paper} from '@mui/material';
import Carousel from 'react-material-ui-carousel'

const styles = () => ({});

function Item(props) {
    return (
        <Paper>
            <h2>{props.item.name}</h2>
            <p>{props.item.description}</p>

            <Button className="CheckButton">
                Check it out!
            </Button>
        </Paper>
    )
}

class HotelOverview extends PureComponent {
    constructor(props) {
        super(props);

        this.items = [
            {
                path: "",
                name: "Random Name #1",
                description: "Probably the most random thing you have ever seen!"
            },
            {
                name: "Random Name #2",
                description: "Hello World!"
            }
        ]
    }

    renderCarouselItem = (props) => {
        return (
            <Paper>
                <h2>{props.item.name}</h2>
                <p>{props.item.description}</p>

                <Button className="CheckButton">
                    Check it out!
                </Button>
            </Paper>
        )
    }

    render() {
        const {classes} = this.props;

        return (
            <React.Fragment>
                <Container>
                    <Carousel>
                        {this.items.map((item, i) =>
                            (
                                this.renderCarouselItem({
                                    item: item,
                                    i: i
                                })
                            )
                        )}
                    </Carousel>

                    <Button variant="contained"
                            onClick={() => this.props.createNewBooking()}
                    >
                        Create booking
                    </Button>
                </Container>
            </React.Fragment>
        )
    }
}

HotelOverview.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(HotelOverview);