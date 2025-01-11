import React, { useEffect, useState } from "react";
import { Breadcrumbs, Link, Typography, Alert, Grid } from "@mui/material";
import App from "../App";
import { API_ENDPOINT } from "../config";

function Summary() {
  const [summary, setSummary] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchSummary = async () => {
      try {
        const response = await fetch(`${API_ENDPOINT}/summary`);
        if (!response.ok) {
          throw new Error(`Error: ${response.status} - ${response.statusText}`);
        }
        const data = await response.json();
        setSummary(data);
      } catch (err) {
        console.error("Error fetching summary data:", err);
        setError(err.message); // Display the error message
      }
    };
  
    fetchSummary();
  }, []);
  

  return (
    <App>
      <Breadcrumbs sx={{ marginBottom: "30px" }}>
        <Link underline="hover" color="inherit" href="/">
          Home
        </Link>
        <Typography sx={{ color: "text.primary" }}>Summary</Typography>
      </Breadcrumbs>

      {error && <Alert color="error">{error}</Alert>}
      {!error && summary && (
        <div>
          <Typography variant="h5">Summary</Typography>
          <br />
          <br />
          <Grid container spacing={2}>
            <Grid item xs={4}>
              <Typography sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Total Number of Students: {summary.totalStudents}
              </Typography>
            </Grid>
            <Grid item xs={4}>
              <Typography sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Total Number of Modules: {summary.totalModules}
              </Typography>
            </Grid>
            <Grid item xs={4}>
              <Typography sx={{ fontSize: '16px', fontWeight: 'bold' }}>
                Overall Average Grade: {summary.overallGrade.toFixed(2)}
              </Typography>
            </Grid>
          </Grid>
        </div>
      )}

      {!error && !summary && <Alert color="warning">Loading summary...</Alert>}
    </App>
  );
}

export default Summary;
