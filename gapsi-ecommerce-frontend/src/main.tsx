import 'primereact/resources/themes/lara-light-blue/theme.css';  
import 'primereact/resources/primereact.min.css';                
import 'primeicons/primeicons.css';                             

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import WelcomePage from './pages/WelcomePage.tsx';
import ProvidersPage from './pages/ProvidersPage.tsx';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<WelcomePage />} />
        <Route path="/providers" element={<ProvidersPage />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
