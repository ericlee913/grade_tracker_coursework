import {
  Breadcrumbs,
  Typography,
  List,
  ListItemButton,
  ListItemText,
} from "@mui/material";

import App from "./App";

function Home() {
  return (
    <App>
      <Breadcrumbs className ="breadcrumbs">
        <Typography sx={{ color: "text.primary" }}>Home</Typography>
      </Breadcrumbs>
      <br />
      <Typography variant="h3" sx={{ fontWeight: "Bold", marginBottom: "20px"}}>Student Grade Management Program</Typography>
      <List>
        <ListItemButton href="/modules" sx={{ padding: "10px", borderRadius: "4px", marginBottom: "10px"}}>
          <ListItemText>Modules</ListItemText>
        </ListItemButton>
        <ListItemButton href="/students" sx={{ padding: "10px", borderRadius: "4px", marginBottom: "10px"}}>
          <ListItemText>Students</ListItemText>
        </ListItemButton>
        <ListItemButton href="/grades" sx={{ padding: "10px", borderRadius: "4px", marginBottom: "10px"}}>
          <ListItemText>Grades</ListItemText>
        </ListItemButton>
        <ListItemButton href="/registration" sx={{ padding: "10px", borderRadius: "4px", marginBottom: "10px"}}>
          <ListItemText>Registration</ListItemText>
        </ListItemButton>
        <ListItemButton href="/summary" sx={{ padding: "10px", borderRadius: "4px", marginBottom: "10px"}}>
          <ListItemText>Summary</ListItemText>
        </ListItemButton>
      </List>
    </App>
  );
}

export default Home;
