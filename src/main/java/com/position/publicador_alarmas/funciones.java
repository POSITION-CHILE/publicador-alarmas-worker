/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;


/**
 *
 * @author egatica
 */
public class funciones
{
    HttpURLConnection_json js;
    
    public funciones() {
        this.js = new HttpURLConnection_json();
    }
    
    public void asiga_lista(final ArrayList<Movil> reg_MovilLista_local) {
        publicador_regalm.reg_MovilLista = reg_MovilLista_local;
    }
    
    public void asiga_listaPologonoMovil(final ArrayList<PoligonoMovil> reg_PoligonoMovilLista_local) {
        publicador_regalm.reg_PoligonoMovilLista = reg_PoligonoMovilLista_local;
    }
    
    int carga_arr_Movil(final int n_procesos, final ArrayList<Movil> reg_MovilLista_local, final tipo_datos.Tupla_alm reg_tupla, final tipo_datos.datos_Registros_conf conf) {
        boolean existe = false;
        int pos = -1;
        try{
            if (reg_MovilLista_local!=null){    
                for (final Movil Mo : reg_MovilLista_local) {
                    if (Mo.getl2()==reg_tupla.l2){
                        existe = true;
                        pos = reg_MovilLista_local.indexOf(Mo);
                        break;
                    }
                }
            }
        }
            catch (Exception e) {
                System.out.println("Grande-----carga_arr_Movil =>"+n_procesos+"<=" + e);
                System.exit(0);        
        }         
        if (!existe) {
           
            final String parameters_datos_moviles = conf.url_obtener_movil + "/cuenta/" + conf.cuenta_api + "/key/" + conf.key_publicador_api + "/L2/" + reg_tupla.l2 + "/nodo/" + conf.nodo;
            String data_datos_moviles;
            data_datos_moviles = this.js.get(parameters_datos_moviles, 5000);
            if (data_datos_moviles != null) {
                final Registros_Movil msg_d_m = (Registros_Movil)new Gson().fromJson(data_datos_moviles, (Class)Registros_Movil.class);
                String nuevo_numero = "";
                if (!"".equals(msg_d_m.response[0].getnumero().trim())) {
                    if (msg_d_m.response[0].getnumero().trim().length() == 11) {
                        nuevo_numero = msg_d_m.response[0].getnumero().trim();
                    }
                    else {
                        nuevo_numero = "569" + msg_d_m.response[0].getnumero().trim();
                    }
                }
                final Movil Movil_perfil = new Movil(msg_d_m.response[0].getuser1(), msg_d_m.response[0].getl2() , msg_d_m.response[0].getplate().trim(), msg_d_m.response[0].getr12(), msg_d_m.response[0].gettipo(), msg_d_m.response[0].getmodem(), nuevo_numero, msg_d_m.response[0].getrendimiento(), msg_d_m.response[0].getfactor_correcion(), msg_d_m.response[0].getcosto_combustible(), msg_d_m.response[0].gettipo_combustible(), msg_d_m.response[0].getmarca(), msg_d_m.response[0].getmodelo(), msg_d_m.response[0].getanno(), msg_d_m.response[0].getkm_inicial(), msg_d_m.response[0].getid_equipo(), msg_d_m.response[0].getestado(), msg_d_m.response[0].gettele(), msg_d_m.response[0].getcolor_stop(), msg_d_m.response[0].getcolor_mov(), msg_d_m.response[0].gettipo_ib(), msg_d_m.response[0].getl2f(), msg_d_m.response[0].getdisponible(), msg_d_m.response[0].getvolt_min(), msg_d_m.response[0].getvolt_max(), msg_d_m.response[0].getobservacion(), msg_d_m.response[0].getcapacidad(), msg_d_m.response[0].getaltura(), msg_d_m.response[0].getancho(), msg_d_m.response[0].getlargo(), msg_d_m.response[0].getcolor_ico(), msg_d_m.response[0].getcorte_motor());
                reg_MovilLista_local.add(Movil_perfil);
                this.asiga_lista(reg_MovilLista_local);
                pos = reg_MovilLista_local.size() - 1;
                
            }
          
        }
        
        return pos;
    }
    
    int obtiene_datos_poligono(final ArrayList<PoligonoMovil> reg_PoligonoMovilLista_local, final tipo_datos.Tupla_alm reg_tupla, final tipo_datos.datos_Registros_conf conf) throws SQLException {
        tipo_datos.datos_poligono datos_poligono = new tipo_datos.datos_poligono();
        //Statement sen_pgsqlserver;
        boolean existe = false;
        int pos = -1;
        try{
        datos_poligono.idpoligono = reg_tupla.idpoly;
        datos_poligono.nombre = "no_existe_gis";
        datos_poligono.planta = -1;
        datos_poligono.user1 = "";
        datos_poligono.res_1 = "";
        datos_poligono.res_2 = "";
        datos_poligono.res_3 = "";
        datos_poligono.res_4 = "";
        datos_poligono.res_5 = "";
        datos_poligono.vel = 500.0f;
        datos_poligono.id_capa = -1;
        if (reg_PoligonoMovilLista_local!=null){   
            for (final PoligonoMovil MoPo : reg_PoligonoMovilLista_local) {
                if (MoPo.getL2_id_poligono().equals(Long.toString(reg_tupla.l2) + "." + Long.toString(reg_tupla.idpoly))) {
                    existe = true;
                    pos = reg_PoligonoMovilLista_local.indexOf(MoPo);
                    break;
                }
            }
        }
        if (!existe) {
            //final String parameters_datos_poligono = conf.url_obtener_nombre_poligono + "/cuenta/" + conf.cuenta_api + "/key/" + conf.key_zona_api + "/idpoligono/" + reg_tupla.idpoly + "/nodo/" + conf.nodo_pg;
            final String parameters_datos_poligono = conf.url_obtener_nombre_poligono + "/cuenta/" + conf.cuenta_api + "/key/" + conf.key_zona_api + "/idpoligono/" + reg_tupla.idpoly + "/nodo/" + conf.nodo_pg+"/format/xml";            
            String data_datos_poligono;
            data_datos_poligono = this.js.get(parameters_datos_poligono, 5000);
            if (data_datos_poligono != null) {
                //<xml><response><item> <Registros_poligonos>
                //</item></response></xml> </Registros_poligonos>
                String xml=data_datos_poligono.replace("<xml><response><item>", "<Registros_poligonos>");
                String xml_fin=xml.replace("</item></response></xml>", "</Registros_poligonos>");
                
                Registros_poligonos Reg_poli = createObjectFromXmlString(xml_fin, Registros_poligonos.class);

                //final Registros_pligonos msg_datos_poligono = (Registros_pligonos)new Gson().fromJson(data_datos_poligono, (Class)Registros_pligonos.class);

                datos_poligono.idpoligono = reg_tupla.idpoly;
                //datos_poligono.nombre = msg_datos_poligono.response[0].getnombre();
                //datos_poligono.planta = msg_datos_poligono.response[0].getplanta();
                //datos_poligono.user1 = msg_datos_poligono.response[0].getuser1();
                //datos_poligono.res_1 = msg_datos_poligono.response[0].getres1();
                //datos_poligono.res_2 = msg_datos_poligono.response[0].getres2();
                //datos_poligono.res_3 = msg_datos_poligono.response[0].getres3();
                //datos_poligono.res_4 = msg_datos_poligono.response[0].getres4();
                //datos_poligono.res_5 = msg_datos_poligono.response[0].getres5();
                //datos_poligono.vel = msg_datos_poligono.response[0].getvel();
                //datos_poligono.id_capa = msg_datos_poligono.response[0].getid_capa();
                
                datos_poligono.nombre = Reg_poli.getnombre();
                datos_poligono.planta = Reg_poli.getplanta();
                datos_poligono.user1 = Reg_poli.getuser1();
                datos_poligono.res_1 = Reg_poli.getres_1();
                datos_poligono.res_2 = Reg_poli.getres_2();
                datos_poligono.res_3 = Reg_poli.getres_3();
                datos_poligono.res_4 = Reg_poli.getres_4();
                datos_poligono.res_5 = Reg_poli.getres_5();
                datos_poligono.vel = Reg_poli.getvel();
                datos_poligono.id_capa = Reg_poli.getid_capa();               
                
                
                final PoligonoMovil PoligonoMovil_perfil = new PoligonoMovil(Long.toString(reg_tupla.l2) + "." + Long.toString(datos_poligono.idpoligono), reg_tupla.l2, datos_poligono.idpoligono, datos_poligono.nombre, datos_poligono.user1, datos_poligono.vel, datos_poligono.res_1, datos_poligono.res_2, datos_poligono.res_3, datos_poligono.res_4, datos_poligono.res_5, datos_poligono.id_capa, datos_poligono.planta);
                reg_PoligonoMovilLista_local.add(PoligonoMovil_perfil);
                this.asiga_listaPologonoMovil(reg_PoligonoMovilLista_local);
                pos = reg_PoligonoMovilLista_local.size() - 1;
            }else{
                System.out.println("ERROR TRAE POLIGONOS");   
            }
         
        }
                    }
            catch (IOException | JAXBException e) {
                System.out.println("Grande-----obtiene_datos_poligono =>" + e);
                System.exit(0);        
            }     
        return pos;
    }
    public static <T> T createObjectFromXmlString(String xml, Class<T> clazz) throws JAXBException, IOException{
        T value ;
        StringReader reader = new StringReader(xml); 
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<T> rootElement=jaxbUnmarshaller.unmarshal(new StreamSource(reader),clazz);
        value = rootElement.getValue();
        return value;
    }    
    tipo_datos.Fecha asigna_fecha(final String str_fecha) {
        final tipo_datos.Fecha fecha = new tipo_datos.Fecha();
        fecha.ano = Integer.parseInt(str_fecha.substring(0, 4));
        fecha.mes = Integer.parseInt(str_fecha.substring(5, 7));
        fecha.dia = Integer.parseInt(str_fecha.substring(8, 10));
        return fecha;
    }
    
    boolean validaFecha(final tipo_datos.Fecha fecha) {
        if (fecha.ano < 1900) {
            throw new IllegalArgumentException("Solo se comprueban fechas del a\u00f1o 1900 o posterior");
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String fechaAValidar = String.format("%d-%d-%d", fecha.ano, fecha.mes, fecha.dia);
        boolean result = true;
        try {
            sdf.parse(fechaAValidar);
        }
        catch (ParseException e) {
            result = false;
        }
        return result;
    }
    
    boolean compareHoraFecha(final tipo_datos.FechaHora fechahora_ini, final tipo_datos.FechaHora fechahora_fin) {
        boolean result = false;
        try {
            result = false;
            final DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Date fechahoraI = dateF.parse(fechahora_ini.ano + "-" + fechahora_ini.mes + "-" + fechahora_ini.dia + " " + fechahora_ini.hora + ":" + fechahora_ini.minuto + ":" + fechahora_ini.segundo);
            final Date fechahoraF = dateF.parse(fechahora_fin.ano + "-" + fechahora_fin.mes + "-" + fechahora_fin.dia + " " + fechahora_fin.hora + ":" + fechahora_fin.minuto + ":" + fechahora_fin.segundo);
            if (fechahoraF.compareTo(fechahoraI) > 0) {
                result = true;
            }
        }
        catch (ParseException ex) {
            Logger.getLogger(funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    tipo_datos.datos_Registros_conf asigna_Registros_conf() {
        final tipo_datos.datos_Registros_conf retorno = new tipo_datos.datos_Registros_conf();
        retorno.nombre_tabla_alarmas = env("NOMBRE_TABLA_ALARMAS");
        retorno.url_publicador_alm = envHttp("URL_PUBLICADOR_ALM");
        retorno.url_publicador_alm_vel = envHttp("URL_PUBLICADOR_ALM_VEL");
        retorno.url_obtener_movil = envHttp("URL_OBTENER_MOVIL");
        retorno.url_publicador_alm_gps = envHttp("URL_PUBLICADOR_ALM_GPS");
        retorno.key_publicador_api = env("KEY_PUBLICADOR_API");
        retorno.url_zonas = envHttp("URL_ZONAS");
        retorno.url_zonas_oculta = envHttp("URL_ZONAS_OCULTA");
        retorno.url_calle_cerca = envHttp("URL_CALLE_CERCA");
        retorno.url_zonas_oculta_cerca = envHttp("URL_ZONAS_OCULTA_CERCA");
        retorno.url_obtener_nombre_poligono = envHttp("URL_OBTENER_NOMBRE_POLIGONO");
        retorno.key_zona_api = env("KEY_ZONA_API");
        retorno.url_eventos_especiales = envHttp("URL_EVENTOS_ESPECIALES");
        retorno.key_eventos_api = env("KEY_EVENTOS_API");
        retorno.cuenta_api = env("CUENTA_API");
        retorno.tiempo_delate = env("TIEMPO_DELATE");
        retorno.fecha_hora_minima = env("FECHA_HORA_MINIMA").replace("_", ":");
        retorno.nodo = env("NODO");
        retorno.nodo_alm = env("NODO_ALM");
        retorno.nodo_pg = env("NODO_PG");
        retorno.servidor_gps = env("SERVIDOR_GPS");
        retorno.servidor_alm = env("SERVIDOR_ALM");
        retorno.servidor_contenedor = env("SERVIDOR_CONTENEDOR");
        retorno.servidor_configuracion = env("SERVIDOR_CONFIGURACION");
        retorno.servidor_vls = env("SERVIDOR_VLS");
        retorno.alarmas_en_poligono = env("ALARMAS_EN_POLIGONO");
        return retorno;
    }

    private String env(final String key) {
        final String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Falta variable de entorno requerida: " + key);
        }
        return value.trim();
    }

    private String envHttp(final String key) {
        final String value = env(key);
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }
        return "http://" + value;
    }
    
    Registros_alm_insert asigna_data_alm_insert(final tipo_datos.Tupla_alm reg_tupla, final String key, final String cuenta, final String dato_historico, final String tipo_alm, final int cod_alm, final int B5, final String tiempo_demora_alarma, final String nodo) {
        final Registros_alm_insert retorno = new Registros_alm_insert();
        retorno.setkey(key);
        retorno.setcuenta(cuenta);
        retorno.setN(dato_historico);
        retorno.setD1(reg_tupla.d1);
        retorno.setL2(Long.toString(reg_tupla.l2));
        retorno.setC3(tipo_alm);
        retorno.setI4(Integer.toString(cod_alm));
        retorno.setB5(Integer.toString(B5));
        retorno.setT_D(tiempo_demora_alarma);
        retorno.setpoligono(Long.toString(reg_tupla.idpoly));
        retorno.setL3(Float.toString(reg_tupla.l3));
        retorno.setL4(Float.toString(reg_tupla.l4));
        retorno.setV1(Double.toString(reg_tupla.v1));
        retorno.setL8(Double.toString(reg_tupla.l8));
        retorno.setnodo(nodo);
        return retorno;
    }
    
    Registros_alm_gps_insert asigna_data_alm_gps_insert(final tipo_datos.Tupla_alm reg_tupla, final String key, final String cuenta, final String dato_historico, final String tipo_alm, final int cod_alm, final String tiempo_demora_alarma, final String nombreplng, final String user1, final String nodo) {
        final Registros_alm_gps_insert retorno = new Registros_alm_gps_insert();
        retorno.setkey(key);
        retorno.setcuenta(cuenta);
        retorno.setN(dato_historico);
        retorno.setD1(reg_tupla.d1);
        retorno.setL2(Long.toString(reg_tupla.l2));
        retorno.setC3(tipo_alm);
        retorno.setI4(Integer.toString(cod_alm));
        retorno.setT_D(tiempo_demora_alarma);
        retorno.setE1(reg_tupla.e1);
        retorno.data_nombre(nombreplng);
        retorno.setid_poligono(Long.toString(reg_tupla.idpoly));
        retorno.setdata_user1(user1);
        retorno.setnodo(nodo);
        return retorno;
    }
    
    Registros_alm_insert_vel asigna_data_alm_vel_insert(final tipo_datos.Tupla_alm reg_tupla, final String key, final String cuenta, final int vel_plng, final String nombreplng, final long id_poligono, final String user1, final String nodo) {
        final Registros_alm_insert_vel retorno = new Registros_alm_insert_vel();
        retorno.setkey(key);
        retorno.setcuenta(cuenta);
        retorno.setD1(reg_tupla.d1);
        retorno.setL2(Long.toString(reg_tupla.l2));
        retorno.setL3(Float.toString(reg_tupla.l3));
        retorno.setL4(Float.toString(reg_tupla.l4));
        retorno.setvel(Double.toString(reg_tupla.l8));
        retorno.setvel_plng(Integer.toString(vel_plng));
        retorno.setnombreplng(nombreplng);
        retorno.setid_plng(Long.toString(id_poligono));
        retorno.setuser1(user1);
        retorno.setnodo(nodo);
        return retorno;
    }
    
    /*Registros_insert asigna_data_insert(final tipo_datos.Tupla reg_tupla, final String key, final String cuenta, final String direc_obtenida, final String comuna_obtenida, final long id_poligono, final long id_poligono_oculto, final String nodo) {
        final Registros_insert retorno = new Registros_insert();
        retorno.setkey(key);
        retorno.setcuenta(cuenta);
        retorno.setN(Long.toString(reg_tupla.n));
        retorno.setD1(reg_tupla.d1);
        retorno.setD2(this.date_a_String(this.sumarRestarSegundosFecha(this.string_a_date(this.retorna_fecha_hora_actual()), 900)));
        retorno.setL2(Long.toString(reg_tupla.l2));
        retorno.setL3(Float.toString(reg_tupla.l3));
        retorno.setL4(Float.toString(reg_tupla.l4));
        retorno.setDIREC(direc_obtenida);
        retorno.setCOMUNA_IN(comuna_obtenida);
        retorno.setL8(Double.toString(reg_tupla.l8));
        retorno.setH1(Double.toString(reg_tupla.h1));
        retorno.setV1(Double.toString(reg_tupla.v1));
        retorno.setE1(reg_tupla.e1);
        retorno.setNS(Integer.toString(reg_tupla.pd));
        retorno.setID(reg_tupla.v2);
        retorno.setV3(Double.toString(reg_tupla.v3));
        retorno.setV4(Double.toString(reg_tupla.v4));
        retorno.setV5(Double.toString(reg_tupla.v5));
        retorno.setV6(Double.toString(reg_tupla.v6));
        retorno.setV7(Double.toString(reg_tupla.v7));
        retorno.setV8(Double.toString(reg_tupla.v8));
        retorno.setV9(Double.toString(reg_tupla.v9));
        retorno.setV10(Double.toString(reg_tupla.v10));
        retorno.setV11(Double.toString(reg_tupla.v11));
        retorno.setID_POLIGONO(Long.toString(id_poligono));
        retorno.setID_POLIGONO_OCULTO(Long.toString(id_poligono_oculto));
        retorno.setmobile_contry_code(reg_tupla.mobile_contry_code);
        retorno.setmobile_networt_code(reg_tupla.mobile_networt_code);
        retorno.setlocal_area_code(reg_tupla.local_area_code);
        retorno.setcell_ID(reg_tupla.cell_id);
        retorno.sethour_meter_count(reg_tupla.hour_meter_count);
        retorno.setnodo(nodo);
        return retorno;
    }*/
    
    Registros_eventos_especiales_alm asigna_eventos_esp_alm(final String key, final String cuenta, final tipo_datos.Tupla_alm reg, final String numero_tel, final String user1, final String patente, final String id_poligono, final String nombre_poligono, final String id_planta, final String planta, final String nodo, final String nodo_pg, final String direc,final String comuna,final String ciclo,final String tipo ) {
        final Registros_eventos_especiales_alm retorno = new Registros_eventos_especiales_alm();
        retorno.setkey(key);
        retorno.setcuenta(cuenta);
        retorno.setL2(Long.toString(reg.l2));
        retorno.setfecha_registro(reg.d1);
        retorno.setlatitude(Float.toString(reg.l3));
        retorno.setlongitude(Float.toString(reg.l4));
        retorno.setvel(Double.toString(reg.l8));
        retorno.setpatente(patente);
        retorno.setnumero(numero_tel);
        retorno.setuser1(user1);
        retorno.seti4(Integer.toString(Math.round((float)reg.i4)));
        retorno.setnodo(nodo);
        retorno.setnodo_pg(nodo_pg);
        retorno.setid_poligono(id_poligono);
        retorno.setnombre_poligono(nombre_poligono);
        retorno.setid_planta(id_planta);
        retorno.setplanta(planta);
        retorno.setdirec(direc);
        retorno.setcomuna(comuna);
        retorno.setciclo(ciclo);
        retorno.settipo(tipo);        
        return retorno;
    }
    
    /*tipo_datos.direc_zonas determina_zonas_direcion_comunas(final tipo_datos.Tupla reg_tupla, final tipo_datos.tramas trama_pol, final tipo_datos.datos_Registros_conf conf) {
        final tipo_datos.direc_zonas direccion_comuna_zonas_obt = new tipo_datos.direc_zonas();
        final HttpURLConnection_json js = new HttpURLConnection_json();
        ArrayList<Registros_poligonos_pos> Reg_poli = new ArrayList<Registros_poligonos_pos>();
        ArrayList<Registros_poligonos_pos> Reg_poli_ocul = new ArrayList<Registros_poligonos_pos>();
        direccion_comuna_zonas_obt.direc_obtenida = "SIN CALLE";
        direccion_comuna_zonas_obt.comuna_obtenida = "SIN COMUNA";
        direccion_comuna_zonas_obt.id_zona = 0L;
        direccion_comuna_zonas_obt.id_zona_oculta = 0L;
        String data_zona = null;
        final String parameters_zonas = conf.url_zonas_oculta_cerca + "/cuenta/" + conf.cuenta_api + "/key/" + conf.key_zona_api + "/L2/" + reg_tupla.l2 + "/latitude/" + reg_tupla.l3 + "/longitude/" + reg_tupla.l4 + "/nodo/" + conf.nodo_pg;
        data_zona = js.get(parameters_zonas, 1000);
        Reg_poli_ocul.clear();
        Reg_poli.clear();
        if (data_zona != null) {
            final Registros_zona_oculta_calle msg_zona_oculta_calle = (Registros_zona_oculta_calle)new Gson().fromJson(data_zona, (Class)Registros_zona_oculta_calle.class);
            final String[] datos = this.splitString(msg_zona_oculta_calle.response[0].determina_zona_oculta_calle, "$$");
            if (!datos[0].contains("NO")) {
                Reg_poli_ocul = this.asigna_poligonos(datos[0], 1, reg_tupla, trama_pol, conf);
                direccion_comuna_zonas_obt.id_zona_oculta = Reg_poli_ocul.get(0).getid_poligono();
            }
            if (!datos[1].contains("NO")) {
                Reg_poli = this.asigna_poligonos(datos[1], 0, reg_tupla, trama_pol, conf);
                direccion_comuna_zonas_obt.id_zona = Reg_poli.get(0).getid_poligono();
            }
            final String direc_completa = datos[2].trim();
            if (!datos[2].contains("|")) {
                final String[] direc_total = this.splitString(direc_completa, "|");
                direccion_comuna_zonas_obt.direc_obtenida = direc_total[0];
                direccion_comuna_zonas_obt.comuna_obtenida = direc_total[1];
            }
        }
        Reg_poli_ocul.clear();
        Reg_poli.clear();
        return direccion_comuna_zonas_obt;
    }
    
    ArrayList<Registros_poligonos_pos> asigna_poligonos(final String data_zona, final int oculto, final tipo_datos.Tupla reg_tupla, final tipo_datos.tramas trama_pol, final tipo_datos.datos_Registros_conf conf) {
        final publicador_alarmas publi_alm = new publicador_alarmas();
        final ArrayList<Registros_poligonos_pos> Reg_poli = new ArrayList<Registros_poligonos_pos>();
        final String[] poli = this.splitString(data_zona, "~@~");
        try{
            for (int pos_poli = 0; pos_poli <= poli.length - 1; ++pos_poli) {
                final Registros_poligonos_pos campos_poli = new Registros_poligonos_pos();
                final String[] poli_tupla = poli[pos_poli].split(",");
                campos_poli.setid_poligono(Long.parseLong(poli_tupla[0]));
                campos_poli.setnombre(poli_tupla[1].trim());
                campos_poli.setuser1(poli_tupla[2].trim());
                if (!poli_tupla[3].trim().equals("")) {
                    campos_poli.setvel_res(Integer.parseInt(poli_tupla[3].trim()));
                }
                else {
                    campos_poli.setvel_res(0);
                }
                campos_poli.setres1(poli_tupla[4].trim());
                campos_poli.setres2(poli_tupla[5].trim());
                campos_poli.setcolor(poli_tupla[6].trim());
                if (oculto == 0) {
                    campos_poli.setplanta(poli_tupla[7].trim());
                    campos_poli.setid_capaPlng(poli_tupla[8].trim());
                }
                else {
                    campos_poli.setplanta("0");
                    campos_poli.setid_capaPlng(poli_tupla[7].trim());
                }
                Reg_poli.add(campos_poli);
            }
        }
        catch (Exception e) {
            System.out.println("Grande-----asigna_poligonos =>" + e);
            System.exit(0);        
        }
        return Reg_poli;
    }*/
    
    /*String retorna_fecha_hora_actual() {
        final Calendar c = Calendar.getInstance();
        final String annio = Integer.toString(c.get(1));
        final String dia = Integer.toString(c.get(5));
        final String mes = Integer.toString(c.get(2) + 1);
        final String hora = Integer.toString(c.get(10));
        final String minuto = Integer.toString(c.get(12));
        final String segundo = Integer.toString(c.get(13));
        return annio + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;
    }*/
    
    tipo_datos.Tupla_alm asigna_tupla_alm(final Registros_alm reg, final int pos) {
        final tipo_datos.Tupla_alm reg_local = new tipo_datos.Tupla_alm();
        reg_local.reg = reg.response[pos].reg;
        reg_local.n = reg.response[pos].n;
        reg_local.d1 = reg.response[pos].d1;
        reg_local.l2 = reg.response[pos].l2;
        reg_local.c3 = reg.response[pos].c3;
        reg_local.i4 = reg.response[pos].i4;
        reg_local.t_d = reg.response[pos].t_d;
        reg_local.e1 = reg.response[pos].e1;
        reg_local.idpoly = reg.response[pos].idpoly;
        reg_local.l3 = reg.response[pos].l3;
        reg_local.l4 = reg.response[pos].l4;
        reg_local.v1 = reg.response[pos].v1;
        reg_local.v2 = reg.response[pos].v2;
        reg_local.l8 = reg.response[pos].l8;
        reg_local.v3 = reg.response[pos].v3;
        return reg_local;
    }
    
    /*tipo_datos.Tupla asigna_tupla(final Registros reg, final int pos) {
        final tipo_datos.Tupla reg_local = new tipo_datos.Tupla();
        reg_local.n = reg.response[pos].n;
        reg_local.d1 = reg.response[pos].d1;
        reg_local.e1 = reg.response[pos].e1;
        reg_local.estado = reg.response[pos].estado;
        reg_local.h1 = 0.0f;
        if (reg.response[pos].h1 > 0.0f && reg.response[pos].h1 <= 360.0f) {
            reg_local.h1 = (float)Math.round(reg.response[pos].h1);
        }
        reg_local.l2 = reg.response[pos].l2;
        reg_local.l3 = reg.response[pos].l3;
        reg_local.l4 = reg.response[pos].l4;
        reg_local.l8 = reg.response[pos].l8;
        reg_local.pd = reg.response[pos].pd;
        reg_local.l8 = reg.response[pos].l8;
        reg_local.te = reg.response[pos].te;
        reg_local.tg = reg.response[pos].tg;
        reg_local.v1 = reg.response[pos].v1;
        reg_local.v2 = reg.response[pos].v2;
        reg_local.v3 = reg.response[pos].v3;
        reg_local.v4 = reg.response[pos].v4;
        reg_local.v5 = reg.response[pos].v5;
        reg_local.v6 = reg.response[pos].v6;
        reg_local.v7 = reg.response[pos].v7;
        reg_local.v8 = reg.response[pos].v8;
        reg_local.v9 = reg.response[pos].v9;
        reg_local.v10 = reg.response[pos].v10;
        reg_local.v11 = reg.response[pos].v11;
        reg_local.dato_can = reg.response[pos].dato_can;
        reg_local.dato_index = reg.response[pos].dato_index;
        reg_local.fecha_de_arribo = reg.response[pos].fecha_de_arribo;
        reg_local.mobile_contry_code = reg.response[pos].mobile_contry_code;
        reg_local.mobile_networt_code = reg.response[pos].mobile_networt_code;
        reg_local.local_area_code = reg.response[pos].local_area_code;
        reg_local.cell_id = reg.response[pos].cell_id;
        reg_local.hour_meter_count = reg.response[pos].hour_meter_count;
        reg_local.id = reg.response[pos].id;
        return reg_local;
    }*/
    
    tipo_datos.FechaHora asigna_fechahora(final String str_fecha_hora) {
        final tipo_datos.FechaHora fecha_hora = new tipo_datos.FechaHora();
        fecha_hora.ano = Integer.parseInt(str_fecha_hora.substring(0, 4));
        fecha_hora.mes = Integer.parseInt(str_fecha_hora.substring(5, 7));
        fecha_hora.dia = Integer.parseInt(str_fecha_hora.substring(8, 10));
        fecha_hora.hora = Integer.parseInt(str_fecha_hora.substring(11, 13));
        fecha_hora.minuto = Integer.parseInt(str_fecha_hora.substring(14, 16));
        fecha_hora.segundo = Integer.parseInt(str_fecha_hora.substring(17, 19));
        return fecha_hora;
    }
    
    /*boolean validaHora(final tipo_datos.Horas horas) {
        boolean result = false;
        if (horas.hora <= 23 && horas.hora >= 0) {
            result = true;
        }
        if (horas.minuto <= 60 && horas.minuto >= 0) {
            result = true;
        }
        if (horas.segundo <= 60 && horas.segundo >= 0) {
            result = true;
        }
        return result;
    }*/
    
    /*boolean isNumeric(final String s) {
        try {
            final double y = Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException err) {
            return false;
        }
    }*/
    
    /*Date string_a_date(final String fechaHora) {
        Date fecha = null;
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fecha = formatter.parse(fechaHora);
        }
        catch (ParseException ex) {
            Logger.getLogger(funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }*/
    
    /*String date_a_String(final Date fechaHora) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String fecha = formatter.format(fechaHora);
        return fecha;
    }*/
    
    /*Date sumarRestarSegundosFecha(final Date fecha, final int segundos) {
        final Calendar calendar = Calendar.getInstance();
        try{
        
        
        calendar.setTime(fecha);
        calendar.add(13, segundos);
        
        }
        catch (Exception e) {
            System.out.println("Grande-----sumarRestarSegundosFecha =>" + e);
            System.exit(0);        
        }
        return calendar.getTime();
    }*/
    
    /*String[] splitString(final String str, final String delims) {
        if (str == null) {
            return null;
        }
        if (str.equals("") || delims == null || delims.length() == 0) {
            return new String[] { str };
        }
        final Vector v = new Vector();
        int pos = 0;
        try{
            for (int newpos = str.indexOf(delims, pos); newpos != -1; newpos = str.indexOf(delims, pos)) {
                v.addElement(str.substring(pos, newpos));
                pos = newpos + delims.length();
            }
            v.addElement(str.substring(pos));
        }
        catch (Exception e) {
            System.out.println("Grande-----splitString =>" + e);
            System.exit(0);        
        }        
        final String[] s = new String[v.size()];
        for (int i = 0, cnt = s.length; i < cnt; ++i) {
            s[i]= (String)v.elementAt(i);
        }
        return s;
    } */   
}
