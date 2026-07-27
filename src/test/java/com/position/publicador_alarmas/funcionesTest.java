package com.position.publicador_alarmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

class funcionesTest {
    private static final AtomicBoolean FACTORY_INSTALLED = new AtomicBoolean(false);

    @BeforeAll
    static void installFactory() {
        if (FACTORY_INSTALLED.compareAndSet(false, true)) {
            try {
                URL.setURLStreamHandlerFactory(new MockFactory());
            }
            catch (Error ignored) {
                // Another test already installed it.
            }
        }
    }

    @Test
    void asignaRegistrosConf_leeEnvYNormalizaValores() {
        final funciones fun = new funciones(baseEnv()::get);
        final tipo_datos.datos_Registros_conf conf = fun.asigna_Registros_conf();

        assertThat(conf.nombre_tabla_alarmas).isEqualTo("alarmas");
        assertThat(conf.url_publicador_alm).isEqualTo("http://mock.local/publicador/alm");
        assertThat(conf.url_publicador_alm_vel).isEqualTo("https://mock.local/publicador/alm/vel");
        assertThat(conf.url_obtener_movil).isEqualTo("http://mock.local/moviles");
        assertThat(conf.url_publicador_alm_gps).isEqualTo("http://mock.local/publicador/gps");
        assertThat(conf.key_publicador_api).isEqualTo("key-publicador");
        assertThat(conf.url_zonas).isEqualTo("http://mock.local/zonas");
        assertThat(conf.url_zonas_oculta).isEqualTo("http://mock.local/zonas/oculta");
        assertThat(conf.url_calle_cerca).isEqualTo("http://mock.local/calle/cerca");
        assertThat(conf.url_zonas_oculta_cerca).isEqualTo("http://mock.local/zonas/oculta/cerca");
        assertThat(conf.url_obtener_nombre_poligono).isEqualTo("http://mock.local/nombre");
        assertThat(conf.key_zona_api).isEqualTo("key-zona");
        assertThat(conf.url_eventos_especiales).isEqualTo("http://mock.local/eventos/especiales");
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
    void asignaFecha_y_asignaFechaHora_parseanValores() {
        final funciones fun = new funciones();

        final tipo_datos.Fecha fecha = fun.asigna_fecha("2026-07-24 12:34:56");
        assertThat(fecha.ano).isEqualTo(2026);
        assertThat(fecha.mes).isEqualTo(7);
        assertThat(fecha.dia).isEqualTo(24);

        final tipo_datos.FechaHora fechaHora = fun.asigna_fechahora("2026-07-24 12:34:56");
        assertThat(fechaHora.ano).isEqualTo(2026);
        assertThat(fechaHora.mes).isEqualTo(7);
        assertThat(fechaHora.dia).isEqualTo(24);
        assertThat(fechaHora.hora).isEqualTo(12);
        assertThat(fechaHora.minuto).isEqualTo(34);
        assertThat(fechaHora.segundo).isEqualTo(56);
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

    @Test
    void asignaDataAlmMapeaCampos() {
        final funciones fun = new funciones();
        final tipo_datos.Tupla_alm reg = sampleTupla();

        final Registros_alm_insert alm = fun.asigna_data_alm_insert(reg, "key", "cuenta", "0", "A", 99, 5, "00:00:00", "nodo");
        assertThat(alm.getkey()).isEqualTo("key");
        assertThat(alm.getcuenta()).isEqualTo("cuenta");
        assertThat(alm.getN()).isEqualTo("0");
        assertThat(alm.getD1()).isEqualTo(reg.d1);
        assertThat(alm.getL2()).isEqualTo("1001");
        assertThat(alm.getC3()).isEqualTo("A");
        assertThat(alm.getI4()).isEqualTo("99");
        assertThat(alm.getB5()).isEqualTo("5");
        assertThat(alm.getT_D()).isEqualTo("00:00:00");
        assertThat(alm.getpoligono()).isEqualTo("77");
        assertThat(alm.getL3()).isEqualTo("1.5");
        assertThat(alm.getL4()).isEqualTo("2.5");
        assertThat(alm.getV1()).isEqualTo("3.5");
        assertThat(alm.getL8()).isEqualTo("4.5");
        assertThat(alm.getnodo()).isEqualTo("nodo");
    }

    @Test
    void asignaDataAlmGpsYVelYEventosMapeanCampos() {
        final funciones fun = new funciones();
        final tipo_datos.Tupla_alm reg = sampleTupla();

        final Registros_alm_gps_insert gps = fun.asigna_data_alm_gps_insert(reg, "key", "cuenta", "0", "A", 99, "00:00:00", "nombre", "user1", "nodo");
        assertThat(gps.getkey()).isEqualTo("key");
        assertThat(gps.getcuenta()).isEqualTo("cuenta");
        assertThat(gps.getN()).isEqualTo("0");
        assertThat(gps.getD1()).isEqualTo(reg.d1);
        assertThat(gps.getL2()).isEqualTo("1001");
        assertThat(gps.getC3()).isEqualTo("A");
        assertThat(gps.getI4()).isEqualTo("99");
        assertThat(gps.getT_D()).isEqualTo("00:00:00");
        assertThat(gps.getE1()).isEqualTo(reg.e1);
        assertThat(gps.getid_poligono()).isEqualTo("77");
        assertThat(gps.getdata_user1()).isEqualTo("user1");
        assertThat(gps.getdata_nombre()).isEqualTo("nombre");
        assertThat(gps.getnodo()).isEqualTo("nodo");

        final Registros_alm_insert_vel vel = fun.asigna_data_alm_vel_insert(reg, "key", "cuenta", 50, "nombre", 88L, "user1", "nodo");
        assertThat(vel.getkey()).isEqualTo("key");
        assertThat(vel.getcuenta()).isEqualTo("cuenta");
        assertThat(vel.getD1()).isEqualTo(reg.d1);
        assertThat(vel.getL2()).isEqualTo("1001");
        assertThat(vel.getL3()).isEqualTo("1.5");
        assertThat(vel.getL4()).isEqualTo("2.5");
        assertThat(vel.getvel()).isEqualTo("4.5");
        assertThat(vel.getvel_plng()).isEqualTo("50");
        assertThat(vel.getnombreplng()).isEqualTo("nombre");
        assertThat(vel.getid_plng()).isEqualTo("88");
        assertThat(vel.getuser1()).isEqualTo("user1");
        assertThat(vel.getnodo()).isEqualTo("nodo");

        final Registros_eventos_especiales_alm eventos = fun.asigna_eventos_esp_alm("key", "cuenta", reg, "987654321", "user1", "ABC123", "77", "nombre", "8", "9", "nodo", "nodo_pg", "dir", "comuna", "ciclo", "tipo");
        assertThat(eventos.getkey()).isEqualTo("key");
        assertThat(eventos.getcuenta()).isEqualTo("cuenta");
        assertThat(eventos.getL2()).isEqualTo("1001");
        assertThat(eventos.getfecha_registro()).isEqualTo(reg.d1);
        assertThat(eventos.getlatitude()).isEqualTo("1.5");
        assertThat(eventos.getlongitude()).isEqualTo("2.5");
        assertThat(eventos.getvel()).isEqualTo("4.5");
        assertThat(eventos.getnumero()).isEqualTo("987654321");
        assertThat(eventos.getuser1()).isEqualTo("user1");
        assertThat(eventos.getpatente()).isEqualTo("ABC123");
        assertThat(eventos.geti4()).isEqualTo("11");
        assertThat(eventos.getnodo()).isEqualTo("nodo");
        assertThat(eventos.getnodo_pg()).isEqualTo("nodo_pg");
        assertThat(eventos.getid_poligono()).isEqualTo("77");
        assertThat(eventos.getnombre_poligono()).isEqualTo("nombre");
        assertThat(eventos.getid_planta()).isEqualTo("8");
        assertThat(eventos.getplanta()).isEqualTo("9");
        assertThat(eventos.getdirec()).isEqualTo("dir");
        assertThat(eventos.getcomuna()).isEqualTo("comuna");
        assertThat(eventos.getciclo()).isEqualTo("ciclo");
        assertThat(eventos.gettipo()).isEqualTo("tipo");
    }

    @Test
    void createObjectFromXmlString_deserializaPoligono() throws Exception {
        final String xml = "<Registros_poligonos><id_poligono>77</id_poligono><nombre>poligono-1</nombre><user1>u1</user1><vel>50.5</vel><res_1>r1</res_1><res_2>r2</res_2><res_3>r3</res_3><res_4>r4</res_4><res_5>r5</res_5><id_capa>8</id_capa><planta>9</planta></Registros_poligonos>";

        final Registros_poligonos poli = funciones.createObjectFromXmlString(xml, Registros_poligonos.class);

        assertThat(poli.getid_poligono()).isEqualTo(77L);
        assertThat(poli.getnombre()).isEqualTo("poligono-1");
        assertThat(poli.getuser1()).isEqualTo("u1");
        assertThat(poli.getvel()).isEqualTo(50.5f);
        assertThat(poli.getres_1()).isEqualTo("r1");
        assertThat(poli.getres_2()).isEqualTo("r2");
        assertThat(poli.getres_3()).isEqualTo("r3");
        assertThat(poli.getres_4()).isEqualTo("r4");
        assertThat(poli.getres_5()).isEqualTo("r5");
        assertThat(poli.getid_capa()).isEqualTo(8);
        assertThat(poli.getplanta()).isEqualTo(9);
    }

    @Test
    void carga_arr_Movil_y_obtiene_datos_poligono_carganYCachen() throws Exception {
        final funciones fun = new funciones(baseEnv()::get);
        final tipo_datos.datos_Registros_conf conf = fun.asigna_Registros_conf();

        final ArrayList<Movil> moviles = new ArrayList<>();
        final tipo_datos.Tupla_alm reg = sampleTupla();

        final int posMovil = fun.carga_arr_Movil(1, moviles, reg, conf);
        final int posMovilCache = fun.carga_arr_Movil(1, moviles, reg, conf);

        assertThat(posMovil).isEqualTo(0);
        assertThat(posMovilCache).isEqualTo(0);
        assertThat(moviles).hasSize(1);
        assertThat(moviles.get(0).getuser1()).isEqualTo("u1");
        assertThat(moviles.get(0).getplate()).isEqualTo("ABC123");
        assertThat(moviles.get(0).getnumero()).isEqualTo("569987654321");

        final ArrayList<PoligonoMovil> poligonos = new ArrayList<>();
        final int posPoligono = fun.obtiene_datos_poligono(poligonos, reg, conf);
        final int posPoligonoCache = fun.obtiene_datos_poligono(poligonos, reg, conf);

        assertThat(posPoligono).isEqualTo(0);
        assertThat(posPoligonoCache).isEqualTo(0);
        assertThat(poligonos).hasSize(1);
        assertThat(poligonos.get(0).getL2_id_poligono()).isEqualTo("1001.77");
        assertThat(poligonos.get(0).getnombre()).isEqualTo("poligono-1");
        assertThat(poligonos.get(0).getid_capa()).isEqualTo(8);
    }

    @Test
    void validaRegistrosAlm_aceptaYRechazaSegunReglas() {
        final valida validator = new valida();
        final tipo_datos.datos_Registros_conf conf = baseConf();

        final tipo_datos.Tupla_alm valido = sampleTupla();
        assertThat(validator.valida_registros_alm(valido, conf)).isTrue();

        final tipo_datos.Tupla_alm velocidadAlta = sampleTupla();
        velocidadAlta.v1 = 201.0f;
        assertThat(validator.valida_registros_alm(velocidadAlta, conf)).isFalse();

        final tipo_datos.Tupla_alm fechaVieja = sampleTupla();
        fechaVieja.d1 = "2025-12-31 23:59:59";
        assertThat(validator.valida_registros_alm(fechaVieja, conf)).isFalse();
    }

    private static Map<String, String> baseEnv() {
        final Map<String, String> env = new HashMap<>();
        env.put("NOMBRE_TABLA_ALARMAS", "alarmas");
        env.put("URL_PUBLICADOR_ALM", "mock.local/publicador/alm");
        env.put("URL_PUBLICADOR_ALM_VEL", "https://mock.local/publicador/alm/vel");
        env.put("URL_OBTENER_MOVIL", "mock.local/moviles");
        env.put("URL_PUBLICADOR_ALM_GPS", "mock.local/publicador/gps");
        env.put("KEY_PUBLICADOR_API", "key-publicador");
        env.put("URL_ZONAS", "mock.local/zonas");
        env.put("URL_ZONAS_OCULTA", "mock.local/zonas/oculta");
        env.put("URL_CALLE_CERCA", "mock.local/calle/cerca");
        env.put("URL_ZONAS_OCULTA_CERCA", "mock.local/zonas/oculta/cerca");
        env.put("URL_OBTENER_NOMBRE_POLIGONO", "mock.local/nombre");
        env.put("KEY_ZONA_API", "key-zona");
        env.put("URL_EVENTOS_ESPECIALES", "mock.local/eventos/especiales");
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
        return env;
    }

    private static tipo_datos.datos_Registros_conf baseConf() {
        return new funciones(baseEnv()::get).asigna_Registros_conf();
    }

    private static tipo_datos.Tupla_alm sampleTupla() {
        final tipo_datos.Tupla_alm reg = new tipo_datos.Tupla_alm();
        reg.reg = 123;
        reg.n = 4;
        reg.l2 = 1001L;
        reg.d1 = "2026-07-24 12:34:56";
        reg.c3 = "A";
        reg.i4 = 11;
        reg.t_d = "00:00:00";
        reg.e1 = "e1";
        reg.idpoly = 77L;
        reg.l3 = 1.5f;
        reg.l4 = 2.5f;
        reg.v1 = 3.5f;
        reg.v2 = "v2";
        reg.l8 = 4.5d;
        reg.v3 = 5.5f;
        return reg;
    }

    private static final class MockFactory implements URLStreamHandlerFactory {
        @Override
        public URLStreamHandler createURLStreamHandler(final String protocol) {
            if ("http".equals(protocol) || "https".equals(protocol)) {
                return new MockHandler();
            }
            return null;
        }
    }

    private static final class MockHandler extends URLStreamHandler {
        @Override
        protected URLConnection openConnection(final URL url) {
            return new MockConnection(url);
        }
    }

    private static final class MockConnection extends java.net.HttpURLConnection {
        private final java.io.ByteArrayOutputStream requestBody = new java.io.ByteArrayOutputStream();

        protected MockConnection(final URL url) {
            super(url);
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() {
        }

        @Override
        public java.io.OutputStream getOutputStream() {
            return this.requestBody;
        }

        @Override
        public java.io.InputStream getInputStream() {
            return new java.io.ByteArrayInputStream(responseBody().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }

        @Override
        public java.io.InputStream getErrorStream() {
            if (getResponseCode() >= 400) {
                return new java.io.ByteArrayInputStream(responseBody().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            return null;
        }

        @Override
        public int getResponseCode() {
            final String path = this.url.getPath().toLowerCase(java.util.Locale.ROOT);
            if (path.contains("delete")) {
                return 200;
            }
            if (path.contains("moviles")) {
                return 200;
            }
            if (path.contains("nombre")) {
                return 200;
            }
            if (path.contains("post")) {
                return 200;
            }
            if (path.contains("ok")) {
                return 200;
            }
            return 500;
        }

        private String responseBody() {
            final String path = this.url.getPath().toLowerCase(java.util.Locale.ROOT);
            if (path.contains("moviles")) {
                return "{\"response\":[{\"user1\":\"u1\",\"l2\":1001,\"plate\":\"ABC123\",\"r12\":1,\"tipo\":2,\"modem\":3,\"numero\":\"987654321\",\"rendimiento\":1.1,\"factor_correcion\":1.2,\"costo_combustible\":1.3,\"tipo_combustible\":1.4,\"marca\":\"marca\",\"modelo\":\"modelo\",\"anno\":2020,\"km_inicial\":100,\"id_equipo\":10,\"estado\":1,\"tele\":1,\"color_stop\":2,\"color_mov\":3,\"tipo_ib\":4,\"l2f\":5,\"disponible\":6,\"volt_min\":7.1,\"volt_max\":8.2,\"observacion\":\"obs\",\"capacidad\":9.3,\"altura\":10.4,\"ancho\":11.5,\"largo\":12.6,\"color_ico\":\"blue\",\"corte_motor\":7}]}";
            }
            if (path.contains("nombre")) {
                return "<xml><response><item><id_poligono>77</id_poligono><nombre>poligono-1</nombre><user1>u1</user1><vel>50.5</vel><res_1>r1</res_1><res_2>r2</res_2><res_3>r3</res_3><res_4>r4</res_4><res_5>r5</res_5><id_capa>8</id_capa><planta>9</planta></item></response></xml>";
            }
            if (path.contains("post")) {
                return "ok";
            }
            return "{\"status\":\"ok\"}";
        }
    }
}
