package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class funcionesTest {

    @Test
    void asignaRegistrosConf_leeEnvYNormalizaValores() {
        final Map<String, String> env = new HashMap<>();
        env.put("NOMBRE_TABLA_ALARMAS", "alarmas");
        env.put("URL_PUBLICADOR_ALM", "api.local/publicador/alm");
        env.put("URL_PUBLICADOR_ALM_VEL", "https://api.local/publicador/alm/vel");
        env.put("URL_OBTENER_MOVIL", "api.local/moviles");
        env.put("URL_PUBLICADOR_ALM_GPS", "api.local/publicador/gps");
        env.put("KEY_PUBLICADOR_API", "key-publicador");
        env.put("URL_ZONAS", "api.local/zonas");
        env.put("URL_ZONAS_OCULTA", "api.local/zonas/oculta");
        env.put("URL_CALLE_CERCA", "api.local/calle/cerca");
        env.put("URL_ZONAS_OCULTA_CERCA", "api.local/zonas/oculta/cerca");
        env.put("URL_OBTENER_NOMBRE_POLIGONO", "api.local/poligonos/nombre");
        env.put("KEY_ZONA_API", "key-zona");
        env.put("URL_EVENTOS_ESPECIALES", "api.local/eventos/especiales");
        env.put("KEY_EVENTOS_API", "key-eventos");
        env.put("CUENTA_API", "cuenta-1");
        env.put("TIEMPO_DELATE", "15");
        env.put("FECHA_HORA_MINIMA", "2026-07-24_00_00_00");
        env.put("NODO", "nodo-1");
        env.put("NODO_ALM", "nodo-alm-1");
        env.put("NODO_PG", "nodo-pg-1");
        env.put("SERVIDOR_GPS", "srv-gps");
        env.put("SERVIDOR_ALM", "srv-alm");
        env.put("SERVIDOR_CONTENEDOR", "srv-contenedor");
        env.put("SERVIDOR_CONFIGURACION", "srv-config");
        env.put("SERVIDOR_VLS", "srv-vls");
        env.put("ALARMAS_EN_POLIGONO", "si");

        final funciones fun = new funciones(env::get);
        final tipo_datos.datos_Registros_conf conf = fun.asigna_Registros_conf();

        assertThat(conf.nombre_tabla_alarmas).isEqualTo("alarmas");
        assertThat(conf.url_publicador_alm).isEqualTo("http://api.local/publicador/alm");
        assertThat(conf.url_publicador_alm_vel).isEqualTo("https://api.local/publicador/alm/vel");
        assertThat(conf.url_obtener_movil).isEqualTo("http://api.local/moviles");
        assertThat(conf.url_publicador_alm_gps).isEqualTo("http://api.local/publicador/gps");
        assertThat(conf.key_publicador_api).isEqualTo("key-publicador");
        assertThat(conf.url_zonas).isEqualTo("http://api.local/zonas");
        assertThat(conf.url_zonas_oculta).isEqualTo("http://api.local/zonas/oculta");
        assertThat(conf.url_calle_cerca).isEqualTo("http://api.local/calle/cerca");
        assertThat(conf.url_zonas_oculta_cerca).isEqualTo("http://api.local/zonas/oculta/cerca");
        assertThat(conf.url_obtener_nombre_poligono).isEqualTo("http://api.local/poligonos/nombre");
        assertThat(conf.key_zona_api).isEqualTo("key-zona");
        assertThat(conf.url_eventos_especiales).isEqualTo("http://api.local/eventos/especiales");
        assertThat(conf.key_eventos_api).isEqualTo("key-eventos");
        assertThat(conf.cuenta_api).isEqualTo("cuenta-1");
        assertThat(conf.tiempo_delate).isEqualTo("15");
        assertThat(conf.fecha_hora_minima).isEqualTo("2026-07-24:00:00:00");
        assertThat(conf.nodo).isEqualTo("nodo-1");
        assertThat(conf.nodo_alm).isEqualTo("nodo-alm-1");
        assertThat(conf.nodo_pg).isEqualTo("nodo-pg-1");
        assertThat(conf.servidor_gps).isEqualTo("srv-gps");
        assertThat(conf.servidor_alm).isEqualTo("srv-alm");
        assertThat(conf.servidor_contenedor).isEqualTo("srv-contenedor");
        assertThat(conf.servidor_configuracion).isEqualTo("srv-config");
        assertThat(conf.servidor_vls).isEqualTo("srv-vls");
        assertThat(conf.alarmas_en_poligono).isEqualTo("si");
    }

    @Test
    void asignaRegistrosConf_fallaSiFaltaVariable() {
        final Map<String, String> env = new HashMap<>();
        env.put("NOMBRE_TABLA_ALARMAS", "alarmas");

        final funciones fun = new funciones(env::get);

        assertThatThrownBy(fun::asigna_Registros_conf)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("URL_PUBLICADOR_ALM");
    }

    @Test
    void validaFecha_reconoceFechasValidasYNoValidas() {
        final funciones fun = new funciones();

        final tipo_datos.Fecha valida = new tipo_datos.Fecha();
        valida.ano = 2026;
        valida.mes = 7;
        valida.dia = 24;

        assertThat(fun.validaFecha(valida)).isTrue();
        final tipo_datos.Fecha invalida = new tipo_datos.Fecha();
        invalida.ano = 1899;
        invalida.mes = 12;
        invalida.dia = 31;
        assertThatThrownBy(() -> fun.validaFecha(invalida))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1900 o posterior");
    }

    @Test
    void compareHoraFecha_comparaOrdenTemporal() {
        final funciones fun = new funciones();

        final tipo_datos.FechaHora inicio = new tipo_datos.FechaHora();
        inicio.ano = 2026;
        inicio.mes = 7;
        inicio.dia = 24;
        inicio.hora = 10;
        inicio.minuto = 0;
        inicio.segundo = 0;

        final tipo_datos.FechaHora fin = new tipo_datos.FechaHora();
        fin.ano = 2026;
        fin.mes = 7;
        fin.dia = 24;
        fin.hora = 10;
        fin.minuto = 0;
        fin.segundo = 1;

        assertThat(fun.compareHoraFecha(inicio, fin)).isTrue();
        assertThat(fun.compareHoraFecha(fin, inicio)).isFalse();
    }
}
