# API REST de Gestión de Proveedores

## Descripción
API REST para gestionar proveedores con paginación, validaciones y almacenamiento en archivo JSON.

## Tecnologías y Patrones Utilizados

### Tecnologías
- **Spring Boot 3.5.6**: Framework principal
- **Java 17**: Lenguaje de programación
- **Maven**: Gestor de dependencias
- **Lombok**: Reducción de boilerplate
- **Gson**: Procesamiento JSON
- **Spring Data Commons**: Paginación
- **Springdoc OpenAPI**: Documentación automática (Swagger)

### Patrones de Diseño Implementados

1. **Repository Pattern** (`JsonDatabase`)
   - Abstrae el acceso a datos del archivo JSON
   - Encapsula la lógica de persistencia

2. **Service Layer Pattern** (`ProviderService`, `ProviderServiceImpl`)
   - Separa la lógica de negocio de los controladores
   - Implementa validaciones de negocio

3. **DTO Pattern** (`ProviderRequestDto`, `ProviderResponseDto`)
   - Separa la representación de datos entre capas
   - Protege el modelo de dominio

4. **Mapper Pattern** (`ProviderMapper`)
   - Convierte entre entidades y DTOs
   - Centraliza la lógica de transformación

5. **Exception Handler Pattern** (`GlobalExceptionHandler`)
   - Manejo centralizado de excepciones
   - Respuestas consistentes de error

6. **Builder Pattern** (Lombok `@Builder`)
   - Construcción fluida de objetos
   - Código más legible

7. **Dependency Injection** (Spring IoC)
   - Bajo acoplamiento entre componentes
   - Facilita testing

### Buenas Prácticas Aplicadas

- **Validaciones**: Jakarta Validation en DTOs
- **Thread-Safety**: `ReadWriteLock` en operaciones de archivo
- **Logging**: SLF4J para trazabilidad
- **Documentación**: JavaDoc y Swagger
- **Separación de Responsabilidades**: Arquitectura en capas
- **Código Limpio**: Nombres descriptivos, métodos pequeños
- **Manejo de Errores**: Excepciones personalizadas
- **Versionado de API**: `/api/v1/providers`

---

## Endpoints Disponibles

### Base URL
```
http://localhost:8080/api/v1/providers
```

### Documentación Swagger
```
http://localhost:8080/swagger-ui.html
```

---

## 0. Mensaje de Bienvenida y Versión

**Endpoint:** `GET /api/v1/welcome`

**Descripción:** Retorna el mensaje de bienvenida personalizado y la versión de la API

**Ejemplo de Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/welcome"
```

**Ejemplo de Response (200 OK):**
```json
{
  "success": true,
  "message": "Información de bienvenida obtenida exitosamente",
  "data": {
    "welcomeMessage": "Bienvenido Candidato 01",
    "version": "v1.0.0"
  },
  "timestamp": "2025-10-16T13:53:55.165259"
}
```

**Configuración:**
Los valores se obtienen del archivo `application.yml`:
```yaml
api:
  version: v1.0.0
  user-welcome: Bienvenido Candidato 01
```

---

## 1. Obtener Lista Paginada de Proveedores

**Endpoint:** `GET /api/v1/providers`

**Descripción:** Retorna una lista paginada de todos los proveedores

**Parámetros Query:**
- `page` (opcional): Número de página (inicia en 0). Default: 0
- `size` (opcional): Tamaño de página. Default: 10

**Ejemplo de Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/providers?page=0&size=10"
```

**Ejemplo de Response (200 OK):**
```json
{
  "success": true,
  "message": "Proveedores obtenidos exitosamente",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Proveedor A",
        "companyName": "Proveedor A S.A.",
        "address": "Av. Siempre Viva 123"
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10,
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
  },
  "timestamp": "2025-10-16T13:00:00"
}
```

---

## 2. Obtener Proveedor por ID

**Endpoint:** `GET /api/v1/providers/{id}`

**Descripción:** Retorna un proveedor específico por su ID

**Parámetros Path:**
- `id` (requerido): ID del proveedor

**Ejemplo de Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/providers/1"
```

**Ejemplo de Response (200 OK):**
```json
{
  "success": true,
  "message": "Proveedor encontrado",
  "data": {
    "id": 1,
    "name": "Proveedor A",
    "companyName": "Proveedor A S.A.",
    "address": "Av. Siempre Viva 123"
  },
  "timestamp": "2025-10-16T13:00:00"
}
```

**Ejemplo de Response (404 Not Found):**
```json
{
  "success": false,
  "message": "No se encontró proveedor con ID: 999",
  "data": null,
  "timestamp": "2025-10-16T13:00:00"
}
```

---

## 3. Crear Nuevo Proveedor

**Endpoint:** `POST /api/v1/providers`

**Descripción:** Crea un nuevo proveedor validando que el nombre no esté duplicado

**Body (JSON):**
```json
{
  "name": "Proveedor B",
  "companyName": "Proveedor B S.A.",
  "address": "Calle Principal 456"
}
```

**Validaciones:**
- `name`: Obligatorio, 3-100 caracteres
- `companyName`: Obligatorio, 3-200 caracteres
- `address`: Obligatorio, 10-300 caracteres
- `name` debe ser único (case-insensitive)

**Ejemplo de Request:**
```bash
curl -X POST "http://localhost:8080/api/v1/providers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Proveedor B",
    "companyName": "Proveedor B S.A.",
    "address": "Calle Principal 456"
  }'
```

**Ejemplo de Response (201 Created):**
```json
{
  "success": true,
  "message": "Proveedor creado exitosamente",
  "data": {
    "id": 2,
    "name": "Proveedor B",
    "companyName": "Proveedor B S.A.",
    "address": "Calle Principal 456"
  },
  "timestamp": "2025-10-16T13:00:00"
}
```

**Ejemplo de Response (409 Conflict - Duplicado):**
```json
{
  "success": false,
  "message": "Ya existe un proveedor con el nombre: Proveedor B",
  "data": null,
  "timestamp": "2025-10-16T13:00:00"
}
```

**Ejemplo de Response (400 Bad Request - Validación):**
```json
{
  "success": false,
  "message": "Error de validación en los campos",
  "data": {
    "name": "El nombre debe tener entre 3 y 100 caracteres",
    "address": "La dirección debe tener entre 10 y 300 caracteres"
  },
  "timestamp": "2025-10-16T13:00:00"
}
```

---

## 4. Eliminar Proveedor

**Endpoint:** `DELETE /api/v1/providers/{id}`

**Descripción:** Elimina un proveedor por su ID

**Parámetros Path:**
- `id` (requerido): ID del proveedor a eliminar

**Ejemplo de Request:**
```bash
curl -X DELETE "http://localhost:8080/api/v1/providers/1"
```

**Ejemplo de Response (200 OK):**
```json
{
  "success": true,
  "message": "Proveedor eliminado exitosamente",
  "data": null,
  "timestamp": "2025-10-16T13:00:00"
}
```

**Ejemplo de Response (404 Not Found):**
```json
{
  "success": false,
  "message": "No se encontró proveedor con ID: 999",
  "data": null,
  "timestamp": "2025-10-16T13:00:00"
}
```

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 400 | Bad Request - Error de validación |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - Conflicto (ej: nombre duplicado) |
| 500 | Internal Server Error - Error del servidor |

---

## Ejecutar la Aplicación

### Requisitos
- Java 17 o superior
- Maven 3.6+ (o usar el wrapper incluido `./mvnw`)

### Compilar
```bash
./mvnw clean compile
```

### Ejecutar Tests
```bash
./mvnw test
```

### Ejecutar la Aplicación
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

## Estructura del Proyecto

```
src/main/java/com/gapsi/ecommerce/
├── GapsiEcommerceBackendApplication.java  # Punto de entrada
├── config/                                 # Configuraciones
│   ├── CorsConfig.java
│   └── OpenApiConfig.java
├── controller/                            # Controladores REST
│   └── ProviderController.java
├── service/                               # Lógica de negocio
│   ├── ProviderService.java
│   └── ProviderServiceImpl.java
├── repository/                            # Acceso a datos
│   └── JsonDatabase.java
├── model/                                 # Entidades
│   └── Provider.java
├── dto/                                   # Data Transfer Objects
│   ├── ApiResponse.java
│   ├── ProviderRequestDto.java
│   └── ProviderResponseDto.java
├── mapper/                                # Conversión Entity-DTO
│   └── ProviderMapper.java
└── exceptions/                            # Excepciones personalizadas
    ├── GlobalExceptionHandler.java
    ├── ProviderNotFoundException.java
    └── DuplicateProviderException.java
```

---

## Base de Datos JSON

El archivo `bd.json` en la raíz del proyecto contiene los datos de proveedores:

```json
[
  {
    "id": 1,
    "name": "Proveedor A",
    "companyName": "Proveedor A S.A.",
    "address": "Av. Siempre Viva 123"
  }
]
```

**Características:**
- IDs auto-incrementales
- Thread-safe con `ReadWriteLock`
- Pretty-printing para legibilidad

---

## Testing con cURL

### Script completo de pruebas

```bash
# 1. Listar proveedores (paginado)
curl -X GET "http://localhost:8080/api/v1/providers?page=0&size=10"

# 2. Crear nuevo proveedor
curl -X POST "http://localhost:8080/api/v1/providers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Proveedor Nuevo",
    "companyName": "Proveedor Nuevo S.A. de C.V.",
    "address": "Calle Ficticia No. 123, Colonia Centro"
  }'

# 3. Obtener proveedor por ID
curl -X GET "http://localhost:8080/api/v1/providers/2"

# 4. Intentar crear proveedor duplicado (debe fallar)
curl -X POST "http://localhost:8080/api/v1/providers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Proveedor Nuevo",
    "companyName": "Otra Empresa",
    "address": "Otra Dirección 456"
  }'

# 5. Eliminar proveedor
curl -X DELETE "http://localhost:8080/api/v1/providers/2"

# 6. Verificar que fue eliminado (debe retornar 404)
curl -X GET "http://localhost:8080/api/v1/providers/2"
```

---

## Documentación Interactiva (Swagger)

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva en:

```
http://localhost:8080/swagger-ui.html
```

Desde ahí podrás:
- Ver todos los endpoints disponibles
- Probar los endpoints directamente desde el navegador
- Ver los esquemas de datos (DTOs)
- Ver ejemplos de request/response

---

## Contacto y Soporte

Para reportar problemas o sugerencias, contactar al equipo de desarrollo.
