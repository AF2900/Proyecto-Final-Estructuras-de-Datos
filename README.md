# Proyecto Final - Estructuras de Datos

## Simulador de Monedero Virtual con Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la Universidad del Quindío.

El sistema simula el funcionamiento de un monedero virtual avanzado en el que los clientes pueden administrar uno o varios monederos, realizar diferentes tipos de transacciones, acumular puntos, obtener rangos, canjear beneficios, programar operaciones, recibir notificaciones, analizar patrones de gasto y generar reportes.

Durante el desarrollo se aplicaron diferentes estructuras de datos como listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios de búsqueda, árboles AVL, listas circulares y grafos dirigidos.

---

## Integrantes

- Federico Cruz
- Johan Steven Alvarez
- Adrián Fernando Pérez Naranjo

---

## Tecnologías utilizadas

- text 21
- Maven
- IntelliJ IDEA
- Git
- GitHub

---

# Objetivo

Desarrollar una aplicación que simule un monedero virtual avanzado, utilizando diferentes estructuras de datos para resolver de forma adecuada las necesidades del sistema.

El proyecto permite trabajar con operaciones financieras básicas y funcionalidades adicionales como sistema de puntos, rangos de clientes, beneficios, transacciones programadas, análisis de gastos, notificaciones, multimonedero, grafos y verificación recursiva de integridad.

---

# Funcionalidades principales

El sistema permite:

- Registrar clientes.
- Crear múltiples monederos por cliente.
- Realizar depósitos.
- Realizar retiros.
- Realizar transferencias entre clientes.
- Realizar transferencias entre monederos del mismo cliente.
- Registrar gastos por categorías.
- Consultar saldos.
- Consultar historial de transacciones.
- Revertir la última transacción mediante una pila.
- Acumular puntos por transacciones.
- Recalcular puntos después de una reversión.
- Calcular automáticamente el rango de cada cliente.
- Canjear puntos por beneficios.
- Programar depósitos.
- Programar transferencias.
- Crear transacciones recurrentes.
- Procesar transacciones según su fecha.
- Generar notificaciones automáticas.
- Gestionar notificaciones pendientes mediante una cola.
- Mantener un historial circular de notificaciones.
- Analizar relaciones entre clientes mediante grafos.
- Analizar relaciones entre monederos.
- Analizar patrones entre categorías de gasto.
- Verificar recursivamente la integridad de las transacciones.
- Generar reportes individuales.
- Generar reportes generales.
- Interactuar con el sistema mediante una interfaz de consola.

---

# Arquitectura general

El proyecto se organizó separando las responsabilidades entre modelos, gestores, estructuras de datos e interfaz.

```text
Main
 │
 ▼
InterfazConsola
 │
 ▼
MonederoVirtual
 │
 ├── GestorClientes
 ├── GestorTransacciones
 ├── GestorReversiones
 ├── GestorPuntos
 ├── GestorProgramadas
 ├── GestorNotificaciones
 ├── GestorGrafos
 ├── GestorPatronesGasto
 ├── GestorIntegridad
 ├── GestorBeneficios
 └── GestorReportes
        │
        ▼
Estructuras de datos
```

La clase `MonederoVirtual` funciona como fachada o coordinador principal.

Esto permite que la interfaz no tenga que comunicarse directamente con cada gestor y mantiene separada la lógica del sistema.

---

# Estructura del proyecto

```text
co.edu.uniquindio.poo
│
├── Main.text
├── MonederoVirtual.text
│
├── enums
│   ├── CategoriaGasto.text
│   ├── RangoCliente.text
│   ├── TipoBeneficio.text
│   ├── TipoNotificacion.text
│   └── TipoTransaccion.text
│
├── model
│   ├── BeneficioCanjeado.text
│   ├── Cliente.text
│   ├── Monedero.text
│   ├── Notificacion.text
│   ├── ReporteCliente.text
│   ├── ReporteSistema.text
│   ├── Transaccion.text
│   └── TransaccionProgramada.text
│
├── estructuras
│   │
│   ├── arbol
│   │   ├── ArbolAVLClientes.text
│   │   ├── ArbolBinarioPuntos.text
│   │   ├── NodoAVLCliente.text
│   │   └── NodoArbolPuntos.text
│   │
│   ├── grafo
│   │   ├── AristaGrafo.text
│   │   └── GrafoDirigido.text
│   │
│   └── listacircular
│       ├── ListaCircular.text
│       └── NodoCircular.text
│
├── service
│   ├── GestorBeneficios.text
│   ├── GestorClientes.text
│   ├── GestorGrafos.text
│   ├── GestorIntegridad.text
│   ├── GestorNotificaciones.text
│   ├── GestorPatronesGasto.text
│   ├── GestorProgramadas.text
│   ├── GestorPuntos.text
│   ├── GestorReportes.text
│   ├── GestorReversiones.text
│   └── GestorTransacciones.text
│
└── ui
    └── InterfazConsola.text
```

---

# Modelo principal

## Cliente

La clase `Cliente` representa a una persona registrada en el sistema.

Cada cliente contiene:

- Identificación.
- Nombre.
- Puntos.
- Rango.
- Uno o varios monederos.
- Historial de transacciones.

Los monederos se almacenan utilizando un `HashMap`.

El historial utiliza una lista de transacciones.

---

## Monedero

La clase `Monedero` representa una cuenta de dinero perteneciente a un cliente.

Cada monedero contiene:

- Identificador.
- Nombre.
- Saldo.

Un cliente puede administrar varios monederos.

Ejemplo:

```text
Cliente C001
│
├── M001 - Principal
├── M002 - Ahorros
└── M003 - Gastos diarios
```

Cada monedero permite realizar depósitos y retiros respetando las validaciones de saldo.

---

## Transacción

La clase `Transaccion` representa una operación realizada dentro del sistema.

Los tipos implementados son:

```text
DEPOSITO
RETIRO
TRANSFERENCIA
GASTO
```

Cada transacción almacena información como:

- Código.
- Fecha.
- Tipo.
- Monto.
- Identificación del cliente origen.
- Monedero origen.
- Identificación del cliente destino.
- Monedero destino.
- Puntos generados.
- Categoría de gasto.
- Categoría anterior.
- Estado de reversión.

Las transacciones revertidas no se eliminan del historial.

Se mantienen almacenadas y se identifican mediante:

```text
revertida = true
```

Esto permite conservar la trazabilidad de las operaciones.

---

# Sistema de puntos

Los puntos se generan dependiendo del tipo y monto de la transacción.

```text
Depósito
1 punto por cada 100 unidades.

Retiro
2 puntos por cada 100 unidades.

Transferencia
3 puntos por cada 100 unidades.
```

Por ejemplo:

```text
Depósito de 1000
→ 10 puntos

Retiro de 1000
→ 20 puntos

Transferencia de 1000
→ 30 puntos
```

Los gastos no generan puntos porque el enunciado no establece una regla de puntos para este tipo de operación.

---

# Rangos de clientes

Cada cliente posee un rango calculado según la cantidad de puntos acumulados.

| Rango | Puntos |
|---|---:|
| Bronce | 0 - 500 |
| Plata | 501 - 1000 |
| Oro | 1001 - 5000 |
| Platino | Más de 5000 |

Cuando los puntos aumentan o disminuyen, el rango se actualiza automáticamente.

Esto puede ocurrir después de:

```text
Realizar una transacción
Revertir una transacción
Canjear un beneficio
```

---

# Canje de beneficios

El sistema permite utilizar los puntos acumulados para obtener beneficios.

| Puntos | Beneficio |
|---:|---|
| 100 | Reducción del 10% en la comisión por transferencias |
| 500 | Un mes sin cargos por retiros |
| 1000 | Bono de 50 unidades en el monedero |

El beneficio de 1000 puntos aplica directamente el bono de 50 unidades al monedero seleccionado.

Los beneficios de transferencia y retiro quedan registrados dentro del historial de beneficios del cliente.

No se definieron valores monetarios base para las comisiones de transferencias o retiros, por lo que el sistema no introduce tarifas arbitrarias.

---

# Estructuras de datos utilizadas

## ArrayList

Se utiliza principalmente para almacenar colecciones que deben mantenerse disponibles para recorridos.

Uno de sus usos principales es el historial de transacciones de cada cliente.

```text
List<Transaccion>
```

Complejidad aproximada:

```text
Agregar al final: O(1) amortizado
Acceso por índice: O(1)
Búsqueda lineal: O(n)
```

---

## HashMap

Se utiliza cuando se necesita acceder rápidamente a información mediante una clave.

Ejemplos dentro del proyecto:

```text
Identificación → Cliente

Id del monedero → Monedero

Identificación → Puntos

Código → Transacción programada

Cliente → Beneficios

Cliente → Notificaciones
```

Complejidad promedio:

```text
put()          O(1)
get()          O(1)
containsKey()  O(1)
```

---

# Pila de transacciones reversibles

Las transacciones reversibles se almacenan en:

```text
Deque<Transaccion>
```

utilizando:

```text
ArrayDeque
```

La pila funciona utilizando el principio:

```text
LIFO
Last In, First Out
```

Es decir, la última transacción registrada es la primera que puede revertirse.

Ejemplo:

```text
TOPE
 │
 ▼
Transferencia 3
Retiro 2
Depósito 1
```

Operaciones utilizadas:

```text
push()
peek()
pop()
```

Complejidad:

```text
push() O(1)
peek() O(1)
pop()  O(1)
```

---

# Cola de notificaciones

Las notificaciones pendientes se almacenan mediante:

```text
Queue<Notificacion>
```

utilizando:

```text
ArrayDeque
```

Su funcionamiento es:

```text
FIFO
First In, First Out
```

Ejemplo:

```text
N1 → N2 → N3
↑
primera en ser atendida
```

Operaciones utilizadas:

```text
offer()
peek()
poll()
```

Complejidad:

```text
offer() O(1)
peek()  O(1)
poll()  O(1)
```

---

# Cola de prioridad

Las transacciones programadas se almacenan mediante:

```text
PriorityQueue<TransaccionProgramada>
```

Cada transacción programada implementa `Comparable`.

El orden se determina utilizando primero:

```text
Fecha de ejecución
```

y en caso de tener la misma fecha:

```text
Código de la transacción
```

Esto permite mantener siempre al frente la operación que debe ejecutarse primero.

Complejidad:

```text
offer() O(log n)
poll()  O(log n)
peek()  O(1)
```

Eliminar una transacción específica mediante `remove(Object)` puede requerir:

```text
O(n)
```

---

# Lista circular

Se implementó una lista circular genérica propia:

```text
ListaCircular<T>
NodoCircular<T>
```

Su principal uso es conservar el historial de notificaciones.

Su funcionamiento puede representarse de esta manera:

```text
N1 → N2 → N3
↑         │
└─────────┘
```

El último nodo vuelve a apuntar al primero.

Esto permite recorrer las notificaciones continuamente.

La lista circular y la cola tienen responsabilidades diferentes:

```text
ListaCircular
→ conserva el historial completo.

Queue
→ conserva únicamente las pendientes.
```

Cuando una notificación es atendida:

```text
Queue
→ la notificación sale.

ListaCircular
→ la notificación permanece.
```

---

# Árbol binario de búsqueda

La clase:

```text
ArbolBinarioPuntos
```

mantiene los clientes ordenados según su cantidad de puntos.

Cuando dos clientes poseen la misma cantidad de puntos se utiliza la identificación como criterio de desempate.

El recorrido inorden permite obtener:

```text
clientes ordenados de menor a mayor cantidad de puntos
```

Complejidad:

```text
Caso promedio:
buscar / insertar / eliminar → O(log n)

Peor caso:
O(n)

Recorrido inorden:
O(n)
```

El peor caso puede ocurrir cuando el árbol queda desbalanceado.

---

# Árbol AVL

También se implementó un árbol balanceado:

```text
ArbolAVLClientes
```

El AVL mantiene controlada su altura utilizando rotaciones.

Se implementaron los casos:

```text
Izquierda - Izquierda
Derecha - Derecha
Izquierda - Derecha
Derecha - Izquierda
```

Complejidad:

```text
Buscar   O(log n)
Insertar O(log n)
Eliminar O(log n)
```

Cuando los puntos de un cliente cambian se realiza el siguiente proceso:

```text
Cliente en árbol
      ↓
Eliminar utilizando puntos anteriores
      ↓
Actualizar puntos
      ↓
Actualizar rango
      ↓
Insertar nuevamente
```

Esto evita que el orden del árbol quede incorrecto después de modificar la cantidad de puntos.

---

# Grafos dirigidos

El sistema implementa:

```text
GrafoDirigido
```

utilizando una lista de adyacencia.

Cada vértice contiene las conexiones que salen desde él.

Ejemplo:

```text
C001
 ├────▶ C002
 └────▶ C003
```

Cada arista conserva:

- Destino.
- Monto total acumulado.
- Cantidad de transacciones.

---

## Grafo de clientes

Representa las transferencias realizadas entre clientes.

Ejemplo:

```text
C001 ──1000──▶ C002
C001 ── 400──▶ C003
```

Permite consultar:

- Monto transferido entre clientes.
- Cantidad de transferencias.
- Total transferido por un cliente.
- Principal destino.
- Relaciones existentes.
- Recorrido del grafo.

---

## Grafo de monederos

Representa las relaciones entre monederos.

Ejemplo:

```text
C001:M001 ───▶ C001:M002
```

También registra relaciones entre monederos pertenecientes a diferentes clientes.

Ejemplo:

```text
C001:M001 ───▶ C002:M001
```

Esto permite trabajar con el requisito de multimonedero.

---

## Grafo de categorías de gasto

Las categorías utilizadas en los gastos también generan relaciones dentro de un grafo dirigido.

Ejemplo:

```text
ALIMENTACION
      │
      ▼
TRANSPORTE
      │
      ▼
ENTRETENIMIENTO
```

Esto permite identificar patrones entre categorías consecutivas.

---

# Recorrido BFS

El grafo implementa recorrido en anchura:

```text
BFS
Breadth First Search
```

Para realizarlo se utiliza una cola:

```text
Queue<String>
```

y un conjunto de vértices visitados:

```text
Set<String>
```

La complejidad del recorrido es:

```text
O(V + E)
```

donde:

```text
V = cantidad de vértices
E = cantidad de aristas
```

El BFS también permite comprobar si existe un camino entre dos clientes.

---

# Gastos

El sistema permite registrar gastos utilizando diferentes categorías.

```text
ALIMENTACION
TRANSPORTE
ENTRETENIMIENTO
SERVICIOS
SALUD
EDUCACION
COMPRAS
OTROS
```

El sistema permite consultar:

- Total gastado.
- Total gastado por categoría.
- Categoría de mayor gasto.
- Última categoría utilizada.
- Relaciones entre categorías.
- Cantidad de transiciones.
- Principal categoría siguiente.

---

# Patrones de gasto

Cada vez que un cliente realiza gastos consecutivos se puede generar una relación entre las categorías.

Ejemplo:

```text
Primer gasto:
ALIMENTACION

Segundo gasto:
TRANSPORTE

Resultado:
ALIMENTACION → TRANSPORTE
```

La arista registra el monto correspondiente y la cantidad de transiciones.

Cuando un gasto se revierte mediante la pila, la información del patrón también se actualiza.

---

# Notificaciones

El sistema genera notificaciones por situaciones como:

```text
Saldo bajo
Transacción programada
Ejecución de transacción programada
Notificación general
```

Las notificaciones utilizan dos estructuras simultáneamente:

```text
ListaCircular
→ historial completo

Queue
→ pendientes
```

Esto permite demostrar el uso de diferentes estructuras sobre la misma funcionalidad con responsabilidades distintas.

---

# Alertas de saldo bajo

Después de determinadas operaciones que disminuyen el saldo se verifica si el monedero queda por debajo del límite establecido.

Para este proyecto se utilizó:

```text
100 unidades
```

Cuando el saldo queda por debajo de este valor se genera automáticamente una notificación de saldo bajo.

Este valor corresponde a una decisión de implementación.

---

# Transacciones programadas

El sistema permite programar:

```text
Depósitos
Transferencias
```

Cada transacción contiene:

- Código.
- Tipo.
- Origen.
- Destino.
- Monto.
- Fecha de ejecución.
- Frecuencia.

Cuando:

```text
frecuenciaDias = 0
```

la transacción se ejecuta una sola vez.

Cuando:

```text
frecuenciaDias > 0
```

la operación se considera recurrente.

Ejemplo:

```text
Fecha:
20/10/2026

Frecuencia:
7 días

Siguiente ejecución:
27/10/2026
```

Después de ejecutar una operación recurrente se calcula la nueva fecha y vuelve a insertarse dentro de la `PriorityQueue`.

---

# Verificación recursiva de integridad

La clase:

```text
GestorIntegridad
```

utiliza algoritmos recursivos para revisar las transacciones.

La idea general es:

```text
Transacción 0
      ↓
Transacción 1
      ↓
Transacción 2
      ↓
...
      ↓
Caso base
```

El caso base ocurre cuando:

```text
indice == historial.size()
```

En caso contrario se analiza la transacción actual y se realiza una llamada recursiva utilizando:

```text
indice + 1
```

---

# Validaciones de integridad

La verificación revisa aspectos como:

- Código válido.
- Código no repetido dentro del historial del mismo cliente.
- Fecha válida.
- Tipo válido.
- Monto mayor que cero.
- Identificación de origen.
- Monedero de origen.
- Datos de destino cuando se trata de una transferencia.
- Categoría cuando se trata de un gasto.
- Coherencia entre el tipo de transacción y los puntos generados.

También se implementaron métodos recursivos para:

```text
Verificar un historial completo.
Verificar todos los clientes.
Contar transacciones válidas.
Buscar la primera transacción inválida.
```

Complejidad aproximada para un historial de `n` elementos:

```text
Tiempo: O(n)
Memoria adicional por recursión: O(n)
```

Una transacción revertida no se considera inválida.

Permanece dentro del historial con:

```text
revertida = true
```

---

# Reversiones

Las operaciones realizadas se almacenan en una pila.

Cuando se solicita una reversión se toma la última transacción:

```text
peek()
```

se realiza la operación inversa y posteriormente se elimina de la pila:

```text
pop()
```

Dependiendo del tipo de transacción, una reversión puede modificar:

```text
Saldo
Puntos
Rango
BST
AVL
Grafo de clientes
Grafo de monederos
Patrones de gasto
```

La transacción no desaparece del historial.

Únicamente queda marcada como revertida.

---

# Reportes

El sistema posee dos reportes principales.

## Reporte de cliente

Incluye:

- Identificación.
- Nombre.
- Cantidad de monederos.
- Saldo total.
- Cantidad de transacciones.
- Transacciones activas.
- Transacciones revertidas.
- Puntos.
- Rango.
- Total de gastos.
- Categoría de mayor gasto.
- Total transferido.
- Beneficios canjeados.
- Notificaciones.
- Estado de integridad.

---

## Reporte general del sistema

Incluye:

- Cantidad de clientes.
- Cantidad de monederos.
- Saldo total.
- Cantidad de transacciones únicas.
- Transacciones activas.
- Transacciones revertidas.
- Total de gastos.
- Beneficios canjeados.
- Notificaciones.
- Estado de integridad.

Una transferencia se guarda tanto en el historial del cliente origen como en el del cliente destino.

Para evitar contarla dos veces en el reporte general se utiliza:

```text
Map<String, Transaccion>
```

utilizando el código de la transacción como clave.

---

# Interfaz de usuario

El proyecto contiene una interfaz de consola:

```text
ui/InterfazConsola.text
```

Desde ella se pueden ejecutar las principales funcionalidades del sistema.

El flujo general es:

```text
Main
  ↓
InterfazConsola
  ↓
MonederoVirtual
  ↓
Gestores
  ↓
Estructuras
```

La clase `Main` únicamente inicia la interfaz.

Esto evita colocar lógica de negocio directamente dentro del punto de entrada de la aplicación.

---

# Complejidades principales

| Operación / estructura | Complejidad |
|---|---|
| Buscar cliente en HashMap | O(1) promedio |
| Buscar monedero en HashMap | O(1) promedio |
| Consultar puntos en HashMap | O(1) promedio |
| Agregar al final de ArrayList | O(1) amortizado |
| Buscar linealmente en una lista | O(n) |
| Push en pila | O(1) |
| Peek en pila | O(1) |
| Pop en pila | O(1) |
| Offer en Queue | O(1) |
| Peek en Queue | O(1) |
| Poll en Queue | O(1) |
| Peek en PriorityQueue | O(1) |
| Offer en PriorityQueue | O(log n) |
| Poll en PriorityQueue | O(log n) |
| Remove(Object) en PriorityQueue | O(n) |
| Agregar en lista circular | O(1) |
| Obtener siguiente en lista circular | O(1) |
| BST promedio | O(log n) |
| BST peor caso | O(n) |
| AVL | O(log n) |
| BFS | O(V + E) |
| Verificación recursiva | O(n) |
| Recorrido inorden | O(n) |

---

# Pruebas

El proyecto contiene diferentes clases de prueba.

```text
PruebaBaseMonederoVirtual
PruebaReversiones
PruebaArbolesPuntos
PruebaTransaccionesProgramadas
PruebaNotificaciones
PruebaGrafos
PruebaGastos
PruebaIntegridad
PruebaBeneficios
PruebaReportes
PruebaSistemaCompleto
```

Las pruebas actuales no utilizan JUnit.

Cada clase contiene un método:

```text
public static void main(String[] args)
```

y utiliza verificaciones que lanzan:

```text
AssertionError
```

cuando una condición esperada no se cumple.

---

# Prueba integral

La clase:

```text
PruebaSistemaCompleto.text
```

realiza una prueba completa del sistema.

Durante una sola ejecución valida:

```text
Registro de clientes
Multimonedero
Depósitos
Retiros
Transferencias
Transferencias internas
Gastos
Puntos
Rangos
Beneficios
HashMap
BST
AVL
PriorityQueue
Transacciones recurrentes
Notificaciones
Queue
Lista circular
Grafos
BFS
Integridad recursiva
Pila
Reversiones
Reportes
```

En la ejecución final realizada durante el desarrollo se obtuvo:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 128
```

y el proceso terminó con:

```text
Process finished with exit code 0
```

El reporte final obtenido fue:

```text
Clientes: 3
Monederos: 4
Saldo total actual: 21000.0
Transacciones únicas: 9
Transacciones activas: 8
Transacciones revertidas: 1
Total gastos: 800.0
Beneficios canjeados: 1
Notificaciones: 4
Integridad válida: true
```

---

# Cómo ejecutar el proyecto

## Requisitos

Se necesita:

```text
text JDK 21
Maven
IntelliJ IDEA
```

---

## Abrir el proyecto

Abrir en IntelliJ IDEA la carpeta:

```text
Proyecto Final Estructuras de Datos
```

El archivo:

```text
pom.xml
```

debe ser reconocido como proyecto Maven.

---

## Compilar

Desde una terminal ubicada en la carpeta donde se encuentra `pom.xml`:

```bash
mvn clean compile
```

---

## Ejecutar el programa

Ejecutar:

```text
Main.text
```

El sistema iniciará la interfaz de consola.

---

## Ejecutar la prueba integral

Ejecutar:

```text
PruebaSistemaCompleto.text
```

La ejecución debe finalizar sin `AssertionError`.

El resultado esperado es:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 128
```

---

# Decisiones de diseño

## Puntos para gastos

Las reglas del proyecto especifican puntos para:

```text
Depósitos
Retiros
Transferencias
```

No se definió una regla para los gastos.

Por esta razón:

```text
GASTO → 0 puntos
```

---

## Beneficios asociados a comisiones

El proyecto define:

```text
100 puntos
→ 10% de reducción en comisión de transferencia.

500 puntos
→ un mes sin cargos por retiros.
```

Sin embargo, no se definieron valores monetarios base para dichas comisiones.

Por esta razón los beneficios se registran como privilegios activos, pero no se introducen tarifas monetarias arbitrarias.

---

## Bono de saldo

El beneficio de:

```text
1000 puntos
```

sí tiene un valor claramente definido:

```text
50 unidades de saldo
```

Por esta razón el sistema agrega directamente las 50 unidades al monedero seleccionado.

El bono no se registra como depósito normal, ya que esto generaría puntos nuevamente.

---

## Saldo bajo

Para poder generar automáticamente las alertas de saldo bajo se estableció como decisión de implementación un límite de:

```text
100 unidades
```

Cuando el saldo queda por debajo de este valor se genera una notificación.

---

# Uso de las estructuras de datos

Cada estructura fue utilizada de acuerdo con la necesidad que debía resolver.

```text
ArrayList
→ historial de transacciones

HashMap
→ búsquedas rápidas

Deque
→ pila de reversiones

Queue
→ notificaciones pendientes

PriorityQueue
→ transacciones programadas

BST
→ clientes ordenados por puntos

AVL
→ mantener estructura balanceada por puntos

ListaCircular
→ historial circular de notificaciones

Grafos dirigidos
→ relaciones entre clientes, monederos y categorías

Recursividad
→ verificación de integridad
```

Esto permite evitar utilizar una única estructura para resolver todos los problemas del sistema.

---

# Resultado final

El sistema integra las diferentes estructuras de datos dentro de un mismo flujo.

Por ejemplo, una transferencia puede generar simultáneamente cambios en:

```text
Saldo
Historial
Puntos
Rango
HashMap
BST
AVL
Pila de reversiones
Grafo de clientes
Grafo de monederos
```

Si posteriormente la transferencia se revierte, las estructuras relacionadas se actualizan nuevamente para conservar la coherencia del sistema.

La prueba integral permite comprobar que estos componentes continúan sincronizados después de realizar operaciones, programaciones, canjes y reversiones.

---

# Conclusión

El desarrollo del monedero virtual permitió aplicar de forma práctica diferentes estructuras estudiadas en la asignatura.

Cada estructura cumple una función específica dentro del sistema y fue seleccionada teniendo en cuenta el tipo de operación que debía realizar.

El proyecto demuestra el uso combinado de:

```text
Listas
Pilas
Colas
Colas de prioridad
Tablas Hash
Árboles binarios
Árboles AVL
Listas circulares
Grafos dirigidos
Recursividad
```

Además de implementar las operaciones básicas de un monedero, se integraron funcionalidades como puntos, rangos, beneficios, transacciones programadas, patrones de gasto, multimonedero, notificaciones, reversiones, integridad y reportes.

La prueba integral permitió verificar el funcionamiento conjunto del sistema con **128 verificaciones superadas** y un estado final de integridad válido.