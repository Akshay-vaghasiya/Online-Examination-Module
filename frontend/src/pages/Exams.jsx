import { List, ListItem } from "@mui/material";
import ExamCard from "../components/ExamCard";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useExamContext } from "../contexts/ExamContext";

const Exams = () => {
  
  const {fetchExams, exams} = useExamContext();
  const navigate = useNavigate();

  useEffect(() => {
    fetchExams();
  }, []);

  const handleStart = (exam) => {
    navigate('/exam/'+exam.id);
  };

  return (
    <List sx={{ width: "100%", margin: "0 auto" }}>
      {exams?.map((exam, index) => (
        <div key={index}>
          <ListItem>
            <ExamCard 
              examName={exam.examName}
              duration={parseInt(exam.duration)}
              status={exam.status}
              onStart={() => handleStart(exam)}
            />
          </ListItem>
        </div>
      ))}
    </List>
  );
};

export default Exams;
