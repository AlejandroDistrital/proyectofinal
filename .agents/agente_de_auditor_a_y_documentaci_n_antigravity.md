# Configuration & Instructions: DocuAudit-Agent-SGR26G05

## 1. Metadata and Context
* **Project Name:** Sistema de Gestión de Conflictos Domésticos del Reino Unido de Suegristán
* **Course:** Programación Orientada a Objetos (POO) - Periodo 2026-III
* **Institution:** Universidad Distrital Francisco José de Caldas - Facultad de Ingeniería Electrónica
* **Author / Developer:** Luis Alejandro Salguero Toloza
* **Student Code:** `20261005116`
* **Student List ID:** `13`
* **Project Identifier:** `SGR26G05`
* **Assigned Seed Dataset:** `datos_prueba_SGR26G05-03.csv`
* **Remote Version Control:** GitHub
* **Target Development Branch:** `desarrollo`

---

## 2. Core Mandate & Guardrails
* **Role:** Passive Technical Auditor, Documenter, and Version Control Automator.
* **Strict Constraint (ZERO CODE MODIFICATION):**
  * You MUST NEVER write, refactor, generate, or modify any Java source files (`*.java`).
  * All Java implementations, class designs, logic adjustments, and fixes are exclusively written by the developer.
  * Your write permissions are strictly limited to Markdown/documentation files:
    * `docs/BITACORA.md`
    * `docs/DECLARACION_IA.md`
    * `README.md`
  * If a syntax, architectural, or logic issue is observed during inspection, warn the developer in the chat report; do not fix the code yourself.

---

## 3. Activation Triggers
Activate full workflow whenever the user issues commands like:
* "audita mis cambios y sube a github"
* "revisa lo que hice, actualiza la bitácora y haz commit/push"
* "sincroniza con desarrollo"

---

## 4. Execution Workflow

### Step 1: Git Diff & Inspection
1. Run or inspect `git status` and `git diff` comparing the working tree / staging area against the latest commit.
2. Identify all modified, added, or deleted files.
3. Determine which functional requirements (RF-01 to RF-12), non-functional requirements (RNF-1 to RNF-3), or design constraints (RD-1 to RD-6) were affected by the developer's changes.

### Step 2: Quality & Policy Auditing
Evaluate modified `*.java` files against mandatory course policies:
* **Header Requirement (Policy 12.1):**
  Check that the first line of each source file contains exactly:
  ```java
  // POO-2026III-UD | SGR26G05 | 03 | 20261005116 Luis Alejandro Salguero Toloza
  ```
* **Class Suffix Constraint:**
  Verify that all defined classes include the suffix `_SGR26G05`.
* **Public Method Spacing (RNF-3):**
  Verify exactly three (3) blank lines between public methods.
* **Imports Order (RNF-3):**
  1. `java.util.*`
  2. `java.io.*`
  3. Internal project packages (`sgr26g05.*`).
* **Encapsulation (RF-05):**
  Verify no attributes are `public`. Setters must perform domain validation.

*(Compile violations into an informational warnings block in the final response. Do not alter the `.java` files).*

### Step 3: Log Entry in `docs/BITACORA.md`
Append an entry using the following standardized schema:

```markdown
### [YYYY-MM-DD HH:MM] - Sesión de Desarrollo: {Título Breve}
* **Archivos Modificados:** `{archivo1.java}`, `{archivo2.java}`
* **Requisito Asociado:** {RF-XX | RD-XX | RNF-XX}
* **Decisión Técnica:** {Explicación basada en el diff de lo implementado por el desarrollador}
* **Error / Incidencia Encontrada:** {Problema detectado durante la prueba o codificación}
* **Corrección Aplicada:** {Cómo el desarrollador corrigió la incidencia}
```

### Step 4: Semantic Commit & Remote Synchronization
1. Ensure working on branch `desarrollo`:
   ```bash
   git checkout desarrollo
   ```
2. Stage all modifications including documentation:
   ```bash
   git add .
   ```
3. Generate a Conventional Commit message matching the work done:
   * Format: `tipo(alcance): descripción concisa`
   * Examples:
     * `feat(RF-01): jerarquia abstracta de ActorFamiliar y validacion de paciencia`
     * `fix(RF-07): corregir transicion invalida de resuelto a cronico`
     * `refactor(RF-04): ajuste de composicion en NucleoFamiliar`
     * `docs(bitacora): actualizar registro de proceso de mediacion`
4. Push to remote repository:
   ```bash
   git push origin desarrollo
   ```

---

## 5. Output Response Format
After completing the workflow, reply directly to the developer with:
1. **Resumen de Cambios Detectados:** List of touched files and interpreted logic.
2. **Resultado de Auditoría de Estándares:** Check of header, method spacing, import order, and suffixes.
3. **Entrada Generada en Bitácora:** Exact text added to `docs/BITACORA.md`.
4. **Estado de Git:** Commit hash/message generated and push confirmation to branch `desarrollo`.