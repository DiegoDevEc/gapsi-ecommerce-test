# Docker Setup - GAPSI E-commerce

Este archivo contiene las instrucciones para ejecutar el proyecto completo usando Docker Compose.

## Arquitectura

El proyecto consta de dos servicios:

1. **Backend** (Spring Boot 3.5.6 + Java 17)
   - Puerto: 8080
   - API REST: `http://localhost:8080/api/v1`
   - Swagger: `http://localhost:8080/swagger-ui.html`

2. **Frontend** (React 19 + Vite + TypeScript)
   - Puerto: 80
   - URL: `http://localhost`

## Requisitos Previos

- Docker (versión 20.10 o superior)
- Docker Compose (versión 2.0 o superior)

## Comandos Disponibles

### Iniciar todos los servicios

```bash
docker-compose up -d
```

### Ver logs en tiempo real

```bash
# Todos los servicios
docker-compose logs -f

# Solo backend
docker-compose logs -f backend

# Solo frontend
docker-compose logs -f frontend
```

### Detener todos los servicios

```bash
docker-compose down
```

### Reconstruir imágenes (después de cambios en código)

```bash
docker-compose up -d --build
```

### Reiniciar un servicio específico

```bash
docker-compose restart backend
docker-compose restart frontend
```

### Ver estado de los servicios

```bash
docker-compose ps
```

### Acceder a los logs de un servicio

```bash
docker-compose logs backend
docker-compose logs frontend
```

## Acceso a la Aplicación

Una vez iniciados los servicios:

- **Frontend**: http://localhost
- **Backend API**: http://localhost:8080/api/v1
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check Backend**: http://localhost:8080/api/v1/welcome

## Persistencia de Datos

El archivo `bd.json` del backend está montado como volumen, por lo que los datos persisten incluso si el contenedor se reinicia.

## Variables de Entorno

### Backend

- `SPRING_PROFILES_ACTIVE`: Perfil de Spring (prod por defecto)
- `JAVA_OPTS`: Opciones de JVM (-Xms256m -Xmx512m por defecto)

### Frontend

- `VITE_API_BASE_URL`: URL base del backend (http://localhost:8080/api)

Para modificar estas variables, edita el archivo `docker-compose.yml`.

## Troubleshooting

### El frontend no se conecta al backend

Verifica que el backend esté corriendo:
```bash
curl http://localhost:8080/api/v1/welcome
```

### Reconstruir desde cero

Si tienes problemas, puedes limpiar todo y empezar de nuevo:

```bash
# Detener y eliminar contenedores, redes y volúmenes
docker-compose down -v

# Eliminar imágenes construidas
docker-compose down --rmi all

# Reconstruir todo
docker-compose up -d --build
```

### Ver el estado del healthcheck

```bash
docker inspect gapsi-ecommerce-backend | grep -A 20 Health
```

## Desarrollo

Para desarrollo, es recomendable ejecutar los servicios por separado:

```bash
# Terminal 1 - Backend
cd gapsi-ecommerce-backend
./mvnw spring-boot:run

# Terminal 2 - Frontend
cd gapsi-ecommerce-frontend
npm run dev
```

## Producción

Para producción, usa Docker Compose como se describe arriba. Considera:

1. Agregar un reverse proxy (como Traefik o Nginx) para manejar SSL/TLS
2. Usar un registro de contenedores privado
3. Configurar límites de recursos en docker-compose.yml
4. Implementar monitoreo y logging centralizado

## Arquitectura de Red

Ambos servicios están conectados a una red bridge personalizada llamada `gapsi-network`, lo que permite la comunicación entre contenedores usando nombres de servicio.

## Health Checks

El backend incluye un health check que verifica el endpoint `/api/v1/welcome` cada 30 segundos. El frontend espera a que el backend esté "healthy" antes de iniciarse.