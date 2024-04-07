import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Изменение импорта
import ButtonAppBar from './components/Appbar';
import User from './components/Users';
import Converter from './components/Converter'
import Conversions from './components/Conversions'
import backgroundImage from './wp2106911.jpg';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  const appStyle = {
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    minHeight: '150vh', // Ensure the background covers the entire viewport
    backgroundAttachment: 'fixed', // Keep the background fixed while scrolling
  };

  return (
    <Router>
      <div className="App" style={appStyle}>
        <ButtonAppBar />
        <Routes>
          <Route path="/" element={<Converter/>} />
          <Route path="/users" element={<User />} />
          <Route path="/history" element={<Conversions />} />
        </Routes>
        <ToastContainer />
      </div>
    </Router>
  );
}

export default App;
