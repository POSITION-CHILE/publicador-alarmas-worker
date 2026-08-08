# Caso 04: Evento especial y GPS

## Objetivo

Comprobar la decisión de negocio que devuelve la API de eventos especiales y
la inserción GPS cuando corresponde.

## Precondiciones

- El registro ya pasó validación.
- El helper de eventos especiales responde con un valor del tipo `00-<cod>`,
  `10-<cod>`, `11-<cod>` o `01-<cod>`.

## SQL de partida

```sql
INSERT INTO `PARAMETRTEMP`.`GPSEventProtocol4000`
(`N`, `D1`, `L2`, `L3`, `L4`, `L8`, `TG`, `H1`, `V1`, `E1`, `PD`, `V2`, `TE`,
 `V3`, `V4`, `V5`, `V6`, `V7`, `V8`, `V9`, `V10`, `V11`, `fecha_de_arribo`,
 `dato_index`, `dato_can`, `ESTADO`, `mobile_contry_code`, `mobile_networt_code`,
 `local_area_code`, `cell_ID`, `hour_meter_count`, `HDOP`, `ibutton1`, `ibutton2`,
 `new_insert`)
VALUES
(0, '2026-07-24 17:20:00', 92149, -33.40, -70.60, 0, '', 10, 0, '0', 571, '',
 1, 0, 0, 0, 0, 0, -1, -1, -1, -1, '2026-07-24 17:20:00', 1, '', 0, '', '', '', '',
 '', -1, '', '28A0988A', 0);

INSERT INTO `PARAMETRTEMP`.`ALMEventProtocol`
(`N`, `D1`, `L2`, `C3`, `I4`, `B5`, `T_D`, `IDPOLY`, `E1`, `L3`, `L4`, `tr`,
 `V1`, `V2`, `L8`, `MIDPOLY`, `V3`)
VALUES
(0, '2026-07-24 17:20:00', 92149, 'A', 345, 1, '00:00:00', 1700001, '', -33.40,
 -70.60, 0, 0, NULL, 51, NULL, NULL);
```

## Qué debe pasar

- `00`: inserta GPS y marca ACK.
- `10`: no inserta GPS y marca ACK.
- `11`: no inserta GPS y no marca ACK.
- `01`: inserta GPS y no marca ACK.

## Evidencia esperada

- `record.decision insert=<bool> delete=<bool>`
- `record.http_event_result`
- `record.insert_failed` solo si la inserción GPS responde `"error"`

## Verificación mínima

- La decisión coincide con el prefijo de la respuesta del evento especial.
- Si `inserta_alm` es verdadero, se invoca `inserta_alarmas_gps(...)`.

## Pendiente de evidencia

La forma exacta del payload de eventos especiales se conserva como contrato del
servicio remoto; este repositorio solo verifica la rama de decisión.
