# 🧙‍♂️ **VariDex: Gestor de Varitas Mágicas**

**VariDex** es una aplicación móvil para el universo de Harry Potter diseñada para catalogar y gestionar las varitas mágicas de distintos personajes. Bajo la premisa *"Todas las varitas, en un solo lugar"*, la app combina un diseño mágico con un motor técnico avanzado basado en el consumo de APIs.

## 📘 **Acerca de este proyecto**

Desarrollado para la asignatura de **Programación multimedia y de dispositivos móviles**. Este proyecto marca un hito importante en mi aprendizaje, ya que da el salto de las aplicaciones con almacenamiento local a la arquitectura **Cliente-Servidor**. El frontend de la aplicación (Android/Kotlin) se diseñó para comunicarse bidireccionalmente con un backend propio desarrollado en **Spring Boot** y **Hibernate ORM**, demostrando mi capacidad para integrar aplicaciones móviles con bases de datos externas mediante servicios REST.

## 🔧 **Funcionalidades y Características**

- **Integración con API REST:** Lógica de red implementada para realizar peticiones HTTP al servidor backend (Spring Boot), permitiendo consultar (GET) la lista completa de varitas en la base de datos y enviar (POST) nuevos registros.
- **Creación de Entidades:** Formulario detallado para registrar nuevas varitas, capturando datos estructurados como la Madera, el Núcleo, la Longitud, el Personaje propietario y un control booleano (*checkbox*) para saber si la varita está rota.
- **Microanimaciones Temáticas:** Cuidado extremo en los detalles visuales dinámicos (UI/UX) para hacer la app más inmersiva:
  - En la pantalla de inicio, la llama de la varita del personaje cuenta con una animación continua.
  - En el formulario de creación, un rastro de huellas recorre la pantalla de forma animada, inspirándose en el famoso *Mapa del Merodeador*.

## 💻 **Tecnologías y Entorno**

- **Frontend**: Kotlin (Android)
- **Backend (API)**: Java, Spring Boot, Hibernate ORM
- **Entorno de pruebas / Emulador**: Medium Phone
- **Enfoque destacado**: Consumo de APIs, arquitectura cliente-servidor, diseño de animaciones por código y temática inmersiva.

## ⚙️ **Estado del proyecto**

Finalizado. Proyecto educativo que cumple con los objetivos de integración de APIs y programación de interfaces animadas en Android. *(Nota: La visualización de la lista de gestión de varitas requiere levantar el servidor Spring Boot local).*

<br><br><br>
<p align="center">
  <img src="https://github.com/user-attachments/assets/d62b1c64-47e6-411b-8958-4bbad443bbfa" width="250" alt="Pantalla de Inicio VariDex" />
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/dad2bac8-a424-4cdc-a605-3fcee4fff90a" width="250" alt="Formulario de Creación de Varitas" />
</p>

```
