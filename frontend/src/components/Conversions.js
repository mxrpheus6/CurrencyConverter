import React, { useState, useEffect } from 'react';
import { Container, Paper, Typography } from '@mui/material';

const ConversionHistory = () => {
  const [conversionHistory, setConversionHistory] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/conversions');
        const data = await response.json();
        setConversionHistory(data);
      } catch (error) {
        console.error('Error fetching conversion history:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <Container maxWidth="md">
      <Typography
        variant="h4"
        align="center"
        gutterBottom
        style={{
          backgroundColor: 'white',
          padding: '10px',
          borderRadius: '10px',
          marginTop: '20px',
          marginBottom: '20px', // Add some margin to separate from the items below
        }}
      >
        Conversion History
      </Typography>
      {conversionHistory.map(conversion => (
        <Paper key={conversion.id} elevation={3} style={{ padding: '1rem', marginBottom: '1rem' }}>
          <Typography variant="subtitle1" gutterBottom>
            User: {conversion.user.name} ({conversion.user.email})
          </Typography>
          <Typography variant="body1" gutterBottom>
            Conversion: {conversion.amount} {conversion.fromCurrency} -&gt; {conversion.convertedAmount}{' '}
            {conversion.toCurrency}
          </Typography>
          <Typography variant="body2" color="textSecondary">
            Data: {conversion.data}
          </Typography>
        </Paper>
      ))}
    </Container>
  );
};

export default ConversionHistory;
