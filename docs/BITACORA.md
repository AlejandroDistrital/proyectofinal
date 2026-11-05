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
