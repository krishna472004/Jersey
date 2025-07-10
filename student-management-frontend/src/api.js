import axios from "axios";

const BASE_URL = "http://localhost:8081/App7/api/students";

export const fetchStudents = () => axios.get(BASE_URL);
export const fetchStudent = (id) => axios.get(`${BASE_URL}/${id}`);
export const createStudent = (data) => axios.post(BASE_URL, data);
export const updateStudent = (data) => axios.put(BASE_URL, data);
export const patchStudent = (data) => axios.patch(BASE_URL, data);
export const deleteStudent = (id) => axios.delete(`${BASE_URL}/${id}`);
