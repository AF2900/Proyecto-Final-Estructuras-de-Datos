# Proyecto Final - Estructuras de Datos

## Simulador de Monedero Virtual con Sistema de Puntos

Proyecto desarrollado para la asignatura **Estructuras de Datos** de la Universidad del Quindío.

El sistema simula el funcionamiento de un monedero virtual en el que los clientes pueden administrar uno o varios monederos, realizar diferentes tipos de transacciones, acumular puntos, obtener rangos, canjear beneficios y consultar reportes.

Durante el desarrollo se aplican diferentes estructuras de datos como listas, pilas, colas, colas de prioridad, tablas hash, árboles binarios, árboles AVL, listas circulares y grafos dirigidos.

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

# Funcionalidades principales

El sistema permite realizar las siguientes operaciones:

- Registrar clientes.
- Crear múltiples monederos por cliente.
- Realizar depósitos.
- Realizar retiros.
- Realizar transferencias entre clientes.
- Realizar transferencias entre monederos del mismo cliente.
- Registrar gastos por categorías.
- Consultar el saldo de los monederos.
- Consultar el historial de transacciones.
- Revertir la última transacción mediante una pila.
- Acumular puntos por las transacciones realizadas.
- Calcular automáticamente el rango de cada cliente.
- Canjear puntos por beneficios.
- Programar depósitos.
- Programar transferencias.
- Crear transacciones recurrentes.
- Procesar transacciones programadas según su fecha.
- Generar notificaciones automáticas.
- Gestionar notificaciones pendientes mediante una cola.
- Recorrer el historial de notificaciones mediante una lista circular.
- Analizar relaciones entre clientes mediante grafos.
- Analizar relaciones entre monederos.
- Analizar patrones entre categorías de gasto.
- Verificar recursivamente la integridad de las transacciones.
- Generar reportes por cliente.
- Generar un reporte general del sistema.
- Utilizar una interfaz de consola para interactuar con el sistema.

---

# Arquitectura general

El proyecto está organizado de forma que cada grupo de clases tenga una responsabilidad específica.

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