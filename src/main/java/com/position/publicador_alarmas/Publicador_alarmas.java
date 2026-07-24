package com.position.publicador_alarmas;

import java.util.Vector;

public class Publicador_alarmas implements Runnable
{
    int n_procesos;
    public static Vector clientesActivos;
    public static funciones fun;
    public static tipo_datos.datos_Registros_conf conf;
    publicador_regalm publica_alarmas;
    
    public static void esperarXsegundos(final int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }    
    
    public Publicador_alarmas(final int n_procesos) {
        publica_alarmas = new publicador_regalm();
        System.out.println("Crea.....:"+n_procesos);
        Publicador_alarmas.clientesActivos.addElement(n_procesos);
        this.n_procesos = n_procesos;
    }

    static String inserta_alarmas_gps(tipo_datos.Tupla_alm reg, int cod_alm, String user1, String nombre_poligono, tipo_datos.datos_Registros_conf conf) {
        HttpURLConnection_json js = new HttpURLConnection_json();
        funciones fun = new funciones();
        Registros_alm_gps_insert data_insert = fun.asigna_data_alm_gps_insert(reg, conf.key_publicador_api, conf.cuenta_api, "0", "A", cod_alm, "00:00:00", nombre_poligono, user1, conf.nodo);
        String resultado = js.post_alm_gps(conf.url_publicador_alm_gps, data_insert);
        if ("error".equals(resultado)) {
            return "error";
        }
        return resultado;
    }

    public static void main(final String[] args) {
        while (true) {
            for (int n_procesos =1 ; n_procesos <=59; ++n_procesos) {
                if (!Publicador_alarmas.clientesActivos.contains(n_procesos)) {
                    final Runnable proceso1 = new Publicador_alarmas(n_procesos);
                    new Thread(proceso1).start();
                }
            }
            Publicador_alarmas.esperarXsegundos(10);
        }
    }
    
    @Override
    public void run() {
        System.out.println("Inicia.....:"+n_procesos);
        publica_alarmas.nuevo_publicador_regalm(n_procesos, Publicador_alarmas.conf);
    }
    
    static {
        Publicador_alarmas.clientesActivos = new Vector();
        Publicador_alarmas.fun = new funciones();
        Publicador_alarmas.conf=Publicador_alarmas.fun.asigna_Registros_conf();
    }
}
