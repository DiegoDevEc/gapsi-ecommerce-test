# Desarrollo Local - GAPSI E-commerce

Este archivo contiene las instrucciones para configurar y ejecutar el proyecto en entorno de desarrollo local.

## Arquitectura

El proyecto consta de dos componentes principales:

1. **Backend** (Spring Boot 3.5.6 + Java 17)
   - Puerto: 8080
   - API REST: `http://localhost:8080/api/v1`
   - Swagger: `http://localhost:8080/swagger-ui.html`
   - IDE Recomendado: **IntelliJ IDEA**

2. **Frontend** (React 19 + Vite + TypeScript)
   - Puerto: 5173 (modo desarrollo)
   - URL: `http://localhost:5173`
   - IDE Recomendado: **Visual Studio Code**

## Requisitos Previos del Sistema

Antes de comenzar, asegúrate de tener instalado lo siguiente en tu equipo:

### Para Backend

- **Java Development Kit (JDK) 17 o superior**
  - Verifica la instalación: `java -version`
  - Descarga: [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o [OpenJDK](https://adoptium.net/)

- **Maven 3.6 o superior** (opcional, el proyecto incluye Maven Wrapper)
  - Verifica la instalación: `mvn -version`
  - Descarga: [Apache Maven](https://maven.apache.org/download.cgi)

- **IntelliJ IDEA** (Community o Ultimate)
  - Descarga: [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

### Para Frontend

- **Node.js 18 o superior** (se recomienda 20.x LTS)
  - Verifica la instalación: `node -v`
  - Descarga: [Node.js](https://nodejs.org/)

- **npm 9 o superior** (incluido con Node.js)
  - Verifica la instalación: `npm -v`

- **Visual Studio Code**
  - Descarga: [VS Code](https://code.visualstudio.com/)

### Extensiones Recomendadas para VS Code

- ESLint
- TypeScript and JavaScript Language Features
- Vite
- ES7+ React/Redux/React-Native snippets
- Prettier - Code formatter

---

## Configuración del Backend en IntelliJ IDEA

### 1. Importar el Proyecto

1. Abre IntelliJ IDEA
2. Selecciona `File` > `Open`
3. Navega hasta la carpeta `gapsi-ecommerce-backend`
4. Selecciona el archivo `pom.xml`
5. Click en `Open as Project`
6. Espera a que IntelliJ descargue las dependencias de Maven

### 2. Configurar el JDK

1. Ve a `File` > `Project Structure` (o presiona `Cmd + ;` en Mac / `Ctrl + Alt + Shift + S` en Windows/Linux)
2. En `Project Settings` > `Project`:
   - SDK: Selecciona Java 17 (o superior)
   - Language level: 17 - Sealed types, always-strict floating-point semantics
3. Click en `Apply` y `OK`

### 3. Configurar Maven

1. Ve a `File` > `Settings` (o `IntelliJ IDEA` > `Preferences` en Mac)
2. Navega a `Build, Execution, Deployment` > `Build Tools` > `Maven`
3. Verifica que el Maven home directory esté configurado correctamente
4. Click en `Apply` y `OK`

### 4. Configuración de Ejecución

#### Opción A: Usando la clase principal

1. Abre el archivo `src/main/java/com/gapsi/ecommerce/GapsiEcommerceBackendApplication.java`
2. Click derecho en la clase
3. Selecciona `Run 'GapsiEcommerceBackendApplication'`

#### Opción B: Crear una configuración personalizada

1. Ve a `Run` > `Edit Configurations`
2. Click en `+` > `Spring Boot`
3. Configura:
   - Name: `Backend - Dev`
   - Main class: `com.gapsi.ecommerce.GapsiEcommerceBackendApplication`
   - Use classpath of module: `gapsi-ecommerce-backend`
   - Environment variables (opcional):
     - `SPRING_PROFILES_ACTIVE=dev`
4. Click en `Apply` y `OK`
5. Ejecuta con `Run` > `Run 'Backend - Dev'`

### 5. Verificar el Backend

Una vez iniciado el backend, verifica que esté funcionando:

- Health Check: http://localhost:8080/api/v1/welcome
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/v3/api-docs

### 6. Hot Reload (Opcional)

El proyecto incluye Spring Boot DevTools para recarga automática:

1. Ve a `File` > `Settings` > `Build, Execution, Deployment` > `Compiler`
2. Marca `Build project automatically`
3. Ve a `Advanced Settings`
4. Marca `Allow auto-make to start even if developed application is currently running`

### 7. Ejecutar Tests

#### Desde IntelliJ:
- Click derecho en la carpeta `src/test/java`
- Selecciona `Run 'All Tests'`

#### Desde Terminal:
```bash
cd gapsi-ecommerce-backend
./mvnw test
```

---

## Configuración del Frontend en Visual Studio Code

### 1. Abrir el Proyecto

1. Abre Visual Studio Code
2. Selecciona `File` > `Open Folder`
3. Navega hasta la carpeta `gapsi-ecommerce-frontend`
4. Click en `Open`

### 2. Instalar Dependencias

Abre la terminal integrada (`Terminal` > `New Terminal` o `` Ctrl + ` ``) y ejecuta:

```bash
npm install
```

### 3. Configurar Variables de Entorno

1. Crea un archivo `.env` en la raíz del proyecto frontend (si no existe):

```bash
touch .env
```

2. Agrega las siguientes variables:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### 4. Ejecutar en Modo Desarrollo

En la terminal integrada, ejecuta:

```bash
npm run dev
```

El frontend estará disponible en: http://localhost:5173

### 5. Scripts Disponibles

```bash
# Modo desarrollo con hot reload
npm run dev

# Compilar para producción
npm run build

# Previsualizar build de producción
npm run preview

# Ejecutar linter
npm run lint
```

### 6. Configuración de Debugging

#### Para depurar en Chrome:

1. Instala la extensión `Debugger for Chrome`
2. Crea un archivo `.vscode/launch.json`:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "chrome",
      "request": "launch",
      "name": "Vite: Chrome",
      "url": "http://localhost:5173",
      "webRoot": "${workspaceFolder}/src"
    }
  ]
}
```

3. Presiona `F5` para iniciar el debugger

---

## Ejecutar Backend y Frontend Simultáneamente

### Opción 1: Usando Terminales Separadas

```bash
# Terminal 1 - Backend
cd gapsi-ecommerce-backend
./mvnw spring-boot:run

# Terminal 2 - Frontend
cd gapsi-ecommerce-frontend
npm run dev
```

### Opción 2: Usando un solo comando (requiere npm-run-all)

Puedes crear un script en la raíz del proyecto para ejecutar ambos servicios:

1. Crea un `package.json` en la raíz:

```json
{
  "name": "gapsi-ecommerce",
  "private": true,
  "scripts": {
    "dev": "concurrently \"cd gapsi-ecommerce-backend && ./mvnw spring-boot:run\" \"cd gapsi-ecommerce-frontend && npm run dev\""
  },
  "devDependencies": {
    "concurrently": "^8.0.0"
  }
}
```

2. Instala las dependencias:

```bash
npm install
```

3. Ejecuta ambos servicios:

```bash
npm run dev
```

---

## Troubleshooting

### Backend

#### Error: JAVA_HOME no configurado

**Solución para Mac/Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

**Solución para Windows:**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

#### Error: Puerto 8080 en uso

Verifica qué proceso está usando el puerto:

**Mac/Linux:**
```bash
lsof -i :8080
kill -9 <PID>
```

**Windows:**
```cmd
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

#### Error: Dependencias Maven no se descargan

```bash
cd gapsi-ecommerce-backend
./mvnw clean install -U
```

### Frontend

#### Error: Puerto 5173 en uso

Vite elegirá automáticamente el siguiente puerto disponible (5174, 5175, etc.)

#### Error: Módulos no encontrados

```bash
# Eliminar node_modules y reinstalar
rm -rf node_modules package-lock.json
npm install
```

#### Error: CORS al conectar con el backend

Verifica que el backend esté corriendo y que la URL en `.env` sea correcta:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

#### Error: TypeScript compilation errors

```bash
# Limpiar caché de TypeScript
rm -rf node_modules/.vite
npm run build
```

---

## Estructura de Archivos

### Backend
```
gapsi-ecommerce-backend/
├── src/
│   ├── main/
│   │   ├── java/com/gapsi/ecommerce/
│   │   │   ├── controller/     # REST Controllers
│   │   │   ├── service/        # Business Logic
│   │   │   ├── repository/     # Data Access
│   │   │   ├── model/          # Domain Models
│   │   │   └── dto/            # Data Transfer Objects
│   │   └── resources/
│   │       ├── application.properties
│   │       └── bd.json         # Archivo de datos
│   └── test/
├── pom.xml
└── mvnw
```

### Frontend
```
gapsi-ecommerce-frontend/
├── src/
│   ├── components/             # React Components
│   ├── services/               # API Services
│   ├── pages/                  # Page Components
│   ├── types/                  # TypeScript Types
│   ├── App.tsx
│   └── main.tsx
├── package.json
├── vite.config.ts
├── tsconfig.json
└── .env
```

---

## Recursos Adicionales

### Backend
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [IntelliJ IDEA Guide](https://www.jetbrains.com/help/idea/)
- [Maven Guide](https://maven.apache.org/guides/)

### Frontend
- [React 19 Documentation](https://react.dev/)
- [Vite Guide](https://vite.dev/guide/)
- [PrimeReact Components](https://primereact.org/)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
