import React from "react";
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, Button
} from "@mui/material";

// TableComponent: A reusable component to display data in a Material UI Table
const TableComponent = ({ columns, data, actions, datacolumns }) => (
    <TableContainer component={Paper}>
        {/* The main table structure */}
        <Table aria-label="custom table">
            <TableHead>
                <TableRow>
                    {/* Rendering column headers dynamically */}
                    {columns.map((col) => (
                        <TableCell key={col}><strong>{col}</strong></TableCell>
                    ))}
                    {/* If actions are provided, render an additional column for actions */}
                    {actions && <TableCell><strong>Actions</strong></TableCell>}
                </TableRow>
            </TableHead>
            <TableBody>
                {/* Check if data is available */}
                {data.length > 0 ? (
                    // Loop through each data row
                    data.map((row, index) => (
                        <TableRow key={index}>
                            {/* Dynamically render data for each row */}
                            {datacolumns.map((col) => (
                                <TableCell key={col}>{row[col]}</TableCell>
                            ))}
                            {/* If actions are provided, render action buttons */}
                            {actions && (
                                <TableCell sx={{ display: 'flex', gap: '10px'}}>
                                    {actions.map((action, i) => (
                                        <Button
                                            key={i}
                                            variant="contained"
                                            color={action.color}
                                            onClick={() => action.handler(row)} // Pass the current row to the action handler
                                            style={{ marginLeft: i > 0 ? "0.5rem" : 0 }} // Add margin for multiple actions
                                            startIcon={action?.icon} // Optional icon for the action button
                                        >
                                            {action.label} {/* Label for the action button */}
                                        </Button>
                                    ))}
                                </TableCell>
                            )}
                        </TableRow>
                    ))
                ) : (
                    // If no data is available, display a "No data found" message
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