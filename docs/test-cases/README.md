# Casos de uso y verificación

Guía operativa para reproducir el comportamiento actual de
`publicador-alarmas-worker`. No reemplaza los tests automáticos: documenta los
flujos que hoy se pueden observar con la API origen, las APIs auxiliares,
Docker y el stack PostgreSQL de soporte.

## Prerrequisitos

- Variables de entorno cargadas desde `.env`.
- Worker compilado y ejecutándose con Docker o `java -jar`.
- API origen y servicios auxiliares accesibles.
- PostgreSQL de soporte disponible cuando el flujo requiera resolver móviles o
  polígonos.
- Acceso a logs estructurados para correlacionar `interaction_id`, `worker_id`
  y `last_id`.

## Casos documentados

| Caso | Qué valida | Señal de éxito |
| --- | --- | --- |
| [01 Lectura y ACK](01-lectura-y-ack.md) | polling, lote, marcado y limpieza | `poll.started`, `batch.received`, `ack.marked`, `ack.deleted` |
| [02 Validación y rechazo](02-validacion-y-rechazo.md) | registros inválidos o vacíos | `record.rejected` o salida silenciosa del lote |
| [03 Móvil y polígono](03-movil-y-poligono.md) | enriquecimiento local | `mobil.cache_loaded`, `polygon.cache_loaded` |
| [04 Evento especial y GPS](04-evento-especial-y-gps.md) | decisión de inserción | `record.decision`, inserción GPS, `record.insert_failed` |
| [05 Fallas y aborto](05-fallas-y-aborto.md) | errores reales que detienen el worker | `poll.failed`, `System.exit(0)` o `break` por HTTP |

## Cómo leer cada caso

Cada archivo describe:

- SQL de partida para sembrar el escenario
- objetivo del flujo
- precondiciones
- datos mínimos o respuesta esperada de la API
- pasos para reproducirlo
- resultado observable en logs o datos

Si un caso no se puede probar hoy sin cambiar el código, se deja marcado como
`pendiente de evidencia`.
