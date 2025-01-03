import { useExamContext } from "../contexts/ExamContext";
import { useNavigate, useParams } from "react-router-dom";

const Pagination = ({
  totalQuestions,
  questionsPerPage,
  onQuestionChange,
  currentQuestion,
  currentPage,
  setCurrentPage,
  answeredQuestions,
  onPageChange,
  section
}) => {
 
  const {examId} = useParams();
  const { getCodingQuestions1, getMcqQuestions1 } = useExamContext();
  const navigate = useNavigate();

  const startIndex = (currentPage) * questionsPerPage + 1;
  const endIndex =  startIndex + totalQuestions - 1;

 
  const handlePreviousPage = () => {
    if (currentQuestion > 8) {
      
      onQuestionChange(startIndex - questionsPerPage);
      onPageChange();
      if(section === 'MCQ'){
        getMcqQuestions1(examId, navigate, currentPage - 1, startIndex - questionsPerPage);
      }else{
        getCodingQuestions1(examId, navigate, currentPage - 1, startIndex - questionsPerPage);
      }
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (endIndex-startIndex === questionsPerPage-1) {

      onQuestionChange(startIndex + questionsPerPage);
      onPageChange();
      if(section === 'MCQ'){
        getMcqQuestions1(examId, navigate, currentPage + 1, startIndex + questionsPerPage);
      }else{
        getCodingQuestions1(examId, navigate, currentPage + 1, startIndex + questionsPerPage);
      }
      setCurrentPage(currentPage + 1);
    }
  };

  const handleQuestionChange = (questionNumber) => {
    onQuestionChange(questionNumber);
  };

  return (
    <div className="flex flex-col items-center space-y-4">
      <div className="flex items-center space-x-2">
        <button
          className="bg-gray-300 text-gray-700 px-3 py-2 rounded disabled:cursor-not-allowed"
          onClick={handlePreviousPage}
          disabled={currentQuestion <= 8}
        >
          &lt;
        </button>

        {startIndex <= endIndex  && Array.from({ length: endIndex - startIndex + 1 }, (_, idx) => idx + startIndex).map(
          (questionNumber) => (
            <button
              key={questionNumber}
              className={`px-3 py-2 rounded border ${
                currentQuestion === questionNumber
                  ? "border-blue-500"
                  : answeredQuestions[questionNumber]
                  ? "bg-green-500 text-white"
                  : "bg-red-500 text-white"
              }`}
              onClick={() => handleQuestionChange(questionNumber)}
            >
              {questionNumber}
            </button>
          )
        )}

        <button
          className="bg-gray-300 text-gray-700 px-3 py-2 rounded disabled:cursor-not-allowed"
          onClick={handleNextPage}
          disabled={endIndex-startIndex !== questionsPerPage-1}
        >
          &gt;
        </button>
      </div>

      <div className="flex space-x-4">
        <button
          className="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-300"
          onClick={() => handleQuestionChange(currentQuestion - 1)}
          disabled={currentQuestion <= startIndex}
        >
          Previous Question
        </button>

        <button
          className="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-300"
          onClick={() => handleQuestionChange(currentQuestion + 1)}
          disabled={currentQuestion >= startIndex + totalQuestions - 1}
        >
          Next Question
        </button>
      </div>

      
    </div>
  );
};

export default Pagination;