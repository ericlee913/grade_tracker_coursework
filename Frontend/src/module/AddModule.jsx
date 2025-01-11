import React from "react";
import axios from "axios";
import {
  Paper,
  TextField,
  Switch,
  FormControlLabel,
  Button,
  Typography,
  Alert,
  Grid,
} from "@mui/material";
import { API_ENDPOINT } from "../config";

function AddModule(props) {
  const [module, setModule] = React.useState({});
  const [error, setError] = React.useState();

  function request() {
    axios
      .post(`${API_ENDPOINT}/modules`, module)
      .then(() => {
        props.update();
      })
      .catch((response) => {
        setError(response.message);
      });
  }

  return (
    <Paper sx={{ padding: "30px" }}>
      <Typography variant="h5">Add/Update Module</Typography>
      <br />
      <Grid container spacing={3} sx={{ marginBottom: "20px" }}>
        <Grid item xs={6}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Module Code"
            onChange={(e) => {
              setModule({ ...module, code: e.target.value.toUpperCase() });
            }}
          />
        </Grid>
        <Grid item xs={6}>
          <TextField
            sx={{ minWidth: "100%" }}
            label="Module Name"
            onChange={(e) => {
              setModule({ ...module, name: e.target.value });
            }}
          />
        </Grid>
      </Grid>
      <FormControlLabel
        control={
          <Switch
            checked={module.mnc ?? false}
            id="is_mnc"
            onChange={(e) => {
              setModule({ ...module, mnc: e.target.checked });
            }}
          />
        }
        label="MNC?"
      />
      <br />
      <br />
      <Button onClick={request}>Add/Update</Button>
      <br />
      {error && <Alert color="error">{error}</Alert>}
    </Paper>
  );
}

export default AddModule;
