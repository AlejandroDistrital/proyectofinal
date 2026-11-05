# Sistema de Gestión de Conflictos Domésticos del Reino Unido de Suegristán (SGR26G05)

## 📌 Información General
* **Institución:** Universidad Distrital Francisco José de Caldas
* **Facultad:** Ingeniería Electrónica
* **Espacio Académico:** Programación Orientada a Objetos (POO) - Periodo 2026-III
* **Docente:** Roberto A. Pava-Díaz, Ph.D.
* **Autor / Desarrollador:** Luis Alejandro Salguero Toloza
* **Código Estudiantil:** `20261005116`
* **Identificador de Proyecto:** `SGR26G05`
* **Semilla Asignada:** `datos_prueba_SGR26G05-03.csv`

---

## 📖 Descripción del Proyecto
Diseño e implementación de un sistema de gestión de incidentes y conflictos familiares para el Ministerio de Armonía Familiar (MAF) del Reino Unido de Suegristán, bajo el Patrón de Mediación Doméstica Adaptativa (PMDA).

---

## 🛠️ Estructura del Repositorio
```text
Proyecto_final_code/
├── .agents/                    # Reglas, especificaciones y rol del Agente Auditor
├── datos/                      # Archivos fuente de datos y enunciado del proyecto
├── docs/                       # Bitácora de desarrollo y declaración de uso de IA
│   ├── BITACORA.md
│   └── DECLARACION_IA.md
└── proyecto_final/             # Proyecto Maven y código fuente en Java
    ├── pom.xml
    └── src/main/java/com/sgr/
        ├── Main.java
        ├── actorFamiliar/
        └── nucleoFamiliar/
```

---

## ⚙️ Requisitos y Compilación
* **JDK:** Java 21 o superior (compatible con Java 25)
* **Gestor de Construcción:** Maven 3.8+

### Compilación con Maven
```bash
cd proyecto_final
mvn clean compile
```

### Ejecución
```bash
mvn exec:java -Dexec.mainClass="com.sgr.Main"
```
