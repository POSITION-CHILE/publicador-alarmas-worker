/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

/**
 *
 * @author egatica
 */
public class tipo_datos
 {
    public static int[] diasMes;
    
    static {
        tipo_datos.diasMes = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    }
    
      public static class Tupla_Movil
    {
        public String USER1;
        public long L2;
        public String plate;
        public int R12;
        public int tipo;
        public int modem;
        public String numero;
        public float rendimiento;
        public float factor_correcion;
        public float costo_combustible;
        public float tipo_combustible;
        public String marca;
        public String modelo;
        public int anno;
        public long km_inicial;
        public long id_equipo;
        public int estado;
        public int tele;
        public int color_stop;
        public int color_mov;
        public int tipo_ib;
        public int L2F;
        public int DISPONIBLE;
        public float VOLT_MIN;
        public float VOLT_MAX;
        public String OBSERVACION;
        public float CAPACIDAD;
        public float ALTURA;
        public float ANCHO;
        public float LARGO;
        public String COLOR_ICO;
        public int CORTE_MOTOR;
    }
    
    /*public static class tramas
    {
        String trama_poligono;
        String trama_poligono_oculto;
    }*/
    
    /*public static class Tupla
    {
        int n;
        String d1;
        long l2;
        float l3;
        float l4;
        double l8;
        String tg;
        float h1;
        float v1;
        String e1;
        int pd;
        String v2;
        int te;
        float v3;
        float v4;
        float v5;
        float v6;
        float v7;
        float v8;
        float v9;
        float v10;
        float v11;
        String fecha_de_arribo;
        int dato_index;
        String dato_can;
        int estado;
        String mobile_contry_code;
        String mobile_networt_code;
        String local_area_code;
        String cell_id;
        String hour_meter_count;
        long id;
    }*/
    
    public static class Tupla_alm
    {
        public long reg;
        public int n;
        public long l2;
        public String d1;
        public String c3;
        public int i4;
        public String t_d;
        public String e1;
        public long idpoly;
        public float l3;
        public float l4;
        public float v1;
        public String v2;
        public double l8;
        public float v3;
    }
    
    /*public static class Horas
    {
        int hora;
        int minuto;
        int segundo;
    }*/
    
    public static class FechaHora
    {
        int ano;
        int mes;
        int dia;
        int hora;
        int minuto;
        int segundo;
    }
    
    public static class Fecha
    {
        int ano;
        int mes;
        int dia;
    }
    
   /* public static class direc_zonas
    {
        String direc_obtenida;
        String comuna_obtenida;
        long id_zona;
        long id_zona_oculta;
    }*/
    
    
    /*public static class datos_vehiculos
    {
        String user1;
        long L2;
        String plate;
        String numero;
        float rendimiento;
        float factor_correccion;
        float costo_conbustible;
        float tipo_combustible;
        float marca;
        float modelo;
        float ano;
        float anno;
        float estado;
        float tele;
        float volt_min;
        float volt_max;
    }*/
    
    public static class datos_poligono
    {
        long idpoligono;
        String nombre;
        float vel;
        String user1;
        String res_1;
        String res_2;
        String res_3;
        String res_4;
        String res_5;
        int id_capa;
        int planta;
    }
    
    public static class datos_Registros_conf
    {
        String nombre_tabla_alarmas;
        String url_publicador_alm;
        String url_publicador_alm_vel;
        String url_publicador_alm_gps;
        String key_publicador_api;
        String key_eventos_api;
        String url_eventos_especiales;
        String cuenta_api;
        String tiempo_delate;
        String fecha_hora_minima;
        String nodo;
        String nodo_alm;
        String nodo_pg;
        String servidor_gps;
        String servidor_alm;
        String servidor_contenedor;
        String servidor_configuracion;
        String servidor_vls;
        String url_obtener_nombre_poligono;
        String url_obtener_movil;
        String key_zona_api;
        String alarmas_en_poligono;
        String url_zonas;
        String url_zonas_oculta;
        String url_calle_cerca;
        String url_zonas_oculta_cerca;
        /*String ipc_pgsqlserv;
        String dbipc_pgsqlserv;
        String puertoc_pgsqlserv;
        String use_pgsqlserv;
        String cla_pgsqlserv;*/
    }
}
