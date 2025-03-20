/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

/**
 *
 * @author egatica
 */
public class valida
{
    funciones fun;
    
    public valida() {
        this.fun = new funciones();
    }
    
    boolean valida_registros_alm(final tipo_datos.Tupla_alm reg_tupla, final tipo_datos.datos_Registros_conf conf) {
        boolean registro_valido;
        final tipo_datos.Fecha fecha_ini = this.fun.asigna_fecha(reg_tupla.d1);
        while (this.fun.validaFecha(fecha_ini)) {
            final tipo_datos.FechaHora fecha_hora_ini = this.fun.asigna_fechahora(conf.fecha_hora_minima);
            final tipo_datos.FechaHora fecha_hora_nex = this.fun.asigna_fechahora(reg_tupla.d1);
            if (this.fun.compareHoraFecha(fecha_hora_ini, fecha_hora_nex)) {
                registro_valido = true;
                if (reg_tupla.v1 > 200.0f) {
                    registro_valido = false;
                }
                else {
                    registro_valido = true;
                    if (reg_tupla.v3 > 200.0f) {
                        registro_valido = false;
                    }
                    else {
                        registro_valido = true;
                        if (!registro_valido) {
                            continue;
                        }
                    }
                }
            }
            else {
                registro_valido = false;
            }
            return registro_valido;
        }
        registro_valido = false;
        return registro_valido;
    }
}
