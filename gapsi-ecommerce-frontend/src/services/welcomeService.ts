import type { ApiResponse, WelcomeResponse } from '../models/Provider';
import { api } from './api';

export const welcomeService = {
  /**
   * Obtiene el mensaje de bienvenida y versión del API
   * GET /api/v1/welcome
   */
  getWelcome: async (): Promise<WelcomeResponse> => {
    const response = await api.get<ApiResponse<WelcomeResponse>>('/v1/welcome');
    return response.data.data;
  },
};
