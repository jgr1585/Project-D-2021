import { createTheme } from '@mui/material/styles';
import { amber, grey, green } from '@mui/material/colors'

export default createTheme({
    typography: {
        useNextVariants: true,
        monospace: {
            fontFamily: 'Arial sans-serif',
            fontSize: '14px',
            // color: 'black',
        },
    },
    components: {
        MuiButtonBase: {
            defaultProps: {
                disableRipple: true,
            },
        },
    },
    dim: {
        drawerWidth: '350px',
        popoverWidth: '400px',
    },
    color: {
        lightGrey: grey[300],
        grey: grey[600],
        warning: amber[900],
        success: green[600],
        white: '#ffffff',
        lightBlue: '#c9daff',
    },
    palette: {
        primary: {
            dark: '#3880f6',
            main: '#458afc',
            light: '#6f9eff',
        },
        secondary: {
            dark: '#00695f',
            main: '#009688',
            light: '#33ab9f',
        },
    },
    mixins: {
        input: {
            fontFamily: 'Arial, sans-serif',
            fontSize: '14px',
            // color: 'black',
            // padding: '2px 0 1px 0',
        },
    },
})
