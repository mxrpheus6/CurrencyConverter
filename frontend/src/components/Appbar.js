import * as React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { Link } from 'react-router-dom'; // Импортируем компонент Link

// Define a custom theme with modern typography styles
const theme = createTheme({
  palette: {
    primary: {
      main: '#008080', // Your primary color
    },
  },
  typography: {
    fontFamily: 'Arial, sans-serif', // Set your desired font family
    h6: {
      fontSize: '1.5rem', // Set the font size for the AppBar title
      fontWeight: 600, // Set the font weight for the AppBar title
    },
  },
});

export default function ButtonAppBar() {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" color="primary">
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
              onClick={handleClick}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              Currency Converter
            </Typography>
          </Toolbar>
        </AppBar>
      </Box>
      <Menu
        id="menu-appbar"
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
        open={Boolean(anchorEl)}
        onClose={handleClose}
      >
        <MenuItem onClick={handleClose} component={Link} to="/">Home</MenuItem> {/* Ссылка на главную страницу */}
        <MenuItem onClick={handleClose} component={Link} to="/users">Users</MenuItem> {/* Ссылка на страницу пользователей */}
        <MenuItem onClick={handleClose} component={Link} to="/history">History</MenuItem> {/* Ссылка на страницу пользователей */}
      </Menu>
    </ThemeProvider>
  );
}
