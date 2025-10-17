// Modelo de Proveedor (Provider)
export interface Provider {
  id: number;
  name: string;
  companyName: string;
  address: string;
}

// DTO para crear/actualizar proveedores
export interface ProviderFormData {
  name: string;
  companyName: string;
  address: string;
}

// Respuesta del backend con estructura ApiResponse
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

// Respuesta paginada de proveedores
export interface PagedProviderResponse {
  items: Provider[];
  totalItems: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
  hasNext: boolean;
  hasPrevious: boolean;
  first: boolean;
  last: boolean;
}

// Respuesta de bienvenida
export interface WelcomeResponse {
  welcomeMessage: string;
  version: string;
}
