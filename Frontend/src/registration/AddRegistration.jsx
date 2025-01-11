import React from "react";
import axios from "axios";
import {
  Paper,
  Button,
  Typography,
  Select,
  MenuItem,
  Alert,
  Grid
} from "@mui/material";
import { API_ENDPOINT } from "../config";

function AddRegistration(props) {
  const [registration, setRegistration] = React.useState({});
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
      .post(`${API_ENDPOINT}/registrations/registerModule`, registration)
      .then(() => {
        props.update();
      })
      .catch((response) => {
        setError(response.message);
      });
  }

  return (
    <Paper sx={{ padding: "30px" }}>
      <Typography variant="h5">Register Student to Module</Typography>
      <br />
      <br />
      <Grid container spacing={3} sx={{ marginBottom: "20px" }}>
        <Grid item xs={6}>
          <Typography variant="body1" sx={{ marginBottom: "10px" }}>
            Select Student
          </Typography>
          <Select
            sx={{ minWidth: "100%" }}
            label="Student"
            value={registration.student_id ?? ""}
            onChange={(e) =>
              setRegistration({ ...registration, student_id: e.target.value })
            }
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
            label="Module"
            value={registration.module_code ?? ""}
            onChange={(e) =>
              setRegistration({ ...registration, module_code: e.target.value })
            }
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
      <br />
      <Button onClick={request}>Register</Button>
      <br />
      <br />
      {error && <Alert color="error">{error}</Alert>}
    </Paper>
  );
}

export default AddRegistration;
