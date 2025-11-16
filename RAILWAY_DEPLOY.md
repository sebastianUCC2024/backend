# Despliegue en Railway

## Pasos para desplegar

### 1. Preparar el repositorio
Asegúrate de que todos los cambios estén commiteados en Git:
```bash
git add .
git commit -m "Preparar para despliegue en Railway"
git push
```

### 2. Crear proyecto en Railway
1. Ve a [railway.app](https://railway.app)
2. Inicia sesión con GitHub
3. Click en "New Project"
4. Selecciona "Deploy from GitHub repo"
5. Selecciona tu repositorio

### 3. Agregar base de datos PostgreSQL
1. En tu proyecto de Railway, click en "+ New"
2. Selecciona "Database" → "Add PostgreSQL"
3. Railway creará automáticamente las variables de entorno

### 4. Configurar variables de entorno
En la configuración de tu servicio, agrega estas variables:

**Variables requeridas:**
- `SPRING_PROFILES_ACTIVE=prod`
- `JWT_SECRET=tu-secreto-jwt-seguro-aqui` (genera uno nuevo y seguro)
- `OPENAI_API_KEY=tu-api-key-de-openai`

**Variables de base de datos (automáticas si usas PostgreSQL de Railway):**
- `DATABASE_URL` (Railway la crea automáticamente)
- `DB_USERNAME` (Railway la crea automáticamente)
- `DB_PASSWORD` (Railway la crea automáticamente)

Si Railway no crea automáticamente DB_USERNAME y DB_PASSWORD, puedes extraerlos de DATABASE_URL o configurarlos manualmente.

### 5. Desplegar
Railway detectará automáticamente que es un proyecto Maven y:
- Ejecutará `mvn clean package -DskipTests`
- Iniciará la aplicación con el perfil `prod`
- Usará PostgreSQL en lugar de H2

### 6. Verificar el despliegue
Una vez desplegado, Railway te dará una URL pública. Puedes probar:
- `https://tu-app.railway.app/api/health` (si tienes un endpoint de health)
- Revisa los logs en Railway para verificar que todo esté funcionando

## Notas importantes

- El perfil `prod` usa PostgreSQL en lugar de H2
- `ddl-auto` está configurado en `update` para producción (no borra datos)
- La consola H2 está deshabilitada en producción
- El puerto se configura automáticamente desde la variable `$PORT` de Railway

## Solución de problemas

Si el despliegue falla:
1. Revisa los logs en Railway
2. Verifica que todas las variables de entorno estén configuradas
3. Asegúrate de que el build de Maven sea exitoso localmente: `mvn clean package`
4. Verifica que la versión de Java sea 17 (configurada en pom.xml)
