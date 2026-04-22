# FinanzaSana – Gestión Inteligente de Deudas

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Ktor](https://img.shields.io/badge/Ktor-087CFA?style=for-the-badge&logo=ktor&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM%20+%20Clean-green?style=for-the-badge)
![Estado](https://img.shields.io/badge/Estado-En_Desarrollo-yellow?style=for-the-badge)

> Aplicación móvil diseñada para ayudar a los usuarios a retomar el control de sus finanzas. Permite gestionar deudas, solicitar prestamos, pagar prestamos y registrar evidencia mediante herramientas avanzadas como biometría, GPS y cámara.

---

## 📋 Tabla de contenidos

- [Descripción](#-descripción)
- [Características Principales](#-características-principales)
- [Arquitectura](#-arquitectura)
- [Stack Tecnológico](#-stack-tecnológico)
- [Implementación de Hardware](#-implementación-de-hardware)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Autores](#-autores)

---

## 📖 Descripción

**FinanzaSana** es una solución integral para la salud financiera personal. La app no solo funciona como un registro de deudas, sino como un asistente inteligente que valida la identidad del usuario,permite geolocalizar los puntos de registro, asegurando un control total y seguro de la información económica.

La aplicación busca eliminar el desorden financiero mediante una interfaz moderna, reactiva y segura, centralizando la gestión de abonos y deudas en una sola plataforma móvil.

---

## ✨ Características Principales

- 🔐 **Autenticación Segura:** Registro e inicio de sesión con manejo de tokens JWT.
- 💳 **Gestión de Deudas:** Listado interactivo, visualización de detalles y registro de nuevas deudas con estado en tiempo real.
- 👤 **Panel Administrativo:** Facultad para gestionar usuarios y eliminación de usuarios.
- 🛡️ **Seguridad Biométrica:** Usa huella digital utilizando `BiometricPrompt` para acceder a listar de usuarios.
- 📸 **Evidencia Fotográfica:** Captura la INE del usuario.

- 📍 **Geolocalización:** Registro automático de la ubicación (latitud/longitud y dirección) al crear nuevas dedudas.

---

## 🏛️ Arquitectura

La aplicación sigue los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)** para garantizar escalabilidad:

* **Data Layer:** Repositorios, consumo de API con Ktor y persistencia local de sesión.
* **Domain Layer:** Casos de uso (`UseCases`) que contienen la lógica de negocio pura.
* **Presentation Layer:** UI reactiva con Jetpack Compose y ViewModels que gestionan el `StateFlow`.

---

## 🛠️ Stack tecnológico

| Capa | Tecnología |
|------|-----------|
| **Lenguaje** | Kotlin 2.x |
| **UI Framework** | Jetpack Compose (Material Theme 3) |
| **Red (API)** | Ktor HttpClient |
| **Persistencia** | Jetpack DataStore (SessionManager) |
| **Arquitectura** | MVVM + Clean Architecture |
| **Localización** | FusedLocationProvider & Geocoder |

---

## ⚙️ Implementación de Hardware

### 1. Biometría
Uso de un `BiometricManager` abstracto que utiliza `BiometricPrompt`. Esto permite verificar la identidad del usuario antes de acceder a datos sensibles sin depender directamente de las APIs del sistema en la capa de UI.

### 2. Cámara
Implementación de `AndroidCameraManager` mediante `ActivityResultContracts.TakePicturePreview()`. Facilita la captura de imágenes que sirven como evidencia de abonos o deudas.

### 3. GPS (Ubicación)
Uso de `FusedLocationProviderClient` para obtener coordenadas precisas. El sistema traduce la ubicación en una dirección legible para el usuario, facilitando el rastreo de dónde se originó la transacción.

---

## 📂 Estructura del proyecto

```text
FinanzaSana/
├── app/
│   └── src/main/java/com/finanzasana/
│       ├── data/           # Repositorios, NetworkModule y SessionManager
│       ├── domain/         # Modelos y Casos de Uso (UseCases)
│       ├── ui.theme/             # Screens (Compose) y ViewModels
│       └── di/             # Inyección de dependencias
├── build.gradle.kts
└── README.md


📍 Universidad Politécnica de Chiapas > Ingeniería en Tecnología de la Información e Innovación Digital · 2026

