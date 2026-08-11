# Backend JuanData

Backend del proyecto JuanData desarrollado con Spring Boot, Java 21, Maven y PostgreSQL.

## Clonar el proyecto

```bash
git clone https://github.com/barojas-gif/Backend-JuanData.git
cd Backend-JuanData
```

## Requisitos

Para ejecutar el proyecto se necesita:

- Java 21
- PostgreSQL
- Git

## Configurar la base de datos

Crear una base de datos en PostgreSQL, por ejemplo:

```sql
CREATE DATABASE JuanData;
```

Luego copiar el archivo de ejemplo:

```bash
copy src\main\resources\application-dev.properties.example src\main\resources\application-dev.properties
```

Abrir `src\main\resources\application-dev.properties` y cambiar los datos de conexion:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/JuanData?prepareThreshold=0
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

Tambien se deben completar estos valores:

```properties
spring.mail.username=tu-correo@gmail.com
spring.mail.password=tu-app-password-de-gmail
jwt.secret=GENERA_UN_SECRET_LARGO_Y_ALEATORIO
app.cors.allowed-origins=http://localhost:4200
```

## Ejecutar el proyecto

En Windows:

```bash
mvnw.cmd spring-boot:run
```

El backend queda disponible en:

```bash
http://localhost:8080
```

Si el proyecto no inicia, revisar que PostgreSQL este encendido, que la base de datos exista y que el usuario y la contrasena sean correctos.
