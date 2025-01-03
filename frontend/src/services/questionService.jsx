import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL + '/api/exam-questions';

const getMcqQuestions = async (examid, headers, pageno) => {
    const response = await axios.get(`${API_URL}/${examid}/mcq/${localStorage.getItem('email')}?page=${pageno}&size=8`, {headers});
    return response?.data;
}

const getCodingQuestions = async (examid, headers, pageno) => {
    const response = await axios.get(`${API_URL}/${examid}/code/${localStorage.getItem('email')}?page=${pageno}&size=8`, {headers});
    return response?.data;
}

export default {getCodingQuestions, getMcqQuestions};