import React from "react";
import axios from "axios";
import { Breadcrumbs, Link, Typography, Alert, Table, TableContainer, Paper, TableHead, TableRow, TableCell, TableBody} from "@mui/material";
import App from "../App.jsx";
import { API_ENDPOINT } from "../config";
import AddRegistration from "./AddRegistration";

function RegistrationRow(props) {
  const { registration } = props;
  const [student, setStudent] = React.useState();
  const [module, setModule] = React.useState();

  React.useEffect(() => {
    axios
      .get(registration._links.module.href)
      .then((response) => setModule(response.data));

    axios
      .get(registration._links.student.href)
      .then((response) => setStudent(response.data));
  }, [registration]);

  return (
    <TableBody>
      <TableRow key={registration.id}>
        <TableCell>{student && `${student.firstName} ${student.lastName} (${student.id})`}</TableCell>
        <TableCell>{module && `${module.code} ${module.name}`}</TableCell>
      </TableRow>
    </TableBody>
  );
}

function Registrations() {
  const [registrations, setRegistrations] = React.useState([]);
  const [error, setError] = React.useState();

  React.useEffect(() => {
    updateRegistrations();
  }, []);

  function updateRegistrations() {
    axios
      .get(`${API_ENDPOINT}/registrations`)
      .then((response) => {
        setRegistrations(response.data._embedded.registrations);
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
        <Typography sx={{ color: "text.primary" }}>Registrations</Typography>
      </Breadcrumbs>
      {error && <Alert color="error">{error}</Alert>}
      {!error && registrations.length < 1 && (
        <Alert color="warning">No registrations</Alert>
      )}
      <AddRegistration update={updateRegistrations} />
      <br /> 
      {registrations.length > 0 && (
        <TableContainer component={Paper} className = "table-container">
        <Table className = "table">
          <TableHead>
            <TableRow>
              <TableCell>Student</TableCell>
              <TableCell>Registered Module</TableCell>
            </TableRow>
          </TableHead>
          {registrations.map((r) => {
            return <RegistrationRow registration={r} />;
          })}
        </Table>
      </TableContainer>
      )}
    </App>
  );
}

export default Registrations;
