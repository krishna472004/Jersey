export default function StudentDetail({ student, onEdit, onBack }) {
  return (
    <div className="max-w-md mx-auto space-y-4">
      <div>
        <h2 className="text-2xl font-semibold">Student Details</h2>
        <p>
          <strong>ID:</strong> {student.id}
        </p>
        <p>
          <strong>Name:</strong> {student.name}
        </p>
        <p>
          <strong>Email:</strong> {student.email}
        </p>
      </div>
      <div className="space-x-2">
        <button
          onClick={onEdit}
          className="px-4 py-2 bg-yellow-500 text-white rounded"
        >
          Edit
        </button>
        <button
          onClick={onBack}
          className="px-4 py-2 bg-gray-500 text-white rounded"
        >
          Back
        </button>
      </div>
    </div>
  );
}

// Usage example:
// import React from 'react';
// import StudentDetail from './StudentDetail';
//
// const student = {
