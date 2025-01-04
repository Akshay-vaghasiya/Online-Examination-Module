import React, { useEffect, useState } from "react";
import { useExamContext } from "../contexts/ExamContext"; 
import TableComponent from "../components/TableComponent";
import CustomDialogForm from "../components/CustomDialogForm";
import ConfirmationDialog from "../components/ConfirmationDialog";
import { Button, Box, Typography, CircularProgress, TextField, IconButton, Switch } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { Delete } from "@mui/icons-material";
import { useUniversityContext } from "../contexts/UniversityContext";
import { useNavigate } from "react-router-dom";

const ExamManagement = () => {

  // Context methods and state for exams
  const {
    filteredExams,
    fetchExams,
    addExam,
    editExam,
    removeExam,
    isLoading,
    isError,
    searchExams,
    searchTerm,
  } = useExamContext();

  // Context methods and state for universities
  const { universities, fetchUniversities } = useUniversityContext();

  const [isFormOpen, setIsFormOpen] = useState(false);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [formData, setFormData] = useState({});
  const [selectedExam, setSelectedExam] = useState(null);
  const [universityOption, setUniversityOption] = useState([]);
  const navigate = useNavigate();

  // Get today's date
  const today = new Date().toISOString().split("T")[0]; 

  // Fetch list of universities if universities list is empty and fetch list of exams on component mount
  useEffect(() => {

    if(!universities.length){{
      fetchUniversities(navigate);
    }}
    fetchExams(navigate);
  }, []);

  // Extract university names from universities list
  useEffect(() => {
    for(let i=0;i<universities.length;i++){
      setUniversityOption((prev) => [...prev, {label: universities[i].universityName, value: universities[i].universityName}])
    }
  }, [universities]);

  // Open dialog for adding a new exam
  const handleAdd = () => {
    setFormData({
      mcqQuestions: [],
      codingQuestions: [],
      status: "SCHEDULED",
      enable: false,
      examName: "",
      duration: "",
      universities: [],
      branch : "",
      semester: 0,
      passingMarks: 0,
      totalMarks: 0,
      difficultyLevel: "Easy",
      scheduleDate: today
    });
    setSelectedExam(null);
    setIsFormOpen(true);
  };

  // Open dialog for editing an exam
  const handleEdit = (exam) => {
    setFormData(exam);
    setSelectedExam(exam);
    setFormData((prev) => ({ ...prev, universities: exam?.universities?.map((exa) => {
      return exa.universityName;
    }) }));
    setIsFormOpen(true);
  };

  // Open confirmation dialog for deleting an exam
  const handleDelete = (exam) => {
    setSelectedExam(exam);
    setIsConfirmOpen(true);
  };

  // Add new question for an exam
  const handleAddQuestion = (type) => {
    setFormData((prev) => ({
      ...prev,
      [type]: [...prev[type], { category: "", noOfQuestion: 0 }],
    }));
  };

  // Update existing question for an exam
  const handleUpdateQuestion = (type, index, field, value) => {

    if(field === "noOfQuestion"){
      value = parseInt(value);
    }
    setFormData((prev) => {
      const updatedQuestions = [...prev[type]];
      updatedQuestions[index][field] = value;
      return { ...prev, [type]: updatedQuestions };
    });
  };

  // Remove a question from an exam
  const handleRemoveQuestion = (type, index) => {
    setFormData((prev) => {
      const updatedQuestions = [...prev[type]];
      updatedQuestions.splice(index, 1);
      return { ...prev, [type]: updatedQuestions };
    });
  };

  // Submit form for adding or updating an exam
  const handleSubmit = () => {
    if (selectedExam) {
      editExam(selectedExam.id, formData, navigate);
    } else {
      addExam(formData, navigate);
    }
    setIsFormOpen(false);
  };

  // Confirm deletion of an exam
  const handleConfirmDelete = () => {
    removeExam(selectedExam.id, navigate);
    setIsConfirmOpen(false);
  };

  // Handle search functionality
  const handleSearch = (event) => {
    searchExams(event.target.value);
  };

  const handleResults = (exam) => {
    navigate(`/admin/results/${exam.id}`);
  };

  // Table configuration
  const columns = ["Exam Name", "Status", "Difficulty", "Duration (In Minutes)", "Total Marks", "Passing Marks", "Schedule Date(YYYY-MM-DD)", "Branch", "Semester"];
  const datacolumns = ["examName", "status", "difficultyLevel", "duration", "totalMarks", "passingMarks", "scheduleDate", "branch", "semester"];

  // Form fields configuration
  const formFields = [
    { label: "Exam Name", name: "examName", type: "text", required: true },
    { label: "Duration", name: "duration", type: "text", required: true },
    { label: "Difficulty Level", name: "difficultyLevel", type: "select", options: [
        { value: "Easy", label: "Easy" },
        { value: "Medium", label: "Medium" },
        { value: "Hard", label: "Hard" },
      ], required: true,  disabled: selectedExam ? true : false },
      { label: "Status", name: "status", type: "select", options: [
          { value: "SCHEDULED", label: "SCHEDULED" },
          { value: "STARTED", label: "STARTED" },
          { value: "PAUSED", label: "PAUSED" },
          { value: "RESUMED", label: "RESUMED" },
          { value: "COMPLETED", label: "COMPLETED" },
        ], required: true },
      { label: "Universities",
        name: "universities",
        type: "select",
        options: universityOption,
        isMultiple: true,
        required: true
      },
    { label: "Branch", name: "branch", type: "text", required: true },
    { label: "Semester", name: "semester", type: "number", required: true },    
    { label: "Total Marks", name: "totalMarks", type: "number", required: true },
    { label: "Passing Marks", name: "passingMarks", type: "number", required: true },
  ];

  // Actions configuration
  const actions = [
    {
      label: "Edit",
      color: "primary",
      handler: handleEdit,
      icon: <EditIcon />,
    },
    {
      label: "Delete",
      color: "error",
      handler: handleDelete,
      icon: <DeleteIcon />,
    },
    {
      label: "Results",
      color: "secondary",
      handler: handleResults,  
    }
  ];  

  // If loading or error, display appropriate message
  if (isLoading) {
    return <CircularProgress />;
  }

  if (isError) {
    return (
      <Typography color="error">
        Error loading exams. Please try again later.
      </Typography>
    );
  }

  return (
    <Box>
      <Typography variant="h4" align="center" gutterBottom>
        Exam Management
      </Typography>

      <Box sx={{ display: "flex", justifyContent: "flex-start", marginBottom: "1rem", gap: "1.5rem", marginTop: "1.5rem" }}>
      {/* Search and Add Exam buttons */}
      <TextField
          label="Search Exams"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearch}
          fullWidth
          sx={{ maxWidth: "40%" }}
        />
        <Button
          variant="contained"
          color="primary"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >
          Add Exam
        </Button>
      </Box>

      {/* Table component */}
      <TableComponent
        columns={columns}
        data={filteredExams}
        datacolumns={datacolumns}
        actions={actions}
      />

      {/* DialogForm component for adding or updating an exam */}
      <CustomDialogForm
        open={isFormOpen}
        onClose={setIsFormOpen}
        formData={formData}
        setFormData={setFormData}
        onSubmit={handleSubmit}
        title={selectedExam ? "Edit Exam" : "Add Exam"}
        submitButtonText={selectedExam ? "Update" : "Create"}
        fields={formFields}
       >

       {/* Schedule Date field */}
       <TextField 
        label="Schedule Date"
        type="date"
        name="scheduleDate"
        value={formData.scheduleDate}
        onChange={(e) => setFormData((prev) => ({ ...prev, scheduleDate: e.target.value }))}
        InputLabelProps={{
          shrink: true,
        }}
        inputProps={{
          min: today,
        }}
        sx={{ mt: 2 }}
        fullWidth
      />
       
      {/* Enable Exam field */}
      <Box sx={{ display: "flex", alignItems: "center", marginBottom: "1rem", marginTop: "0.5rem" }}>
        <Typography variant="body1" sx={{ flexGrow: 1 }}>
          Enable Exam
        </Typography>
        <Switch
          checked={formData.enable}
          onChange={(e) => setFormData((prev) => ({ ...prev, enable: e.target.checked }))}
          color="primary"
        />
      </Box>

      {/* MCQ Questions section */}
        <Typography variant="h6" gutterBottom>
          MCQ Questions
        </Typography>
        {selectedExam===null && formData.mcqQuestions?.map((question, index) => (
          <div
            key={index}
            style={{
              display: "flex",
              alignItems: "center",
              marginBottom: "1rem",
              gap: "1rem",
            }}
          >
            <TextField
              label="Category"
              value={question.category}
              onChange={(e) =>
                handleUpdateQuestion("mcqQuestions", index, "category", e.target.value)
              }
              style={{ flex: 1 }}
            />
            <TextField
              label="Number of Questions"
              type="number"
              value={question.noOfQuestion}
              onChange={(e) =>
                handleUpdateQuestion("mcqQuestions", index, "noOfQuestion", e.target.value)
              }
              style={{ flex: 1 }}
            />
            <IconButton onClick={() => handleRemoveQuestion("mcqQuestions", index)} color="error">
              <Delete />
            </IconButton>
          </div>
        ))}
        { selectedExam===null && <Button 
          key={Math.random()}
          variant="outlined"
          color="primary"
          onClick={() => handleAddQuestion("mcqQuestions")}
        >
          Add MCQ Question
        </Button>}

      {/* Coding Questions section */}
        <Typography variant="h6" gutterBottom style={{ marginTop: "1rem" }}>
          Coding Questions
        </Typography>
        { selectedExam === null && formData.codingQuestions?.map((question, index) => (
          <div
            key={index}
            style={{
              display: "flex",
              alignItems: "center",
              marginBottom: "0.5rem",
              gap: "1rem",
            }}
          >
            <TextField
              label="Category"
              value={question.category}
              onChange={(e) =>
                handleUpdateQuestion("codingQuestions", index, "category", e.target.value)
              }
              style={{ flex: 1 }}
            />
            <TextField
              label="Number of Questions"
              type="number"
              value={question.noOfQuestion}
              onChange={(e) =>
                handleUpdateQuestion("codingQuestions", index, "noOfQuestion", e.target.value)
              }
              style={{ flex: 1 }}
            />
            <IconButton onClick={() => handleRemoveQuestion("codingQuestions", index)} color="error">
              <Delete />
            </IconButton>
          </div>
        ))}
        {selectedExam===null && <Button
          key={Math.random()}
          variant="outlined"
          color="primary"
          onClick={() => handleAddQuestion("codingQuestions")}
        >
          Add Coding Question
        </Button>}
       
       </CustomDialogForm>

      {/* Confirmation dialog for deleting an exam */}
      <ConfirmationDialog
        open={isConfirmOpen}
        title="Confirm Delete"
        message={`Are you sure you want to delete the exam: ${selectedExam?.examName}?`}
        onConfirm={handleConfirmDelete}
        onCancel={setIsConfirmOpen}
      />
    </Box>
  );
};
export default ExamManagement;