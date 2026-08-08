# Caso 05: Fallas y aborto

## Objetivo

Documentar los puntos donde el worker deja de avanzar o termina por una falla
real.

## Casos observables

- `js.get(...)` devuelve `"error"` en el polling principal: el worker corta el
  ciclo con `break`.
- Ocurre `JsonSyntaxException`, `NumberFormatException` o `SQLException` en el
  procesamiento: el worker entra al `catch` final y ejecuta `System.exit(0)`.
- Falla la carga del móvil o del polígono por excepción no controlada: el
  helper actual termina el proceso.

## SQL de partida

```sql
INSERT INTO `PARAMETRTEMP`.`GPSEventProtocol4000`
(`N`, `D1`, `L2`, `L3`, `L4`, `L8`, `TG`, `H1`, `V1`, `E1`, `PD`, `V2`, `TE`,
 `V3`, `V4`, `V5`, `V6`, `V7`, `V8`, `V9`, `V10`, `V11`, `fecha_de_arribo`,
 `dato_index`, `dato_can`, `ESTADO`, `mobile_contry_code`, `mobile_networt_code`,
 `local_area_code`, `cell_ID`, `hour_meter_count`, `HDOP`, `ibutton1`, `ibutton2`,
 `new_insert`)
VALUES
(0, '0000-00-00 00:00:00', 0, -33.40, -70.60, 0, '', 10, 0, '0', 571, '', 0,
 0, 0, 0, 0, 0, -1, -1, -1, -1, '2026-07-24 17:30:00', 1, '', 0, '', '', '', '',
 '', -1, '', '28A0988A', 0);
```

## Evidencia esperada

- `poll.failed`
- `mobil.cache_load_failed` o `polygon.fetch_failed`
- mensajes legacy impresos antes de salir

## Verificación mínima

- El proceso no sigue consumiendo nuevos lotes después del aborto.
- El error queda asociado al `interaction_id` activo.

## Pendiente de evidencia

La política actual mezcla abortos de proceso con errores de registro. El
`INSERT` anterior solo prepara un caso de rechazo/validación; el aborto duro
por `System.exit(0)` sigue dependiendo de una falla real del API o del parser y
queda como evidencia pendiente.
