# Caso 02: Validación y rechazo

## Objetivo

Comprobar que los registros incompletos o inválidos no avanzan al resto del
flujo.

## Precondiciones

- La API origen devuelve al menos un registro con `d1` nulo o igual a
  `0000-00-00 00:00:00`, o bien un registro que `valida_registros_alm(...)`
  rechaza.

## SQL de partida

```sql
INSERT INTO `PARAMETRTEMP`.`GPSEventProtocol4000`
(`N`, `D1`, `L2`, `L3`, `L4`, `L8`, `TG`, `H1`, `V1`, `E1`, `PD`, `V2`, `TE`,
 `V3`, `V4`, `V5`, `V6`, `V7`, `V8`, `V9`, `V10`, `V11`, `fecha_de_arribo`,
 `dato_index`, `dato_can`, `ESTADO`, `mobile_contry_code`, `mobile_networt_code`,
 `local_area_code`, `cell_ID`, `hour_meter_count`, `HDOP`, `ibutton1`, `ibutton2`,
 `new_insert`)
VALUES
(0, '0000-00-00 00:00:00', 92149, -33.40, -70.60, 0, '', 10, 0, '0', 571, '', 0,
 0, 0, 0, 0, 0, -1, -1, -1, -1, '2026-07-24 17:10:00', 1, '', 0, '', '', '', '',
 '', -1, '', '28A0988A', 0);
```

## Qué debe pasar

- El worker ignora el registro antes de resolver móvil, polígono o evento.
- No se intenta inserción GPS para ese registro.
- No se marca ACK para el dato rechazado.

## Evidencia esperada

- ausencia de `record.decision` para ese `last_id`
- `record.rejected` cuando el rechazo llega desde el evento especial
- en logs legacy, el registro aparece omitido sin tumbar el ciclo completo

## Verificación mínima

- El worker sigue vivo y procesa el siguiente registro del lote.
- No aparecen inserts ni deletes asociados al registro rechazado.

## Pendiente de evidencia

El detalle exacto de las reglas de `valida_registros_alm(...)` depende de la
implementación del helper y se valida mejor con tests unitarios.
