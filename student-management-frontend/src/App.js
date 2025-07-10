import { useState } from "react";
import StudentDetail from "./components/StudentDetail";
import StudentForm from "./components/StudentForm";
import StudentList from "./components/StudentList";

export default function App() {
  const [selected, setSelected] = useState(null);
  const [mode, setMode] = useState("list"); // list | view | edit | create

  const handleSelect = (student) => {
    setSelected(student);
    setMode("view");
  };
  const handleCreate = () => {
    setSelected(null);
    setMode("create");
  };
  const handleEdit = (student) => {
    setSelected(student);
    setMode("edit");
  };
  const handleBack = () => setMode("list");

  return (
    <div className="container mx-auto p-4">
      <header className="mb-6">
        <h1 className="text-3xl font-bold">Student Management</h1>
      </header>
      {mode === "list" && (
        <>
          <button
            onClick={handleCreate}
            className="mb-4 px-4 py-2 bg-blue-500 text-white rounded"
          >
            Add Student
          </button>
          <StudentList onSelect={handleSelect} />
        </>
      )}
      {(mode === "create" || mode === "edit") && (
        <StudentForm
          student={mode === "edit" ? selected : null}
          onSuccess={handleBack}
          onCancel={handleBack}
        />
      )}
      {mode === "view" && selected && (
        <StudentDetail
          student={selected}
          onEdit={() => handleEdit(selected)}
          onBack={handleBack}
        />
      )}
    </div>
  );
}
