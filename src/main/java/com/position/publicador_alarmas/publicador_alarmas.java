/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

/**
 *
 * @author egatica
 */
public class publicador_alarmas
{
    HttpURLConnection_json js;
    funciones fun;
    
    public publicador_alarmas() {
        this.js = new HttpURLConnection_json();
        this.fun = new funciones();
    }
    
    String inserta_alarmas_gps(tipo_datos.Tupla_alm reg, int cod_alm, String user1, String nombre_poligono, tipo_datos.datos_Registros_conf conf) {
        //String dato_historico = "0";
        //String tipo_alm = "A";
        //String tiempo_demora_alarma = "00:00:00";
        Registros_alm_gps_insert data_insert = this.fun.asigna_data_alm_gps_insert(reg, conf.key_publicador_api, conf.cuenta_api, "0", "A", cod_alm, "00:00:00", nombre_poligono, user1, conf.nodo);
        String resultado = this.js.post_alm_gps(conf.url_publicador_alm_gps, data_insert);
        if ("error".equals(resultado)) {
            return "error";
        }
        return resultado;
    }
    
    /*boolean inserta_alarmas(tipo_datos.Tupla_alm reg, int cod_alm, int B5, tipo_datos.datos_Registros_conf conf) {
        //String dato_historico = "0";
        //String tipo_alm = "A";
        //String tiempo_demora_alarma = "00:00:00";
        Registros_alm_insert data_insert = this.fun.asigna_data_alm_insert(reg, conf.key_publicador_api, conf.cuenta_api, "0", "A", cod_alm, B5, "00:00:00", conf.nodo_alm);
        this.js.post_alm(conf.url_publicador_alm, data_insert);
        return true;
    }*/
    
    /*boolean inserta_alarmas_vel(tipo_datos.Tupla_alm reg, int vel_png, String nombreplng, long id_poligono, String user1, tipo_datos.datos_Registros_conf conf) {
        Registros_alm_insert_vel data_insert = this.fun.asigna_data_alm_vel_insert(reg, conf.key_publicador_api, conf.cuenta_api, vel_png, nombreplng, id_poligono, user1, conf.nodo);
        String resultado = this.js.post_alm_vel(conf.url_publicador_alm, data_insert);
        return true;
    }*/
}