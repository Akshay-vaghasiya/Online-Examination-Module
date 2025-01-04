import { useParams } from "react-router-dom";
import { useExamContext } from "../contexts/ExamContext";
import { useEffect, useState } from "react";
import { Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from "@mui/material";


const ExamResult = () => {

    const {exams, searchStudent, filteredResults, results, setResults, searchResults} = useExamContext();
    const { examId } = useParams();
    const [totalMarks, setTotalMarks] = useState(0);
    const [passingMarks, setPassingMarks] = useState(0);

    useEffect(() => {   
        setResults(parseInt(examId));            
    }, []);

    useEffect(() => {
        setTotalMarks(exams.find((exam) => exam.id === parseInt(examId))?.totalMarks);
        setPassingMarks(exams.find((exam) => exam.id === parseInt(examId))?.passingMarks);
    }, [exams]);
    

    console.log(totalMarks);
    const handleSearch = (event) => {
        searchResults(event.target.value);
    };
    
    const columns = ["Stduent Name", "marks", "Status","Total Marks", "Passing Marks"];

    return (
    
    <Box>
    <Typography variant="h4" align="center" gutterBottom>
      Result of Exam
    </Typography>

    <Box sx={{ display: "flex", justifyContent: "flex-start", marginBottom: "1rem", gap: "1.5rem" }}>
        <TextField
            label="Search Student"
            variant="outlined"
            value={searchStudent}
            onChange={handleSearch}
            fullWidth
            sx={{ maxWidth: "40%" }}
        />
    </Box>

    <TableContainer component={Paper}>
        <Table aria-label="custom table">
            <TableHead>
                <TableRow>
                    {columns.map((col) => (
                        <TableCell key={col}><strong>{col}</strong></TableCell>
                    ))}
                </TableRow>
            </TableHead>
            <TableBody>
                {filteredResults != undefined || filteredResults.length > 0 ? (
                    filteredResults.map((row, index) => (
                        <TableRow key={index}>  
                            <TableCell key={row['student'].username}>{row['student'].username}</TableCell>  
                            <TableCell key={row['marksObtained']}>{row['marksObtained']}</TableCell>  
                            <TableCell key={row['passed']} sx={{ color: row['passed'] ? "green" : "red" }}>{row['passed']? "Passed" : "Failed"}</TableCell>
                            <TableCell key={totalMarks}>{totalMarks}</TableCell>
                            <TableCell key={passingMarks}>{passingMarks}</TableCell>
                        </TableRow>
                    ))
                ) : (
                    <TableRow>
                        <TableCell colSpan={columns.length + 1} align="center">
                            No data found.
                        </TableCell>
                    </TableRow>
                )}
            </TableBody>
        </Table>
    </TableContainer>
    </Box>
    );};

export default ExamResult;