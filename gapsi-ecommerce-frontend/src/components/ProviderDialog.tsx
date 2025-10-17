import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import type { ProviderFormData } from '../models/Provider';

interface ProviderDialogProps {
  visible: boolean;
  readOnly: boolean;
  formData: ProviderFormData;
  errors: Partial<ProviderFormData>;
  onHide: () => void;
  onChange: (key: keyof ProviderFormData, value: string) => void;
  onSave?: () => void;
}

export const ProviderDialog = ({
  visible,
  readOnly,
  formData,
  errors,
  onHide,
  onChange,
  onSave,
}: ProviderDialogProps) => {
  const footer = readOnly ? (
    <Button label="Cerrar" icon="pi pi-times" outlined onClick={onHide} />
  ) : (
    <>
      <Button label="Cancelar" icon="pi pi-times" outlined onClick={onHide} />
      <Button label="Guardar" icon="pi pi-check" onClick={onSave} />
    </>
  );

  return (
    <Dialog
      visible={visible}
      style={{ width: '450px' }}
      header={readOnly ? 'Detalle del Proveedor' : 'Nuevo Proveedor'}
      modal
      className="p-fluid"
      footer={footer}
      onHide={onHide}
    >
      {['name', 'companyName', 'address'].map((field) => (
        <div key={field} className="form-field">
          <label htmlFor={field}>
            {field === 'name'
              ? 'Nombre'
              : field === 'companyName'
              ? 'Compañía'
              : 'Dirección'}
          </label>
          <InputText
            id={field}
            value={formData[field as keyof ProviderFormData]}
            onChange={(e) => onChange(field as keyof ProviderFormData, e.target.value)}
            readOnly={readOnly}
            className={errors[field as keyof ProviderFormData] ? 'p-invalid' : ''}
          />
          {!readOnly && errors[field as keyof ProviderFormData] && (
            <small className="p-error">{errors[field as keyof ProviderFormData]}</small>
          )}
        </div>
      ))}
    </Dialog>
  );
};
