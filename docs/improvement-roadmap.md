# Recorrido Y Puntos De Mejora

Este documento resume los huecos observados en `publicador-alarmas-worker`.
No propone cambios funcionales por sí solo; sirve como mapa para PRs pequeños,
verificables y reversibles.

## Estado Actual

`publicador_alarmas` consume lotes desde una API origen, valida y enriquece
registros, consulta móvil y polígono por HTTP, publica eventos especiales,
inserta alarmas GPS cuando corresponde y confirma el lote con ACK.

El runtime ya cuenta con:

- configuración por variables de entorno
- build Maven
- contenedorización con Docker
- logs logfmt nuevos con correlación por worker e interacción
- tests unitarios e integración para helpers críticos

## Puntos De Mejora

### P0: Aborto Y Errores Mezclados

Hoy hay errores que abortan el proceso completo y otros que solo rechazan un
registro. Falta separar explícitamente esas dos políticas.

### P1: Contratos HTTP Más Claros

El cliente HTTP sigue devolviendo strings como `ok` y `error`. Eso hace más
difícil distinguir API vacía, API caída y respuesta inválida.

### P1: ACK Y Reintento

La confirmación depende del último ID visto. Conviene definir mejor qué pasa
cuando un registro intermedio falla.

### P1: Logs Legacy

Todavía existen mensajes antiguos que pueden exponer detalles operativos.
Conviene sanitizarlos gradualmente sin romper el soporte legado.

### P2: Desacople De Helpers

`publicador_regalm` y `funciones` siguen concentrando varias decisiones. Hay
espacio para extraer componentes más pequeños sin cambiar contratos.

## Uso Del Documento

Cada ítem de mejora debe terminar en:

- una hipótesis clara
- una prueba de caracterización
- un PR pequeño
- una validación reproducible
