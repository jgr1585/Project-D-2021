import {styled} from "@mui/material/styles";
import Paper from "@mui/material/Paper";

export default styled(Paper)(({theme}) => ({
    ...theme.typography.body2,
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    boxShadow: 'none',
}));