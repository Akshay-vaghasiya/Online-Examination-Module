import React, { useEffect, useState } from "react";
import {
  Button,
  Typography,
  TextField,
  IconButton,
  Radio,
  CircularProgress,
} from "@mui/material";
import { Add, Delete, Edit } from "@mui/icons-material";
import TableComponent from "../components/TableComponent";
import CustomDialogForm from "../components/CustomDialogForm";
import ConfirmationDialog from "../components/ConfirmationDialog";
import { useQuestionContext } from "../contexts/QuestionContext";
import { useNavigate } from "react-router-dom";

const QuestionManagement = () => {
  // State variables for dialogs and forms
  const [dialogOpen, setDialogOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [formData, setFormData] = useState({
    type: "MCQ",
    options: [],
    testCases: [],
  });
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedQuestion, setSelectedQuestion] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const navigate = useNavigate();

  // Extracting functions and states from QuestionContext
  const {
    isLoading,
    isError,
    filteredquestions,
    registerMcqQuestion,
    registerCodingQuestion,
    updateMcqQuestion,
    updateCodingQuestion,
    fetchMcqQuestions,
    fetchCodingQuestions,
    deleteMcqQuestion,
    deleteCodingQuestion,
    searchQuestions,
  } = useQuestionContext();

  // Fetch questions on component mount
  useEffect(() => {
    fetchCodingQuestions(navigate);
    fetchMcqQuestions(navigate);
  }, []);

  console.log(filteredquestions);
  

  // Open dialog for adding or editing a question
  const handleOpenDialog = (question = null) => {
    setIsEditing(!!question); // Set editing mode based on question existence
    setFormData(
      question || {
        type: "MCQ",
        options: [],
        testCases: [],
      }
    );
    setSelectedQuestion(question); // Set selected question for editing
    setDialogOpen(true); // Open the dialog
  };

  // Close dialog and reset form
  const handleCloseDialog = () => {
    setDialogOpen(false);
    setFormData({
      type: "MCQ",
      options: [],
      testCases: [],
    });
  };

  // Handle form submission for adding or updating questions
  const handleSubmit = () => {
    if (isEditing) {
      // Update existing question
      if (formData.type === "MCQ") {
        updateMcqQuestion(formData, navigate);
      } else {
        updateCodingQuestion(formData, navigate);
      }
    } else {
      // Register new question
      if (formData.type === "MCQ") {
        registerMcqQuestion(formData, navigate);
      } else {
        registerCodingQuestion(formData, navigate);
      }
    }
    handleCloseDialog();
  };

  // Open delete confirmation dialog
  const handleDelete = (question) => {
    setSelectedQuestion(question);
    setDeleteDialogOpen(true);
  };

  // Confirm and delete the selected question
  const confirmDelete = () => {
    if (selectedQuestion.type === "MCQ") {
      deleteMcqQuestion(selectedQuestion.id, navigate);
    } else {
      deleteCodingQuestion(selectedQuestion.id, navigate);
    }
    setDeleteDialogOpen(false);
    setSelectedQuestion(null);
  };

  // Handle search query change
  const handleSearchChange = (event) => {
    const query = event.target.value;
    setSearchQuery(query);
    searchQuestions(query); // Filter questions based on search
  };

  // Handle MCQ option changes
  const handleOptionChange = (index, field) => (event) => {
    const updatedOptions = [...formData.options];
    updatedOptions[index][field] = event.target.value;
    setFormData({ ...formData, options: updatedOptions });
  };

  // Add new option for MCQ question
  const addOption = () => {
    setFormData({
      ...formData,
      options: [
        ...(formData.options || []),
        { optionText: "", isCorrect: false },
      ],
    });
  };

  // Remove an option from MCQ question
  const removeOption = (index) => {
    const updatedOptions = [...formData.options];
    updatedOptions.splice(index, 1);
    setFormData({ ...formData, options: updatedOptions });
  };

  // Select the correct option for MCQ
  const selectCorrectOption = (index) => {
    const updatedOptions = formData.options.map((option, i) => ({
      ...option,
      isCorrect: i === index,
    }));
    setFormData({ ...formData, options: updatedOptions });
  };

  // Handle coding test case changes
  const handleTestCaseChange = (index, field) => (event) => {
    const updatedTestCases = [...formData.testCases];
    updatedTestCases[index][field] = event.target.value;
    setFormData({ ...formData, testCases: updatedTestCases });
  };

  // Add a new test case for coding question
  const addTestCase = () => {
    setFormData({
      ...formData,
      testCases: [
        ...(formData.testCases || []),
        { inputData: "", expectedOutput: "" },
      ],
    });
  };

  // Remove a test case from coding question
  const removeTestCase = (index) => {
    const updatedTestCases = [...formData.testCases];
    updatedTestCases.splice(index, 1);
    setFormData({ ...formData, testCases: updatedTestCases });
  };

  // Define table columns and actions
  const columns = ["Type", "Question Text", "Difficulty Level", "Category"];
  const dataColumns = ["type", "questionText", "difficultyLevel", "category"];
  const actions = [
    {
      label: "Edit",
      color: "primary",
      icon: <Edit />,
      handler: (row) => handleOpenDialog(row),
    },
    {
      label: "Delete",
      color: "error",
      icon: <Delete />,
      handler: (row) => handleDelete(row),
    },
  ];

  // Define form fields for dialog
  const formFields = [
    {
      label: "Type of Question",
      name: "type",
      type: "select",
      required: true,
      options: [
        { value: "MCQ", label: "MCQ" },
        { value: "Coding", label: "Coding" },
      ],
      disabled: isEditing, // Disable type selection during edit
    },
    {
      label: "Question Text",
      name: "questionText",
      type: "textarea",
      required: true,
      rows: 10,
    },
    {
      label: "Difficulty Level",
      name: "difficultyLevel",
      type: "select",
      required: true,
      options: [
        { value: "Easy", label: "Easy" },
        { value: "Medium", label: "Medium" },
        { value: "Hard", label: "Hard" },
      ],
    },
    {
      label: "Category",
      name: "category",
      type: "text",
      required: true,
    },
  ];

  // Add coding-specific fields if the question type is Coding
  if (formData.type === "Coding") {
    formFields.push(
      {
        label: "Question Title",
        name: "title",
        type: "text",
        required: true,
      },
      {
        label: "Function Signature",
        name: "functionSignature",
        type: "text",
        required: true,
      },
      {
        label: "Correct Code",
        name: "correctCode",
        type: "textarea",
        required: true,
        rows: 15,
      }
    );
  }

  // Show loading spinner if data is being fetched
  if (isLoading) {
    return <CircularProgress />;
  }

  // Show error message if there's an error
  if (isError) {
    return (
      <Typography color="error">
        Error loading questions. Please try again later.
      </Typography>
    );
  }

  // Main component layout
  return (
    <div>
      <Typography variant="h4" align="center" gutterBottom>
        Question Management
      </Typography>

      {/* Search bar and add button */}
      <div style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
        <TextField
          label="Search"
          variant="outlined"
          value={searchQuery}
          onChange={handleSearchChange}
          sx={{ width: "35rem" }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={() => handleOpenDialog()}
        >
          Add New Question
        </Button>
      </div>

      {/* Table component to display questions */}
      <TableComponent
        columns={columns}
        data={filteredquestions}
        datacolumns={dataColumns}
        actions={actions}
      />

      {/* Dialog for adding or editing a question */}
      <CustomDialogForm
        open={dialogOpen}
        onClose={handleCloseDialog}
        formData={formData}
        setFormData={setFormData}
        onSubmit={handleSubmit}
        title={isEditing ? "Edit Question" : "Add Question"}
        submitButtonText={isEditing ? "Update" : "Create"}
        fields={formFields}
      >
        {/* Form for MCQ-specific options */}
        {formData.type === "MCQ" && (
          <>
            <h3>Options</h3>
            {formData.options?.map((option, index) => (
              <div
                key={index}
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: "1rem",
                  marginBottom: "1rem",
                }}
              >
                <Radio
                  checked={option.isCorrect}
                  onChange={() => selectCorrectOption(index)}
                />
                <TextField
                  label={`Option ${index + 1}`}
                  fullWidth
                  value={option.optionText}
                  onChange={handleOptionChange(index, "optionText")}
                  placeholder="Enter option text"
                />
                <IconButton onClick={() => removeOption(index)} color="error">
                  <Delete />
                </IconButton>
              </div>
            ))}
            <Button
              variant="outlined"
              startIcon={<Add />}
              onClick={addOption}
              color="primary"
            >
              Add Option
            </Button>
          </>
        )}

        {/* Form for coding-specific test cases */}
        {formData.type === "Coding" && (
          <>
            <h3>Test Cases</h3>
            {formData.testCases?.map((testCase, index) => (
              <div
                key={index}
                style={{
                  display: "flex",
                  alignItems: "center",
                  gap: "1rem",
                  marginBottom: "1rem",
                }}
              >
                <TextField
                  label={`Input ${index + 1}`}
                  fullWidth
                  value={testCase.inputData}
                  onChange={handleTestCaseChange(index, "inputData")}
                  placeholder="Enter input"
                />
                <TextField
                  label={`Output ${index + 1}`}
                  fullWidth
                  value={testCase.expectedOutput}
                  onChange={handleTestCaseChange(index, "expectedOutput")}
                  placeholder="Enter expected output"
                />
                <IconButton onClick={() => removeTestCase(index)} color="error">
                  <Delete />
                </IconButton>
              </div>
            ))}
            <Button
              variant="outlined"
              startIcon={<Add />}
              onClick={addTestCase}
              color="primary"
            >
              Add Test Case
            </Button>
          </>
        )}
      </CustomDialogForm>

      {/* Delete confirmation dialog */}
      <ConfirmationDialog
        open={deleteDialogOpen}
        onCancel={() => setDeleteDialogOpen(false)}
        onConfirm={confirmDelete}
        title={"Delete Question"}
        message={`Are you sure you want to delete "${selectedQuestion?.questionText}" ${selectedQuestion?.type} question?`}
        confirmButtonText="Delete"
      />
    </div>
  );
};

export default QuestionManagement;
