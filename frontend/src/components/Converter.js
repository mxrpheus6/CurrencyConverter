import React, { useState, useEffect } from 'react';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import axios from 'axios';

const Converter = () => {
  const [fromCurrency, setFromCurrency] = useState('');
  const [toCurrency, setToCurrency] = useState('');
  const [amount, setAmount] = useState('');
  const [result, setResult] = useState('');
  const [currencies, setCurrencies] = useState([]);
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState('');

  useEffect(() => {
    // Здесь выполняем GET запрос для получения доступных валют
    axios.get('http://localhost:8080/codes/currencies')
      .then(response => {
        setCurrencies(response.data);
      })
      .catch(error => {
        console.error('Error fetching currencies:', error);
      });

    // Здесь выполняем GET запрос для получения списка пользователей
    axios.get('http://localhost:8080/users')
      .then(response => {
        setUsers(response.data);
      })
      .catch(error => {
        console.error('Error fetching users:', error);
      });
  }, []);

  const handleFromCurrencyChange = (event) => {
    setFromCurrency(event.target.value);
  };

  const handleToCurrencyChange = (event) => {
    setToCurrency(event.target.value);
  };

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  const handleUserChange = (event) => {
    setSelectedUser(event.target.value);
  };

  const handleConvert = () => {
    // Здесь выполняем GET запрос для конвертации суммы
    axios.get(`http://localhost:8080/conversions/convert/user/${selectedUser}/from/${fromCurrency}/amount/${amount}/to/${toCurrency}`)
      .then(response => {
        setResult(response.data.convertedAmount);
      })
      .catch(error => {
        console.error('Error converting amount:', error);
      });
  };

  return (
    <div>
      <Paper elevation={3} style={{ padding: '20px', width: '400px', margin: '20px auto' }}>
        <Typography variant="h6" gutterBottom>
          Convert Currency
        </Typography>
        <Select value={selectedUser} onChange={handleUserChange} displayEmpty fullWidth style={{ marginBottom: '10px' }}>
          <MenuItem value="" disabled>
            Select User
          </MenuItem>
          {users.map(user => (
            <MenuItem key={user.id} value={user.id}>{user.name}</MenuItem>
          ))}
        </Select>
        <Select value={fromCurrency} onChange={handleFromCurrencyChange} displayEmpty fullWidth style={{ marginBottom: '10px' }}>
          <MenuItem value="" disabled>
            From Currency
          </MenuItem>
          {currencies.map(currency => (
            <MenuItem key={currency.code} value={currency.code}>{currency.code}</MenuItem>
          ))}
        </Select>
        <Select value={toCurrency} onChange={handleToCurrencyChange} displayEmpty fullWidth style={{ marginBottom: '10px' }}>
          <MenuItem value="" disabled>
            To Currency
          </MenuItem>
          {currencies.map(currency => (
            <MenuItem key={currency.code} value={currency.code}>{currency.code}</MenuItem>
          ))}
        </Select>
        <TextField label="Amount" variant="outlined" fullWidth value={amount} onChange={handleAmountChange} style={{ marginBottom: '10px' }} />
        <Button variant="contained" color="primary" onClick={handleConvert} fullWidth>
          Convert
        </Button>
        <TextField label="Result" variant="outlined" fullWidth readOnly value={result} style={{ marginTop: '20px' }} InputProps={{ readOnly: true }} />
      </Paper>
    </div>
  );
}

export default Converter;
