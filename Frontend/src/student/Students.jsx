import React from "react";
import axios from "axios";
import { Breadcrumbs, Link, Typography, Alert, Table, TableContainer, Paper, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import App from "../App";
import { API_ENDPOINT } from "../config";
import AddStudent from "./AddStudent";

function Students() {
  const [students, setStudents] = React.useState([]);
  const [error, setError] = React.useState();

  React.useEffect(() => {
    updateStudents();
  }, []);

  function updateStudents() {
    axios
      .get(`${API_ENDPOINT}/students`)
      .then((response) => {
        setStudents(response.data._embedded.students);
      })
      .catch((response) => {
        setError(response.message);
      });
  }

  return (
    <App>
      <Breadcrumbs sx={{ marginBottom: "30px" }}>
        <Link underline="hover" color="inherit" href="/">
          Home
        </Link>
        <Typography sx={{ color: "text.primary" }}>Students</Typography>
      </Breadcrumbs>
      {error && <Alert color="error">{error}</Alert>}
      {!error && students.length < 1 && (
        <Alert color="warning">No students</Alert>
      )}
      <AddStudent update={updateStudents} />
      <br />

      {students.length > 0 && (
        <TableContainer component={Paper} className = "table-container">
        <Table className = "table">
          <TableHead>
            <TableRow>
              <TableCell>Student ID</TableCell>
              <TableCell>First Name</TableCell>
              <TableCell>Last Name</TableCell>
              <TableCell>Username</TableCell>
              <TableCell>Email</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {students.map((s) => (
              <TableRow key={s.id}>
                <TableCell>{s.id}</TableCell>
                <TableCell>{s.firstName}</TableCell>
                <TableCell>{s.lastName}</TableCell>
                <TableCell>{s.userName}</TableCell>
                <TableCell>{s.email}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      )}
    </App>
  );
}

export default Students;
