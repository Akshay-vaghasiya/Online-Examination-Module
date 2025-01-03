import React from "react";
import { Card, CardContent, Typography, Button } from "@mui/material";

const ExamCard = ({ examName, duration, status, onStart }) => {
    return (
      <Card
        sx={{
          borderRadius: 2,
          boxShadow: 3,
          backgroundColor: "#f9f9f9",
          width: "100%",
        }}
      >
        <CardContent sx={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
          <Typography variant="h5" component="div" gutterBottom>
            <Typography variant="h6" component="div" gutterBottom>
                {examName}
            </Typography>
            <Typography variant="body2" color="text.secondary">
                Duration: {duration} minutes
            </Typography>
          </Typography>

          <Button
            variant="contained"
            color={status === "STARTED" || status === "RESUMED" ? "primary" : "secondary"}
            disabled={status !== "STARTED" && status !== "RESUMED"}
            sx={{ marginTop: 1, width: 150 }}
            onClick={onStart}
          >
            {status === "STARTED" || status === "RESUMED" ? "Start" : "Unavailable"}
          </Button>
        </CardContent>
      </Card>
    );
  };
  
export default ExamCard;