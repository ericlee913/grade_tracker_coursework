import React, { useEffect, useState } from "react";
import axios from "axios";
import { Paper, Typography, Alert } from "@mui/material";
import { API_ENDPOINT } from "../config";

function ShowSummary() {
  const [summary, setSummary] = useState({
    totalStudents: 0,
    totalModules: 0,
    overallGrade: 0.0,
  });
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchSummary();
  }, []);

  function fetchSummary() {
    axios
      .get(`${API_ENDPOINT}/summary`)
      .then((response) => {
        setSummary(response.data);
      })
      .catch((error) => {
        setError(error.message);
      });
  }

  return (
    <Paper sx={{ padding: "30px", margin: "30px auto", maxWidth: "600px" }}>
      <Typography variant="h5">Summary</Typography>
      <br />
      {error && <Alert severity="error">{error}</Alert>}
      {!error && (
        <>
          <Typography variant="body1">
            <strong>Total Students:</strong> {summary.totalStudents}
          </Typography>
          <Typography variant="body1">
            <strong>Total Modules:</strong> {summary.totalModules}
          </Typography>
          <Typography variant="body1">
            <strong>Overall Grade:</strong> {summary.overallGrade.toFixed(2)}
          </Typography>
        </>
      )}
    </Paper>
  );
}

export default ShowSummary;
