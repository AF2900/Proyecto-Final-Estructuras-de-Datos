# Proyecto Final - Estructuras de Datos

## Monedero Virtual con Interfaz Gráfica y Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la **Universidad del Quindío**.

La aplicación simula un monedero virtual avanzado mediante una **interfaz gráfica de escritorio desarrollada con Java Swing**. Permite administrar clientes y monederos, realizar operaciones financieras, acumular puntos, obtener rangos, canjear beneficios, programar transacciones, gestionar notificaciones, analizar patrones de gasto, consultar reportes y visualizar el uso de diferentes estructuras de datos.

El proyecto fue desarrollado con **Java 21 y Maven**.

---

## Integrantes

- Federico Cruz
- Johan Steven Alvarez
- Adrián Fernando Pérez Naranjo

---

# Objetivo

Desarrollar una aplicación con interfaz gráfica que simule un monedero virtual avanzado y permita aplicar de manera práctica diferentes estructuras de datos estudiadas durante el curso.

El sistema integra operaciones financieras con listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios, árboles AVL, listas circulares, grafos dirigidos y recursividad.

---

# Tecnologías utilizadas

- Java 21
- Java Swing
- Maven
- IntelliJ IDEA
- Git
- GitHub

---

# Funcionalidades principales

La aplicación permite:

- Registrar clientes.
- Crear varios monederos por cliente.
- Realizar depósitos.
- Realizar retiros.
- Realizar transferencias entre clientes.
- Realizar transferencias entre monederos del mismo cliente.
- Registrar gastos por categoría.
- Consultar saldos.
- Consultar historial de transacciones.
- Cobrar comisiones en retiros y transferencias.
- Acumular puntos.
- Calcular automáticamente el rango de los clientes.
- Canjear beneficios.
- Revertir la última transacción mediante una pila.
- Programar depósitos.
- Programar transferencias.
- Crear operaciones recurrentes.
- Procesar operaciones programadas.
- Cancelar operaciones programadas.
- Generar notificaciones automáticas.
- Gestionar notificaciones pendientes mediante una cola FIFO.
- Mantener el historial de notificaciones mediante una lista circular.
- Analizar relaciones entre clientes mediante grafos dirigidos.
- Analizar relaciones entre monederos.
- Analizar patrones de gasto.
- Realizar recorridos BFS.
- Verificar recursivamente la integridad de las transacciones.
- Consultar árboles BST y AVL.
- Generar reportes por cliente.
- Generar un reporte general del sistema.
- Consultar las principales funcionalidades mediante una interfaz gráfica.

---

# Arquitectura general

La aplicación mantiene separada la interfaz gráfica de la lógica del negocio.

```text
Main
 │
 ▼
InterfazGrafica
 │
 ├── PanelClientesMonederos
 ├── PanelTransacciones
 ├── PanelReversiones
 ├── PanelPuntosBeneficios
 ├── PanelProgramadas
 ├── PanelNotificaciones
 ├── PanelReportes
 └── PanelEstructuras
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

`MonederoVirtual` funciona como fachada principal del sistema.

Los componentes de la interfaz gráfica utilizan esta clase para acceder a las funcionalidades del sistema sin comunicarse directamente con cada uno de los gestores.

De esta forma se mantiene separada la capa de presentación de la lógica del negocio.

---

# Estructura del proyecto

```text
co.edu.uniquindio.poo
│
├── Main.java
├── MonederoVirtual.java
│
├── PruebaBaseMonederoVirtual.java
├── PruebaReversiones.java
├── PruebaArbolesPuntos.java
├── PruebaTransaccionesProgramadas.java
├── PruebaNotificaciones.java
├── PruebaGrafos.java
├── PruebaGastos.java
├── PruebaIntegridad.java
├── PruebaBeneficios.java
├── PruebaReportes.java
├── PruebaSistemaCompleto.java
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
    ├── InterfazConsola.java
    ├── InterfazGrafica.java
    ├── PanelClientesMonederos.java
    ├── PanelTransacciones.java
    ├── PanelReversiones.java
    ├── PanelPuntosBeneficios.java
    ├── PanelProgramadas.java
    ├── PanelNotificaciones.java
    ├── PanelReportes.java
    └── PanelEstructuras.java
```

`InterfazConsola.java` se conserva dentro del proyecto como una implementación anterior y como apoyo para pruebas manuales.

Sin embargo, el punto de entrada principal utiliza actualmente `InterfazGrafica.java`.

---

# Interfaz gráfica

La interfaz fue desarrollada con **Java Swing** y está diseñada como una aplicación de escritorio.

La ventana principal utiliza componentes como:

```text
JFrame
BorderLayout
CardLayout
JPanel
JTabbedPane
JTable
JComboBox
JTextField
JSpinner
JOptionPane
```

La navegación principal utiliza un menú lateral que permite cambiar entre los distintos módulos.

Los módulos disponibles son:

```text
Inicio
Clientes
Transacciones
Reversiones
Puntos y beneficios
Programadas
Notificaciones
Reportes
Estructuras
```

---

# Inicio

La pantalla principal funciona como dashboard y muestra:

- Cantidad de clientes.
- Cantidad de monederos.
- Saldo total del sistema.
- Estado de integridad.
- Accesos rápidos a diferentes funciones.

---

# Clientes y monederos

El módulo permite:

- Registrar clientes.
- Crear monederos.
- Consultar clientes registrados.
- Consultar puntos.
- Consultar rango.
- Consultar cantidad de monederos.
- Visualizar los monederos registrados.
- Consultar sus saldos.

Cada cliente puede tener varios monederos asociados.

Ejemplo:

```text
Cliente C001
│
├── M001 - Principal
└── M002 - Ahorros
```

---

# Transacciones

La pantalla de transacciones utiliza pestañas para separar las operaciones.

Incluye:

```text
Depósito
Retiro
Transferencia
Gasto
Consultas
```

La pestaña de consultas permite visualizar:

- Saldo.
- Historial de transacciones.
- Código.
- Fecha.
- Tipo.
- Monto.
- Comisión.
- Origen.
- Destino.
- Categoría.
- Puntos generados.
- Estado de reversión.

---

# Modelo Cliente

La clase `Cliente` representa a una persona registrada dentro del sistema.

Cada cliente contiene:

- Identificación.
- Nombre.
- Puntos.
- Rango.
- Monederos.
- Historial de transacciones.

Los monederos utilizan:

```text
Map<String, Monedero>
```

El historial utiliza:

```text
List<Transaccion>
```

---

# Modelo Monedero

La clase `Monedero` representa una cuenta asociada a un cliente.

Cada monedero contiene:

- Identificador.
- Nombre.
- Saldo.

Un cliente puede poseer varios monederos.

---

# Transacciones

La clase `Transaccion` representa una operación financiera realizada dentro del sistema.

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
- Cliente de origen.
- Monedero de origen.
- Cliente de destino.
- Monedero de destino.
- Puntos generados.
- Comisión.
- Categoría de gasto.
- Categoría anterior.
- Estado de reversión.

Las transacciones revertidas permanecen dentro del historial y quedan identificadas mediante su estado de reversión.

---

# Sistema de puntos

Las reglas implementadas son:

```text
Depósito:
1 punto por cada 100 unidades.

Retiro:
2 puntos por cada 100 unidades.

Transferencia:
3 puntos por cada 100 unidades.

Gasto:
No genera puntos.
```

Los puntos se calculan utilizando únicamente el monto de la operación.

La comisión no genera puntos adicionales.

Ejemplo:

```text
Depósito de 1000
→ 10 puntos

Retiro de 1000
→ 20 puntos

Transferencia de 1000
→ 30 puntos
```

---

# Rangos

Los clientes se clasifican automáticamente de acuerdo con sus puntos.

| Rango | Puntos |
|---|---:|
| Bronce | 0 - 500 |
| Plata | 501 - 1000 |
| Oro | 1001 - 5000 |
| Platino | Más de 5000 |

El rango puede cambiar cuando:

- El cliente genera puntos.
- Se revierte una transacción.
- El cliente utiliza puntos para canjear beneficios.

---

# Comisiones

El sistema utiliza una comisión base del:

```text
1 %
```

para:

```text
RETIRO
TRANSFERENCIA
```

Ejemplo de transferencia:

```text
Monto:            1000
Comisión:           10
Total descontado: 1010
Destino recibe:   1000
```

La comisión queda almacenada dentro de la transacción para conocer exactamente cuánto fue cobrado y poder devolver ese valor durante una reversión.

---

# Beneficios

Los puntos pueden ser canjeados por tres beneficios.

| Puntos | Beneficio |
|---:|---|
| 100 | Reducción del 10 % en la comisión de transferencias |
| 500 | Un mes sin cargos por retiros |
| 1000 | Bono de 50 unidades |

La interfaz gráfica permite consultar:

- Puntos disponibles.
- Rango actual.
- Beneficios activos.
- Historial de beneficios.
- Fecha de vencimiento.
- Monedero beneficiado.

---

## Beneficio de 100 puntos

El beneficio:

```text
DESCUENTO_TRANSFERENCIA
```

reduce en un 10 % la comisión calculada para una transferencia.

Ejemplo:

```text
Transferencia: 1000

Comisión base:
1000 × 1 % = 10

Descuento:
10 × 10 % = 1

Comisión final:
10 - 1 = 9
```

---

## Beneficio de 500 puntos

El beneficio:

```text
RETIROS_SIN_CARGO
```

permite realizar retiros sin comisión durante un mes.

Su vencimiento se calcula utilizando:

```text
fecha de canje + 1 mes
```

---

## Beneficio de 1000 puntos

El beneficio:

```text
BONO_SALDO
```

agrega:

```text
50 unidades
```

directamente al monedero seleccionado.

El bono se aplica directamente sobre el saldo y no se registra como depósito normal para evitar que genere puntos adicionales.

---

# Reversiones

Las operaciones reversibles utilizan:

```text
Deque<Transaccion>
```

implementado mediante:

```text
ArrayDeque
```

Esta estructura trabaja como una pila:

```text
LIFO
Last In, First Out
```

Por esta razón, la última transacción realizada es la primera que puede ser revertida.

La interfaz gráfica muestra la transacción que actualmente se encuentra en el tope de la pila.

---

## Reversión de depósito

Se retira del monedero el monto depositado y se eliminan los puntos generados.

---

## Reversión de retiro

Se devuelve:

```text
monto + comisión
```

y se descuentan los puntos generados por el retiro.

---

## Reversión de transferencia

El destino devuelve el dinero recibido.

El origen recupera:

```text
monto + comisión
```

También se actualizan:

- Puntos.
- Grafo de clientes.
- Grafo de monederos.

---

## Reversión de gasto

Se devuelve el monto al monedero y se actualiza el patrón de gasto.

---

# Transacciones programadas

Las operaciones programadas utilizan:

```text
PriorityQueue<TransaccionProgramada>
```

Se pueden programar:

- Depósitos.
- Transferencias.

Cada operación contiene:

- Código.
- Tipo.
- Cliente origen.
- Monedero origen.
- Cliente destino cuando aplica.
- Monedero destino cuando aplica.
- Monto.
- Fecha.
- Frecuencia.

La prioridad se determina mediante:

```text
1. Fecha de ejecución.
2. Código en caso de empate.
```

La interfaz permite:

- Programar depósitos.
- Programar transferencias.
- Definir fecha y hora.
- Definir frecuencia.
- Consultar las operaciones en orden.
- Consultar la siguiente operación.
- Cancelar operaciones.
- Procesar operaciones cuya fecha ya llegó.

---

# Operaciones recurrentes

Cuando:

```text
frecuenciaDias = 0
```

la operación se ejecuta una sola vez.

Cuando:

```text
frecuenciaDias > 0
```

la operación se vuelve a insertar dentro de la `PriorityQueue` utilizando una nueva fecha.

Las transferencias programadas utilizan la misma lógica de transacciones normales, incluyendo:

- Comisión.
- Puntos.
- Beneficios.
- Grafos.
- Notificaciones.

---

# Notificaciones

El sistema utiliza dos estructuras diferentes para manejar las notificaciones.

---

## Cola FIFO

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

La primera notificación recibida es la primera en ser atendida.

---

## Lista circular

El historial completo utiliza una estructura propia:

```text
ListaCircular<T>
NodoCircular<T>
```

Ejemplo:

```text
N1 → N2 → N3
↑         │
└─────────┘
```

La interfaz permite recorrer las notificaciones de forma circular.

Cuando una notificación es atendida:

```text
Sale de la cola de pendientes.
Permanece en la lista circular.
```

---

# Saldo bajo

Después de las operaciones que disminuyen el saldo se verifica si el monedero queda por debajo de:

```text
100 unidades
```

Si ocurre, el sistema genera una notificación de:

```text
SALDO_BAJO
```

---

# Gastos

Las categorías disponibles son:

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

`GestorPatronesGasto` registra los gastos de los clientes y analiza las relaciones entre categorías consecutivas mediante un grafo dirigido.

Ejemplo:

```text
ALIMENTACION
      │
      ▼
TRANSPORTE
```

El sistema permite consultar:

- Total gastado.
- Total gastado por categoría.
- Categoría de mayor gasto.
- Última categoría utilizada.
- Principal categoría siguiente.
- Monto entre categorías.
- Cantidad de transiciones.

Cuando se revierte un gasto también se actualiza la información correspondiente.

---

# Grafos dirigidos

El proyecto utiliza:

```text
GrafoDirigido
```

implementado mediante listas de adyacencia.

Cada arista almacena:

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
- Total transferido por cliente.
- Principal destino.
- Existencia de relaciones.
- Recorrido BFS.

Las transferencias entre dos monederos del mismo cliente no generan relaciones en este grafo.

---

## Grafo de monederos

Toda transferencia registra una relación entre monederos.

Los vértices utilizan la forma:

```text
C001:M001
```

Ejemplo:

```text
C001:M001 ───▶ C001:M002
```

Esto permite representar también transferencias entre monederos del mismo cliente.

---

# BFS

El grafo implementa el recorrido:

```text
BFS
Breadth First Search
```

Se utilizan:

```text
Queue<String>
Set<String>
```

para recorrer los vértices y evitar visitarlos varias veces.

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

# HashMap

Se utilizan tablas hash para búsquedas rápidas mediante claves.

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

# ArrayList

Se utiliza para almacenar colecciones que requieren recorridos y acceso secuencial.

Uno de sus principales usos es:

```text
List<Transaccion>
```

para almacenar el historial de cada cliente.

Complejidades aproximadas:

```text
Agregar al final: O(1) amortizado
Acceso por índice: O(1)
Búsqueda lineal: O(n)
```

---

# Árbol binario de búsqueda

La clase:

```text
ArbolBinarioPuntos
```

organiza los clientes de acuerdo con sus puntos.

Si dos clientes tienen la misma cantidad de puntos, la identificación se utiliza como criterio de desempate.

El recorrido inorden permite obtener los clientes desde la menor hasta la mayor puntuación.

Complejidad:

```text
Promedio: O(log n)
Peor caso: O(n)
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

La complejidad aproximada de búsqueda, inserción y eliminación es:

```text
O(log n)
```

Cuando los puntos de un cliente cambian, se actualizan también los árboles.

La interfaz permite consultar:

```text
Altura BST
Altura AVL
Recorrido BST
Recorrido AVL
```

---

# Verificación recursiva de integridad

`GestorIntegridad` utiliza recursividad para verificar los historiales de transacciones.

Entre las validaciones se encuentran:

- Código válido.
- Código no repetido.
- Fecha válida.
- Tipo válido.
- Monto mayor que cero.
- Comisión no negativa.
- Cliente origen válido.
- Monedero origen válido.
- Datos de destino para transferencias.
- Categoría válida para gastos.
- Coherencia de puntos.
- Coherencia de comisiones.

Para retiros se admite:

```text
Comisión normal del 1 %
o
0 cuando aplica el beneficio.
```

Para transferencias se admite:

```text
Comisión normal del 1 %
o
comisión reducida por beneficio.
```

También existen métodos recursivos para:

- Verificar un cliente.
- Verificar todo el sistema.
- Contar transacciones válidas.
- Buscar la primera transacción inválida.

La interfaz gráfica permite consultar estos resultados desde el módulo de estructuras.

---

# Reportes

## Reporte por cliente

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

Como una transferencia puede aparecer en el historial del origen y del destino, el reporte utiliza el código de la transacción para evitar contarla dos veces.

---

# Estructuras de datos utilizadas

| Estructura | Uso principal |
|---|---|
| ArrayList | Historiales y colecciones |
| HashMap | Búsquedas y asociaciones por clave |
| Deque / ArrayDeque | Pila de reversiones |
| Queue / ArrayDeque | Notificaciones pendientes |
| PriorityQueue | Transacciones programadas |
| ListaCircular | Historial de notificaciones |
| Árbol BST | Clientes ordenados por puntos |
| Árbol AVL | Clientes ordenados y balanceados |
| GrafoDirigido | Clientes, monederos y patrones |
| HashSet | Visitados y códigos |
| Recursividad | Verificación de integridad |

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
| Verificación recursiva | O(n) |

---

# Pruebas

El proyecto contiene diferentes clases de prueba:

```text
PruebaBaseMonederoVirtual.java
PruebaReversiones.java
PruebaArbolesPuntos.java
PruebaTransaccionesProgramadas.java
PruebaNotificaciones.java
PruebaGrafos.java
PruebaGastos.java
PruebaIntegridad.java
PruebaBeneficios.java
PruebaReportes.java
PruebaSistemaCompleto.java
```

Las pruebas se ejecutan mediante métodos:

```text
public static void main(String[] args)
```

y utilizan `AssertionError` cuando una condición no se cumple.

---

# Prueba integral

`PruebaSistemaCompleto.java` reúne las principales funcionalidades dentro de un mismo escenario.

Entre los elementos comprobados se encuentran:

```text
Clientes
Multimonedero
Depósitos
Retiros
Transferencias
Transferencias internas
Comisiones
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

La prueba integral contiene actualmente:

```text
132 verificaciones
```

Entre las comisiones comprobadas se encuentran:

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

Si todas las condiciones se cumplen, la prueba finaliza mostrando:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 132
```

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

En IntelliJ IDEA se debe abrir la carpeta:

```text
Proyecto Final Estructuras de Datos
```

Dentro de esta carpeta se encuentra:

```text
pom.xml
```

---

## Compilar

Desde una terminal ubicada en la carpeta donde se encuentra `pom.xml`:

```text
mvn clean compile
```

---

## Ejecutar la aplicación

Ejecutar:

```text
Main.java
```

`Main.java` inicia la interfaz gráfica utilizando el Event Dispatch Thread de Swing:

```text
SwingUtilities.invokeLater(...)
```

y crea una instancia de:

```text
InterfazGrafica
```

Por lo tanto, la aplicación inicia directamente mediante la ventana gráfica.

---

## Ejecutar la prueba integral

Ejecutar:

```text
PruebaSistemaCompleto.java
```

La ejecución debe finalizar sin lanzar `AssertionError`.

Si todo funciona correctamente se debe mostrar:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 132
```

---

# Flujo recomendado para demostrar el proyecto

Durante la presentación se puede seguir este flujo:

```text
1. Registrar dos clientes.

2. Crear uno o más monederos.

3. Realizar un depósito.

4. Mostrar los puntos generados.

5. Realizar una transferencia.

6. Mostrar la comisión.

7. Registrar un gasto.

8. Consultar patrones de gasto.

9. Canjear un beneficio.

10. Realizar otra transferencia o retiro
    para mostrar el efecto del beneficio.

11. Programar una transacción.

12. Mostrar la cola de prioridad.

13. Consultar las notificaciones.

14. Mostrar Queue y ListaCircular.

15. Consultar BST y AVL.

16. Mostrar los grafos.

17. Revertir la última operación.

18. Mostrar los reportes.

19. Verificar la integridad.
```

Este flujo permite demostrar tanto las funcionalidades del monedero como las estructuras de datos utilizadas detrás de cada operación.

---

# Decisiones de diseño

## Separación entre interfaz y lógica

La interfaz gráfica se encarga principalmente de:

- Capturar información del usuario.
- Mostrar resultados.
- Navegar entre módulos.
- Mostrar tablas.
- Mostrar mensajes.

La lógica del negocio permanece en `MonederoVirtual` y los gestores.

Por ejemplo:

```text
sistema.depositar(...)
sistema.retirar(...)
sistema.transferir(...)
sistema.canjearBeneficio(...)
```

Esto evita duplicar reglas financieras dentro de la interfaz Swing.

---

## Comisión base

Se definió una comisión del:

```text
1 %
```

para retiros y transferencias.

---

## Descuento de transferencia

El beneficio de 100 puntos reduce:

```text
10 %
```

sobre la comisión calculada.

---

## Retiros sin cargo

El beneficio de 500 puntos elimina la comisión de retiro durante:

```text
1 mes
```

---

## Bono de saldo

El beneficio de 1000 puntos agrega:

```text
50 unidades
```

al monedero seleccionado.

---

## Puntos y comisiones

Los puntos se calculan únicamente sobre:

```text
monto de la operación
```

La comisión no genera puntos.

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

## Saldo bajo

Se utiliza un límite de:

```text
100 unidades
```

para generar una notificación automática de saldo bajo.

---

# Resultado

El proyecto integra las diferentes estructuras dentro del mismo flujo del sistema.

Por ejemplo, una transferencia puede modificar:

```text
Saldo
Comisión
Historial
Puntos
Rango
HashMap
BST
AVL
Pila de reversiones
Grafo de clientes
Grafo de monederos
Notificaciones
Reportes
```

Cuando una transferencia es revertida se devuelve tanto el monto como la comisión realmente cobrada y se actualizan las estructuras relacionadas.

La interfaz gráfica permite utilizar y consultar estas funcionalidades manteniendo separada la presentación de la lógica interna.

---

# Conclusión

El proyecto permitió aplicar de manera práctica diferentes estructuras de datos dentro de una aplicación de monedero virtual.

Se utilizaron listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios de búsqueda, árboles AVL, listas circulares, grafos dirigidos y recursividad.

Además de las operaciones básicas de un monedero, la aplicación implementa puntos, rangos, beneficios, comisiones, transacciones programadas, recurrencia, notificaciones, multimonedero, gastos, patrones, reversiones, reportes y validación de integridad.

La incorporación de una interfaz gráfica de escritorio permite realizar y consultar las principales operaciones del sistema de una manera más clara, manteniendo separadas la presentación y la lógica de negocio.

La prueba integral contiene actualmente **132 verificaciones** para comprobar el funcionamiento conjunto de los principales componentes del sistema.