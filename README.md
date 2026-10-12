Universidad del Valle de Guatemala
Curso: CC2008 - Programación Orientada a Objetos
Ciclo: 2, 2026

Descripción del proyecto

Este proyecto consiste en desarrollar un sistema de gestión de envíos para la empresa ficticia EnvíaGT, utilizando Java y los principios de programación orientada a objetos, principalmente herencia, encapsulamiento y polimorfismo.

El sistema permite registrar envíos, calcular sus costos, administrar vehículos, organizar despachos y controlar los intentos de entrega.

Objetivo

Implementar una aplicación de consola en Java que permita gestionar diferentes categorías de envíos, aplicando herencia y polimorfismo para organizar las responsabilidades y evitar la duplicación de código.

Funcionalidades

El programa permite realizar las siguientes operaciones:

Cotizar envíos según su categoría.

Registrar nuevos envíos.

Registrar vehículos de transporte.

Crear despachos.

Asignar y retirar envíos de los despachos.

Registrar la salida de los despachos.

Registrar entregas exitosas o fallidas.

Controlar los intentos de entrega.

Consultar información de envíos y vehículos.

Generar reportes de envíos, despachos e ingresos.

Categorías de envíos

El sistema maneja cuatro categorías:

1. Sobre

Peso máximo de 0.5 kg.

Costo urbano: Q20.

Costo departamental: Q35.

Máximo de 3 intentos de entrega.

2. Paquete

Se registra el peso y las dimensiones.

El costo depende del destino y del peso facturable.

Se utiliza el mayor valor entre el peso real y el volumétrico.

Máximo de 3 intentos de entrega.

3. Paquete frágil

Tiene las mismas características de un paquete.

Incluye Q15 adicionales por embalaje.

Se cobra un seguro del 3 % del valor declarado.

Máximo de 2 intentos de entrega.

4. Carga refrigerada

Solo se permite para destinos urbanos.

Requiere vehículos con refrigeración.

Incluye un cargo fijo de Q60 por cadena de frío.

Solo permite 1 intento de entrega.

Requisitos para ejecutar el programa

Para compilar y ejecutar el proyecto se necesita:

Tener instalado Java JDK.

Utilizar una terminal o consola.

Contar con los archivos .java del proyecto.

Instrucciones de compilación y ejecución

Descargar o clonar el repositorio de GitHub.

Abrir una terminal en la carpeta donde se encuentran los archivos Java.

Compilar los archivos utilizando el comando:

javac *.java

Ejecutar el programa utilizando:

java Main

Nota: Estos comandos suponen que los archivos Java están en la carpeta principal y que la clase que contiene el método main se llama Main.

Funcionamiento del programa

Al iniciar, el sistema muestra un menú de opciones que permite al usuario realizar las diferentes operaciones.

El programa trabaja con información almacenada en memoria, por lo que no requiere una base de datos.

El menú permanece disponible hasta que el usuario selecciona la opción de salir.

Validaciones

El sistema contempla las siguientes restricciones:

No se permiten códigos de envío repetidos.

No se permiten placas de vehículos repetidas.

Los valores numéricos deben estar dentro de los rangos establecidos.

Los vehículos no pueden superar su capacidad máxima.

Las cargas refrigeradas requieren vehículos compatibles.

Los envíos no pueden asignarse a más de un despacho al mismo tiempo.

Se controla el número máximo de intentos de entrega.

Las operaciones inválidas deben rechazarse sin modificar la información existente.

Reportes

El sistema contempla reportes de:

Cantidad de envíos por categoría.

Cantidad de envíos por estado.

Ingresos por registro de envíos.

Ingresos por reintentos.

Ingresos por categoría.

Despachos pendientes de cierre.

Capacidad utilizada y disponible de los vehículos.

Conceptos de programación orientada a objetos

En este laboratorio se aplican los siguientes conceptos:

Herencia: permite compartir atributos y métodos entre las diferentes categorías de envíos.

Polimorfismo: permite que cada categoría implemente sus propias reglas de cálculo y comportamiento.

Encapsulamiento: protege los atributos de los objetos y controla su modificación mediante métodos.

Abstracción: permite definir características y comportamientos comunes para los diferentes tipos de envío.

Pruebas

Las pruebas del proyecto deben verificar:

El cálculo correcto de los costos.

Los tiempos estimados de entrega.

Las restricciones de los vehículos.

Los límites de intentos de entrega.

El funcionamiento de los despachos.

Que las cotizaciones y operaciones rechazadas no modifiquen los ingresos.

Tecnologías utilizadas

Java

Programación orientada a objetos

Aplicación de consola

GitHub para el control de versiones

Información adicional

Proyecto académico desarrollado para el Laboratorio 3 - Herencia del curso CC2008, Programación Orientada a Objetos, de la Universidad del Valle de Guatemala.
