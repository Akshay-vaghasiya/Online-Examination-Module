import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL + '/api/exam';

// Function to fetch all exams
const getExams = async (headers) => { 
    const response = await axios.get(`${API_URL}/exams`, { headers }); 
    return response?.data;
}

// Function to create a new exam
const createExam = async (data, headers) => {
    const response = await axios.post(`${API_URL}/create-exam`, data, { headers });
    return response?.data;
}

// Function to update an existing exam
const updateExam = async (id, data, headers) => {
    const response = await axios.put(`${API_URL}/update-exam/${id}`, data, { headers });
    return response?.data;
}

// Function to delete an existing exam
const deleteExam = async (id, headers) => {
    const response = await axios.delete(`${API_URL}/delete-exam/${id}`, { headers });
    return response?.data;
}

export default { getExams, createExam, updateExam, deleteExam };