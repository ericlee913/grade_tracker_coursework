import React from "react";
import axios from "axios";
import { Paper, TextField, Button, Typography, Alert, Grid } from "@mui/material";
import { API_ENDPOINT } from "../config";

function AddStudent(props) {
  const [student, setStudent] = React.useState({});
  const [error, setError] = React.useState();

  function request() {
    axios
      .post(`${API_ENDPOINT}/students`, student)
      .then(() => {
        props.update();
      })
      .catch((response) => {
        setError(response.message);
      });
  }

  return (
    <Paper sx={{ padding: "30px" }}>
      <Typography variant="h5">Add/Update Student</Typography>
      <br />
      <Grid container spacing={3} sx={{ marginBottom: "20px" }}>
        <Grid item xs={4}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Student ID"
            onChange={(e) => {
              setStudent({ ...student, id: Number(e.target.value) });
            }}
          />
        </Grid>
        <Grid item xs={4}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="First Name"
            onChange={(e) => {
              setStudent({ ...student, firstName: e.target.value });
            }}
          />
        </Grid>
        <Grid item xs={4}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Last Name"
            onChange={(e) => {
              setStudent({ ...student, lastName: e.target.value });
            }}
          />
        </Grid>
        <Grid item xs={6}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Username"
            onChange={(e) => {
              setStudent({ ...student, userName: e.target.value });
            }}
          />
        </Grid>
        <Grid item xs={6}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Email"
            onChange={(e) => {
              setStudent({ ...student, email: e.target.value });
            }}
          />
        </Grid>
      </Grid>
      <Button onClick={request}>Add/Update</Button>
      {error && <Alert color="error">{error}</Alert>}
    </Paper>
  );
}

export default AddStudent;
