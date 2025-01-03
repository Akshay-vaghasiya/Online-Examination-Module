import React, { useState } from "react";
import {
  Box,
  Button,
  Menu,
  MenuItem,
  Typography,
  MenuList,
  List,
  ListItemButton,
} from "@mui/material";

const LanguageSelector = ({ language, onSelect }) => {
  const [anchorEl, setAnchorEl] = useState(null);
  const languages = [
    "javascript",
    "python",
    "java",
    "c",
    "cpp",
  ];
  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  return (
    <Box sx={{ marginLeft: 2, marginBottom: 4 }}>
      <Typography sx={{ marginBottom: 2, fontSize: "1.25rem" }}>Language:</Typography>
      <Button
        variant="contained"
        onClick={handleMenuOpen}
        sx={{ textTransform: "none" }}
      >
        {language}
      </Button>

      <Menu
        
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleMenuClose}
        MenuListProps={{
          sx: {
            bgcolor: "#110c1b",
            color: "white",
          },
        }}
      >
        <MenuList>
          {languages.map((lang) => (
            <MenuItem
              key={lang}
              onClick={() => {
                onSelect(lang);
                handleMenuClose();
              }}
              sx={{
                color: lang === language ? "blue" : "inherit",
                bgcolor: lang === language ? "rgba(255, 255, 255, 0.1)" : "transparent",
                "&:hover": {
                  color: "blue",
                  bgcolor: "rgba(255, 255, 255, 0.1)",
                },
              }}
            >
              {lang}        
            </MenuItem>
          ))}
        </MenuList>
      </Menu>
    </Box>
  );
};

export default LanguageSelector;