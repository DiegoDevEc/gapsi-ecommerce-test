import { useState, useEffect, useRef } from 'react';
import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import { Toast } from 'primereact/toast';
import { providerService } from '../services/providerService';
import type { Provider, ProviderFormData } from '../models/Provider';
import { CustomHeader } from '../components/CustomHeader';
import { ProviderTable } from '../components/ProviderTable';
import { ProviderDialog } from '../components/ProviderDialog';
import { ProviderActionsHeader } from '../components/ProviderActionsHeader';
import './ProvidersPage.css';

const ProvidersPage = () => {
  const [providers, setProviders] = useState<Provider[]>([]);
  const [loading, setLoading] = useState(true);
  const [displayDialog, setDisplayDialog] = useState(false);
  const [readOnlyMode, setReadOnlyMode] = useState(false);
  const [formData, setFormData] = useState<ProviderFormData>({ name: '', companyName: '', address: '' });
  const [errors, setErrors] = useState<Partial<ProviderFormData>>({});
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalRecords, setTotalRecords] = useState(0);
  const toast = useRef<Toast>(null);

  useEffect(() => {
    loadProviders();
  }, [page, pageSize]);

  const loadProviders = async () => {
    try {
      setLoading(true);
      const data = await providerService.getAllPaginated(page, pageSize);
      setProviders(data.items);
      setTotalRecords(data.totalItems);
    } catch {
      toast.current?.show({ severity: 'error', summary: 'Error', detail: 'No se pudieron cargar los proveedores', life: 3000 });
    } finally {
      setLoading(false);
    }
  };

  const openNew = () => {
    setReadOnlyMode(false);
    setFormData({ name: '', companyName: '', address: '' });
    setErrors({});
    setDisplayDialog(true);
  };

  const openView = (provider: Provider) => {
    setReadOnlyMode(true);
    setFormData(provider);
    setDisplayDialog(true);
  };

  const hideDialog = () => setDisplayDialog(false);

  const saveProvider = async () => {
    try {
      await providerService.create(formData);
      toast.current?.show({ severity: 'success', summary: 'Éxito', detail: 'Proveedor creado', life: 3000 });
      hideDialog();
      loadProviders();
    } catch {
      toast.current?.show({ severity: 'error', summary: 'Error', detail: 'No se pudo guardar', life: 3000 });
    }
  };

  const confirmDelete = (provider: Provider) => {
    confirmDialog({
      message: `¿Eliminar proveedor "${provider.name}"?`,
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => deleteProvider(provider.id),
    });
  };

  const deleteProvider = async (id: number) => {
    await providerService.delete(id);
    loadProviders();
  };

  return (
    <div className="providers-page">
      <Toast ref={toast} />
      <ConfirmDialog />

      <CustomHeader title="Administración de Proveedores" />

      <main className="providers-content">
        <div className="content-card">
          <ProviderActionsHeader onNew={openNew} onPrint={() => window.print()} />
          <ProviderTable
            providers={providers}
            loading={loading}
            page={page}
            pageSize={pageSize}
            totalRecords={totalRecords}
            onPageChange={(e) => {
              setPage(e.page ?? 0);
              setPageSize(e.rows);
            }}
            onView={openView}
            onDelete={confirmDelete}
          />
        </div>
      </main>

      <ProviderDialog
        visible={displayDialog}
        readOnly={readOnlyMode}
        formData={formData}
        errors={errors}
        onHide={hideDialog}
        onChange={(key, value) => setFormData({ ...formData, [key]: value })}
        onSave={saveProvider}
      />
    </div>
  );
};

export default ProvidersPage;
