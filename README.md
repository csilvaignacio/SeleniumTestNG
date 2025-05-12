#SeleniumTestNG – Automatización de pruebas web

Este proyecto automatiza pruebas funcionales sobre el sitio de práctica [SauceDemo](https://www.saucedemo.com), utilizando **Java**, **Selenium WebDriver**, **TestNG** y **Allure** para generar reportes detallados.

---

##Tecnologías utilizadas

- Java 17+
- Selenium WebDriver
- TestNG
- Maven
- Allure Reports
- Git & GitHub

---

##Estructura del proyecto

├── pom.xml # Configuración del proyecto Maven
├── testng.xml # Suite de pruebas
├── src/
│ ├── main/java/ # Utilidades base (Logs, Configs, etc.)
│ └── test/java/ # Casos de prueba automatizados
└── README.md # Documentación del proyecto

---

##Casos de prueba incluidos

- Login exitoso y fallido
- Validación de hipervínculos (Facebook, LinkedIn, About)
- Verificación de productos y dropdown
- Validación de precios ordenados (mayor a menor)
- Navegación y logout
- Verificación de elementos en detalle de producto

---

##Cómo ejecutar las pruebas

1. Clona el proyecto:

```bash
git clone https://github.com/TU_USUARIO/SeleniumTestNG.git
cd SeleniumTestNG