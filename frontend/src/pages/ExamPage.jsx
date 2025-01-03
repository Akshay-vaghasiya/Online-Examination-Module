import React, { useState, useEffect } from "react";
import {
  Button,
  Box,
  Typography,
  Radio,
  RadioGroup,
  FormControlLabel,
} from "@mui/material";
import Pagination from "../components/Pagination";
import ConfirmationDialog from "../components/ConfirmationDialog";
import { useExamContext } from "../contexts/ExamContext";
import { useNavigate, useParams } from "react-router-dom";
import CodeEditor from "../components/CodeEditor";
import NoCopyContent from "../components/NoCopyContent";
import { fireToast } from "../components/fireToast";

const ExamPage = () => {
  const [questions, setQuestions] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [dialogOpen1, setDialogOpen1] = useState(true);
  const [section, setSection] = useState("MCQ");
  const { examId } = useParams();
  const navigate = useNavigate();
  const {
    startedExam1,
    startedExam,
    mcqQuestions,
    codingQuestions,
    getMcqQuestions1,
    getCodingQuestions1,
    autosave,
    submitExam1
  } = useExamContext();
  const [currentQuestion, setCurrentQuestion] = useState(1);
  const [timer, setTimer] = useState(0);
  const [stdin, setStdin] = useState("");
  const [answeredQuestions, setAnsweredQuestions] = useState({});
  const [tabSwitchCounter, setTabSwitchCounter] = useState(0);

  const handlePageChange = async () => {
    autoSave();
  };

  const handleQuestionChange = (questionNumber) => {
    setCurrentQuestion(questionNumber);
  };

  const autoSave = async () => {
    let answers = [];
    if (section === "MCQ") {
      answers = mcqQuestions.map((question) => {
         let obj = {};
         obj.language = question.language;
         obj.answer = question.answer;
         obj.questionType = section.toLowerCase();
         obj.questionId = question.mcqQuestion.id;

         return obj;
      });
    } else {
      answers = codingQuestions.map((question) => {
        let obj = {};
         obj.language = question.language;
         obj.answer = question.answer;
         obj.questionType = section.toLowerCase();
         obj.questionId = question.codingQuestion.id;

         return obj;
      });
    }

    const data = {
      studentExamId : startedExam.id,
      answers
    }

    await autosave(data, examId, navigate);
  }

  const confirmFullscreen = async () => {
    document
      .getElementById("exam-page")
      .requestFullscreen()
      .catch(console.error);
    await startedExam1(examId, navigate);
    getMcqQuestions1(examId, navigate, currentPage, 1);
    setQuestions(mcqQuestions);
    setDialogOpen1(false);
  };

  useEffect(() => {
    setCurrentPage(0);
    setCurrentQuestion(1);

    if (section === "MCQ") {
      getMcqQuestions1(examId, navigate, currentPage, 1);
    } else {
      getCodingQuestions1(examId, navigate, currentPage, 1);
    }
  }, [section]);

  useEffect(() => {
    let answers = {};
    if (section === "MCQ") {
      setQuestions(mcqQuestions);
      mcqQuestions.map((question) => {
        answers[question.idno] = question.answer ? true : false;
      });
      setAnsweredQuestions(answers);
    } else {
      setQuestions(codingQuestions);
      codingQuestions.map((question) => {
        answers[question.idno] = question.answer ? true : false;
      });
      setAnsweredQuestions(answers);
    }
  }, [mcqQuestions, codingQuestions]);

  useEffect(() => {
    let countdown, autosave;
    const startTime = new Date(startedExam?.startTime);
    const endTime = new Date(startedExam?.endTime);
    const seconds = ((endTime - startTime) / 1000);
    setTimer(parseInt((startedExam?.duration * 60) - seconds));
    if (!dialogOpen1) {
      countdown = setInterval(() => {
        setTimer((prevCountdown) => {
          if (prevCountdown <= 1) {
            clearInterval(countdown);
            autoSave();
            submitExam1(startedExam.id, navigate);
            fireToast("Time's up. Exam submitted", "warning");
            navigate("/student/exams");
            return 0;
          }
          return prevCountdown - 1;
        });
      }, 1000);

      autosave = setInterval(async () => {
         autoSave();
      }, 5000);
    }

    return () => {
      clearInterval(countdown);
      clearInterval(autosave);
    };
  }, [startedExam]);

  useEffect(() => {
    if (section === "MCQ") {
    } else {
      let createstring = questions[
        currentQuestion - 1
      ]?.codingQuestion?.testCases?.map((testCase) => {
        return testCase.inputData;
      });

      createstring = createstring?.join("\n");
      createstring =
        questions[currentQuestion - 1]?.codingQuestion?.testCases.length +
        "\n" +
        createstring;

      setStdin(createstring);
    }
  }, [currentQuestion, section, questions]);

  const handleDialogClose = async (submit) => {
    setDialogOpen(false);
    if (submit) {
      autoSave();
      submitExam1(startedExam.id, navigate);
      navigate("/student/exams");
    } else {
      document.documentElement.requestFullscreen().catch(console.error);
    }
  };

  const handleAnswerChange = (questionId, answer) => {
    if (section === "MCQ") {
      mcqQuestions.map((question) => {
        if (question.idno === questionId) {
          question.answer = answer;
        }
      });
      setAnsweredQuestions((prev) => ({
        ...prev,
        [questionId]: answer ? true : false,
      }));
      setQuestions(mcqQuestions);
    } else {
      codingQuestions.map((question) => {
        if (question.idno === questionId) {
          question.answer = answer;
        }
      });
      setAnsweredQuestions((prev) => ({
        ...prev,
        [questionId]: answer ? true : false,
      }));
      setQuestions(codingQuestions);
    }
  };

  const handleLanguageChange = (questionId, language) => {
    if (section === "Coding") {
      codingQuestions.map((question) => {
        if (question.idno === questionId) {
          question.language = language;
        }
      });
    }
  };

  const handleSubmit = async () => {
    if (document.fullscreenElement) {
      await document.exitFullscreen();
    }
    await autoSave();
    await submitExam1(startedExam.id, navigate);
    navigate("/student/exams");
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${String(mins).padStart(2, "0")}:${String(secs).padStart(2, "0")}`;
  };


  useEffect(() => {
    const handleTabSwich = async () => {
      if (tabSwitchCounter >= 3) {
        await autoSave();
        await submitExam1(startedExam.id, navigate);
        navigate("/student/exams");
      }
    };
    
    handleTabSwich();
  }, [tabSwitchCounter]);

  const handleVisibilityChange = async (e) => {
    if (document.visibilityState === "hidden") {
      e.preventDefault();
      setTabSwitchCounter((prev) => prev + 1);
    }
  };

  const handleBeforeUnload = (event) => {
    event.preventDefault();
    return (event.returnValue = "Are you sure you want to leave?");
  };

  const handleFullscreenChange = () => {
    if (!document.fullscreenElement) {
      setDialogOpen(true);
    }
  };

  useEffect(() => {
    document.addEventListener("visibilitychange", handleVisibilityChange);
    document.addEventListener("fullscreenchange", handleFullscreenChange);
    window.addEventListener("beforeunload", handleBeforeUnload);
    return () => {
      document.removeEventListener("visibilitychange", handleVisibilityChange);
      document.removeEventListener("fullscreenchange", handleFullscreenChange);
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, []);

  return (
    <div
      style={{
        padding: "2%",
        minHeight: "100vh",
        backgroundColor: "#f5f5f5",
        overflow: "scroll",
      }}
      id="exam-page"
    >
      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={2}
      >
        <Typography variant="h6">
          Time Remaining: {formatTime(timer)}
        </Typography>
      </Box>

      <Box display="flex" justifyContent="center" mb={2}>
        <Button
          variant={section === "MCQ" ? "contained" : "outlined"}
          onClick={() => setSection("MCQ")}
          sx={{ marginRight: "8px" }}
        >
          MCQ Section
        </Button>
        <Button
          variant={section === "Coding" ? "contained" : "outlined"}
          onClick={() => setSection("Coding")}
        >
          Coding Section
        </Button>
      </Box>
      <Pagination
        totalQuestions={questions.length || 0}
        questionsPerPage={8}
        currentQuestion={currentQuestion}
        onQuestionChange={handleQuestionChange}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        answeredQuestions={answeredQuestions}
        onPageChange={handlePageChange}
        section={section}
      />
      <Box sx={{ mt: 4 }}>
        {section === "MCQ" &&
          questions?.map((question) => (
            <Box key={question?.idno} mb={2}>
              <NoCopyContent>
              {question?.idno === currentQuestion && (
                <>
                  <Typography variant="h6">
                    {question?.idno}. {question?.mcqQuestion?.questionText}
                  </Typography>

                  {section === "MCQ" && question?.mcqQuestion?.options && (
                    <RadioGroup
                      sx={{width: "25%"}}
                      value={question?.answer || ""}
                      onChange={(e) =>
                        handleAnswerChange(question.idno, e.target.value)
                      }
                    >
                      {question?.mcqQuestion?.options.map((option, index) => (
                        <FormControlLabel
                          key={index}
                          value={option?.optionText}
                          control={<Radio />}
                          label={option?.optionText}
                        />
                      ))}
                    </RadioGroup>
                  )}

                  <Button
                variant="contained"
                color="primary"
                sx={{ marginTop: 2 }}
                onClick={() => handleAnswerChange(question.idno, "")}
              >
                Clear Response
              </Button>
                </>
              )}
              </NoCopyContent>
            </Box>
          ))}

        {section === "Coding" &&
          questions?.map((question) => (
            <Box key={question?.idno} mb={2}>
              {question?.idno === currentQuestion && (
                <>

                  <NoCopyContent>

                  <Typography variant="h6">
                    {question?.idno}. {question?.codingQuestion?.questionText}
                  </Typography>

                  <Box
                    sx={{
                      display: "flex",
                      justifyContent: "space-between",
                      width: "30%",
                      background: "#f2f6ff",
                      padding: "2%",
                      borderColor: "#e0e0e0",
                      borderWidth: "1px",
                      borderRadius: "5%",
                    }}
                  >
                    <Typography variant="h6">
                      input :
                      <br />
                      {question?.codingQuestion?.testCases?.length}
                      {question?.codingQuestion?.testCases?.map(
                        (testCase, index) => (
                          <div key={index} style={{ whiteSpace: "pre-line" }}>
                            {testCase?.inputData}
                          </div>
                        )
                      )}
                    </Typography>
                    <Typography variant="h6">
                      output :
                      <br />
                      {question?.codingQuestion?.testCases?.map(
                        (testCase, index) => (
                          <div key={index} style={{ whiteSpace: "pre-line" }}>
                            {testCase?.expectedOutput === ""
                              ? "\n"
                              : testCase?.expectedOutput}
                          </div>
                        )
                      )}
                    </Typography>
                  </Box>
                  </NoCopyContent>

                  <CodeEditor
                    stdin={stdin}
                    setStdin={setStdin}
                    answer={
                      question?.answer ||
                      `\nfunction greet(name) {\n\tconsole.log("Hello, " + name + "!");\n}\n\ngreet("Alex");\n`
                    }
                    setAnswer={handleAnswerChange}
                    language={question?.language || "javascript"}
                    setLanguage={handleLanguageChange}
                    questionId={question?.idno}
                    id={question?.codingQuestion?.id}
                  />
                </>
              )}
            </Box>
          ))}
      </Box>

      <Box display="flex" justifyContent="flex-end" mt={2}>
        <Button variant="contained" color="primary" onClick={handleSubmit}>
          Submit Exam
        </Button>
      </Box>

      <ConfirmationDialog
        open={dialogOpen}
        onCancel={() => handleDialogClose(false)}
        onConfirm={() => handleDialogClose(true)}
        confirmText = "Submit"
        cancelText = "Cancel"
        title="Warning"
        message={`You have exited full-screen mode. Do you want to submit exam?  \nyou can change the tab at max total 3 times, after that exam will be submitted` }
      />

      <ConfirmationDialog
        open={dialogOpen1}
        onCancel={() => {
          setDialogOpen1(false);
          navigate("/student/exams");
        }}
        confirmText="Start"
        cancelText="Cancel"
        onConfirm={confirmFullscreen}
        title="Term and Condition"
        message={`Are you sure you want to start the exam? \nyou can change the tab at max total 3 times, after that exam will be submitted.`}
      />
    </div>
  );
};

export default ExamPage;
