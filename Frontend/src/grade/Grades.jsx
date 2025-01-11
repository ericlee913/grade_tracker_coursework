import React from "react";
import axios from "axios";
import { Breadcrumbs, Link, Typography, Alert, Table, TableContainer, Paper, TableHead, TableRow, TableCell, TableBody} from "@mui/material";
import App from "../App";
import { API_ENDPOINT } from "../config";
import AddGrade from "./AddGrade";

function GradeRow(props) {
  const { grade } = props;
  const [student, setStudent] = React.useState();
  const [module, setModule] = React.useState();

  React.useEffect(() => {
    axios
      .get(grade._links.module.href)
      .then((response) => setModule(response.data));

    axios
      .get(grade._links.student.href)
      .then((response) => setStudent(response.data));
  }, [grade]);

  return (
    <TableBody>
      <TableRow key={grade.id}>
        <TableCell>{student && `${student.firstName} ${student.lastName} (${student.id})`}</TableCell>
        <TableCell>{module && `${module.code} ${module.name}`}</TableCell>
        <TableCell>{grade.score}</TableCell>
      </TableRow>
    </TableBody>
  );
}

function Grades() {
  const [grades, setGrades] = React.useState([]);
  const [error, setError] = React.useState();

  React.useEffect(() => {
    updateGrades();
  }, []);

  function updateGrades() {
    axios
      .get(`${API_ENDPOINT}/grades`)
      .then((response) => {
        setGrades(response.data._embedded.grades);
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
        <Typography sx={{ color: "text.primary" }}>Grades</Typography>
      </Breadcrumbs>
      {error && <Alert color="error">{error}</Alert>}
      {!error && grades.length < 1 && <Alert color="warning">No grades</Alert>}
      <AddGrade update={updateGrades} />
      <br />
      {grades.length > 0 && (
        <TableContainer component={Paper} className = "table-container">
        <Table className = "table">
          <TableHead>
            <TableRow>
              <TableCell>Student</TableCell>
              <TableCell>Module</TableCell>
              <TableCell>Score</TableCell>
            </TableRow>
          </TableHead>
          {grades.map((g) => {
            return <GradeRow grade={g} />;
          })}
        </Table>
      </TableContainer>
      )}
    </App>
  );
}

export default Grades;
