# Caso 01: Lectura y ACK

## Objetivo

Comprobar que el worker lee un lote desde la API origen, procesa cada
registro, marca los IDs para borrar y ejecuta la limpieza final con DELETE.

## Precondiciones

- `URL_PUBLICADOR_ALM`, `CUENTA_API`, `KEY_PUBLICADOR_API`, `NODO_ALM` y
  `SERVIDOR_ALM` configuradas.
- El worker activo debe tener un `worker_id` conocido.
- La API origen debe responder con un JSON `Registros_alm` válido.

## SQL de partida

Usa `PARAMETRTEMP.GPSEventProtocol4000` como plantilla local. Si el worker que
estás probando usa otro sufijo, cambia `4000` por ese número.

```sql
INSERT INTO `PARAMETRTEMP`.`GPSEventProtocol4000`
(`N`, `D1`, `L2`, `L3`, `L4`, `L8`, `TG`, `H1`, `V1`, `E1`, `PD`, `V2`, `TE`,
 `V3`, `V4`, `V5`, `V6`, `V7`, `V8`, `V9`, `V10`, `V11`, `fecha_de_arribo`,
 `dato_index`, `dato_can`, `ESTADO`, `mobile_contry_code`, `mobile_networt_code`,
 `local_area_code`, `cell_ID`, `hour_meter_count`, `HDOP`, `ibutton1`, `ibutton2`,
 `new_insert`)
VALUES
(0, '2026-07-24 17:00:00', 92149, -33.40, -70.60, 0, '', 10, 0, '0', 571, '', 0,
 0, 0, 0, 0, 0, -1, -1, -1, -1, '2026-07-24 17:00:00', 1, '', 0, '', '', '', '',
 '', -1, '', '28A0988A', 0),
(0, '2026-07-24 17:00:01', 92149, -33.40, -70.60, 0, '', 10, 0, '0', 571, '', 0,
 0, 0, 0, 0, 0, -1, -1, -1, -1, '2026-07-24 17:00:01', 1, '', 0, '', '', '', '',
 '', -1, '', '28A0988A', 0);
```

## Qué debe pasar

1. El worker emite `poll.started`.
2. La API responde con un lote no vacío.
3. Cada registro válido genera decisión de negocio y, si corresponde, ACK.
4. Al terminar el lote, el worker ejecuta DELETE para limpiar los marcados.

## Evidencia esperada

- `poll.http_completed result=ok`
- `batch.received records=<n>`
- uno o más `record.decision`
- `ack.marked last_id=<id>`
- `ack.deleted result=<valor>`

## Verificación mínima

- En logs, todos los eventos del polling comparten el mismo
  `interaction_id`.
- El último `last_id` del lote coincide con el ID confirmado para borrar.
- Si el lote era procesable, el worker vuelve al siguiente ciclo sin salir.

## Pendiente de evidencia

No se documenta aquí el formato exacto del JSON de la API origen porque ese
contrato depende del servicio remoto, no de este repositorio. El `INSERT`
anterior solo prepara el escenario en la base fuente.
