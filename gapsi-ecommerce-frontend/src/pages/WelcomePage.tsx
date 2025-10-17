import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { Card } from 'primereact/card';
import { Divider } from 'primereact/divider';
import { Skeleton } from 'primereact/skeleton';
import { welcomeService } from '../services/welcomeService';
import type { WelcomeResponse } from '../models/Provider';
import { CustomHeader } from '../components/CustomHeader';
import { CustomFooter } from '../components/CustomFooter';
import './WelcomePage.css';

const WelcomePage = () => {
  const [welcomeResponse, setWelcomeResponse] = useState<WelcomeResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const toast = useRef<Toast>(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadWelcomeMessage();
  }, []);

  const loadWelcomeMessage = async () => {
    try {
      setLoading(true);
      const data = await welcomeService.getWelcome();
      setWelcomeResponse(data);
    } catch (error) {
      console.error(error);
      toast.current?.show({
        severity: 'error',
        summary: 'Error',
        detail: 'Error al cargar mensaje de bienvenida',
        life: 3000,
      });
    } finally {
      setLoading(false);
    }
  };

  const handleContinue = () => navigate('/providers');

  return (
    <div className="welcome-page">
      <Toast ref={toast} />

      {/* HEADER */}
      <CustomHeader />

      {/* MAIN */}
      <main className="welcome-content">
        <Card className="welcome-card">
          {loading ? (
            <>
              <Skeleton width="70%" height="1.8rem" borderRadius="8px" className="mb-4" />
              <Skeleton width="90%" className="mb-3" />
              <Skeleton width="60%" className="mb-4" />
              <Skeleton width="40%" height="2.5rem" borderRadius="10px" />
            </>
          ) : (
            <>
              {/* NUEVA FOTO CIRCULAR */}
              <div className="welcome-avatar-container">
                <img src="/user.jpg" alt="Candidato" className="welcome-avatar" />
              </div>

              {/* TEXTO DE BIENVENIDA */}
              <h2 className="welcome-title">{welcomeResponse?.welcomeMessage}</h2>

              <p className="welcome-message">
                Bienvenido al sistema de gestión de proveedores.<br />
                Conecta, administra y optimiza tus operaciones fácilmente.
              </p>

              <Divider align="center">
                <i className="pi pi-chevron-down text-primary" />
              </Divider>

              <Button
                label="Continuar"
                icon="pi pi-arrow-right"
                iconPos="right"
                onClick={handleContinue}
                className="continue-button p-button-rounded p-button-lg pulse-once"
              />
            </>
          )}
        </Card>
      </main>

      {/* FOOTER */}
      <CustomFooter version={welcomeResponse?.version} />
    </div>
  );
};

export default WelcomePage;
