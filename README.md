# Proyecto Final - Estructuras de Datos

## Simulador de Monedero Virtual con Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la Universidad del Quindío.

El sistema simula un monedero virtual en el que los clientes pueden administrar uno o varios monederos, realizar transacciones, acumular puntos, obtener rangos, canjear beneficios, programar operaciones, recibir notificaciones, analizar patrones de gasto y generar reportes.

El proyecto fue desarrollado en **Java 21** y utiliza diferentes estructuras de datos como listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios de búsqueda, árboles AVL, listas circulares y grafos dirigidos.

---

## Integrantes

- Federico Cruz
- Johan Steven Alvarez
- Adrián Fernando Pérez Naranjo

---

## Tecnologías utilizadas

- Java 21
- Maven
- IntelliJ IDEA
- Git
- GitHub

---

# Objetivo

Desarrollar un simulador de monedero virtual aplicando diferentes estructuras de datos vistas durante la asignatura.

El sistema integra operaciones financieras básicas con funcionalidades adicionales relacionadas con puntos, beneficios, rangos, transacciones programadas, notificaciones, grafos, patrones de gasto, reversiones, reportes y verificación recursiva de integridad.

---

# Funcionalidades implementadas

El sistema permite:

- Registrar clientes.
- Crear uno o varios monederos por cliente.
- Realizar depósitos.
- Realizar retiros.
- Realizar transferencias entre clientes.
- Realizar transferencias entre monederos del mismo cliente.
- Cobrar comisiones en retiros y transferencias.
- Aplicar beneficios sobre las comisiones.
- Registrar gastos por categorías.
- Consultar saldos.
- Consultar historial de transacciones.
- Acumular y descontar puntos.
- Calcular automáticamente el rango de cada cliente.
- Canjear beneficios.
- Revertir la última transacción utilizando una pila.
- Programar depósitos.
- Programar transferencias.
- Crear operaciones recurrentes.
- Procesar operaciones programadas según su fecha.
- Generar notificaciones.
- Gestionar notificaciones pendientes mediante una cola.
- Mantener un historial de notificaciones mediante una lista circular.
- Analizar transferencias mediante grafos dirigidos.
- Analizar relaciones entre monederos.
- Analizar patrones entre categorías de gasto.
- Realizar recorridos BFS.
- Verificar recursivamente la integridad de las transacciones.
- Generar reportes por cliente.
- Generar un reporte general del sistema.
- Utilizar una interfaz de consola.

---

# Arquitectura general

El proyecto separa las responsabilidades entre modelos, estructuras, gestores e interfaz.

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

`MonederoVirtual` funciona como coordinador principal del sistema.

La interfaz utiliza esta clase para acceder a las funcionalidades sin comunicarse directamente con cada uno de los gestores.

---

# Estructura del proyecto

```text
co.edu.uniquindio.poo
│
├── Main.java
├── MonederoVirtual.java
│
├── enums
│   ├── CategoriaGasto.java
│   ├── RangoCliente.java
│   ├── TipoBeneficio.java
│   ├── TipoNotificacion.java
│   └── TipoTransaccion.java
│
├── model
│   ├── BeneficioCanjeado.java
│   ├── Cliente.java
│   ├── Monedero.java
│   ├── Notificacion.java
│   ├── ReporteCliente.java
│   ├── ReporteSistema.java
│   ├── Transaccion.java
│   └── TransaccionProgramada.java
│
├── estructuras
│   │
│   ├── arbol
│   │   ├── ArbolAVLClientes.java
│   │   ├── ArbolBinarioPuntos.java
│   │   ├── NodoAVLCliente.java
│   │   └── NodoArbolPuntos.java
│   │
│   ├── grafo
│   │   ├── AristaGrafo.java
│   │   └── GrafoDirigido.java
│   │
│   └── listacircular
│       ├── ListaCircular.java
│       └── NodoCircular.java
│
├── service
│   ├── GestorBeneficios.java
│   ├── GestorClientes.java
│   ├── GestorGrafos.java
│   ├── GestorIntegridad.java
│   ├── GestorNotificaciones.java
│   ├── GestorPatronesGasto.java
│   ├── GestorProgramadas.java
│   ├── GestorPuntos.java
│   ├── GestorReportes.java
│   ├── GestorReversiones.java
│   └── GestorTransacciones.java
│
└── ui
    └── InterfazConsola.java
```

---

# Modelo principal

## Cliente

La clase `Cliente` representa a una persona registrada en el sistema.

Cada cliente contiene:

- Identificación.
- Nombre.
- Puntos acumulados.
- Rango.
- Monederos.
- Historial de transacciones.

Los monederos se almacenan mediante:

```text
Map<String, Monedero>
```

y el historial mediante:

```text
List<Transaccion>
```

---

## Monedero

La clase `Monedero` representa una cuenta asociada a un cliente.

Cada monedero contiene:

- Identificador.
- Nombre.
- Saldo.

Un cliente puede tener varios monederos.

Ejemplo:

```text
Cliente C001
│
├── M001 - Principal
└── M002 - Ahorros
```

---

## Transacción

La clase `Transaccion` representa una operación realizada por el sistema.

Los tipos implementados son:

```text
DEPOSITO
RETIRO
TRANSFERENCIA
GASTO
```

Una transacción puede almacenar:

- Código.
- Fecha.
- Tipo.
- Monto.
- Identificación de origen.
- Monedero de origen.
- Identificación de destino.
- Monedero de destino.
- Puntos generados.
- Comisión cobrada.
- Categoría de gasto.
- Categoría anterior.
- Estado de reversión.

La comisión se almacena dentro de la misma transacción para poder conocer exactamente cuánto se cobró y devolver ese valor en caso de reversión.

Las transacciones revertidas permanecen en el historial y quedan identificadas mediante:

```text
revertida = true
```

---

# Sistema de puntos

Los puntos se calculan sobre el **monto de la operación**, sin incluir la comisión.

Las reglas implementadas son:

```text
Depósito:
1 punto por cada 100 unidades.

Retiro:
2 puntos por cada 100 unidades.

Transferencia:
3 puntos por cada 100 unidades.

Gasto:
0 puntos.
```

Ejemplo:

```text
Depósito de 1000
→ 10 puntos

Retiro de 1000
→ 20 puntos

Transferencia de 1000
→ 30 puntos
```

Los gastos no generan puntos porque no se definió una regla de acumulación para este tipo de operación.

---

# Rangos de clientes

El rango se calcula según los puntos actuales del cliente.

| Rango | Puntos |
|---|---:|
| Bronce | 0 - 500 |
| Plata | 501 - 1000 |
| Oro | 1001 - 5000 |
| Platino | Más de 5000 |

El rango se actualiza cuando los puntos aumentan o disminuyen.

Esto puede ocurrir después de:

```text
Realizar una transacción
Revertir una transacción
Canjear un beneficio
```

---

# Comisiones

El sistema implementa una comisión base del **1 %** para retiros y transferencias.

## Comisión de retiro

Un retiro normal calcula:

```text
comisión = monto × 0.01
```

Ejemplo:

```text
Retiro:          1000
Comisión 1 %:      10
Total descontado: 1010
```

El cliente debe tener saldo suficiente para cubrir:

```text
monto + comisión
```

---

## Comisión de transferencia

Una transferencia normal también cobra:

```text
comisión = monto × 0.01
```

Ejemplo:

```text
Monto enviado:    1000
Comisión:            10
Total al origen:   1010
Destino recibe:    1000
```

La comisión no se envía al destinatario.

---

# Beneficios

El sistema permite canjear puntos por tres beneficios.

| Puntos | Beneficio |
|---:|---|
| 100 | Reducción del 10 % en la comisión de transferencias |
| 500 | Un mes sin cargos por retiros |
| 1000 | Bono de 50 unidades en un monedero |

---

## Beneficio de 100 puntos

`DESCUENTO_TRANSFERENCIA`

Reduce en un **10 % la comisión calculada** de una transferencia.

Por ejemplo:

```text
Transferencia: 1000

Comisión normal:
1000 × 1 % = 10

Descuento:
10 × 10 % = 1

Comisión final:
10 - 1 = 9
```

En la implementación actual este beneficio permanece activo después de ser canjeado, ya que no posee una fecha de vencimiento.

---

## Beneficio de 500 puntos

`RETIROS_SIN_CARGO`

Permite realizar retiros sin comisión durante un mes.

Ejemplo:

```text
Retiro: 1000

Comisión normal:
10

Con beneficio:
0
```

La fecha de vencimiento se calcula utilizando:

```text
fechaCanje + 1 mes
```

---

## Beneficio de 1000 puntos

`BONO_SALDO`

Agrega directamente:

```text
50 unidades
```

al monedero seleccionado.

El bono se aplica directamente sobre el saldo y no se registra como un depósito normal, para evitar que genere nuevos puntos.

---

# Reversiones

Las transacciones reversibles se administran mediante:

```text
Deque<Transaccion>
```

implementado con:

```text
ArrayDeque
```

La estructura funciona como una pila:

```text
LIFO
Last In, First Out
```

La última transacción registrada es la primera que puede revertirse.

---

## Reversión de depósito

Se retira del monedero el monto depositado y se descuentan los puntos generados.

---

## Reversión de retiro

Se devuelve:

```text
monto + comisión
```

y se descuentan los puntos generados por el retiro.

---

## Reversión de transferencia

El destino devuelve únicamente el monto que recibió.

El origen recupera:

```text
monto + comisión
```

También se actualizan los grafos y los puntos generados.

---

## Reversión de gasto

Se devuelve el monto al monedero y se actualiza el patrón de gasto correspondiente.

---

# ArrayList

Se utiliza para almacenar colecciones que requieren recorridos y acceso secuencial.

Ejemplo principal:

```text
List<Transaccion>
```

para el historial de transacciones de cada cliente.

Complejidad aproximada:

```text
Agregar al final: O(1) amortizado
Acceso por índice: O(1)
Búsqueda lineal: O(n)
```

---

# HashMap

Se utilizan mapas para búsquedas rápidas mediante claves.

Ejemplos:

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
put()         O(1)
get()         O(1)
containsKey() O(1)
```

---

# Cola de notificaciones

Las notificaciones pendientes utilizan:

```text
Queue<Notificacion>
```

implementada mediante:

```text
ArrayDeque
```

Trabaja con el principio:

```text
FIFO
First In, First Out
```

La primera notificación recibida es la primera que se atiende.

Operaciones principales:

```text
offer()
peek()
poll()
```

---

# Lista circular

Se implementó una estructura propia:

```text
ListaCircular<T>
NodoCircular<T>
```

Se utiliza para conservar el historial de notificaciones.

```text
N1 → N2 → N3
↑         │
└─────────┘
```

El último nodo vuelve al primero.

La cola y la lista circular cumplen funciones diferentes:

```text
Queue
→ notificaciones pendientes.

ListaCircular
→ historial completo.
```

Cuando una notificación es atendida, sale de la cola de pendientes pero continúa dentro del historial circular.

---

# Transacciones programadas

Las operaciones programadas utilizan:

```text
PriorityQueue<TransaccionProgramada>
```

Se pueden programar:

```text
Depósitos
Transferencias
```

Cada transacción programada contiene:

- Código.
- Tipo.
- Cliente y monedero de origen.
- Cliente y monedero de destino cuando aplica.
- Monto.
- Fecha de ejecución.
- Frecuencia en días.

La prioridad se determina primero por la fecha y, en caso de empate, por el código.

---

## Operaciones recurrentes

Cuando:

```text
frecuenciaDias = 0
```

la operación se ejecuta una sola vez.

Cuando:

```text
frecuenciaDias > 0
```

la operación vuelve a insertarse en la `PriorityQueue` con una nueva fecha.

Las transferencias programadas utilizan la misma lógica de `GestorTransacciones`, por lo que también cobran comisión y aplican beneficios cuando corresponde.

---

# Árbol binario de búsqueda

La clase:

```text
ArbolBinarioPuntos
```

mantiene los clientes ordenados por puntos.

Si dos clientes tienen la misma cantidad de puntos se utiliza su identificación como criterio de desempate.

El recorrido inorden permite obtener los clientes ordenados de menor a mayor puntuación.

Complejidad:

```text
Promedio:
O(log n)

Peor caso:
O(n)
```

---

# Árbol AVL

La clase:

```text
ArbolAVLClientes
```

mantiene balanceado el árbol utilizando rotaciones.

Se manejan los casos:

```text
Izquierda - Izquierda
Derecha - Derecha
Izquierda - Derecha
Derecha - Izquierda
```

La búsqueda, inserción y eliminación mantienen una complejidad aproximada de:

```text
O(log n)
```

Cuando cambian los puntos de un cliente, este se elimina de los árboles utilizando su valor anterior, se actualizan sus puntos y luego se inserta nuevamente.

---

# Grafos dirigidos

El proyecto utiliza grafos dirigidos mediante:

```text
GrafoDirigido
```

implementado con listas de adyacencia.

Cada arista registra:

- Destino.
- Monto acumulado.
- Cantidad de transacciones.

---

## Grafo de clientes

Registra transferencias entre clientes diferentes.

Ejemplo:

```text
C001 ───▶ C002
```

Permite consultar:

- Monto transferido.
- Cantidad de transferencias.
- Total transferido por un cliente.
- Destino con mayor monto.
- Existencia de caminos.
- Recorrido BFS.

Las transferencias entre dos monederos del mismo cliente no generan una arista en este grafo.

---

## Grafo de monederos

Toda transferencia registra una relación entre monederos.

Los vértices se identifican de la forma:

```text
C001:M001
```

Ejemplo:

```text
C001:M001 ───▶ C001:M002
```

Por esta razón también se pueden representar transferencias internas entre monederos del mismo cliente.

---

# BFS

El grafo implementa recorrido en anchura:

```text
BFS
Breadth First Search
```

Utiliza:

```text
Queue<String>
Set<String>
```

para recorrer los vértices y evitar visitar varias veces el mismo nodo.

Complejidad:

```text
O(V + E)
```

donde:

```text
V = cantidad de vértices
E = cantidad de aristas
```

---

# Gastos

El sistema permite registrar gastos utilizando las siguientes categorías:

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

Los gastos:

```text
Disminuyen el saldo.
No generan puntos.
No cobran comisión.
```

---

# Patrones de gasto

`GestorPatronesGasto` registra los gastos de cada cliente y analiza las relaciones entre categorías consecutivas.

Ejemplo:

```text
ALIMENTACION
      │
      ▼
TRANSPORTE
```

El sistema permite consultar:

- Total gastado por cliente.
- Total por categoría.
- Categoría de mayor gasto.
- Última categoría.
- Monto entre categorías consecutivas.
- Cantidad de transiciones.
- Principal categoría siguiente.

Al revertir un gasto también se actualiza la información del patrón.

---

# Notificaciones

El sistema maneja tres tipos:

```text
SALDO_BAJO
TRANSACCION_PROGRAMADA
GENERAL
```

Las notificaciones se almacenan simultáneamente en:

```text
ListaCircular
→ historial.

Queue
→ pendientes.
```

Las transacciones programadas generan notificaciones al ser programadas y al ejecutarse correctamente.

---

# Saldo bajo

Después de operaciones que disminuyen el saldo se verifica si el monedero queda por debajo de:

```text
100 unidades
```

Si ocurre, se genera una notificación de tipo:

```text
SALDO_BAJO
```

El valor de 100 corresponde a una decisión de implementación del proyecto.

---

# Verificación recursiva de integridad

`GestorIntegridad` utiliza recursividad para verificar los historiales.

Entre las validaciones realizadas se encuentran:

- Código válido.
- Código no repetido dentro del historial del cliente.
- Fecha válida.
- Tipo válido.
- Monto mayor que cero.
- Comisión no negativa.
- Identificación de origen.
- Monedero de origen.
- Datos de destino en transferencias.
- Categoría válida para gastos.
- Coherencia de puntos.
- Coherencia de la comisión según el tipo de operación.

Para retiros se acepta:

```text
Comisión normal del 1 %
o
0 cuando aplica el beneficio.
```

Para transferencias se acepta:

```text
Comisión normal del 1 %
o
comisión reducida por el beneficio.
```

También existen métodos recursivos para:

```text
Verificar un cliente.
Verificar todos los clientes.
Contar transacciones válidas.
Buscar la primera transacción inválida.
```

Una transacción revertida continúa formando parte del historial y no se considera inválida únicamente por estar revertida.

---

# Reportes

## Reporte de cliente

`ReporteCliente` contiene:

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
- Cantidad de beneficios.
- Cantidad de notificaciones.
- Estado de integridad.

---

## Reporte general

`ReporteSistema` contiene:

- Cantidad de clientes.
- Cantidad de monederos.
- Saldo total del sistema.
- Cantidad de transacciones únicas.
- Transacciones activas.
- Transacciones revertidas.
- Total de gastos.
- Cantidad de beneficios.
- Cantidad de notificaciones.
- Estado de integridad.

Como una transferencia entre clientes aparece en los historiales de origen y destino, el reporte utiliza el código de la transacción para evitar contarla dos veces.

---

# Interfaz de usuario

El proyecto cuenta con una interfaz de consola:

```text
ui/InterfazConsola.java
```

`Main.java` crea una instancia de esta interfaz y ejecuta:

```text
interfaz.iniciar()
```

Desde el menú de consola se puede acceder a las principales funcionalidades del sistema, incluyendo:

```text
Registro de clientes
Creación de monederos
Depósitos
Retiros
Transferencias
Gastos
Consulta de saldo
Historial
Puntos y rango
Canje de beneficios
Programación de operaciones
Notificaciones
Reversiones
Reportes
Patrones de gasto
Integridad
Grafos
```

Las operaciones muestran la información de la `Transaccion`, incluyendo la comisión que fue cobrada cuando aplica.

La lógica financiera no se encuentra dentro de la interfaz, sino en los gestores correspondientes.

---

# Pruebas

El proyecto contiene las siguientes clases de prueba:

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

Las pruebas se ejecutan mediante métodos:

```text
public static void main(String[] args)
```

y utilizan `AssertionError` para indicar una condición incorrecta.

---

# Prueba integral

`PruebaSistemaCompleto.java` reúne diferentes funcionalidades dentro de un mismo escenario.

Actualmente contiene:

```text
132 verificaciones
```

Entre ellas se comprueban:

```text
Clientes
Multimonedero
Depósitos
Retiros
Transferencias
Transferencias internas
Comisiones
Descuento de comisión
Gastos
Puntos
Rangos
Beneficios
HashMap
BST
AVL
PriorityQueue
Operaciones recurrentes
Notificaciones
Queue
Lista circular
Grafos
BFS
Recursividad
Pila
Reversiones
Reportes
```

En el escenario definido por la prueba se validan, entre otras, estas comisiones:

```text
Transferencia de 1000
→ comisión 10

Transferencia interna de 2000
→ comisión 20

Retiro de 200
→ comisión 2

Transferencia programada de 400
con beneficio de descuento
→ comisión 3.6
```

Antes de revertir la última transferencia programada, el saldo esperado del sistema es:

```text
20964.4
```

Después de revertirla y devolver también su comisión:

```text
20968.0
```

Si todas las verificaciones son correctas, la prueba finaliza mostrando:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 132
```

---

# Complejidades principales

| Operación | Complejidad aproximada |
|---|---:|
| Buscar cliente en HashMap | O(1) promedio |
| Buscar monedero en HashMap | O(1) promedio |
| Consultar puntos en HashMap | O(1) promedio |
| Agregar al final de ArrayList | O(1) amortizado |
| Búsqueda lineal | O(n) |
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
| Siguiente en lista circular | O(1) |
| BST promedio | O(log n) |
| BST peor caso | O(n) |
| AVL | O(log n) |
| BFS | O(V + E) |
| Recorrido inorden | O(n) |
| Verificación recursiva de un historial | O(n) |

---

# Cómo ejecutar el proyecto

## Requisitos

```text
JDK 21
Maven
IntelliJ IDEA
```

El `pom.xml` está configurado con:

```text
maven.compiler.source = 21
maven.compiler.target = 21
```

---

## Abrir el proyecto

Abrir en IntelliJ IDEA la carpeta:

```text
Proyecto Final Estructuras de Datos
```

donde se encuentra:

```text
pom.xml
```

---

## Compilar

Desde una terminal ubicada en la carpeta del proyecto:

```bash
mvn clean compile
```

---

## Ejecutar el sistema

Ejecutar:

```text
Main.java
```

Esto inicia la interfaz de consola.

---

## Ejecutar la prueba integral

Ejecutar:

```text
PruebaSistemaCompleto.java
```

Si todas las condiciones se cumplen, debe finalizar sin lanzar `AssertionError`.

---

# Decisiones de diseño

## Comisión base

Se definió una comisión del:

```text
1 %
```

tanto para retiros como para transferencias.

Esta comisión se almacena dentro de cada `Transaccion`.

---

## Descuento de transferencia

El beneficio de 100 puntos reduce un:

```text
10 %
```

sobre la comisión calculada.

Por ejemplo:

```text
Comisión base: 10
Descuento:      1
Comisión final: 9
```

---

## Duración del descuento de transferencia

La implementación no asigna una fecha de vencimiento a `DESCUENTO_TRANSFERENCIA`.

Por esta razón, una vez canjeado permanece activo.

---

## Retiros sin cargo

`RETIROS_SIN_CARGO` tiene una duración de:

```text
1 mes
```

Durante este periodo la comisión del retiro es cero.

---

## Puntos y comisiones

Los puntos se calculan únicamente sobre:

```text
monto de la operación
```

La comisión no genera puntos adicionales.

---

## Transferencias internas

Las transferencias entre dos monederos diferentes del mismo cliente están permitidas.

Estas operaciones:

```text
Cobran comisión.
Generan puntos.
Se registran en el grafo de monederos.
No generan una relación entre clientes.
```

---

## Gastos

Los gastos:

```text
No generan puntos.
No cobran comisión.
```

---

## Bono de saldo

El bono de 50 unidades se aplica directamente al monedero para evitar generar puntos como si fuera un depósito normal.

---

## Saldo bajo

Se utiliza un límite de:

```text
100 unidades
```

para generar la alerta automática.

---

# Uso de estructuras de datos

```text
ArrayList
→ historial de transacciones y colecciones.

HashMap
→ búsquedas rápidas y asociaciones por clave.

Deque / ArrayDeque
→ pila de transacciones reversibles.

Queue / ArrayDeque
→ notificaciones pendientes.

PriorityQueue
→ transacciones programadas.

ListaCircular
→ historial circular de notificaciones.

ArbolBinarioPuntos
→ ordenamiento de clientes por puntos.

ArbolAVLClientes
→ árbol balanceado de clientes por puntos.

GrafoDirigido
→ relaciones entre clientes, monederos y categorías.

HashSet
→ control de visitados y detección de códigos repetidos.

Recursividad
→ verificación de integridad.
```

---

# Resultado

El proyecto integra las diferentes estructuras dentro del mismo flujo de negocio.

Por ejemplo, una transferencia puede modificar:

```text
Saldo
Comisión
Historial
Puntos
Rango
HashMap de puntos
BST
AVL
Pila de reversiones
Grafo de clientes
Grafo de monederos
```

Cuando la transferencia es revertida se devuelve tanto el monto como la comisión realmente cobrada y se actualizan las estructuras relacionadas.

Esto permite mantener sincronizados los diferentes componentes del sistema.

---

# Conclusión

El proyecto permitió aplicar de manera práctica diferentes estructuras de datos dentro de un único sistema.

La aplicación integra listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios, árboles AVL, listas circulares, grafos dirigidos y recursividad.

Además de las operaciones básicas de un monedero, el sistema implementa puntos, rangos, beneficios, comisiones, transacciones programadas, recurrencia, notificaciones, gastos, patrones, multimonedero, reversiones, validación de integridad y reportes.

Las comisiones permiten que los beneficios de 100 y 500 puntos tengan un efecto real dentro del funcionamiento financiero del monedero, mientras que las reversiones restauran tanto los montos como las comisiones correspondientes.

La prueba integral actual contiene **132 verificaciones** para comprobar el funcionamiento conjunto de las principales características del sistema.