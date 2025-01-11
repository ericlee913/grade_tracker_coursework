import React from "react";
import axios from "axios";
import { Breadcrumbs, Link, Typography, Alert, Table, TableContainer, Paper, TableHead, TableRow, TableCell, TableBody} from "@mui/material";
import App from "../App.jsx";
import { API_ENDPOINT } from "../config";
import AddModule from "./AddModule";

function Modules() {
  const [modules, setModules] = React.useState([]);
  const [error, setError] = React.useState();

  React.useEffect(() => {
    updateModules();
  }, []);

  function updateModules() {
    axios
      .get(`${API_ENDPOINT}/modules`)
      .then((response) => {
        setModules(response.data._embedded.modules);
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
        <Typography sx={{ color: "text.primary" }}>Modules</Typography>
      </Breadcrumbs>
      {error && <Alert color="error">{error}</Alert>}
      {!error && modules.length < 1 && (
        <Alert color="warning">No modules</Alert>
      )}
      <AddModule update={updateModules} />
      <br />
      {modules.length > 0 && (
        <TableContainer component={Paper} className = "table-container">
        <Table className = "table">
          <TableHead>
            <TableRow>
              <TableCell>Module Code</TableCell>
              <TableCell>Module Name</TableCell>
              <TableCell>Is MNC</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {modules.map((m) => (
              <TableRow key={m.code}>
                <TableCell>{m.code}</TableCell>
                <TableCell>{m.name}</TableCell>
                <TableCell>{m.mnc ? "Yes" : "No"}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      )}
    </App>
  );
}

export default Modules;
