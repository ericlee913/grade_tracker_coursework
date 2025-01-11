import React from "react";
import axios from "axios";
import {
  Paper,
  Button,
  Typography,
  Select,
  MenuItem,
  TextField,
  Alert,
  Grid
} from "@mui/material";
import { API_ENDPOINT } from "../config";

function AddGrade(props) {
  const [grade, setGrade] = React.useState({});
  const [students, setStudents] = React.useState([]);
  const [modules, setModules] = React.useState();
  const [error, setError] = React.useState();

  React.useEffect(() => {
    axios
      .get(`${API_ENDPOINT}/students`)
      .then((response) => {
        setStudents(response.data._embedded.students);
      })
      .catch((response) => setError(response.message));

    axios
      .get(`${API_ENDPOINT}/modules`)
      .then((response) => setModules(response.data._embedded.modules))
      .catch((response) => setError(response.message));
  }, []);

  function request() {
    axios
      .post(`${API_ENDPOINT}/grades/addGrade`, grade)
      .then(() => {
        props.update();
        setError(null); // Clear error after successful request
      })
      .catch((error) => {
        if (error.response && error.response.status === 400) {
          // Check if the error message is for "No registration found"
          if (error.response.data.includes("No registration found")) {
            setError("No registration exists for this student and module.");
          } else {
            setError(error.response.data); // Display other server error messages
          }
        } else {
          setError("An unexpected error occurred. Please try again."); // General error message for non-400 errors
        }
      });
  }

  return (
    <Paper sx={{ padding: "30px" }}>
      <Typography variant="h5">Add Grade</Typography>
      <br />
      <br />
      <Grid container spacing={3} sx={{ marginBottom: "20px" }}>
        <Grid item xs={6}>
          <Typography variant="body1" sx={{ marginBottom: "10px" }}>
            Select Student
          </Typography>
          <Select
            sx={{ minWidth: "100%" }}
            value={grade.student_id ?? ""}
            onChange={(e) => setGrade({ ...grade, student_id: e.target.value })}
            label="Student"
          >
            {students &&
              students.map((s) => {
                return (
                  <MenuItem
                    key={s.id}
                    value={s.id}
                  >{`${s.firstName} ${s.lastName} (${s.id})`}</MenuItem>
                );
              })}
          </Select>
        </Grid>
        <Grid item xs={6}>
          <Typography variant="body1" sx={{ marginBottom: "10px" }}>
            Select Module
          </Typography>
          <Select
            sx={{ minWidth: "100%" }}
            value={grade.module_code ?? ""}
            onChange={(e) => setGrade({ ...grade, module_code: e.target.value })}
            label="Module"
          >
            {modules &&
              modules.map((m) => {
                return (
                  <MenuItem
                    key={m.code}
                    value={m.code}
                  >{`${m.code} ${m.name}`}</MenuItem>
                );
              })}
          </Select>
        </Grid>
      </Grid>
      <br />
      <TextField
        sx={{ minWidth: "100px" }}
        label="Score"
        value={grade.score ?? 0}
        onChange={(e) => setGrade({ ...grade, score: e.target.value })}
      />
      <br />
      <br />
      <Button onClick={request}>Add</Button>
      {error && <Alert color="error">{error}</Alert>}
    </Paper>
  );
}

export default AddGrade;