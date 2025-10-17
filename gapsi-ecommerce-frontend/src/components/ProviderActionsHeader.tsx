import { Button } from 'primereact/button';

interface ProviderActionsHeaderProps {
  onNew: () => void;
  onPrint: () => void;
}

export const ProviderActionsHeader = ({ onNew, onPrint }: ProviderActionsHeaderProps) => (
  <div className="card-header">
    <h3 className="card-title">Lista de Proveedores</h3>
    <div className="action-buttons-header">
      <Button
        label="Nuevo"
        icon="pi pi-plus"
        className="p-button-success mr-2"
        onClick={onNew}
      />
      <Button
        label="Imprimir"
        icon="pi pi-print"
        className="p-button-secondary"
        onClick={onPrint}
      />
    </div>
  </div>
);
