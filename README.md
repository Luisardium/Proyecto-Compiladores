# Analizador Léxico con JFlex y Java 

Este proyecto es un Analizador Léxico desarrollado en Java utilizando la herramienta **JFlex**. Cuenta con una interfaz gráfica construida con Java Swing que permite analizar código fuente, separando e identificando tokens válidos y reportando errores léxicos de manera detallada.

##  Características Principales

* **Interfaz Gráfica (GUI):** Ventana intuitiva para ingresar el código a analizar.
* **Clasificación de Tokens:** Identifica palabras reservadas, identificadores, números, cadenas, símbolos y operadores.
* **Tabla de Errores Independiente:** Detecta y separa errores léxicos específicos como:
  * Identificadores que exceden la longitud permitida (máx. 10 caracteres).
  * Números fuera de rango (mayores a 100).
  * Cadenas que no cumplen con los requisitos del lenguaje (contener "asdfg").
  * Símbolos o caracteres desconocidos.

---

##  Requisitos Previos

* **Java Development Kit (JDK):** Versión 8 o superior.
* **IDE Recomendado:** IntelliJ IDEA (o Apache NetBeans / Eclipse).
* **Librería JFlex:** El archivo `.jar` se encuentra incluido en los archivos de este repositorio.

---

##  Configuración e Instalación (¡IMPORTANTE!)

Para que el proyecto compile y funcione correctamente, **es obligatorio vincular la librería JFlex a tu entorno de desarrollo**. 

Si utilizas **IntelliJ IDEA**, sigue estos pasos:

1. Clona o descarga este repositorio y ábrelo en IntelliJ.
2. Ve al menú superior: `File` -> `Project Structure...` (o presiona `Ctrl + Alt + Shift + S`).
3. En el panel izquierdo, selecciona **Modules**.
4. Ve a la pestaña **Dependencies** (a la derecha).
5. Haz clic en el botón **`+`** (Add) y selecciona **JARs or directories...**.
6. Busca en las carpetas de este proyecto el archivo `jflex-1.x.x.jar` incluido, selecciónalo y haz clic en **Apply** y luego en **OK**.

---

##  Cómo Ejecutar el Proyecto

### Iniciar la Interfaz Gráfica
1. Abre el archivo `FrmPrincipal.java`.
2. Ejecuta el método `main` de esta clase.
3. Se abrirá la ventana del analizador. Escribe tu código de prueba, presiona el botón "Analizar" y observa los resultados divididos en Tokens Válidos y Tabla de Errores.

---

##  Estructura del Código

* `Lexer.flex`: Archivo de reglas léxicas (Expresiones regulares).
* `Tokens.java`: Enum que contiene las categorías de los tokens y los tipos de errores.
* `Main.java`: Script para compilar el archivo `.flex` y generar la clase Java.
* `FrmPrincipal.java`: Lógica y diseño de la interfaz gráfica.
* `Archivo.txt`: Archivo temporal utilizado para la lectura de datos entre la GUI y el Lexer.

---
*Desarrollado para la clase de Compiladores por Luis Morán.*
