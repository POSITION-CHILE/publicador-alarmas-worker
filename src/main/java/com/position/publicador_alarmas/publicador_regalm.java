/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author egatica
 */
public class publicador_regalm
{
    HttpURLConnection_json js;
    funciones fun;
    valida val;
    publicador_alarmas alm;
    static ArrayList<Movil> reg_MovilLista;
    static ArrayList<PoligonoMovil> reg_PoligonoMovilLista;    
    
    public publicador_regalm() {
        this.js = new HttpURLConnection_json();
        this.fun = new funciones();
        this.val = new valida();
        this.alm = new publicador_alarmas();
    }
    
    public void nuevo_publicador_regalm(int n_procesos, tipo_datos.datos_Registros_conf conf)  {
        ArrayList<Movil> reg_MovilLista = new ArrayList<Movil>();
        ArrayList<PoligonoMovil>reg_PoligonoMovilLista = new ArrayList<PoligonoMovil> ();
        String data = "";
        String error ="";
        String resultado="";
        String retvalinsert = "";
        int cod_alm;
        long last_id;
        try {
            while (true) {
                error = "";
                //System.out.println("Captura registros... de:"+n_procesos);
                data = this.js.get(conf.url_publicador_alm + "/nodo_alm/" + conf.nodo_alm + "/sql_num/" + String.valueOf(n_procesos) + "/servidor/" + conf.servidor_alm + "/cuenta/" + conf.cuenta_api + "/key/" + conf.key_publicador_api, 5000);
                int tiempo_sleep = Integer.parseInt(conf.tiempo_delate);
                if (data != null) {
                    if ("error".equals(data)){
                        //Publicador_alarmas.clientesActivos.removeElement(n_procesos);
                        break;
                    }
                    tiempo_sleep = 0;
                    Registros_alm msg = (Registros_alm)new Gson().fromJson(data, (Class)Registros_alm.class);
                    for (int pos_reg_tabla = 0; pos_reg_tabla <= msg.response.length - 1; ++pos_reg_tabla) {
                        last_id = msg.response[pos_reg_tabla].reg;
                        boolean borra_alm = true;
                        if (msg.response[pos_reg_tabla].d1 != null) {
                            if (!"0000-00-00 00:00:00".equals(msg.response[pos_reg_tabla].d1)) { 
                                tipo_datos.Tupla_alm reg = this.fun.asigna_tupla_alm(msg, pos_reg_tabla);
                                if (this.val.valida_registros_alm(reg, conf)) {
                                    int index_movil = this.fun.carga_arr_Movil(n_procesos,reg_MovilLista, reg, conf);
                                    if (index_movil != -1) {
                                        String nombre_poligono = "";
                                        long id_poligono = -1L;
                                        //long L2 = reg.l2;
                                        //long I4 = reg.i4;
                                        long id_planta = -1L;
                                        long planta = 0L;
                                        //String fecha_pos = reg.d1;
                                        //float L3 = reg.l3;
                                        //float L4 = reg.l4;
                                        String user1 = reg_MovilLista.get(index_movil).getuser1();
                                        String patente = reg_MovilLista.get(index_movil).getplate();
                                        if (reg.idpoly != -1L) {
                                            id_poligono = reg.idpoly;
                                            int index_poligono = this.fun.obtiene_datos_poligono(reg_PoligonoMovilLista, reg, conf);
                                            if (index_poligono != -1) {
                                                nombre_poligono = reg_PoligonoMovilLista.get(index_poligono).getnombre();
                                                id_planta = reg_PoligonoMovilLista.get(index_poligono).getid_capa();
                                                planta = reg_PoligonoMovilLista.get(index_poligono).getplanta();
                                            }
                                        }
                                        String ciclo = "";
                                        if (!"00:00:00".equals(reg.t_d)) {
                                            ciclo = reg.t_d;
                                        }
                                        String direc="";
                                        String comuna="";
                                        String tipo=Integer.toString(reg_MovilLista.get(index_movil).gettipo());

                                        Registros_eventos_especiales_alm eventos_esp_alm = this.fun.asigna_eventos_esp_alm(conf.key_eventos_api, conf.cuenta_api, reg, reg_MovilLista.get(index_movil).getnumero(), user1, patente, Long.toString(id_poligono), nombre_poligono, Long.toString(id_planta), Long.toString(planta), conf.nodo, conf.nodo_pg,direc,comuna,ciclo,tipo);
                                        
                                        //resultado="00-" + eventos_esp_alm.i4;
                                        resultado = this.js.post_event_esp_alm(conf.url_eventos_especiales, eventos_esp_alm);
                                        error = "post_event_esp_alm =>" + resultado + "<=";
                                        //System.out.println("RETORNO " + error);
                                        boolean inserta_alm = false;

                                        if (resultado!=null){
                                            if ("error".equals(resultado)){
                                                //Publicador_alarmas.clientesActivos.removeElement(n_procesos);
                                                break;
                                            }                                            
                                            String[] resultado_arr = resultado.split("-");
                                            if ("00".equals(resultado_arr[0])) {
                                                inserta_alm = true;
                                                borra_alm = true;
                                            }
                                            if ("10".equals(resultado_arr[0])) {
                                                inserta_alm = false;
                                                borra_alm = true;
                                            }
                                            if ("11".equals(resultado_arr[0])) {
                                                inserta_alm = false;
                                                borra_alm = false;
                                            }
                                            if ("01".equals(resultado_arr[0])) {
                                                inserta_alm = true;
                                                borra_alm = false;
                                            }
                                            //cod_alm = reg.i4;
                                            cod_alm = Integer.parseInt(resultado_arr[1]);

                                            retvalinsert = "";
                                            if (inserta_alm) {
                                                //System.out.println("Inserta.....");
                                                System.out.println("Inserta Fecha=" + reg.d1 + ",L2=" + reg.l2 + ",I4=" + reg.i4 + ",Last id=" + last_id + ",ID poly:" + reg.idpoly + ",Nombre:>" + nombre_poligono+"<");
                                                retvalinsert = this.alm.inserta_alarmas_gps(reg, cod_alm, user1, nombre_poligono, conf);
                                            }
                                            else{
                                                System.out.println("NO Inserta Fecha=" + reg.d1 + ",L2=" + reg.l2 + ",I4=" + reg.i4 + ",Last id=" + last_id + ",ID poly:" + reg.idpoly + ",Nombre:>" + nombre_poligono+"<");
                                            }
                                            //System.out.println("ID poly:" + reg.idpoly + ",Nombre:>" + nombre_poligono+"<");
                                            
                                            if ("error".equals(retvalinsert)) {
                                                System.out.println("=>Inser error " + user1 + " " + patente + " " + nombre_poligono + " T LMOVIL:" + publicador_regalm.reg_MovilLista.size() + " T LMOVILPOLI:" + publicador_regalm.reg_PoligonoMovilLista.size());
                                            }
                                            //else {
                                            //    System.out.println(retvalinsert);
                                            //}
                                        }else{
                                            borra_alm = false;
                                        }
                                    }
                                }
                            }
                        }
                        if (borra_alm) {
                            //marca para borrar campo tr=1
                            String parameters = "key=" + conf.key_publicador_api + "&cuenta=" + conf.cuenta_api + "&nodo_alm=" + conf.nodo_alm + "&last_id=" + String.valueOf(last_id) + "&servidor=" + conf.servidor_alm;
                            js.put(conf.url_publicador_alm, parameters);
                        }
                    }
                    //borra todos los marcados
                    String parameters2 = "key=" + conf.key_publicador_api + "&cuenta=" + conf.cuenta_api + "&nodo_alm=" + conf.nodo_alm + "&servidor=" + conf.servidor_alm;
                    this.js.detete(conf.url_publicador_alm, parameters2);
                    //System.out.println("\nRegistros borrados ::..." + "("+n_procesos+")" + data_borra + "\n");
                }
                if (tiempo_sleep > 0) {
                    //System.out.println(conf.nodo_alm + " Numero " + String.valueOf(n_procesos) + " Revisa tabla cada " + tiempo_sleep + " segundos ....");
                    esperarXsegundos(tiempo_sleep);
                }
                
            }
        }
        catch (JsonSyntaxException | NumberFormatException | SQLException e2) {
            System.out.println("Grande final---Error Numero proceso:" + n_procesos + " Error =>" + error + "<= " + e2);
            System.out.println("Leyendo data---:>"+data+"<" );
            System.out.println("Resultado Eventos Especiales---:>" + resultado +"<" );
            System.out.println("Salida Insert---:> "+ retvalinsert+"<");
            //Publicador_alarmas.clientesActivos.removeElement(n_procesos);
            System.exit(0);
        }
        finally {
            System.out.println("Remove.....:"+n_procesos);
            Publicador_alarmas.clientesActivos.removeElement(n_procesos);
        }
    }
    
    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
