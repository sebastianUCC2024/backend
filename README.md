# AgriGo - Backend Agrícola Inteligente

Backend completo para plataforma agrícola con IA integrada, desarrollado con Spring Boot.

## 🌟 Características Principales

- **Autenticación JWT** con roles (FARMER, STORE, BUYER, ADMIN)
- **Módulo de IA** integrado con ChatGPT-4 para recomendaciones inteligentes
- **Marketplace** para venta de productos agrícolas
- **Comparador de precios** automático entre agrotiendas
- **Gestión de cultivos** para agricultores
- **Gestión de insumos** para agrotiendas

## 🏗️ Arquitectura Modular

```
com.agrigo
├── auth              → Autenticación y seguridad JWT
├── ai                → Motor de IA con ChatGPT-4
├── farmer            → Gestión de agricultores y cultivos
├── store             → Gestión de agrotiendas e insumos
├── marketplace       → Marketplace de productos agrícolas
├── pricecomparator   → Comparador de precios
└── common            → Utilidades y configuración común
```

## 🎨 Patrones de Diseño Implementados

### Builder Pattern
- `Recommendation` - Construcción de recomendaciones de IA
- `Crop` - Construcción de cultivos
- `FarmerProduct` - Construcción de productos
- `StoreInputPrice` - Construcción de precios

### Adapter Pattern
- `ChatGPTAdapter` - Adaptador para conectar con OpenAI API

### Abstract Factory Pattern
- `AIRequestFactory` - Interfaz para factories
- `FertilizerRequestFactory` - Prompts de fertilizantes
- `PesticideRequestFactory` - Prompts de pesticidas
- `OptimizationRequestFactory` - Prompts de optimización

## 🚀 Endpoints Principales

### Autenticación
- `POST /auth/register` - Registro de usuarios
- `POST /auth/login` - Login y obtención de JWT

### Agricultores (FARMER)
- `POST /farmers/crops` - Crear cultivo
- `GET /farmers/crops` - Listar mis cultivos
- `PUT /farmers/crops/{id}` - Actualizar cultivo
- `DELETE /farmers/crops/{id}` - Eliminar cultivo

### Agrotiendas (STORE)
- `POST /stores/inputs` - Agregar insumo con precio
- `GET /stores/inputs` - Listar mis insumos
- `PUT /stores/inputs/{id}` - Actualizar precio/stock
- `DELETE /stores/inputs/{id}` - Eliminar insumo

### Marketplace (Público)
- `GET /marketplace/products` - Listar productos disponibles
- `GET /marketplace/products/{id}` - Ver detalle de producto
- `POST /marketplace/products` - Publicar producto (FARMER)
- `PUT /marketplace/products/{id}` - Actualizar producto (FARMER)

### IA (FARMER, ADMIN)
- `POST /ai/recommend` - Generar recomendación
- `GET /ai/explain/{cropId}` - Obtener explicación
- `GET /ai/recommendations/{cropId}` - Historial de recomendaciones

### Comparador de Precios (Público)
- `GET /price-comparator/compare/{inputId}` - Comparar precios de un insumo
- `GET /price-comparator/all` - Ver todas las comparaciones

## 🔧 Tecnologías

- **Spring Boot 3.5.7**
- **Spring Security** con JWT
- **Spring Data JPA**
- **H2 Database** (desarrollo)
- **PostgreSQL** (producción)
- **Lombok**
- **Maven**

## ⚙️ Configuración

### Variables de Entorno

```yaml
OPENAI_API_KEY=tu-api-key-de-openai
```

### Base de Datos

Por defecto usa H2 en memoria. Para PostgreSQL, actualiza `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/agrigo
    username: tu-usuario
    password: tu-password
```

## 🏃 Ejecutar el Proyecto

```bash
# Compilar
./mvnw clean package

# Ejecutar
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 👥 Usuarios de Prueba

El sistema carga automáticamente usuarios de prueba:

| Usuario | Password | Rol |
|---------|----------|-----|
| farmer1 | password123 | FARMER |
| store1 | password123 | STORE |
| admin | admin123 | ADMIN |

## 📊 Flujo de Uso

1. **Registro/Login** → Obtener JWT token
2. **Agricultor** → Registra cultivos → Solicita recomendaciones de IA
3. **Agrotienda** → Registra insumos con precios
4. **Sistema** → Compara precios automáticamente
5. **Agricultor** → Publica productos en marketplace
6. **Comprador** → Navega productos disponibles

## 🧪 Testing

```bash
./mvnw test
```

## 📝 Commits

El proyecto incluye 43+ commits progresivos en español, documentando cada paso del desarrollo.

## 🤝 Contribución

Este es un proyecto educativo que demuestra:
- Arquitectura modular en Spring Boot
- Implementación de patrones de diseño
- Integración con IA (ChatGPT)
- Seguridad con JWT
- API REST completa

## 📄 Licencia

Proyecto educativo - AgriGo 2024
