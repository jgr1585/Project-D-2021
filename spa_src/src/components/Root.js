import React, {PureComponent} from 'react'
import CssBaseline from '@mui/material/CssBaseline'
import {ThemeProvider, StyledEngineProvider} from '@mui/material/styles';

import App from './App'
import theme from '../inc/theme'

class Root extends PureComponent {
    render() {
        return (
            <StyledEngineProvider injectFirst>
                <ThemeProvider theme={theme}>
                    <CssBaseline/>
                    <App/>
                </ThemeProvider>
            </StyledEngineProvider>
        );
    }
}

export default Root;