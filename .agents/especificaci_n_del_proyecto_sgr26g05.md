# Universidad Distrital Francisco José de Caldas
## Facultad de Ingeniería
### Ingeniería Electrónica

**Proyecto Final**  
**Sistema de Gestión de Conflictos Domésticos del Reino Unido de Suegristán**  
*Programación Orientada a Objetos*

| Parámetro | Valor |
| :--- | :--- |
| **Código de proyecto:** | SGR26G05 |
| **Periodo:** | 2026-III |
| **Docente:** | Roberto A. Pava-Díaz, Ph.D. |
| **Modalidad:** | Individual |
| **Peso:** | 30% de la nota del curso |
| **Inicio:** | 14 de octubre de 2026 |
| **Entrega final:** | 24 de noviembre de 2026 |
| **Sustentación:** | 25 y 26 de noviembre de 2026 |

> **Indicación:** todos los nombres de clases del proyecto llevan el sufijo `_SGR26G05`. Por ejemplo: `Suegra_SGR26G05`, `ConflictoDomestico_SGR26G05`, `RankingToxicidad_SGR26G05`.

---

## Índice

1. Contexto y título (pág. 3)
2. Resultados de aprendizaje (pág. 3)
3. Parámetros del sistema (pág. 3)
4. Archivos de datos por estudiante (pág. 4)
5. Cronograma (pág. 4)
6. Requisitos funcionales (pág. 4)
   * 6.1. RF-01 Actor familiar abstracto (pág. 5)
   * 6.2. RF-02 Validación de categorías (pág. 5)
   * 6.3. RF-03 Jerarquía de suegras (pág. 5)
   * 6.4. RF-04 Núcleo familiar por composición (pág. 6)
   * 6.5. RF-05 Encapsulamiento y accesores (pág. 6)
   * 6.6. RF-06 Ranking de toxicidad (pág. 6)
   * 6.7. RF-07 Máquina de estados del conflicto (pág. 7)
   * 6.8. RF-08 Mediadores polimórficos y sistema de mediación (pág. 7)
   * 6.9. RF-09 Índice de conflictividad doméstica (pág. 7)
   * 6.10. RF-10 Yernos y nueras con resiliencia (pág. 8)
   * 6.11. RF-11 Persistencia (pág. 8)
   * 6.12. RF-12 Generador de reportes (pág. 9)
7. Requisitos no funcionales (pág. 9)
8. Restricciones de diseño (pág. 9)
   * 8.1. RD-1 Inmutabilidad del resultado (pág. 9)
   * 8.2. RD-2 Proxy inverso (pág. 10)
   * 8.3. RD-3 Validación antes de registrar (pág. 10)
   * 8.4. RD-4 Sustitución de Liskov (pág. 10)
   * 8.5. RD-5 Escala (pág. 10)
   * 8.6. RD-6 Estrategia de asignación (pág. 10)
9. Datos de prueba y pruebas unitarias (pág. 10)
10. Sustentación individual (pág. 11)
    * 10.1. Defensa (pág. 11)
    * 10.2. Cambio en vivo (pág. 11)
    * 10.3. Preparación (pág. 12)
11. Rúbrica de evaluación (pág. 13)
12. Políticas de entrega e integridad (pág. 14)
    * 12.1. Encabezado obligatorio (pág. 14)
    * 12.2. Declaración de uso de inteligencia artificial (pág. 14)
    * 12.3. Bitácora de proceso (pág. 14)
    * 12.4. Anexo: errores detectados en el enunciado (pág. 15)
    * 12.5. Entregas (pág. 15)

---

## 1. Contexto y título

Diseñar e implementar en Java un sistema de gestión de conflictos domésticos para el Reino Unido de Suegristán, una monarquía parlamentaria ficticia. Su Ministerio de Armonía Familiar (MAF) mantiene un registro nacional de suegras, nueras y yernos, y calcula cada trimestre el Índice de Conflictividad Doméstica (ICD) de cada suegra. La suegra con el ICD más alto al cierre del año fiscal recibe la Orden del Rodillo de Oro.

Se modeló un sistema de gestión de incidentes: registro de actores, clasificación de eventos, flujos de estado, indicadores agregados y reportes. Eso permite ejercitar herencia, polimorfismo y encapsulamiento sobre un dominio conocido.

El diseño sigue el Patrón de Mediación Doméstica Adaptativa (PMDA) propuesto por Suégrez-Martínez y Nuera-Balcázar (Suégrez-Martínez & Nuera-Balcázar, 2023). Cada conflicto es un objeto con estado mutable, y su resolución se delega a un mediador polimórfico elegido en tiempo de ejecución.

---

## 2. Resultados de aprendizaje

* **RA-1:** Modelar un problema del mundo real mediante abstracción: clases, atributos, métodos y relaciones.
* **RA-2:** Implementar jerarquías de clases en Java con herencia, constructores, métodos abstractos y sobreescritura.
* **RA-3:** Aplicar polimorfismo dinámico y estático en la clasificación de conflictos y la selección de mediadores.
* **RA-4:** Gestionar el estado de los objetos mediante encapsulamiento, validación y manejo de excepciones.
* **RA-5:** Aplicar principios SOLID y el estándar de codificación del curso.
* **RA-6:** Usar herramientas de IA de forma ética y transparente, y explicar el código entregado.

---

## 3. Parámetros del sistema

Los umbrales son iguales para todos los estudiantes. Lo que cambia por estudiante es el archivo de datos de prueba.

| Parámetro | Valor | Uso |
| :--- | :--- | :--- |
| Intensidad de un conflicto | entero de 1 a 10 | RF-02, RF-09 |
| Factor de reincidencia | $1 + 0,1 \times r$ | RF-09 |
| Resiliencia inicial de yernos y nueras | entero de 0 a 100 | RF-10 |
| Descuento por conflicto activo | 5 unidades | RF-10 |
| Recuperación por conflicto resuelto | 10 unidades | RF-10 |
| Umbral de fuga doméstica | resiliencia igual a 0 | RF-10 |
| Recuperación mínima para salir de fuga | 20 unidades | RF-10 |

---

## 4. Archivos de datos por estudiante

Cada estudiante recibe el archivo de datos de su número de lista. Cada archivo contiene 10 suegras, 4 núcleos familiares, 35 conflictos y 6 resultados de mediación. El programa debe cargar el archivo de su semilla con el nombre `datos_prueba_SGR26G05-NN.csv`.

| Estudiante | Archivo | Estudiante | Archivo |
| :---: | :--- | :---: | :--- |
| 01 | `...-SGR26G05-01.csv` | 10 | `...-SGR26G05-10.csv` |
| 02 | `...-SGR26G05-02.csv` | 11 | `...-SGR26G05-11.csv` |
| 03 | `...-SGR26G05-03.csv` | 12 | `...-SGR26G05-12.csv` |
| 04 | `...-SGR26G05-04.csv` | 13 | `...-SGR26G05-13.csv` |
| 05 | `...-SGR26G05-05.csv` | 14 | `...-SGR26G05-14.csv` |
| 06 | `...-SGR26G05-06.csv` | 15 | `...-SGR26G05-15.csv` |
| 07 | `...-SGR26G05-07.csv` | 16 | `...-SGR26G05-16.csv` |
| 08 | `...-SGR26G05-08.csv` | 17 | `...-SGR26G05-17.csv` |
| 09 | `...-SGR26G05-09.csv` | | |

Los archivos están en la carpeta `datos/`. Modificar o agregar datos para reemplazar el archivo asignado invalida la verificación de la entrega final.

---

## 5. Cronograma

| Entrega | Fecha | Contenido |
| :---: | :--- | :--- |
| **E1** | viernes 23 de octubre | Diagrama UML v1, jerarquía de actores y máquina de estados del conflicto |
| **E2** | viernes 30 de octubre | Código v0.1: jerarquías de actores y conflictos |
| **E3** | viernes 13 de noviembre | Versión completa preliminar: mediación, ranking, persistencia y pruebas JUnit |
| **E4** | martes 24 de noviembre | Versión final, informe, README, UML final y anexo de errores |
| **S** | miércoles 25 y jueves 26 de noviembre | Sustentación individual y cambio en vivo |

---

## 6. Requisitos funcionales

Cada requisito tiene un identificador, un enunciado, un criterio de aceptación, un ejemplo y una línea de alcance. Los ejemplos usan IDs que no existen en los archivos de datos. Lo que no aparece aquí no es requisito. Si una decisión no está resuelta en el enunciado, debe justificarse en E1.

### 6.1. RF-01 Actor familiar abstracto
* **Enunciado:** Todo actor familiar se registra con nombre completo, edad, parentesco (suegra, nuera o yerno), años de convivencia y nivel de paciencia inicial (entero de 0 a 100). La clase `ActorFamiliar_SGR26G05` es abstracta y es la raíz de la jerarquía.
* **Criterio de aceptación:** No es posible instanciar `ActorFamiliar_SGR26G05` directamente. El sistema rechaza una paciencia fuera de 0 a 100.
* **Ejemplo:** Se registra Suegra con paciencia 101: se rechaza. Se registra una `Nuera_SGR26G05` con paciencia 60: se acepta.
* **Alcance:** No se pide autenticación ni interfaz de registro.

### 6.2. RF-02 Validación de categorías
* **Enunciado:** La clase `ValidadorConflicto_SGR26G05` acepta solo las categorías del catálogo oficial: *gastronómico*, *territorial*, *ceremonial*, *financiero*, *tecnológico*, *vestimentario* y *cría de nietos*. Una categoría fuera del catálogo lanza `CategoriaInvalidaExcepcion_SGR26G05`, que indica la categoría rechazada y la fecha del reporte.
* **Criterio de aceptación:** La excepción se lanza antes de registrar el conflicto, y el mensaje contiene la categoría y la fecha.
* **Ejemplo:** Un conflicto de categoría "diplomático" se rechaza con la excepción. Un conflicto de categoría "ceremonial" se acepta.
* **Alcance:** El catálogo es fijo. No se pide edición del catálogo desde la interfaz.

### 6.3. RF-03 Jerarquía de suegras
* **Enunciado:** Se modelan al menos cuatro tipos de suegra, todos subclases de `Suegra_SGR26G05`: `SuegraClasica_SGR26G05` (presencial, tácticas de cocina y comentarios pasivo-agresivos), `SuegraDigital_SGR26G05` (opera por mensajería y redes sociales), `SuegraFinanciera_SGR26G05` (controla mediante préstamos y herencias condicionadas) y `SuegraOmnipresente_SGR26G05` (combina las anteriores).
* **Criterio de aceptación:** Cada subclase sobreescribe al menos un método de `Suegra_SGR26G05`, y el polimorfismo funciona: una lista de `Suegra_SGR26G05` puede contener los cuatro tipos.
* **Ejemplo:** Una `SuegraDigital_SGR26G05` puede pasarse a un método que recibe `Suegra_SGR26G05` y ejecuta su comportamiento propio.
* **Alcance:** El comportamiento de cada tipo es libre, siempre que sea distinto.

### 6.4. RF-04 Núcleo familiar por composición
* **Enunciado:** La clase `NucleoFamiliar_SGR26G05` contiene exactamente una suegra, entre 1 y 4 nueras o yernos y un historial de conflictos. La relación es de composición: el núcleo crea o recibe sus integrantes y no los comparte con otro núcleo.
* **Criterio de aceptación:** El sistema rechaza un núcleo sin suegra, con más de una suegra o con 0 o más de 4 nueras o yernos.
* **Ejemplo:** Un núcleo con una suegra y cinco yernos se rechaza. Un núcleo con una suegra y dos nueras se acepta.
* **Alcance:** No se modelan parentescos adicionales.

### 6.5. RF-05 Encapsulamiento y accesores
* **Enunciado:** Todos los atributos son privados. Los accesos siguen la convención get/set del estándar de codificación del curso. Ningún atributo es público.
* **Criterio de aceptación:** Un atributo público en cualquier clase del proyecto implica no cumplir este requisito. Los set validan el valor antes de asignarlo.
* **Ejemplo:** `setIntensidad(11)` en un `ConflictoDomestico_SGR26G05` lanza una excepción, porque la intensidad es de 1 a 10.
* **Alcance:** No se pide serialización de accesores ni equals personalizado.

### 6.6. RF-06 Ranking de toxicidad
* **Enunciado:** La clase `RankingToxicidad_SGR26G05` implementa `Comparable` para ordenar suegras por ICD descendente. El método `determinarGanadora()` retorna la suegra con mayor ICD global al cierre del período.
* **Criterio de aceptación:** El ranking queda ordenado por ICD descendente. Si dos suegras empatan en el primer puesto, el método lo informa en lugar de elegir una al azar.
* **Ejemplo:** Con ICD 7,0 y 9,5, la primera posición es la de 9,5. Con dos ICD de 8,0 en primer lugar, el método informa el empate.
* **Alcance:** No se pide desempate por antigüedad ni por otro criterio.

### 6.7. RF-07 Máquina de estados del conflicto
* **Enunciado:** `ConflictoDomestico_SGR26G05` tiene identificador único, fecha de reporte, categoría, intensidad (1 a 10), descripción, actores involucrados, estado y mediador asignado. Los estados son *latente*, *activo*, *en mediación*, *resuelto* y *crónico*. Las transiciones válidas son:
  * de *latente* a *activo*;
  * de *activo* a *en mediación* o a *crónico*;
  * de *en mediación* a *resuelto* o a *crónico*.
  Cualquier otra transición lanza `TransicionInvalidaExcepcion_SGR26G05`. Un conflicto resuelto no puede pasar a crónico.
* **Criterio de aceptación:** Las transiciones válidas se aplican y las inválidas lanzan la excepción, sin cambiar el estado.
* **Ejemplo:** Un conflicto en latente pasa a activo. Ese mismo conflicto no puede pasar a resuelto sin pasar por en mediación.
* **Alcance:** No se pide un diagrama automático de estados. El diagrama UML sí debe mostrar las transiciones.

### 6.8. RF-08 Mediadores polimórficos y sistema de mediación
* **Enunciado:** `SistemaMediacion_SGR26G05` administra un pool de mediadores. Se modelan al menos tres tipos: `MediadorDiplomatico_SGR26G05` (negociación suave), `MediadorAutoritario_SGR26G05` (imposición de acuerdos) y `MediadorTerapeutico_SGR26G05` (sesión de terapia familiar). Cada mediador tiene una tasa de éxito configurable y un método `mediar(ConflictoDomestico_SGR26G05 conflicto)` que retorna un `ResultadoMediacion_SGR26G05`.
* **Criterio de aceptación:** La mediación cambia el conflicto a *en mediación* y registra el resultado. Un fracaso no marca el conflicto como resuelto.
* **Ejemplo:** Un `MediadorTerapeutico_SGR26G05` con tasa 0,75 mediando un conflicto activo produce un resultado de acuerdo o de fracaso, según la tasa.
* **Alcance:** No se pide aprendizaje de las tasas.

### 6.9. RF-09 Índice de conflictividad doméstica
* **Enunciado:** El ICD de una `SuegraOmnipresente_SGR26G05` se calcula en dos pasos:
  1. Primero se suman las intensidades de todos sus conflictos activos y crónicos y se divide entre el número de esos conflictos.
  2. Después se multiplica por el factor de reincidencia $1 + 0,1 \times r$, donde $r$ es el número de conflictos que pasaron de resuelto a crónico en los últimos 6 meses.
  Para las demás suegras, el ICD es el mismo cálculo sin el factor, sobre sus propios conflictos.
* **Criterio de aceptación:** El ICD se calcula con las mismas fórmulas en todas las suegras. Una suegra sin conflictos activos ni crónicos tiene ICD 0.
* **Ejemplo:** Una suegra con tres conflictos activos de intensidades 4, 6 y 8 tiene una suma de 18 y un promedio de 6,0. Sin reincidencia, su ICD es 6,0.
* **Alcance:** No se pide actualizar el ICD en tiempo real.

### 6.10. RF-10 Yernos y nueras con resiliencia
* **Enunciado:** `Yerno_SGR26G05` y `Nuera_SGR26G05` heredan de `ActorFamiliar_SGR26G05` y tienen el atributo `nivelResiliencia` (0 a 100). Cada conflicto activo descuenta 5 unidades y cada conflicto resuelto recupera 10 unidades. Si la resiliencia llega a 0, el actor entra en estado "fuga doméstica"¹ y no participa en conflictos nuevos hasta recuperar al menos 20 unidades.
* **Criterio de aceptación:** La resiliencia nunca queda por debajo de 0 ni por encima de 100. El estado de fuga se activa y se levanta en los umbrales indicados.
* **Ejemplo:** Una nuera con resiliencia 12 y un conflicto activo nuevo queda en 7. Un segundo conflicto activo la lleva a 2, y un tercero la deja en 0, con fuga activa.
* **Alcance:** No se pide un modelo de salud emocional.

> ¹ En Suegristán, la "fuga doméstica" es un derecho constitucional consagrado en el Artículo 42 de la Carta Magna Suegril.

### 6.11. RF-11 Persistencia
* **Enunciado:** Todos los actores, núcleos, conflictos, mediadores y resultados de mediación se serializan en archivos `.dat` con `ObjectOutputStream` y se deserializan con `ObjectInputStream` al iniciar. Para cargar los datos de prueba, el programa lee el `.csv` de la semilla del estudiante y construye los objetos.
* **Criterio de aceptación:** Guardar y volver a cargar no cambia ningún atributo. Los archivos del CSV de la semilla producen el mismo ranking antes y después de la serialización.
* **Ejemplo:** Se guarda el sistema después de cargar el archivo, se cierra el programa y se vuelve a abrir. El ranking es idéntico.
* **Alcance:** No se pide base de datos ni formatos binarios distintos de `.dat`.

### 6.12. RF-12 Generador de reportes
* **Enunciado:** `GeneradorReportes_SGR26G05` produce cuatro informes en texto plano:
  * (a) ranking nacional de toxicidad por ICD,
  * (b) historial de conflictos por núcleo familiar,
  * (c) estadísticas de mediación con tasa de éxito por tipo de mediador, y
  * (d) listado de actores en estado de fuga doméstica.
* **Criterio de aceptación:** Cada informe se escribe en un archivo propio y su contenido coincide con los datos del sistema.
* **Ejemplo:** El informe (c) muestra una tasa de éxito para cada tipo de mediador que tuvo al menos una mediación.
* **Alcance:** No se piden gráficas en los reportes de texto.

---

## 7. Requisitos no funcionales

* **RNF-1:** La interfaz gráfica se construye con JavaFX. Muestra el ranking en un `BarChart` horizontal, el diagrama de estados de cada conflicto y un dashboard con estadísticas por núcleo. Las transiciones de estado se animan con un efecto de fade.
* **RNF-2:** La clase `ResultadoMediacion_SGR26G05` debe proveer el método `rectificarVeredicto(String nuevoVeredicto)` para que el MAF corrija un error administrativo.
* **RNF-3:** Convenciones obligatorias: exactamente 3 líneas en blanco entre métodos públicos; comentario de encabezado en cada archivo fuente con el formato indicado en la sección de políticas; imports ordenados (primero `java.util`, luego `java.io`, luego el paquete del proyecto).

---

## 8. Restricciones de diseño

Estas restricciones se evalúan además de los requisitos funcionales.

* **8.1. RD-1 Inmutabilidad del resultado:**  
  La clase `ResultadoMediacion_SGR26G05` es inmutable: una vez creada con el veredicto, la fecha y el mediador responsable, ninguno de sus campos puede modificarse. Se implementa sin setters y con atributos `final`.
* **8.2. RD-2 Proxy inverso:**  
  El proxy verifica si el actor que consulta (suegra, nuera, yerno o mediador) tiene permiso según su rol.
* **8.3. RD-3 Validación antes de registrar:**  
  Ningún método público distinto de un constructor registra un conflicto sin validar antes que su categoría pertenece al catálogo oficial.
* **8.4. RD-4 Sustitución de Liskov:**  
  Toda jerarquía respeta el principio de sustitución de Liskov. Una `SuegraDigital_SGR26G05` puede usarse en cualquier contexto que espere un `ActorFamiliar_SGR26G05`.
* **8.5. RD-5 Escala:**  
  El sistema funciona con 5000 suegras, 15000 nueras y yernos, y 100 000 conflictos sin degradación perceptible de rendimiento.
* **8.6. RD-6 Estrategia de asignación:**  
  La selección del mediador para cada conflicto se implementa con el patrón Strategy. Debe ser posible cambiar el algoritmo de asignación en tiempo de ejecución sin modificar `SistemaMediacion_SGR26G05`.

---

## 9. Datos de prueba y pruebas unitarias

El archivo de datos de prueba de cada estudiante contiene un catálogo de 10 suegras, 4 núcleos familiares, 35 conflictos con sus transiciones de estado y 6 resultados de mediación. Su nombre sigue el patrón `datos_prueba_SGR26G05-NN.csv`.

El formato usa `;` como separador y una cabecera común. Cada fila indica su tipo en la primera columna: `SUEGRA`, `NUCLEO`, `ACTOR`, `CONFLICTO` o `MEDIACION`. El campo extra de un conflicto contiene su historial de estados separado por `>`. El campo fecha sigue el formato ISO `AAAA-MM-DD`.

El programa debe cargar el archivo y producir el ranking de toxicidad del primer trimestre. Cualquier dato modificado o agregado para reemplazar el archivo asignado invalida la verificación de la entrega final.

Además, el estudiante debe escribir al menos cinco pruebas unitarias con JUnit 5 que verifiquen:
* Rechazo de categorías de conflicto que no pertenecen al catálogo oficial (RF-02).
* Inmutabilidad efectiva de `ResultadoMediacion_SGR26G05` (RD-1).
* Cálculo correcto del ICD de una `SuegraOmnipresente_SGR26G05` con datos conocidos (RF-09).
* Transiciones de estado válidas e inválidas de `ConflictoDomestico_SGR26G05` (RF-07).
* Decremento y recuperación correctos de `nivelResiliencia` en `Yerno_SGR26G05` (RF-10).

---

## 10. Sustentación individual

La sustentación es individual y dura entre 10 y 15 minutos. Se realiza el miércoles 25 y el jueves 26 de noviembre, en el horario que publica el docente. El orden lo define el docente.

### 10.1. Defensa
El estudiante explica la jerarquía de actores y de suegras, la máquina de estados del conflicto, el cálculo del ICD y el funcionamiento del Proxy inverso y del patrón Strategy. Debe justificar cualquier decisión de E1 a E4 y explicar con sus propias palabras el ranking que produce su programa con el archivo de su semilla.

### 10.2. Cambio en vivo
Al inicio de la sustentación, el docente sortea uno de los siguientes cambios. El estudiante lo implementa frente al docente, sobre su propio código, sin herramientas de generación de código:

* **C1. Nueva subclase de suegra:** Crear `SuegraTradicional_SGR26G05` con un comportamiento propio y añadirla al catálogo. *Verificación:* la clase compila, es sustituible por `Suegra_SGR26G05` y aparece en el ranking.
* **C2. Factor de reincidencia:** Cambiar el coeficiente 0,1 del factor $1 + 0,1 \times r$ al valor que indique el docente. *Verificación:* el ICD de una `SuegraOmnipresente_SGR26G05` cambia de forma consistente con el nuevo coeficiente.
* **C3. Nuevo mediador:** Agregar `MediadorConciliador_SGR26G05` con una tasa de éxito configurable. *Verificación:* el pool lo incluye y sus resultados aparecen en el informe (c).
* **C4. Peso de la suegra en el núcleo:** Cambiar el peso de la suegra en el cálculo del ICD agregado del núcleo al valor indicado. *Verificación:* el ICD del núcleo cambia de acuerdo con el nuevo peso.
* **C5. Nuevo método de reporte:** Agregar un método a `GeneradorReportes_SGR26G05` que liste los conflictos crónicos. *Verificación:* el listado contiene exactamente los conflictos en estado crónico.
* **C6. Umbral de fuga:** Cambiar la recuperación mínima para salir de fuga doméstica al valor indicado. *Verificación:* un actor en fuga sale de ese estado solo al alcanzar el nuevo umbral.
* **C7. Nueva categoría:** Agregar una categoría al catálogo oficial. *Verificación:* un conflicto con esa categoría se acepta y uno con categoría inexistente se rechaza.
* **C8. Desempate del ranking:** Agregar un criterio de desempate por número de conflictos cuando dos suegras empatan en ICD. *Verificación:* `determinarGanadora()` devuelve una sola suegra en el empate.
* **C9. Top de suegras:** Mostrar las tres primeras suegras del ranking en lugar de solo la ganadora. *Verificación:* la lista tiene tres entradas ordenadas por ICD descendente.
* **C10. Nueva transición:** Permitir la transición de activo a resuelto, sin pasar por en mediación. *Verificación:* la transición se acepta y las demás transiciones inválidas siguen lanzando `TransicionInvalidaExcepcion_SGR26G05`.

### 10.3. Preparación
Cada estudiante lleva su código y su informe final impreso o en PDF, junto con su diagrama UML.

---

## 11. Rúbrica de evaluación

El proyecto equivale al 30% de la nota del curso, distribuido así: Código fuente (5%), Documentación e informe (10%) y Sustentación individual (15%). Cada criterio se califica en tres niveles: Logrado, En progreso y No logrado.

### Tabla 1: Criterios de código fuente y documentación

| Criterio | Logrado | En progreso | No logrado |
| :--- | :--- | :--- | :--- |
| **Diseño OO (RF-01 a RF-10)** | Jerarquías coherentes, alta cohesión, bajo acoplamiento y principios SOLID aplicados | Jerarquía correcta con SOLID parcial | Clases sin jerarquía clara o sin diseño OO |
| **Herencia y polimorfismo** | Herencia justificada y polimorfismo dinámico funcional | Herencia correcta con polimorfismo parcial | Herencia forzada o ausente |
| **Encapsulamiento y validación** | Atributos privados y validación en los setters | Encapsulamiento correcto con validación parcial | Atributos públicos o sin encapsulamiento |
| **Persistencia y pruebas (RF-11, RD-1, JUnit)** | Serialización funcional y al menos cinco pruebas JUnit que pasan | Serialización funcional o pruebas incompletas | Sin persistencia o sin pruebas |
| **Estándar de codificación** | Cumple todas las convenciones | Cumple las convenciones principales con pocas excepciones | Múltiples violaciones de las convenciones |
| **Compilación** | Sin advertencias | Hasta dos advertencias explicables | Errores de compilación |

### Tabla 2: Criterios de documentación

| Criterio | Logrado | En progreso | No logrado |
| :--- | :--- | :--- | :--- |
| **Informe técnico** | Completo: problema, diseño OO, implementación y pruebas | Completo con redacción mejorable o secciones incompletas | Incompleto o ausente |
| **Diagrama UML** | Correcto, completo y coherente con el código | Inconsistencias menores con el código | Incompleto o ausente |
| **Comentarios y Javadoc** | Encabezado y Javadoc en clases y métodos públicos | Presentes pero escasos | Sin comentarios |
| **README** | Compilación, ejecución y dependencias claras | Parcial | Ausente |
| **Bitácora y declaración de IA** | Bitácora coherente con el código; declaración completa con prompts y correcciones | Bitácora o declaración incompletas | Ausente o contradice el código |

### Tabla 3: Criterios de sustentación individual

| Criterio | Logrado | En progreso | No logrado |
| :--- | :--- | :--- | :--- |
| **Dominio del diseño OO** | Explica jerarquías, herencia, polimorfismo y composición con precisión | Explica la mayoría de los puntos con precisión | No puede explicar el diseño |
| **Modificación en vivo** | Realiza el cambio sorteado de forma autónoma | Realiza el cambio con guía del docente | No logra realizar el cambio |
| **Depuración** | Identifica y corrige errores de herencia o casting | Identifica el error y lo corrige con apoyo | No identifica errores |
| **Principios SOLID** | Identifica y argumenta al menos tres principios | Identifica dos principios | No menciona principios SOLID |
| **Autonomía cognitiva** | Comprende a fondo su código y argumenta alternativas | Comprende lo esencial, con dudas en detalles | Depende totalmente de herramientas de IA |

---

## 12. Políticas de entrega e integridad

### 12.1. Encabezado obligatorio
Todo archivo fuente debe iniciar con el comentario:  
`// POO-2026III-UD | SGR26G05 | {Número de lista} | {CódigoEstudiante} {NombreEstudiante}`

### 12.2. Declaración de uso de inteligencia artificial
Toda asistencia de modelos generativos debe declararse en el informe final. La declaración incluye la herramienta usada, los prompts o instrucciones enviados, la parte del código o del informe a la que se aplicaron y las correcciones que el estudiante hizo o descartó. Una declaración incompleta se califica como si no se hubiera declarado. Si no se usó ninguna herramienta, el informe lo indica de forma expresa. No se permite presentar código generado íntegramente por IA. La sustentación verifica la autoría y la comprensión.

### 12.3. Bitácora de proceso
La bitácora se entrega solo con la versión final (E4). Contiene una entrada por cada decisión relevante, con la fecha, la decisión tomada, el error encontrado y la corrección aplicada. Se evalúa la coherencia entre la bitácora y el código final.

### 12.4. Anexo: errores detectados en el enunciado
*(Sección reservada para registrar observaciones o discrepancias detectadas formalmente durante el desarrollo).*

### 12.5. Entregas
No se reciben entregas fuera de la fecha publicada en el cronograma.

---

## Referencias

Suégrez-Martínez, D. E., & Nuera-Balcázar, R. d. P. (2023). *The Adaptive Domestic Mediation Pattern: An Object-Oriented Framework for Household Conflict Resolution Systems* [ISBN 978-987-8801-33-2]. Actas del 1er Simposio Internacional de Ingeniería de Software para Dinámicas Familiares (SISDF 2023), 44-67.