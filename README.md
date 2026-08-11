# Backend JuanData

Backend del proyecto JuanData desarrollado con Spring Boot, Maven, Java 21, Spring Security, JWT, JPA/Hibernate y PostgreSQL.

## Ubicacion del proyecto

Repositorio GitHub:

```bash
https://github.com/barojas-gif/Backend-JuanData.git
```

Ruta local donde esta ubicado actualmente:

```bash
C:\Users\brian\Documents\Repositorio-JDC\RepoJuanData
```

## Requisitos

Antes de ejecutar el proyecto se necesita tener instalado:

- Java 21
- PostgreSQL
- Git
- Maven, o usar el wrapper incluido `mvnw.cmd`

## Clonar el proyecto

```bash
git clone https://github.com/barojas-gif/Backend-JuanData.git
cd Backend-JuanData
```

## Configuracion de la base de datos local

El proyecto usa PostgreSQL. Para correrlo en local se debe crear una base de datos, por ejemplo:

```sql
CREATE DATABASE JuanData;
```

Luego se debe crear el archivo de configuracion local:

```bash
copy src\main\resources\application-dev.properties.example src\main\resources\application-dev.properties
```

Despues abrir este archivo:

```bash
src\main\resources\application-dev.properties
```

Y cambiar estos datos por los de la base de datos local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/JuanData?prepareThreshold=0
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

Tambien se deben ajustar estas variables segun el entorno:

```properties
spring.mail.username=tu-correo@gmail.com
spring.mail.password=tu-app-password-de-gmail
jwt.secret=GENERA_UN_SECRET_LARGO_Y_ALEATORIO
app.cors.allowed-origins=http://localhost:4200
```

El perfil local por defecto es `dev`, definido en:

```bash
src\main\resources\application.properties
```

## Configuracion para produccion

La configuracion de produccion esta en:

```bash
src\main\resources\application-prod.properties
```

Este archivo toma los datos desde variables de entorno. En Render, Neon u otro servidor se deben configurar variables como:

```env
SPRING_PROFILES_ACTIVE=prod
DB_HOST=ep-xxxx-pooler.region.aws.neon.tech
DB_NAME=neondb
DB_USERNAME=neondb_owner
DB_PASSWORD=TU_PASSWORD
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=tu-correo@gmail.com
MAIL_PASSWORD=tu-app-password-de-gmail
JWT_SECRET=GENERA_UN_SECRET_LARGO_Y_DIFERENTE_AL_DE_DEV
APP_CORS_ALLOWED_ORIGINS=https://tu-frontend.onrender.com
```

Hay un archivo de ejemplo para estas variables en:

```bash
.env.example
```

Nota: en Render free tier el puerto SMTP `587` puede estar bloqueado. Para correo se puede usar un proveedor como SendGrid con puerto `2525`, o usar un plan que permita SMTP.

## Ejecutar el proyecto en local

En Windows:

```bash
mvnw.cmd spring-boot:run
```

O si Maven esta instalado globalmente:

```bash
mvn spring-boot:run
```

El backend queda disponible normalmente en:

```bash
http://localhost:8080
```

## Compilar el proyecto

```bash
mvnw.cmd clean package
```

El archivo compilado queda en la carpeta:

```bash
target
```

## Estructura principal

```text
src/main/java/com/jdc/repojuandata
```

Carpetas importantes:

- `Auth`: controladores y clases para autenticacion.
- `config`: configuracion de seguridad, correo y usuarios.
- `DTO`: objetos de transferencia de datos.
- `jwt`: filtro y servicio JWT.
- `models`: entidades de base de datos.
- `repository`: repositorios JPA.
- `rest`: endpoints REST.
- `service`: logica de negocio.

Configuracion principal:

- `src/main/resources/application.properties`: configuracion general.
- `src/main/resources/application-dev.properties.example`: ejemplo para desarrollo local.
- `src/main/resources/application-dev.properties`: configuracion local real, no debe subirse con credenciales.
- `src/main/resources/application-prod.properties`: configuracion para produccion.
- `.env.example`: ejemplo de variables de entorno para despliegue.

## Subir cambios al repositorio

Despues de modificar archivos:

```bash
git status
git add .
git commit -m "Descripcion de los cambios"
git push
```

## Importante

No subir contrasenas, secrets, credenciales de correo ni datos reales de la base de datos. Usar archivos de ejemplo y variables de entorno para compartir configuraciones sin exponer informacion sensible.
