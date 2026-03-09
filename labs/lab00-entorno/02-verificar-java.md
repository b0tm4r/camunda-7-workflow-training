# Verificar instalación de Java

## 🎯 Objetivo

Comprobar que el entorno dispone de **Java 17**, versión requerida para ejecutar la aplicación Spring Boot utilizada en el curso.

---

## 🧠 Contexto

Camunda 7 y Spring Boot necesitan una **JVM (Java Virtual Machine)** para ejecutar la aplicación.

En este curso utilizaremos:

```
Java 17
```

Antes de continuar es necesario verificar que Java está instalado correctamente en el entorno. En **Codespaces** suele venir Java 17 preinstalado; este paso confirma que el entorno es el esperado y evita fallos al compilar más adelante.

---

## 🔎 Verificar versión de Java

Abre una **terminal** en VS Code (menú **Terminal** → **New Terminal**, o atajo **Ctrl+ñ** / **Ctrl+`** según tu sistema). En la terminal ejecuta:

```bash
java -version
```

---

## 📌 Resultado esperado

El comando debería mostrar una salida similar a:

```text
openjdk version "17.x.x"
OpenJDK Runtime Environment
OpenJDK 64-Bit Server VM
```

Lo importante es que aparezca:

```
version "17"
```

---

## 🔎 Verificar el compilador Java

También es recomendable comprobar que el compilador está disponible.

Ejecutar:

```bash
javac -version
```

---

## 📌 Resultado esperado

```text
javac 17.x.x
```

---

## ⚠️ Si la versión no es correcta

Si la versión mostrada **no es Java 17** (por ejemplo 11, 8 o 21), debes instalar o configurar Java 17 **antes de seguir**:

- **GitHub Codespaces**: normalmente ya viene con Java 17. Si ves otra versión, abre el fichero `.devcontainer` o las opciones de Codespaces del repo y selecciona una imagen con **Java 17** (consulta la documentación de tu organización si aplica).
- **Linux (Ubuntu/Debian)**: puedes instalar OpenJDK 17 con:

  ```bash
  sudo apt update
  sudo apt install -y openjdk-17-jdk
  ```

  Después vuelve a ejecutar `java -version` y `javac -version` para confirmar que ya estás en la versión 17.
- **Otros sistemas (Windows, macOS, otras distros)**: instala **Java 17 (JDK)** desde una distribución oficial (por ejemplo Temurin, Oracle JDK u OpenJDK) y asegúrate de que el `PATH` apunta a esa versión. Si trabajas en un entorno corporativo, sigue la guía estándar de tu equipo para instalar Java 17.


---

## ✅ Comprobación final

Si los siguientes comandos funcionan correctamente:

```bash
java -version
javac -version
```

y muestran **Java 17**, el entorno está listo para continuar con el siguiente paso.
