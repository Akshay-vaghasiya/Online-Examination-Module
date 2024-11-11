import React from "react";
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, Button
} from "@mui/material";

// TableComponent is a reusable component that renders a table with customizable columns,
// data, and action buttons for each row.
const TableComponent = ({ columns, data, actions }) => (
    <TableContainer component={Paper}> {/* Paper provides elevation to the table */}
        <Table aria-label="custom table">
            <TableHead> {/* Header row for table columns */}
                <TableRow>
                    {columns.map((col) => (
                        <TableCell key={col}><strong>{col}</strong></TableCell> 
                    ))}
                    {actions && <TableCell><strong>Actions</strong></TableCell>} {/* Action header if actions are provided */}
                </TableRow>
            </TableHead>
            <TableBody>
                {/* Check if there is data to display */}
                {data.length > 0 ? (
                    data.map((row, index) => (  // Map over each row of data
                        <TableRow key={index}>
                            {/* Render each cell based on column names */}
                            {columns.map((col) => (
                                <TableCell key={col}>{row[col.toLowerCase()]}</TableCell>
                            ))}
                            {actions && ( // Render action buttons if actions are provided
                                <TableCell sx={{ display: 'flex', gap: '10px' }}>
                                    {actions.map((action, i) => (
                                        <Button
                                            key={i}
                                            variant="contained"
                                            color={action.color} // Button color
                                            onClick={() => action.handler(row)} // Action handler
                                            style={{ marginLeft: i > 0 ? "0.5rem" : 0 }}
                                            startIcon={action?.icon} // Optional icon
                                        >
                                            {action.label} {/* Button label */}
                                        </Button>
                                    ))}
                                </TableCell>
                            )}
                        </TableRow>
                    ))
                ) : (
                    // Display message if no data is available
                    <TableRow>
                        <TableCell colSpan={columns.length + 1} align="center">
                            No data found.
                        </TableCell>
                    </TableRow>
                )}
            </TableBody>
        </Table>
    </TableContainer>
);

export default TableComponent;
