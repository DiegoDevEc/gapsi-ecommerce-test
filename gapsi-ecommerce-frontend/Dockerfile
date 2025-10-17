# Etapa 1: Build
FROM node:20-alpine AS builder

WORKDIR /app

# Argumentos de build para variables de entorno
ARG VITE_API_BASE_URL=http://localhost:8080/api

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar el resto del código
COPY . .

# Configurar variables de entorno para Vite
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL

# Build de la aplicación
RUN npm run build

# Etapa 2: Producción con Nginx
FROM nginx:alpine

# Copiar los archivos build a Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Copiar configuración personalizada de Nginx (opcional)
# Si necesitas CORS o configuraciones específicas
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponer puerto 80
EXPOSE 80

# Comando por defecto de Nginx
CMD ["nginx", "-g", "daemon off;"]