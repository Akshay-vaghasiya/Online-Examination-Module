import { useState } from "react";
import { Box, Button, CircularProgress, Stack, TextField, Typography } from "@mui/material";
import { Editor } from "@monaco-editor/react";
import LanguageSelector from "./LanguageSelector";
import examService from "../services/examService";
import AuthHeader from "../helper/AuthHeader";
import { fireToast } from "./fireToast";
import { useParams } from "react-router-dom";

const CodeEditor = ({stdin, setStdin, answer, setAnswer, language, setLanguage, questionId, id}) => {
  const [isLoading, setIsLoading] = useState(false);
  const [submitLoading, setSubmitLoading] = useState(false);
  const [output, setOutput] = useState("");
  const [isError, setError] = useState(false);
  const headers = AuthHeader();
  const {examId} = useParams();

  const CODE_SNIPPETS = {
    javascript: `\nfunction greet(name) {\n\tconsole.log("Hello, " + name + "!");\n}\n\ngreet("Alex");\n`,
    python: `\ndef greet(name):\n\tprint("Hello, " + name + "!")\n\ngreet("Alex")\n`,
    java: `\npublic class Main {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println("Hello World");\n\t}\n}\n`,
    'c': `\n#include <stdio.h>\n\nint main() {\n\tprintf("Hello, World in C!\\n");\n\treturn 0;\n}\n`,
    'cpp': `\n#include <iostream>\n\nint main() {\n\tstd::cout << "Hello, World in C++!" << std::endl;\n\treturn 0;\n}\n`
  };
  

  const onSelect = (language) => {
    setLanguage(questionId, language);
    setAnswer(questionId, CODE_SNIPPETS[language]);
  };

  const handleRunCode = async () => {

    try {
        setIsLoading(true);
        setError(false);
        const data = { stdin, 
          sourceCode : answer,
          language
         };

        const output = await examService.runCode(data, headers); 

        if(output.stdout === null){ 

          if(output.stderr === null){
            setError(true)
            setOutput(output.compile_output);
            return;
          }
          setError(true);
          setOutput(output.compile_output);
          return;
        } 
        setOutput(output.stdout);
        
    } catch (error) {
      fireToast(error.message, "error");
    } finally {
      setIsLoading(false);
    }
  };

  const handleCodeSumbit = async () => {
    
    try {
      setSubmitLoading(true);
      setError(false);
      const data = { answer, language, questionId : id, questionType: "coding" };
      const output = await examService.submitCode(data, examId, headers);
      if(output === "Your code is correct and submitted successfully") {
        fireToast(output, "success");
      } else {
        fireToast(output, "error");
      }

    } catch (error) {
      fireToast(error.message, "error");
    } finally {
      setSubmitLoading(false);
    }
  }

  return (
    <Box sx={{ padding: 2 }}>
      <Stack direction="row" spacing={2}>
        <Box sx={{ flex: 1 }}>
          <LanguageSelector language={language} onSelect={onSelect} />
          <Editor
            options={{
              minimap: {
                enabled: false,
              },
            }}
            height="75vh"
            language={language}
            defaultValue={CODE_SNIPPETS[language]}
            value={answer}
            onChange={(value) => setAnswer(questionId, value)}
          />
        </Box>

        <Box sx={{ flex: 1 }}>
          <Box sx={{ width: "50%" }}>
            <Button
              variant="outlined"
              color="success"
              disabled={isLoading}
              sx={{ marginBottom: 2, marginRight: 2 }}
              onClick={() => handleRunCode()}
            >
              {isLoading ? <CircularProgress size={24} /> : "Run"}
            </Button>

            <Button
              variant="outlined"
              color="primary"
              disabled={submitLoading}
              sx={{ marginBottom: 2 }}
              onClick={() => handleCodeSumbit()}
            >
              {submitLoading ? <CircularProgress size={24} /> : "Submit"}
            </Button>

            <Typography sx={{fontSize: "1.25rem" }}>
              
            </Typography>
            
            <Typography sx={{fontSize: "1.25rem" }}>
              Input
            </Typography>
            <TextField
                margin="normal"
                multiline
                fullWidth
                type={"textarea"}
                value={stdin}
                onChange={(event) => setStdin(event.target.value)}
                rows={12}
            />
            <Typography sx={{fontSize: "1.25rem" }}>
              Output
            </Typography>
            <TextField
                margin="normal"
                multiline
                fullWidth
                sx={{
                  backgroundColor: isError ? "rgba(255, 0, 0, 0.1)" : "white",
                }}
                type={"textarea"}
                value={output}
                onChange={(event) => setOutput(event.target.value)}
                rows={12}
            />
             
          </Box>
        </Box>
      </Stack>
    </Box>
  );
};

export default CodeEditor;
