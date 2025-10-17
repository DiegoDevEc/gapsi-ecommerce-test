import { useNavigate } from 'react-router-dom';
import './CustomHeader.css';

type Props = {
  title?: string
}

export const CustomHeader = ({title} : Props) => {
    const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate('/');
  };

  return (
      <header className="custom-header">
        <div className="logo-container" onClick={handleLogoClick}>
          <img src="/logo.png" alt="GAPSI Logo" />
        </div>
        {title && <h2 className="header-title">{title}</h2>}
      </header>
  )
}
