# Foro Hub 💬

## Descripción del Proyecto

Foro Hub es una API REST diseñada para gestionar tópicos de un foro, construida con Java y el framework **Spring Boot**. La aplicación permite a los usuarios registrarse, crear, leer, actualizar y eliminar tópicos. Para garantizar la seguridad, se ha implementado un sistema de autenticación basado en **JWT (JSON Web Tokens)**. Este proyecto fue desarrollado como parte del desafío de backend de Alura Latam.

---

## Tecnologías Utilizadas

-   **Java**: Lenguaje de programación.
-   **Spring Boot**: Framework para el desarrollo de APIs.
-   **Spring Data JPA**: Para la persistencia de datos.
-   **Spring Security**: Para la autenticación y autorización.
-   **JWT (JSON Web Tokens)**: Para la seguridad de la API.
-   **Flyway**: Para la gestión de migraciones de la base de datos.
-   **MySQL**: Base de datos relacional.
-   **Maven**: Gestor de dependencias.
-   **Lombok**: Para reducir el código repetitivo.

---

## Funcionalidades

-   **Autenticación**: Los usuarios pueden iniciar sesión para obtener un token JWT, que es necesario para acceder a los endpoints protegidos.
-   **Gestión de Tópicos (CRUD)**:
    -   **`POST /topics`**: Crear un nuevo tópico (requiere token).
    -   **`GET /topics`**: Listar todos los tópicos con paginación y ordenamiento.
    -   **`GET /topics/{id}`**: Ver los detalles de un tópico específico.
    -   **`PUT /topics/{id}`**: Actualizar un tópico (requiere token).
    -   **`DELETE /topics/{id}`**: Eliminar un tópico (requiere token).

---

## Requisitos

Asegúrate de tener instalado lo siguiente para ejecutar el proyecto:

-   **Java 17** o superior.
-   **Maven**.
-   Una base de datos **MySQL** con las credenciales de acceso.

---

## Instalación y Configuración

1.  **Clona el repositorio**:
    ```bash
    git clone [https://aws.amazon.com/es/what-is/repo/](https://aws.amazon.com/es/what-is/repo/)
    cd foro-hub
    ```

2.  **Configura la base de datos**:
    -   Crea una base de datos con el nombre `foro_hub`.
    -   Abre `src/main/resources/application.properties` y configura las credenciales de tu base de datos:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
        spring.datasource.username=root
        spring.datasource.password=tu_contraseña
        ```

3.  **Ejecuta la aplicación**:
    -   Las migraciones de Flyway se ejecutarán automáticamente al iniciar la aplicación, creando las tablas necesarias.
    -   Desde tu terminal, navega a la raíz del proyecto y ejecuta:
    ```bash
    mvn spring-boot:run
    ```

---

## Endpoints de la API

Usa una herramienta como Postman o Insomnia para interactuar con la API.

### 1. Autenticación

-   **`POST /login`**
    -   **Descripción**: Autentica a un usuario y devuelve un token JWT para futuras solicitudes.
    -   **Cuerpo de la solicitud (JSON)**:
        ```json
        {
          "login": "correo@ejemplo.com",
          "password": "tu_contraseña"
        }
        ```

### 2. Tópicos

-   **`POST /topics`**
    -   **Descripción**: Crea un nuevo tópico.
    -   **Encabezado**: `Authorization: Bearer <tu_token_jwt>`
    -   **Cuerpo de la solicitud (JSON)**:
        ```json
        {
          "titulo": "Título del nuevo tópico",
          "mensaje": "Contenido del mensaje.",
          "autorId": 1,
          "cursoId": 1
        }
        ```
-   **`GET /topics`**
    -   **Descripción**: Obtiene la lista de tópicos.
    -   **Ejemplo de URL con paginación y ordenamiento**:
        `http://localhost:8080/topics?size=10&page=0&sort=fechaCreacion,desc`

-   **`GET /topics/{id}`**
    -   **Descripción
