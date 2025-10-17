import { DataTable, type DataTablePageEvent } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import type { Provider } from '../models/Provider';

interface ProviderTableProps {
  providers: Provider[];
  loading: boolean;
  page: number;
  pageSize: number;
  totalRecords: number;
  onPageChange: (event: DataTablePageEvent) => void;
  onView: (provider: Provider) => void;
  onDelete: (provider: Provider) => void;
}

export const ProviderTable = ({
  providers,
  loading,
  page,
  pageSize,
  totalRecords,
  onPageChange,
  onView,
  onDelete,
}: ProviderTableProps) => {
  const actionBodyTemplate = (rowData: Provider) => (
    <div className="action-buttons">
      <Button
        icon="pi pi-eye"
        rounded
        outlined
        severity="info"
        tooltip="Ver Detalle"
        tooltipOptions={{ position: 'top' }}
        onClick={() => onView(rowData)}
      />
      <Button
        icon="pi pi-trash"
        rounded
        outlined
        severity="danger"
        tooltip="Eliminar"
        tooltipOptions={{ position: 'top' }}
        onClick={() => onDelete(rowData)}
      />
    </div>
  );

  return (
    <DataTable
      value={providers}
      loading={loading}
      lazy
      paginator
      rows={pageSize}
      totalRecords={totalRecords}
      onPage={onPageChange}
      first={page * pageSize}
      rowsPerPageOptions={[5, 10, 25, 50]}
      tableStyle={{ minWidth: '60rem' }}
      emptyMessage="No se encontraron proveedores"
      className="providers-table"
    >
      <Column field="id" header="ID" sortable style={{ width: '10%' }} />
      <Column field="name" header="Nombre" sortable style={{ width: '20%' }} />
      <Column field="companyName" header="Compañía" sortable style={{ width: '30%' }} />
      <Column field="address" header="Dirección" sortable style={{ width: '30%' }} />
      <Column
        body={actionBodyTemplate}
        header="Acciones"
        exportable={false}
        style={{ width: '10%' }}
      />
    </DataTable>
  );
};
