# Proyecto Final - Estructuras de Datos

## Monedero Virtual con Interfaz Gráfica y Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la **Universidad del Quindío**.

La aplicación simula un monedero virtual avanzado mediante una **interfaz gráfica de escritorio desarrollada con text Swing**. Permite administrar clientes y monederos, realizar operaciones financieras, acumular puntos, obtener rangos, canjear beneficios, programar transacciones, gestionar notificaciones, analizar patrones de gasto, consultar reportes y visualizar el uso de diferentes estructuras de datos.

El proyecto fue desarrollado con **text 21 y Maven**.

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

- text 21
- text Swing
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
- Consultar toda la información mediante una interfaz gráfica.

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

Los componentes de la interfaz gráfica no acceden directamente a las estructuras internas. En su lugar, utilizan los métodos públicos disponibles en `MonederoVirtual`.

Esto permite mantener separada la presentación de la lógica del sistema.

---

# Estructura del proyecto

```text
co.edu.uniquindio.poo
│
├── Main.text
├── MonederoVirtual.text
│
├── PruebaBaseMonederoVirtual.text
├── PruebaReversiones.text
├── PruebaArbolesPuntos.text
├── PruebaTransaccionesProgramadas.text
├── PruebaNotificaciones.text
├── PruebaGrafos.text
├── PruebaGastos.text
├── PruebaIntegridad.text
├── PruebaBeneficios.text
├── PruebaReportes.text
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

`InterfazConsola` se conserva dentro del proyecto como una implementación anterior y como apoyo para pruebas manuales, pero el punto de entrada principal de la aplicación utiliza actualmente `InterfazGrafica`.

---

# Interfaz gráfica

La interfaz fue desarrollada con **text Swing** y está diseñada como una aplicación de escritorio.

La ventana principal utiliza:

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

La navegación principal se encuentra en un menú lateral.

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

## Inicio

El dashboard principal muestra:

- Cantidad de clientes.
- Cantidad de monederos.
- Saldo total del sistema.
- Estado de integridad.
- Accesos rápidos a las funciones principales.

---

## Clientes y monederos

Permite:

- Registrar clientes.
- Crear monederos.
- Consultar clientes registrados.
- Consultar puntos.
- Consultar rango.
- Visualizar los monederos existentes.
- Consultar sus saldos.

---

## Transacciones

La pantalla utiliza pestañas para separar las operaciones.

Incluye:

```text
Depósito
Retiro
Transferencia
Gasto
Consultas
```

Desde la pestaña de consultas es posible visualizar:

- Saldo.
- Historial de transacciones.
- Código de transacción.
- Fecha.
- Tipo.
- Monto.
- Comisión.
- Origen.
- Destino.
- Categoría.
- Puntos.
- Estado de reversión.

---

# Modelo principal

## Cliente

`Cliente` representa a una persona registrada dentro del sistema.

Cada cliente mantiene:

- Identificación.
- Nombre.
- Puntos.
- Rango.
- Monederos.
- Historial de transacciones.

Los monederos se almacenan utilizando:

```text
Map<String, Monedero>
```

El historial utiliza:

```text
List<Transaccion>
```

---

## Monedero

Un monedero contiene:

- Identificador.
- Nombre.
- Saldo.

Un cliente puede poseer varios monederos.

Ejemplo:

```text
Cliente C001
│
├── M001 - Principal
└── M002 - Ahorros
```

---

# Transacciones

Los tipos implementados son:

```text
DEPOSITO
RETIRO
TRANSFERENCIA
GASTO
```

Una `Transaccion` puede almacenar:

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

Los puntos se calculan utilizando el monto de la operación y no la comisión.

---

# Rangos

Los clientes se clasifican automáticamente según sus puntos:

| Rango | Puntos |
|---|---:|
| Bronce | 0 - 500 |
| Plata | 501 - 1000 |
| Oro | 1001 - 5000 |
| Platino | Más de 5000 |

El rango puede cambiar cuando:

- Se generan puntos.
- Se revierten transacciones.
- Se utilizan puntos para beneficios.

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

La comisión queda almacenada dentro de la transacción para poder ser restaurada correctamente durante una reversión.

---

# Beneficios

Los puntos se pueden canjear por tres beneficios:

| Puntos | Beneficio |
|---:|---|
| 100 | Reducción del 10 % de la comisión en transferencias |
| 500 | Un mes sin cargos por retiros |
| 1000 | Bono de 50 unidades |

La interfaz gráfica permite consultar:

- Puntos disponibles.
- Rango.
- Beneficios activos.
- Historial de canjes.
- Fecha de vencimiento.
- Monedero beneficiado.

---

## Beneficio de 100 puntos

Reduce un 10 % sobre la comisión de transferencia calculada.

Ejemplo:

```text
Transferencia: 1000
Comisión base: 10
Reducción:      1
Comisión final: 9
```

---

## Beneficio de 500 puntos

Permite realizar retiros sin comisión durante un mes.

```text
fechaVencimiento = fechaCanje + 1 mes
```

---

## Beneficio de 1000 puntos

Agrega:

```text
50 unidades
```

al monedero seleccionado.

El saldo se modifica directamente para evitar que el bono genere puntos como si fuera un depósito.

---

# Reversiones y pila

Las operaciones reversibles utilizan:

```text
Deque<Transaccion>
```

implementado mediante:

```text
ArrayDeque
```

La estructura trabaja con:

```text
LIFO
Last In, First Out
```

La última operación realizada es la primera que puede revertirse.

La interfaz muestra la transacción ubicada actualmente en el tope de la pila.

---

## Reversión de depósito

Retira el monto depositado y descuenta los puntos generados.

---

## Reversión de retiro

Devuelve:

```text
monto + comisión
```

y elimina los puntos generados por el retiro.

---

## Reversión de transferencia

El destino devuelve el dinero recibido.

El origen recupera:

```text
monto + comisión
```

También se actualizan los puntos y grafos asociados.

---

## Reversión de gasto

Devuelve el saldo y actualiza el patrón de gasto.

---

# Transacciones programadas

Las operaciones programadas utilizan:

```text
PriorityQueue<TransaccionProgramada>
```

Se pueden programar:

- Depósitos.
- Transferencias.

La prioridad se determina mediante:

1. Fecha más cercana.
2. Código en caso de empate.

La interfaz permite:

- Crear depósitos programados.
- Crear transferencias programadas.
- Indicar fecha y hora.
- Definir recurrencia.
- Consultar las operaciones en orden.
- Consultar la siguiente operación.
- Cancelar operaciones.
- Procesar operaciones cuya fecha ya llegó.

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

la transacción se vuelve a insertar en la cola de prioridad con una nueva fecha.

---

# Notificaciones

El sistema utiliza simultáneamente dos estructuras.

## Cola FIFO

Las notificaciones pendientes se almacenan utilizando:

```text
Queue<Notificacion>
```

implementada con:

```text
ArrayDeque
```

Funciona mediante:

```text
FIFO
First In, First Out
```

La primera notificación recibida es la primera en ser atendida.

---

## Lista circular

El historial completo utiliza una implementación propia:

```text
ListaCircular<T>
NodoCircular<T>
```

La interfaz permite recorrer las notificaciones de forma circular.

Cuando una notificación es atendida:

```text
Sale de la cola de pendientes.
Permanece en la lista circular.
```

---

# Gastos y patrones

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
No cobran comisión.
No generan puntos.
```

`GestorPatronesGasto` utiliza un grafo para analizar relaciones entre categorías consecutivas.

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
- Categoría de mayor gasto.
- Última categoría.
- Principal categoría siguiente.
- Monto de las transiciones.
- Cantidad de transiciones.

---

# Grafos dirigidos

El sistema utiliza `GrafoDirigido` con listas de adyacencia.

Cada arista registra:

- Destino.
- Monto acumulado.
- Cantidad de transacciones.

---

## Grafo de clientes

Representa transferencias entre clientes diferentes.

Ejemplo:

```text
C001 ───▶ C002
```

Permite analizar:

- Total transferido.
- Número de transferencias.
- Principal destino.
- Existencia de caminos.
- Recorrido BFS.

---

## Grafo de monederos

Representa relaciones entre monederos.

Un vértice tiene la forma:

```text
C001:M001
```

Esto permite representar también transferencias internas.

```text
C001:M001 ───▶ C001:M002
```

---

# BFS

El recorrido en anchura utiliza:

```text
BFS
Breadth First Search
```

y se apoya en:

```text
Queue<String>
Set<String>
```

Su complejidad es:

```text
O(V + E)
```

---

# HashMap

Se utilizan tablas hash para búsquedas rápidas.

Ejemplos:

```text
Identificación → Cliente
Id → Monedero
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

# Árbol binario de búsqueda

`ArbolBinarioPuntos` organiza los clientes según sus puntos.

Si dos clientes tienen la misma puntuación, se utiliza la identificación como criterio de desempate.

El recorrido inorden permite obtenerlos de menor a mayor puntuación.

Complejidad:

```text
Promedio: O(log n)
Peor caso: O(n)
```

---

# Árbol AVL

`ArbolAVLClientes` mantiene el árbol balanceado mediante rotaciones.

Casos manejados:

```text
Izquierda - Izquierda
Derecha - Derecha
Izquierda - Derecha
Derecha - Izquierda
```

Complejidad aproximada:

```text
O(log n)
```

La interfaz permite comparar:

```text
Altura BST
Altura AVL
Recorrido BST
Recorrido AVL
```

---

# Recursividad e integridad

`GestorIntegridad` utiliza recursividad para revisar los historiales.

Entre las condiciones verificadas se encuentran:

- Código válido.
- Código no repetido.
- Fecha válida.
- Tipo válido.
- Monto positivo.
- Comisión válida.
- Cliente de origen.
- Monedero de origen.
- Datos de destino.
- Categoría de gasto.
- Coherencia de puntos.
- Coherencia de comisiones.

La interfaz gráfica permite consultar:

- Integridad del sistema.
- Integridad de un cliente.
- Número de transacciones válidas.
- Primera transacción inválida.

---

# Reportes

## Reporte individual

Incluye:

- Identificación.
- Nombre.
- Cantidad de monederos.
- Saldo total.
- Número de transacciones.
- Transacciones activas.
- Transacciones revertidas.
- Puntos.
- Rango.
- Total de gastos.
- Categoría principal.
- Total transferido.
- Beneficios.
- Notificaciones.
- Estado de integridad.

---

## Reporte general

Incluye:

- Clientes.
- Monederos.
- Saldo total.
- Transacciones únicas.
- Transacciones activas.
- Transacciones revertidas.
- Total de gastos.
- Beneficios.
- Notificaciones.
- Integridad general.

Las transferencias no se cuentan dos veces aunque aparezcan en el historial de origen y destino.

---

# Estructuras de datos utilizadas

| Estructura | Uso principal |
|---|---|
| ArrayList | Historiales y colecciones |
| HashMap | Búsquedas por claves |
| Deque / ArrayDeque | Pila de reversiones |
| Queue / ArrayDeque | Notificaciones pendientes |
| PriorityQueue | Transacciones programadas |
| ListaCircular | Historial de notificaciones |
| Árbol BST | Clientes ordenados por puntos |
| Árbol AVL | Clientes ordenados y balanceados |
| GrafoDirigido | Clientes, monederos y patrones |
| HashSet | Visitados y códigos |
| Recursividad | Integridad del sistema |

---

# Complejidades principales

| Operación | Complejidad aproximada |
|---|---:|
| Buscar cliente en HashMap | O(1) promedio |
| Buscar monedero | O(1) promedio |
| Consultar puntos | O(1) promedio |
| Agregar en ArrayList | O(1) amortizado |
| Búsqueda lineal | O(n) |
| Push de pila | O(1) |
| Peek de pila | O(1) |
| Pop de pila | O(1) |
| Queue offer | O(1) |
| Queue peek | O(1) |
| Queue poll | O(1) |
| PriorityQueue peek | O(1) |
| PriorityQueue offer | O(log n) |
| PriorityQueue poll | O(log n) |
| PriorityQueue remove(Object) | O(n) |
| Lista circular agregar | O(1) |
| Lista circular siguiente | O(1) |
| BST promedio | O(log n) |
| BST peor caso | O(n) |
| AVL | O(log n) |
| BFS | O(V + E) |
| Recorrido inorden | O(n) |
| Integridad recursiva | O(n) |

---

# Pruebas

El proyecto contiene pruebas independientes para diferentes módulos:

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

Las pruebas utilizan:

```text
public static void main(String[] args)
```

y generan `AssertionError` cuando una condición no se cumple.

---

# Prueba integral

`PruebaSistemaCompleto.text` valida el funcionamiento combinado del sistema.

La prueba comprueba, entre otros aspectos:

- Clientes.
- Multimonedero.
- Depósitos.
- Retiros.
- Transferencias.
- Transferencias internas.
- Comisiones.
- Gastos.
- Puntos.
- Rangos.
- Beneficios.
- HashMap.
- BST.
- AVL.
- PriorityQueue.
- Recurrencia.
- Notificaciones.
- Queue.
- Lista circular.
- Grafos.
- BFS.
- Recursividad.
- Reversiones.
- Reportes.

La prueba integral actual contiene:

```text
132 verificaciones
```

Si todas se cumplen finaliza mostrando:

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

El proyecto utiliza:

```text
maven.compiler.source = 21
maven.compiler.target = 21
```

---

## Abrir

En IntelliJ IDEA abrir la carpeta:

```text
Proyecto Final Estructuras de Datos
```

Esta carpeta contiene:

```text
pom.xml
```

---

## Compilar

Desde la carpeta donde se encuentra `pom.xml`:

```bash
mvn clean compile
```

---

## Ejecutar la aplicación

Ejecutar:

```text
Main.text
```

`Main` inicia la interfaz gráfica utilizando el Event Dispatch Thread de Swing:

```text
SwingUtilities.invokeLater(...)
```

y crea:

```text
InterfazGrafica
```

Por lo tanto, la aplicación inicia directamente en la ventana gráfica.

---

## Ejecutar prueba integral

Ejecutar:

```text
PruebaSistemaCompleto.text
```

La prueba debe finalizar sin lanzar `AssertionError`.

---

# Flujo recomendado para demostrar el proyecto

Durante la presentación se puede utilizar el siguiente flujo:

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

12. Mostrar PriorityQueue.

13. Consultar notificaciones.

14. Mostrar Queue y ListaCircular.

15. Consultar BST y AVL.

16. Mostrar grafos.

17. Revertir la última operación.

18. Mostrar reportes.

19. Verificar integridad.
```

Este flujo permite demostrar tanto las funcionalidades del monedero como las estructuras de datos que se encuentran detrás de cada operación.

---

# Decisiones de diseño

## Separación de interfaz y lógica

La interfaz gráfica se encarga exclusivamente de:

- Capturar información.
- Mostrar datos.
- Navegar entre módulos.
- Mostrar mensajes al usuario.

La lógica financiera permanece dentro de los gestores.

Por ejemplo:

```text
sistema.depositar(...)
sistema.retirar(...)
sistema.transferir(...)
sistema.canjearBeneficio(...)
```

Esto evita duplicar reglas financieras dentro de Swing.

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

## Bono

El beneficio de 1000 puntos agrega:

```text
50 unidades
```

directamente al monedero.

---

## Saldo bajo

Cuando un monedero queda por debajo de:

```text
100 unidades
```

se genera una notificación de saldo bajo.

---

# Resultado

El sistema integra las estructuras de datos dentro de un mismo flujo de negocio.

Una transferencia puede modificar:

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
Grafo de clientes
Grafo de monederos
Notificaciones
Reportes
```

Una reversión actualiza nuevamente las estructuras relacionadas y devuelve también la comisión que fue cobrada.

La interfaz gráfica permite visualizar y utilizar estas funcionalidades sin modificar la lógica interna de los gestores.

---

# Conclusión

El proyecto permitió aplicar las estructuras de datos estudiadas durante el curso dentro de una aplicación funcional de monedero virtual.

Se utilizaron listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios de búsqueda, árboles AVL, listas circulares, grafos dirigidos y recursividad.

Además de las operaciones básicas, la aplicación incluye puntos, rangos, beneficios, comisiones, transacciones programadas, recurrencia, notificaciones, multimonedero, gastos, patrones, reversiones, reportes y validación de integridad.

La incorporación de una interfaz gráfica de escritorio permite realizar y consultar las principales operaciones del sistema de una manera más clara, manteniendo separadas la presentación y la lógica de negocio.

La prueba integral contiene **132 verificaciones** para validar el funcionamiento conjunto de los principales componentes.