# Sistema de Gestión de Conflictos Domésticos del Reino Unido de Suegristán (SGR26G05)

## Información General
* **Institución:** Universidad Distrital Francisco José de Caldas
* **Facultad:** Ingeniería Electrónica
* **Espacio Académico:** Programación Orientada a Objetos (POO) - Periodo 2026-III
* **Docente:** Roberto A. Pava-Díaz, Ph.D.
* **Autor / Desarrollador:** Luis Alejandro Salguero Toloza
* **Código Estudiantil:** `20261005116`
* **Identificador de Proyecto:** `SGR26G05`
* **Semilla Asignada:** `datos_prueba_SGR26G05-03.csv`

---

## Descripción del Proyecto
Diseño e implementación en Java del sistema de gestión de incidentes y conflictos domésticos para el Ministerio de Armonía Familiar (MAF) del Reino Unido de Suegristán, siguiendo el Patrón de Mediación Doméstica Adaptativa (PMDA).

Incluye:
* Jerarquía abstracta de actores familiares (`ActorFamiliar_SGR26G05`), suegras polimórficas (`SuegraClasica_SGR26G05`, `SuegraDigital_SGR26G05`, `SuegraFinanciera_SGR26G05`, `SuegraOmnipresente_SGR26G05`) y descendientes con resiliencia y fuga doméstica (`Nuera_SGR26G05`, `Yerno_SGR26G05`).
* Composición exclusiva en `NucleoFamiliar_SGR26G05` (1 suegra, 1 a 4 descendientes).
* Máquina de estados de `ConflictoDomestico_SGR26G05` y validación de catálogo en `ValidadorConflicto_SGR26G05`.
* Mediadores polimórficos, patrón **Strategy** en `SistemaMediacion_SGR26G05`, y resultado inmutable `ResultadoMediacion_SGR26G05`.
* Patrón **Proxy inverso** (`ProxyAccesoMAF_SGR26G05`) con control de acceso por rol.
* Persistencia binaria `.dat` con `ObjectOutputStream` / `ObjectInputStream`, carga de semilla CSV, generador de 4 reportes de texto plano e interfaz gráfica JavaFX (`InterfazGrafica_SGR26G05`).

---

## Estructura del Repositorio
```text
Proyecto_final_code/
├── .agents/                    # Especificación y configuración de auditoría
├── datos/                      # Semilla datos_prueba_SGR26G05-03.csv y enunciado PDF
├── docs/                       # Bitácora de desarrollo
└── proyecto_final/             # Proyecto Maven (Java 21+ / JavaFX 23.0.2 / JUnit 5.11.4)
    ├── pom.xml
    └── src/
        ├── main/java/com/sgr/
        │   ├── Main.java
        │   ├── actorFamiliar/
        │   ├── excepciones/
        │   ├── mediacion/
        │   ├── nucleoFamiliar/
        │   ├── persistencia/
        │   ├── proxy/
        │   ├── ranking/
        │   ├── reportes/
        │   └── ui/
        └── test/java/com/sgr/
            └── PruebasSistema_SGR26G05Test.java
```

---

## Requisitos y Dependencias
* **JDK:** Java 21 o superior (requerido por JavaFX 23.0.2; probado con JDK 21 y OpenJDK 25)
* **Gestor de Construcción:** Apache Maven 3.8+ (incluye wrapper en `~/.m2/wrapper` si no está en `PATH`)
* **Dependencias declaradas en `proyecto_final/pom.xml`:**
  * `org.openjfx:javafx-controls:23.0.2`
  * `org.junit.jupiter:junit-jupiter:5.11.4` (`test`)

---

## Compilación, Pruebas y Ejecución

Desde la carpeta `proyecto_final/`:

### Compilación (con `-Xlint:all`)
```bash
mvn compile
```

### Ejecución de Pruebas Unitarias (JUnit 5)
```bash
mvn test
```

### Ejecución en Modo Consola (CSV + Persistencia `.dat` + 4 Reportes + Ranking)
```bash
mvn exec:java -Dexec.mainClass="com.sgr.Main" -Dexec.args="--cli"
```

### Ejecución de la Interfaz Gráfica JavaFX
```bash
mvn javafx:run
```
