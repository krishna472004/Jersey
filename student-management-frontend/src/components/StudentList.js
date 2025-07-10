import axios from "axios";
import { useEffect, useState } from "react";

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [formData, setFormData] = useState({
    id: "",
    name: "",
    dob: "",
    email: "",
    marks: "",
  });
  const [isEditing, setIsEditing] = useState(false);

  const API_URL = "http://localhost:8081/App7/api/students";

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    try {
      const response = await axios.get(API_URL);
      setStudents(response.data.student);
    } catch (error) {
      console.error("Error fetching students:", error);
    }
  };

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEditing) {
        await axios.put(`${API_URL}`, formData);
      } else {
        await axios.post(API_URL, formData);
      }
      setFormData({ id: "", name: "", dob: "", email: "", marks: "" });
      setIsEditing(false);
      fetchStudents();
    } catch (error) {
      console.error("Error saving student:", error);
    }
  };

  const handleEdit = (student) => {
    setFormData(student);
    setIsEditing(true);
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchStudents();
    } catch (error) {
      console.error("Error deleting student:", error);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>{isEditing ? "Update Student" : "Add Student"}</h2>
      <form onSubmit={handleSubmit} style={{ marginBottom: "20px" }}>
        <input
          name="id"
          value={formData.id}
          onChange={handleChange}
          placeholder="ID"
          required
        />
        <input
          name="name"
          value={formData.name}
          onChange={handleChange}
          placeholder="Name"
          required
        />
        <input
          name="dob"
          type="date"
          value={formData.dob}
          onChange={handleChange}
          required
        />
        <input
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Email"
          required
        />
        <input
          name="marks"
          value={formData.marks}
          onChange={handleChange}
          placeholder="Marks"
          required
        />
        <button type="submit">{isEditing ? "Update" : "Add"}</button>
      </form>

      <h2>Student List</h2>
      {students.length > 0 ? (
        <table border="1" cellPadding="10">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>DOB</th>
              <th>Email</th>
              <th>Marks</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map((s) => (
              <tr key={s.id}>
                <td>{s.id}</td>
                <td>{s.name}</td>
                <td>{s.dob}</td>
                <td>{s.email}</td>
                <td>{s.marks}</td>
                <td>
                  <button onClick={() => handleEdit(s)}>Edit</button>
                  <button
                    onClick={() => handleDelete(s.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No students found.</p>
      )}
    </div>
  );
};

export default StudentList;
