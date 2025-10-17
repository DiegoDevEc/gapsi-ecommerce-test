# GAPSI e-Commerce Frontend

Frontend desarrollado en React + TypeScript + PrimeReact para el sistema de gestión de proveedores.

## Tecnologías utilizadas

- **React 18** con TypeScript
- **Vite** como build tool
- **PrimeReact** para componentes UI
- **PrimeFlex** para utilidades CSS
- **React Router** para navegación
- **Axios** para llamadas HTTP

## Estructura del proyecto

```
src/
├── pages/              # Páginas de la aplicación
│   ├── WelcomePage.tsx
│   ├── WelcomePage.css
│   ├── SuppliersPage.tsx
│   └── SuppliersPage.css
├── services/           # Servicios de API
│   ├── api.ts
│   └── supplierService.ts
├── models/             # Interfaces TypeScript
│   └── Supplier.ts
├── components/         # Componentes reutilizables
├── assets/            # Recursos estáticos
└── styles/            # Estilos globales
```

## Instalación

```bash
npm install
```

## Configuración

El archivo `.env` contiene la URL del backend:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

Puedes modificar esta URL según tu configuración.

## Ejecución en desarrollo

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:5173`

## Build para producción

```bash
npm run build
```

Los archivos compilados se generarán en la carpeta `dist/`

## Pantallas

### 1. Pantalla de Bienvenida
- Logo de GAPSI en el header
- Mensaje de bienvenida personalizado
- Botón "Continuar" para ir a la gestión de proveedores
- Versión del sistema en el footer

### 2. Gestión de Proveedores
- Header con logo y título
- Tabla con lista de proveedores (paginada)
- Botones de acción:
  - Agregar elemento (azul)
  - Imprimir elemento (rojo)
- Acciones por fila:
  - Editar (ícono lápiz)
  - Eliminar (ícono basura)
- Modal para crear/editar proveedores
- Validaciones de formulario
- Confirmación de eliminación
- Notificaciones toast

## Características implementadas

- CRUD completo de proveedores
- Validación de formularios
- Confirmaciones de eliminación
- Notificaciones de éxito/error
- Diseño responsive
- Estilos modernos con gradientes
- Función de impresión
- Navegación con React Router
- Integración con backend Spring Boot

## Endpoints del backend

El frontend consume los siguientes endpoints:

- `GET /api/suppliers` - Listar todos los proveedores
- `GET /api/suppliers/{id}` - Obtener un proveedor por ID
- `POST /api/suppliers` - Crear un nuevo proveedor
- `PUT /api/suppliers/{id}` - Actualizar un proveedor
- `DELETE /api/suppliers/{id}` - Eliminar un proveedor

## Modelo de datos

```typescript
interface Supplier {
  id?: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  createdAt?: string;
  updatedAt?: string;
}
```

## Estilos

- Diseño moderno con gradientes (purple/indigo)
- Animaciones suaves
- Cards con sombras
- Botones con efectos hover
- Responsive design para móviles
- Tema PrimeReact Lara Light Indigo
