/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

/**
 *
 * @author egatica
 */
public class Registros_poligonos_pos
{
    private long id_poligono;
    private String nombre;
    private String user1;
    private int vel_res;
    private String res1;
    private String res2;
    private String color;
    private String id_capaPlng;
    private String planta;
    
    public long getid_poligono() {
        return this.id_poligono;
    }
    
    public void setid_poligono(long id_poligono) {
        this.id_poligono = id_poligono;
    }
    
    public String getnombre() {
        return this.nombre;
    }
    
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getuser1() {
        return this.user1;
    }
    
    public void setuser1(String user1) {
        this.user1 = user1;
    }
    
    public int getvel_res() {
        return this.vel_res;
    }
    
    public void setvel_res(int vel_res) {
        this.vel_res = vel_res;
    }
    
    public String getres1() {
        return this.res1;
    }
    
    public void setres1(String res1) {
        this.res1 = res1;
    }
    
    public String getres2() {
        return this.res2;
    }
    
    public void setres2(String res2) {
        this.res2 = res2;
    }
    
    public String getcolor() {
        return this.color;
    }
    
    public void setcolor(String color) {
        this.color = color;
    }
    
    public void setid_capaPlng(String id_capaPlng) {
        this.id_capaPlng = id_capaPlng;
    }
    
    public void setplanta(String planta) {
        this.planta = planta;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nid_poligono: ");
        sb.append(this.id_poligono);
        sb.append("\nnombre: ");
        sb.append(this.nombre);
        sb.append("\nuser1: ");
        sb.append(this.user1);
        sb.append("\nres_vel: ");
        sb.append(this.vel_res);
        sb.append("\nres1: ");
        sb.append(this.res1);
        sb.append("\nres2: ");
        sb.append(this.res2);
        sb.append("\ncolor: ");
        sb.append(this.color);
        sb.append("\nid_capaPlng: ");
        sb.append(this.id_capaPlng);
        sb.append("\nplanta: ");
        sb.append(this.planta);
        return sb.toString();
    }
}