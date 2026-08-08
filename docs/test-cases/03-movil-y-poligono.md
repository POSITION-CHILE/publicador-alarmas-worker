# Caso 03: Móvil y polígono

## Objetivo

Comprobar que el worker enriquece el registro con móvil y polígono cuando los
IDs existen y los servicios auxiliares responden.

## Precondiciones

- La API de móviles responde para el `L2` consultado.
- La API de polígonos responde para `idpoly`.
- El worker puede construir `url_obtener_movil` y `url_obtener_nombre_poligono`
  desde el entorno.

## SQL de partida

```sql
INSERT INTO `gps`.`veiculos`
(`l2`, `plate`, `user1`, `r12`, `tipo`, `modem`, `numero`, `rendimiento`,
 `factor_correcion`, `costo_combustible`, `tipo_combustible`, `marca`, `modelo`,
 `anno`, `km_inicial`, `id_equipo`, `estado`, `tele`, `color_stop`, `color_mov`,
 `tipo_ib`, `l2f`, `disponible`, `volt_min`, `volt_max`, `observacion`,
 `capacidad`, `altura`, `ancho`, `largo`, `color_ico`, `corte_motor`)
VALUES
(92149, 'TA10NUU', 'POSITION', NULL, 0, NULL, '987654321', 1, 1, 1, 1, 'Marca', 'Modelo',
 2025, 0, 1, 1, 0, '', '', 0, 0, 1, 0, 0, '', 0, 0, 0, 0, '', 0);

INSERT INTO `gis`.`poligono`
(`id_poligono`, `nombre`, `user1`, `vel`, `res_1`, `res_2`, `color`, `res_3`,
 `res_4`, `res_5`, `sms`, `id_capa`, `planta`, `zoom`, `activo`)
VALUES
(1700001, 'POLIGONO PRUEBA', 'POSITION', 500, '', '', '2861D4', '', '', '', 0, 8, 1, 14, 1);

INSERT INTO `gis`.`poligono_movil`
(`id_poligono`, `l2`)
VALUES
(1700001, 92149);
```

## Qué debe pasar

- `carga_arr_Movil(...)` llena el caché y deja el móvil disponible.
- `obtiene_datos_poligono(...)` llena el caché de polígonos.
- El registro final incluye `user1`, `patente`, `nombre_poligono` e `id_capa`
  para la rama de evento.

## Evidencia esperada

- `mobil.cache_loaded`
- `polygon.cache_loaded`
- `record.http_event_result resultado=<...>`

## Verificación mínima

- En una segunda pasada con el mismo `L2` e `idpoly`, el helper reutiliza el
  caché local.
- No se duplican entradas en las listas internas de móviles o polígonos.

## Pendiente de evidencia

El worker no consulta PostgreSQL directo para resolver móvil o polígono; esos
datos llegan por HTTP desde servicios del ecosistema.
