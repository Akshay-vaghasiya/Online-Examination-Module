import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL + '/api/student-exam';

const getExams = async (headers) => {
    const response = await axios.get(`${API_URL}/find-exam/${localStorage.getItem('email')}`, {headers});
    return response?.data;
}

const startExam = async (examid, headers) => {
    const response = await axios.post(`${API_URL}/create-exam/${localStorage.getItem('email')}/${examid}`, null, {headers});
    return response?.data;
}

const submitExam = async (studentexamid, headers) => {
    const response = await axios.post(`${API_URL}/submit-exam/${studentexamid}`, null, {headers});
    return response?.data;
}

const autoSave = async (data, examid, headers) => {
    const response = await axios.post(`${API_URL}/auto-save/${examid}`, data, {headers});
    return response?.data;
}

const runCode = async (data, headers) => {
    const response = await axios.post(`${API_URL}/run-code`, data, {headers});
    return response?.data;
}

const submitCode = async (data, examid, headers) => {
    const response = await axios.post(`${API_URL}/submit-code/${localStorage.getItem('email')}/${examid}`, data, {headers});
    return response?.data;
}

export default { getExams, startExam, submitExam, autoSave, runCode, submitCode };