# Proyecto Final - Estructuras de Datos

## Monedero Virtual con Interfaz Gráfica y Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la **Universidad del Quindío**.

La aplicación simula un monedero virtual mediante una interfaz gráfica de escritorio desarrollada con **Java Swing**.

El sistema permite administrar clientes y monederos, realizar operaciones financieras, acumular puntos, calcular rangos, canjear beneficios, programar transacciones, generar notificaciones, analizar relaciones mediante grafos, consultar reportes y aplicar diferentes estructuras de datos estudiadas durante el curso.

Una parte importante del proyecto consiste en la implementación propia de estructuras como:

- Pilas.
- Colas.
- Colas de prioridad.
- Árboles binarios de búsqueda.
- Árboles AVL.
- Listas circulares.
- Grafos dirigidos.

El proyecto fue desarrollado utilizando **Java 21 y Maven**.

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

- Java 21.
- Java Swing.
- Maven.
- IntelliJ IDEA.
- Git.
- GitHub.

El proyecto utiliza principalmente funcionalidades estándar de Java y no requiere dependencias externas adicionales.

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
├── Main.java
├── MonederoVirtual.java
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
│   ├── cola
│   │   ├── Cola.java
│   │   ├── ColaLlena.java
│   │   ├── ColaPrioridad.java
│   │   └── Nodo.java
│   │
│   ├── grafo
│   │   ├── AristaGrafo.java
│   │   └── GrafoDirigido.java
│   │
│   ├── listacircular
│   │   ├── ListaCircular.java
│   │   └── NodoCircular.java
│   │
│   └── pila
│       ├── Nodo.java
│       └── Pila.java
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

`InterfazConsola.java` permanece dentro del proyecto como una implementación alternativa.

Sin embargo, actualmente el punto de entrada principal utiliza:

```text
InterfazGrafica.java
```

---

# Interfaz gráfica

La interfaz principal fue construida utilizando **Java Swing**.

`Main.java` inicia la aplicación mediante:

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
JTextField
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

Comple