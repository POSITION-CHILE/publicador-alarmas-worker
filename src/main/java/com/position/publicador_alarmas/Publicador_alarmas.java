
package com.position.publicador_alarmas;

import java.util.Vector;

/**
 *
 * @author egatica
 */
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
    //publicador_alarmas 1  to 59
    //publicador_alarmas_recal 60 to 60 
    public static void main(final String[] args) {
        while (true) {
            for (int n_procesos =1 ; n_procesos <=59; ++n_procesos) {
                if (!Publicador_alarmas.clientesActivos.contains(n_procesos)) {
                    final Runnable proceso1 = new Publicador_alarmas(n_procesos);
                    new Thread(proceso1).start();
                }
            }
            //System.out.println("Revisa Procesos cada 5 segundo");
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
