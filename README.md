# Proyecto Final - Estructuras de Datos

## Monedero Virtual con Interfaz Gráfica y Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la **Universidad del Quindío**.

La aplicación simula un monedero virtual mediante una interfaz gráfica de escritorio desarrollada con **text Swing**.

El sistema permite administrar clientes y monederos, realizar operaciones financieras, acumular puntos, calcular rangos, canjear beneficios, programar transacciones, generar notificaciones, analizar relaciones mediante grafos, consultar reportes y aplicar diferentes estructuras de datos estudiadas durante el curso.

Una parte importante del proyecto consiste en la implementación propia de estructuras como:

- Pilas.
- Colas.
- Colas de prioridad.
- Árboles binarios de búsqueda.
- Árboles AVL.
- Listas circulares.
- Grafos dirigidos.

El proyecto fue desarrollado utilizando **text 21 y Maven**.

---

# Integrantes

- Federico Cruz
- Johan Steven Alvarez
- Adrián Fernando Pérez Naranjo

---

# Objetivo

Desarrollar una aplicación de escritorio que simule un monedero virtual y permita aplicar de manera práctica diferentes estructuras de datos vistas durante la asignatura.

El sistema integra:

- Listas.
- Pilas.
- Colas.
- Colas de prioridad.
- Tablas hash.
- Árboles binarios de búsqueda.
- Árboles AVL.
- Listas circulares.
- Grafos dirigidos.
- Recursividad.

Además, se busca relacionar estas estructuras con funcionalidades reales de una aplicación, evitando utilizarlas únicamente como ejercicios aislados.

---

# Tecnologías utilizadas

- text 21.
- text Swing.
- Maven.
- IntelliJ IDEA.
- Git.
- GitHub.

El proyecto utiliza principalmente funcionalidades estándar de text y no requiere dependencias externas adicionales.

---

# Funcionalidades principales

La aplicación permite:

- Registrar clientes.
- Crear múltiples monederos por cliente.
- Consultar clientes registrados.
- Realizar depósitos.
- Realizar retiros.
- Realizar transferencias entre clientes.
- Realizar transferencias entre monederos del mismo cliente.
- Registrar gastos por categoría.
- Consultar saldos.
- Consultar historiales de transacciones.
- Aplicar comisiones.
- Acumular puntos.
- Calcular automáticamente el rango del cliente.
- Canjear beneficios.
- Revertir la última transacción.
- Programar depósitos.
- Programar transferencias.
- Configurar operaciones recurrentes.
- Procesar transacciones programadas.
- Cancelar transacciones programadas.
- Generar notificaciones automáticas.
- Manejar notificaciones pendientes mediante una cola.
- Mantener el historial mediante una lista circular.
- Analizar relaciones entre clientes.
- Analizar relaciones entre monederos.
- Registrar patrones de gasto.
- Realizar recorridos BFS.
- Consultar árboles BST y AVL.
- Verificar la integridad de las transacciones utilizando recursividad.
- Generar reportes por cliente.
- Generar un reporte general del sistema.
- Utilizar todas las funciones desde una interfaz gráfica.

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

La clase:

```text
MonederoVirtual
```

funciona como fachada principal del sistema.

Los componentes de la interfaz gráfica interactúan con esta clase en lugar de acceder directamente a cada gestor.

Esto permite mantener separadas:

```text
Presentación
     │
     ▼
Lógica del negocio
     │
     ▼
Estructuras de datos
```

---

# Estructura actual del proyecto

```text
co.edu.uniquindio.poo
│
├── Main.text
├── MonederoVirtual.text
├── PruebaSistemaCompleto.text
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
│   ├── cola
│   │   ├── Cola.text
│   │   ├── ColaLlena.text
│   │   ├── ColaPrioridad.text
│   │   └── Nodo.text
│   │
│   ├── grafo
│   │   ├── AristaGrafo.text
│   │   └── GrafoDirigido.text
│   │
│   ├── listacircular
│   │   ├── ListaCircular.text
│   │   └── NodoCircular.text
│   │
│   └── pila
│       ├── Nodo.text
│       └── Pila.text
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
    ├── InterfazConsola.text
    ├── InterfazGrafica.text
    ├── PanelClientesMonederos.text
    ├── PanelTransacciones.text
    ├── PanelReversiones.text
    ├── PanelPuntosBeneficios.text
    ├── PanelProgramadas.text
    ├── PanelNotificaciones.text
    ├── PanelReportes.text
    └── PanelEstructuras.text
```

`InterfazConsola.text` permanece dentro del proyecto como una implementación alternativa.

Sin embargo, actualmente el punto de entrada principal utiliza:

```text
InterfazGrafica.text
```

---

# Interfaz gráfica

La interfaz principal fue construida utilizando **text Swing**.

`Main.text` inicia la aplicación mediante:

```text
SwingUtilities.invokeLater(...)
```

y crea una instancia de:

```text
InterfazGrafica
```

La interfaz utiliza componentes como:

```text
JFrame
JPanel
JTable
JTabbedPane
JComboBox
JtextField
JSpinner
JOptionPane
CardLayout
BorderLayout
```

Los principales módulos disponibles son:

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

# Clientes

La clase:

```text
Cliente
```

representa una persona registrada dentro del sistema.

Cada cliente almacena:

- Identificación.
- Nombre.
- Puntos.
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

# Monederos

La clase:

```text
Monedero
```

representa una cuenta perteneciente a un cliente.

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

# Transacciones

La clase:

```text
Transaccion
```

representa una operación financiera realizada dentro del sistema.

Los tipos disponibles son:

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
- Cliente origen.
- Monedero origen.
- Cliente destino.
- Monedero destino.
- Comisión.
- Puntos generados.
- Categoría.
- Categoría anterior.
- Estado de reversión.

Las transacciones revertidas permanecen en el historial, pero quedan identificadas como revertidas.

---

# Sistema de puntos

Los puntos se calculan utilizando únicamente el monto de la operación.

La comisión no genera puntos adicionales.

Las reglas implementadas son:

```text
Depósito
1 punto por cada 100 unidades.

Retiro
2 puntos por cada 100 unidades.

Transferencia
3 puntos por cada 100 unidades.

Gasto
No genera puntos.
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

---

# Rangos

Los clientes cambian automáticamente de rango dependiendo de sus puntos.

| Rango | Puntos |
|---|---:|
| Bronce | 0 - 500 |
| Plata | 501 - 1000 |
| Oro | 1001 - 5000 |
| Platino | Más de 5000 |

El rango puede cambiar cuando:

- Se generan puntos.
- Se restan puntos.
- Se revierte una transacción.
- Se canjea un beneficio.

---

# Comisiones

El sistema aplica una comisión base del:

```text
1 %
```

para:

```text
RETIRO
TRANSFERENCIA
```

Ejemplo:

```text
Transferencia:     1000
Comisión:            10
Total descontado:  1010
Destino recibe:    1000
```

La comisión queda almacenada dentro de la transacción.

Esto es importante porque durante una reversión se devuelve exactamente la comisión que había sido cobrada.

---

# Beneficios

Los puntos pueden utilizarse para canjear beneficios.

| Puntos | Beneficio |
|---:|---|
| 100 | Reducción del 10 % en la comisión por transferencias |
| 500 | Un mes sin comisión en retiros |
| 1000 | Bono de 50 unidades en un monedero |

---

## Beneficio de 100 puntos

```text
DESCUENTO_TRANSFERENCIA
```

Reduce un:

```text
10 %
```

sobre la comisión calculada.

Ejemplo:

```text
Transferencia: 1000

Comisión normal:
1000 × 1 % = 10

Descuento:
10 × 10 % = 1

Comisión final:
9
```

---

## Beneficio de 500 puntos

```text
RETIROS_SIN_CARGO
```

permite realizar retiros sin comisión durante:

```text
1 mes
```

El vencimiento se calcula utilizando:

```text
fecha de canje + 1 mes
```

---

## Beneficio de 1000 puntos

```text
BONO_SALDO
```

agrega:

```text
50 unidades
```

directamente al monedero seleccionado.

Este bono no se procesa como un depósito normal porque eso volvería a generar puntos.

---

# Pila propia

Las reversiones utilizan una implementación propia:

```text
Pila<T>
```

con nodos enlazados:

```text
Nodo<T>
```

La estructura almacena una referencia llamada:

```text
cima
```

y trabaja siguiendo el principio:

```text
LIFO
Last In, First Out
```

La última transacción registrada es la primera que puede ser revertida.

Las principales operaciones son:

```text
push()
peek()
pop()
isEmpty()
getSize()
```

Ejemplo conceptual:

```text
           cima
             ↓
       Transacción 4
             ↓
       Transacción 3
             ↓
       Transacción 2
             ↓
       Transacción 1
```

---

# Reversiones

`GestorReversiones` utiliza:

```text
Pila<Transaccion>
```

para almacenar las transacciones reversibles.

---

## Reversión de depósito

Al revertir un depósito:

- Se retira el monto depositado.
- Se restan los puntos generados.

---

## Reversión de retiro

Se devuelve:

```text
monto + comisión
```

y se restan los puntos generados.

---

## Reversión de transferencia

El destino devuelve:

```text
monto
```

y el origen recupera:

```text
monto + comisión
```

También se actualizan:

- Puntos.
- Grafo de clientes.
- Grafo de monederos.

---

## Reversión de gasto

Se devuelve el monto al monedero.

También se actualiza el patrón de gasto correspondiente.

---

# Cola propia

El proyecto contiene una implementación propia:

```text
Cola<T>
```

mediante nodos enlazados.

La cola conserva referencias al:

```text
primer nodo
último nodo
```

y trabaja según el principio:

```text
FIFO
First In, First Out
```

Las principales operaciones son:

```text
encolar()
desencolar()
verPrimero()
estaVacia()
getSize()
eliminar()
obtenerElementos()
```

También existe:

```text
ColaLlena
```

para controlar el caso en el que se alcanza el límite definido para la cola.

---

# Notificaciones

Las notificaciones utilizan dos estructuras diferentes.

## Cola de pendientes

Las notificaciones pendientes utilizan:

```text
Cola<Notificacion>
```

La primera notificación recibida es la primera que será atendida.

Ejemplo:

```text
Entrada

N1
N2
N3

Salida

N1 → N2 → N3
```

Cuando una notificación es atendida:

```text
sale de la cola de pendientes
```

pero permanece almacenada en el historial.

---

## Lista circular

El historial completo de notificaciones utiliza:

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

Esto permite recorrer continuamente el historial.

---

# Saldo bajo

Después de determinadas operaciones se verifica el saldo del monedero.

Si el saldo queda por debajo de:

```text
100 unidades
```

se genera una notificación:

```text
SALDO_BAJO
```

---

# Cola de prioridad propia

Las transacciones programadas utilizan una implementación propia:

```text
ColaPrioridad<T>
```

La estructura utiliza nodos enlazados.

A diferencia de una cola FIFO normal, al insertar un elemento se busca la posición correspondiente según su prioridad.

Para las transacciones programadas se utiliza:

```text
ColaPrioridad<TransaccionProgramada>
```

---

# Transacciones programadas

Se pueden programar:

- Depósitos.
- Transferencias.

Cada operación programada contiene:

- Código.
- Tipo.
- Cliente origen.
- Monedero origen.
- Cliente destino.
- Monedero destino.
- Monto.
- Fecha de ejecución.
- Frecuencia.

La prioridad se determina por:

```text
1. Fecha de ejecución.
2. Código en caso de empate.
```

Por ejemplo:

```text
TP002 → 11:00
TP001 → 09:00
```

aunque `TP002` sea insertada primero, la estructura queda:

```text
TP001
TP002
```

porque `TP001` debe ejecutarse antes.

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

después de ejecutarse:

```text
se actualiza su fecha
        ↓
se vuelve a insertar
        ↓
ColaPrioridad
```

Las transacciones programadas utilizan la misma lógica financiera que las operaciones normales:

- Comisión.
- Puntos.
- Beneficios.
- Grafos.
- Notificaciones.

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
disminuyen saldo
no generan puntos
no cobran comisión
```

---

# Patrones de gasto

`GestorPatronesGasto` analiza relaciones entre categorías consecutivas.

Ejemplo:

```text
ALIMENTACION
      │
      ▼
TRANSPORTE
```

El sistema permite consultar:

- Total de gastos.
- Total por categoría.
- Categoría con mayor gasto.
- Última categoría.
- Principal categoría siguiente.
- Monto entre categorías.
- Cantidad de transiciones.

---

# Grafos dirigidos

El proyecto utiliza una estructura:

```text
GrafoDirigido
```

basada en listas de adyacencia.

Cada arista:

```text
AristaGrafo
```

almacena:

- Destino.
- Monto acumulado.
- Cantidad de transacciones.

---

# Grafo de clientes

Representa las transferencias realizadas entre clientes diferentes.

Ejemplo:

```text
C001 ─────▶ C002
```

Permite consultar:

- Monto transferido.
- Cantidad de transferencias.
- Total transferido.
- Principal destino.
- Existencia de caminos.
- Recorrido BFS.

Las transferencias entre monederos del mismo cliente no crean una relación entre clientes.

---

# Grafo de monederos

Las transferencias también se representan entre monederos.

Los vértices utilizan el formato:

```text
C001:M001
```

Ejemplo:

```text
C001:M001 ─────▶ C001:M002
```

Esto permite representar transferencias internas entre monederos del mismo cliente.

---

# BFS

El recorrido en anchura:

```text
BFS
Breadth First Search
```

utiliza la cola propia:

```text
Cola<String>
```

junto con:

```text
Set<String>
```

La cola determina el orden FIFO en el que se procesan los vértices.

El conjunto evita visitar el mismo vértice más de una vez.

Ejemplo:

```text
        A
       / \
      B   C
     /
    D
```

El recorrido BFS sería:

```text
A → B → C → D
```

Complejidad:

```text
O(V + E)
```

donde:

```text
V = vértices
E = aristas
```

---

# HashMap

Se utilizan tablas hash para realizar búsquedas mediante claves.

Ejemplos:

```text
Identificación → Cliente

Id monedero → Monedero

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

Se utiliza para colecciones que requieren recorridos o almacenamiento secuencial.

Ejemplo:

```text
List<Transaccion>
```

para almacenar el historial de un cliente.

Complejidades aproximadas:

```text
Agregar al final
O(1) amortizado

Acceder mediante índice
O(1)

Búsqueda lineal
O(n)
```

---

# Árbol binario de búsqueda - BST

La clase:

```text
ArbolBinarioPuntos
```

implementa un árbol binario de búsqueda.

Cada nodo:

```text
NodoArbolPuntos
```

contiene:

```text
elemento
hijoIzquierdo
hijoDerecho
padre
```

Los clientes se organizan principalmente por:

```text
puntos
```

Cuando dos clientes tienen los mismos puntos se utiliza:

```text
identificación
```

como criterio de desempate.

---

## Inserción

Si el elemento es menor:

```text
subárbol izquierdo
```

Si es mayor:

```text
subárbol derecho
```

---

## Recorridos

El árbol implementa:

```text
Inorden
Preorden
Postorden
```

El recorrido inorden permite obtener los clientes ordenados por puntos.

---

## Eliminación

Se consideran tres casos:

```text
1. Nodo hoja.

2. Nodo con un hijo.

3. Nodo con dos hijos.
```

Cuando el nodo tiene dos hijos se utiliza:

```text
máximo del subárbol izquierdo
```

para reemplazar el elemento eliminado.

---

## Complejidad

```text
Promedio
O(log n)

Peor caso
O(n)
```

---

# Árbol AVL

La clase:

```text
ArbolAVLClientes
```

implementa un árbol binario de búsqueda balanceado.

Cada nodo:

```text
NodoAVLCliente
```

almacena:

```text
elemento
hijoIzquierdo
hijoDerecho
padre
altura
factor de equilibrio
```

---

# Factor de equilibrio

La convención utilizada en el proyecto es:

```text
FE =
altura del subárbol derecho
-
altura del subárbol izquierdo
```

Por lo tanto:

```text
FE = -2
árbol cargado hacia la izquierda

FE = +2
árbol cargado hacia la derecha
```

Un nodo correctamente balanceado debe tener:

```text
FE = -1
FE = 0
FE = 1
```

---

# Rotaciones AVL

El árbol contempla los cuatro casos principales:

```text
Izquierda - Izquierda
→ rotación derecha

Derecha - Derecha
→ rotación izquierda

Izquierda - Derecha
→ rotación izquierda
→ rotación derecha

Derecha - Izquierda
→ rotación derecha
→ rotación izquierda
```

Después de cada operación se actualizan:

```text
altura
factor de equilibrio
referencia al padre
```

---

# Eliminación AVL

La eliminación considera:

- Nodo hoja.
- Nodo con un hijo.
- Nodo con dos hijos.

Cuando existen dos hijos se utiliza:

```text
máximo del subárbol izquierdo
```

Después de eliminar se recalculan:

```text
altura
FE
balance
```

y se realizan las rotaciones necesarias.

---

# Puntos, BST y AVL

`GestorPuntos` mantiene sincronizados:

```text
Cliente
HashMap de puntos
BST
AVL
```

Cuando los puntos cambian:

```text
1. Se elimina el cliente de los árboles.

2. Se modifican sus puntos.

3. Se actualiza el HashMap.

4. Se vuelve a insertar en BST.

5. Se vuelve a insertar en AVL.
```

Esto permite conservar correctamente el orden de los árboles.

---

# Recursividad

La recursividad se utiliza principalmente en:

- Inserción en árboles.
- Búsqueda en árboles.
- Recorridos.
- Cálculo de altura.
- Eliminación.
- Verificación de integridad.

---

# Integridad

`GestorIntegridad` verifica las transacciones del sistema.

Entre las validaciones se encuentran:

- Código válido.
- Código no repetido.
- Fecha válida.
- Tipo válido.
- Monto mayor que cero.
- Comisión no negativa.
- Cliente origen válido.
- Monedero origen válido.
- Datos de destino válidos.
- Categoría válida para gastos.
- Coherencia de puntos.
- Coherencia de comisiones.

También permite:

- Verificar un cliente.
- Verificar todo el sistema.
- Contar transacciones válidas.
- Encontrar la primera transacción inválida.

---

# Reportes

## Reporte por cliente

`ReporteCliente` contiene información como:

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
- Categoría principal.
- Total transferido.
- Cantidad de beneficios.
- Cantidad de notificaciones.
- Estado de integridad.

---

## Reporte general

`ReporteSistema` contiene:

- Cantidad de clientes.
- Cantidad de monederos.
- Saldo total.
- Cantidad de transacciones únicas.
- Transacciones activas.
- Transacciones revertidas.
- Total de gastos.
- Cantidad de beneficios.
- Cantidad de notificaciones.
- Estado de integridad.

Las transacciones que aparecen en más de un historial se identifican mediante su código para evitar contarlas dos veces.

---

# Estructuras de datos utilizadas

| Estructura | Implementación | Uso |
|---|---|---|
| ArrayList | text | Historiales y colecciones |
| HashMap | text | Búsquedas mediante claves |
| HashSet | text | Control de elementos visitados |
| Pila | Propia | Reversiones |
| Cola | Propia | Notificaciones y BFS |
| ColaPrioridad | Propia | Transacciones programadas |
| ListaCircular | Propia | Historial de notificaciones |
| BST | Propia | Clientes ordenados por puntos |
| AVL | Propia | Clientes ordenados y balanceados |
| GrafoDirigido | Propia | Clientes, monederos y patrones |
| Recursividad | Implementación propia | Árboles e integridad |

---

# Complejidades principales

| Operación | Complejidad aproximada |
|---|---:|
| Buscar en HashMap | O(1) promedio |
| Insertar en HashMap | O(1) promedio |
| Agregar al final de ArrayList | O(1) amortizado |
| Búsqueda lineal | O(n) |
| `push()` en Pila | O(1) |
| `peek()` en Pila | O(1) |
| `pop()` en Pila | O(1) |
| `encolar()` en Cola | O(1) |
| `verPrimero()` en Cola | O(1) |
| `desencolar()` en Cola | O(1) |
| `eliminar()` en Cola | O(n) |
| `encolar()` en ColaPrioridad | O(n) |
| `verPrimero()` en ColaPrioridad | O(1) |
| `desencolar()` en ColaPrioridad | O(1) |
| `eliminar()` en ColaPrioridad | O(n) |
| Agregar en ListaCircular | O(1) |
| Siguiente en ListaCircular | O(1) |
| BST promedio | O(log n) |
| BST peor caso | O(n) |
| AVL búsqueda | O(log n) |
| AVL inserción | O(log n) |
| AVL eliminación | O(log n) |
| Recorrido de árbol | O(n) |
| BFS | O(V + E) |

Es importante destacar que la implementación propia de:

```text
ColaPrioridad
```

mantiene los nodos ordenados durante la inserción.

Por esta razón:

```text
encolar()
```

tiene una complejidad de:

```text
O(n)
```

mientras que consultar o retirar el primer elemento es:

```text
O(1)
```

---

# Prueba del sistema

Actualmente el proyecto utiliza una única clase de prueba integral:

```text
PruebaSistemaCompleto.text
```

Esta clase ejecuta un escenario completo utilizando las diferentes funcionalidades del sistema.

Las pruebas anteriores por módulo fueron eliminadas para mantener una única validación integral del proyecto.

---

# Elementos comprobados

`PruebaSistemaCompleto` verifica:

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
Cola de prioridad
Operaciones recurrentes
Notificaciones
Cola FIFO
Lista circular
Grafos
BFS con cola propia
Recursividad
Pila LIFO
Reversiones
Reportes
Integridad
```

---

# Cantidad de pruebas

Actualmente:

```text
98 verificaciones booleanas
+
34 verificaciones numéricas
=
132 verificaciones
```

Si todas las condiciones se cumplen, la ejecución termina mostrando:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 132
```

---

# Escenario de la prueba integral

La prueba crea:

```text
3 clientes
4 monederos
```

y realiza operaciones como:

- Depósitos.
- Transferencias.
- Transferencias internas.
- Gastos.
- Retiro.
- Canje de beneficio.
- Programación de transacciones.
- Ejecución de transacciones programadas.
- Recurrencia.
- Notificaciones.
- Reversiones.
- Actualización de árboles.
- Actualización de grafos.
- Generación de reportes.

---

# Comisiones verificadas

Entre las comprobaciones realizadas se encuentran:

```text
Transferencia de 1000
→ comisión = 10

Transferencia interna de 2000
→ comisión = 20

Retiro de 200
→ comisión = 2

Transferencia programada de 400
con beneficio del 10 %
→ comisión = 3.6
```

---

# Cómo ejecutar el proyecto

## Requisitos

Se necesita:

```text
JDK 21
Maven
```

Opcionalmente:

```text
IntelliJ IDEA
```

El `pom.xml` utiliza:

```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

---

# Abrir el proyecto

Se debe abrir la carpeta:

```text
Proyecto Final Estructuras de Datos
```

En esta carpeta se encuentra:

```text
pom.xml
```

---

# Compilar

Desde una terminal ubicada donde se encuentra el `pom.xml`:

```bash
mvn clean compile
```

---

# Ejecutar la interfaz gráfica

Ejecutar:

```text
Main.text
```

El programa iniciará:

```text
InterfazGrafica
```

utilizando text Swing.

---

# Ejecutar la prueba integral

Ejecutar:

```text
PruebaSistemaCompleto.text
```

La ejecución debe terminar sin lanzar:

```text
AssertionError
```

y mostrar:

```text
PRUEBA INTEGRAL TERMINADA CORRECTAMENTE
Pruebas superadas: 132
```

---

# Flujo recomendado para demostrar el proyecto

Durante una presentación se puede utilizar el siguiente orden:

```text
1. Registrar clientes.

2. Crear monederos.

3. Realizar un depósito.

4. Mostrar puntos y rango.

5. Realizar una transferencia.

6. Mostrar la comisión.

7. Realizar un retiro.

8. Registrar un gasto.

9. Consultar patrones de gasto.

10. Canjear un beneficio.

11. Mostrar el efecto del beneficio.

12. Programar una transacción.

13. Consultar la cola de prioridad.

14. Consultar las notificaciones.

15. Mostrar la cola FIFO.

16. Mostrar la lista circular.

17. Consultar el BST.

18. Consultar el AVL.

19. Mostrar relaciones del grafo.

20. Mostrar el recorrido BFS.

21. Consultar la pila de reversiones.

22. Revertir la última transacción.

23. Consultar reportes.

24. Verificar la integridad del sistema.
```

---

# Decisiones de diseño

## Estructuras propias

Para reforzar los conceptos estudiados en la asignatura se implementaron directamente:

```text
Pila
Cola
Cola de prioridad
Lista circular
BST
AVL
Grafo dirigido
```

De esta forma es posible observar cómo funcionan internamente las operaciones principales de cada estructura.

---

## Separación de responsabilidades

La interfaz gráfica se encarga principalmente de:

- Recibir datos.
- Mostrar información.
- Mostrar tablas.
- Navegar entre módulos.
- Mostrar mensajes.

La lógica permanece en:

```text
MonederoVirtual
```

y los gestores.

---

## Comisión base

La comisión para:

```text
RETIRO
TRANSFERENCIA
```

es:

```text
1 %
```

---

## Puntos y comisión

Los puntos se calculan exclusivamente utilizando:

```text
monto
```

La comisión no genera puntos.

---

## Transferencias internas

Las transferencias entre monederos diferentes del mismo cliente:

```text
están permitidas
cobran comisión
generan puntos
se registran en el grafo de monederos
```

pero:

```text
no generan una relación en el grafo de clientes
```

---

## Gastos

Los gastos:

```text
no generan puntos
no cobran comisión
```

---

## Reversiones

Solo se revierte la operación que se encuentra:

```text
en la cima de la pila
```

siguiendo:

```text
LIFO
```

---

## Notificaciones

Las pendientes siguen:

```text
FIFO
```

mientras el historial utiliza:

```text
ListaCircular
```

---

## Transacciones programadas

Las operaciones se ordenan mediante:

```text
fecha de ejecución
```

y, en caso de empate:

```text
código
```

---

## Árbol AVL

La convención utilizada es:

```text
FE =
altura derecha
-
altura izquierda
```

---

# Resultado

El proyecto integra las estructuras de datos dentro de un mismo sistema.

Una sola transferencia puede modificar:

```text
Saldo
Comisión
Historial
Puntos
Rango
HashMap
BST
AVL
Pila
Grafos
Notificaciones
Reportes
```

Esto permite observar cómo diferentes estructuras pueden colaborar para resolver un problema más amplio.

---

# Conclusión

El proyecto permitió aplicar de manera práctica los principales conceptos estudiados en la asignatura de Estructuras de Datos.

Se desarrolló un monedero virtual que integra operaciones financieras con estructuras implementadas directamente dentro del proyecto, entre ellas pilas, colas, colas de prioridad, listas circulares, árboles binarios de búsqueda, árboles AVL y grafos dirigidos.

Además se utilizaron estructuras estándar como `ArrayList`, `HashMap` y `HashSet` cuando su propósito era servir como apoyo al modelo del sistema.

La implementación propia de las estructuras permite comprender mejor conceptos como:

```text
LIFO
FIFO
prioridad
nodos enlazados
recursividad
recorridos
balanceo AVL
BFS
grafos
```

La aplicación también incorpora una interfaz gráfica desarrollada con text Swing que permite utilizar las diferentes funcionalidades sin mezclar la presentación con la lógica del negocio.

Finalmente, `PruebaSistemaCompleto.text` realiza actualmente **132 verificaciones** que comprueban el funcionamiento conjunto de las principales funcionalidades y estructuras del proyecto.