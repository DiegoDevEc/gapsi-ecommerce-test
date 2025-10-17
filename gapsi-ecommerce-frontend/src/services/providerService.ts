import { api } from './api';
import type {
  ProviderFormData,
  ApiResponse,
  PagedProviderResponse,
  Provider
} from '../models/Provider';

export const providerService = {
  /**
   * Obtiene todos los proveedores con paginación
   * GET /api/v1/providers?page=0&size=10
   */
  getAllPaginated: async (page: number = 0, size: number = 10): Promise<PagedProviderResponse> => {
    const response = await api.get<ApiResponse<PagedProviderResponse>>(
      `/v1/providers?page=${page}&size=${size}`
    );
    return response.data.data;
  },

  /**
   * Obtiene un proveedor por ID
   * GET /api/v1/providers/{id}
   */
  getById: async (id: number): Promise<Provider> => {
    const response = await api.get<ApiResponse<Provider>>(`/v1/providers/${id}`);
    return response.data.data;
  },

  /**
   * Crea un nuevo proveedor
   * POST /api/v1/providers
   */
  create: async (provider: ProviderFormData): Promise<Provider> => {
    const response = await api.post<ApiResponse<Provider>>('/v1/providers', provider);
    return response.data.data;
  },

  /**
   * Elimina un proveedor por ID
   * DELETE /api/v1/providers/{id}
   */
  delete: async (id: number): Promise<void> => {
    await api.delete(`/v1/providers/${id}`);
  },

  /**
   * NOTA: El backend NO tiene endpoint PUT para actualizar
   * Por ahora, esta funcionalidad no está disponible
   * Se debe agregar en el backend: PUT /api/v1/providers/{id}
   */
};
