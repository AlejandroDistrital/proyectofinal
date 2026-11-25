# Bitácora de Proceso - Proyecto SGR26G05

> **Estudiante:** Luis Alejandro Salguero Toloza  
> **Código:** `20261005116`  
> **Curso:** Programación Orientada a Objetos (POO) - 2026-III  
> **Proyecto:** Sistema de Gestión de Conflictos Domésticos del Reino Unido de Suegristán (`SGR26G05`)  
> **Semilla Asignada:** `datos_prueba_SGR26G05-03.csv`

---

### [2026-11-05 17:40] - Sesión de Desarrollo: Estructura Inicial de Jerarquía de Actores, Núcleo Familiar y Estados de Conflicto
* **Archivos Modificados:** `proyecto_final/src/main/java/com/sgr/actorFamiliar/ActorFamiliar_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/descendientePolitico/DescendientePolitico_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/descendientePolitico/tipo/Nuera_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/descendientePolitico/tipo/Yerno_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/suegra/Suegra_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/suegra/tipo/SuegraClasica_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/suegra/tipo/SuegraDigital_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/suegra/tipo/SuegraFinanciera_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/actorFamiliar/suegra/tipo/SuegraOmnipresente_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/nucleoFamiliar/NucleoFamiliar_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/nucleoFamiliar/conflictos/EstadoConflicto_SGR26G05.java`, `proyecto_final/src/main/java/com/sgr/nucleoFamiliar/conflictos/ValidadorConflicto_SGR26G05.java`
* **Requisito Asociado:** RF-01, RF-02, RF-03, RF-04, RF-05, RF-07, RF-10
* **Decisión Técnica:** Implementación del esqueleto de dominio orientado a objetos para modelar la jerarquía de actores familiares mediante la clase base abstracta `ActorFamiliar_SGR26G05`, ramificando hacia `Suegra_SGR26G05` (y sus subtipos Clásica, Digital, Financiera y Omnipresente) y `DescendientePolitico_SGR26G05` (`Nuera_SGR26G05` y `Yerno_SGR26G05`). Definición de `NucleoFamiliar_SGR26G05` con listas de descendientes y composición de suegra, así como enums preliminares para estados de conflicto (`EstadoConflicto_SGR26G05`) y catálogo (`ValidadorConflicto_SGR26G05`).
* **Error / Incidencia Encontrada:** Durante la auditoría técnica se detectó una declaración incompleta (`private List`) en `NucleoFamiliar_SGR26G05.java:13` que interrumpe la compilación, ausencia de encabezado obligatorio institucional (Política 12.1), convención de 3 líneas en blanco entre métodos públicos pendiente, y herencia de subtipos de suegra hacia `SuegraClasica_SGR26G05` en lugar de extender directamente a `Suegra_SGR26G05`.
* **Corrección Aplicada:** Identificada por auditoría técnica para subsanación directa del estudiante.

---

### [2026-11-24 12:00] - Sesión de Desarrollo: Corrección de Jerarquías, Composición, Máquina de Estados y Validación de Catálogo
* **Archivos Modificados:** `ActorFamiliar_SGR26G05.java`, `Parentesco_SGR26G05.java`, `ParticipanteSistema_SGR26G05.java`, `DescendientePolitico_SGR26G05.java`, `Nuera_SGR26G05.java`, `Yerno_SGR26G05.java`, `Suegra_SGR26G05.java`, `SuegraClasica_SGR26G05.java`, `SuegraDigital_SGR26G05.java`, `SuegraFinanciera_SGR26G05.java`, `SuegraOmnipresente_SGR26G05.java`, `NucleoFamiliar_SGR26G05.java`, `ValidadorConflicto_SGR26G05.java`, `ConflictoDomestico_SGR26G05.java`, `EstadoConflicto_SGR26G05.java`, `CategoriaInvalidaExcepcion_SGR26G05.java`, `TransicionInvalidaExcepcion_SGR26G05.java`
* **Requisito Asociado:** RF-01, RF-02, RF-03, RF-04, RF-05, RF-07, RF-09, RF-10, RD-3, RD-4
* **Decisión Técnica:** Corrección de la herencia directa de los cuatro subtipos de suegra hacia `Suegra_SGR26G05`; reemplazo de `ValidadorConflicto_SGR26G05` por clase con normalización lexicográfica (tildes, mayúsculas y guiones) compatible con el CSV; implementación de la máquina de estados en `ConflictoDomestico_SGR26G05`; gestión de resiliencia (`-5` por conflicto activo, `+10` por resuelto, fuga doméstica en `0` y salida en `>= 20`) en `DescendientePolitico_SGR26G05`; y cálculo del ICD en `Suegra_SGR26G05` y `SuegraOmnipresente_SGR26G05` (`1 + 0.1 * r`).
* **Error / Incidencia Encontrada:** En la ejecución inicial de pruebas unitarias, el constructor de `NucleoFamiliar_SGR26G05` asignaba `idNucleoAsignado` a la suegra antes de validar la cardinalidad de descendientes (`1..4`), dejando mutada la referencia cuando la validación fallaba. Además, `-Xlint:all` reportó advertencias `possible 'this' escape` por invocación de métodos sobreescribibles desde constructores y campos declarados con interfaces `List`/`Map` en clases `Serializable`.
* **Corrección Aplicada:** Se incorporó `validarComposicionCompleta()` antes de mutar referencias en `NucleoFamiliar_SGR26G05`, se delegó la inicialización de constructores a métodos privados de validación y se declararon las colecciones internas serializables con tipos concretos (`ArrayList`, `HashMap`), alcanzando compilación sin advertencias.

---

### [2026-11-24 12:06] - Sesión de Desarrollo: Mediación Polimórfica (Strategy), Inmutabilidad, Ranking, Persistencia `.dat`, CSV y Reportes
* **Archivos Modificados:** `Mediador_SGR26G05.java`, `MediadorDiplomatico_SGR26G05.java`, `MediadorAutoritario_SGR26G05.java`, `MediadorTerapeutico_SGR26G05.java`, `EstrategiaAsignacionMediador_SGR26G05.java`, `EstrategiaMayorTasaExito_SGR26G05.java`, `EstrategiaPorCategoria_SGR26G05.java`, `SistemaMediacion_SGR26G05.java`, `ResultadoMediacion_SGR26G05.java`, `RankingToxicidad_SGR26G05.java`, `RegistroNacional_SGR26G05.java`, `Persistencia_SGR26G05.java`, `CargadorDatosCSV_SGR26G05.java`, `GeneradorReportes_SGR26G05.java`
* **Requisito Asociado:** RF-06, RF-08, RF-11, RF-12, RD-1, RD-6, RNF-2
* **Decisión Técnica:** Implementación del pool de mediadores y patrón Strategy intercambiable en tiempo de ejecución en `SistemaMediacion_SGR26G05`; diseño de `ResultadoMediacion_SGR26G05` con atributos `private final` sin setters donde `rectificarVeredicto(String)` retorna una nueva instancia (conciliando RD-1 y RNF-2); ordenamiento descendente por `Comparable` dual (`Suegra_SGR26G05` y `RankingToxicidad_SGR26G05`) con reporte explícito de empate; carga integral del archivo semilla `datos_prueba_SGR26G05-03.csv` (10 suegras, 8 actores, 4 núcleos, 35 conflictos y 6 mediaciones); serialización binaria `.dat` con `ObjectOutputStream`/`ObjectInputStream` y generación de los 4 archivos de informe en texto plano.
* **Error / Incidencia Encontrada:** Al procesar las filas `CONFLICTO` de `datos_prueba_SGR26G05-03.csv`, el cargador leyó inicialmente la columna `6` (`valor`, vacía en filas de conflicto) en lugar de la columna `4`, produciendo intensidad `0` y disparando `IllegalArgumentException` en `ConflictoDomestico_SGR26G05`.
* **Corrección Aplicada:** Se ajustó `CargadorDatosCSV_SGR26G05.procesarConflicto()` para extraer la intensidad desde la columna `4`, verificando en ejecución CLI la carga de los 35 conflictos, el triunfo de Doña Blanca (`SG04`, ICD `5,67`) y el registro de `AF03` (`Y3`) en fuga doméstica.

---

### [2026-11-24 21:05] - Sesión de Desarrollo: Alineación con Entrega 1 (`ProxyAccesoMAF_SGR26G05`, Índices O(1)) y Pruebas JUnit 5
* **Archivos Modificados:** `ProxyAccesoMAF_SGR26G05.java`, `ServicioConsultas_SGR26G05.java`, `ServicioConsultasReal_SGR26G05.java`, `AccesoDenegadoExcepcion_SGR26G05.java`, `RegistroNacional_SGR26G05.java`, `PruebasSistema_SGR26G05Test.java`
* **Requisito Asociado:** RD-2, RD-5, Sección 9
* **Decisión Técnica:** Alineación del código con las decisiones arquitectónicas documentadas en la Entrega 1: implementación del proxy inverso bajo `ProxyAccesoMAF_SGR26G05` con control estricto por rol (familiares restringidos a su núcleo, suegras sin acceso al censo de fuga doméstica y mediadores limitados a conflictos asignados a su gestión) e indexación mediante `HashMap` en `RegistroNacional_SGR26G05` para consultas O(1).
* **Error / Incidencia Encontrada:** La revisión contra la Entrega 1 evidenció divergencia en el nombre de la clase proxy y permisos demasiado amplios para el rol `MEDIADOR` sobre núcleos ajenos, así como ausencia de aserciones explícitas sobre los 8 actores creados desde el CSV en las pruebas.
* **Corrección Aplicada:** Se consolidó `ProxyAccesoMAF_SGR26G05` con `consultarConflictosAsignados()` y filtrado por mediador en `consultarHistorialNucleo()`, y se amplió `PruebasSistema_SGR26G05Test` con 12 pruebas unitarias incluyendo verificación de los 8 actores del CSV, contenido de los 4 reportes y prueba de escala RD-5 (5 000 suegras, 15 000 descendientes y 100 000 conflictos).

---

### [2026-11-24 21:18] - Sesión de Desarrollo: Simplificación de Interfaz JavaFX y Depuración Final para Entrega E4
* **Archivos Modificados:** `InterfazGrafica_SGR26G05.java`, `Main.java`, `pom.xml`, `README.md`, `.gitignore` y depuración en paquetes de `com.sgr.*`
* **Requisito Asociado:** RNF-1, RNF-3, Política 12.1
* **Decisión Técnica:** Reducción de `InterfazGrafica_SGR26G05` exclusivamente a los tres elementos exigidos en RNF-1 (`BarChart` horizontal de ranking, diagrama de estados por conflicto con animación `FadeTransition` y dashboard de estadísticas por núcleo) y simplificación general del código hacia estructuras directas (`for` por índice, `if/else` y `Collections.sort` mediante `Comparable`), eliminando métodos, constructores vacíos y clases auxiliares ajenas a los requisitos.
* **Error / Incidencia Encontrada:** La versión previa contenía controles no solicitados en la interfaz gráfica y métodos auxiliares sin uso que incrementaban la complejidad innecesariamente frente a la rúbrica de sustentación.
* **Corrección Aplicada:** Se eliminó todo el código no requerido, confirmando tras la última edición compilación limpia con `-Xlint:all` (37 clases principales + 1 de pruebas, 0 advertencias), `12/12` pruebas JUnit 5 aprobadas en `0.569 s` y auditoría RNF-3 sin incidencias (`TOTAL_AUDIT_ISSUES=0`).
