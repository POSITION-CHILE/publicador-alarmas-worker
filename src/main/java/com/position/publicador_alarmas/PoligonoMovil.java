/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

/**
 *
 * @author egatica
 */
public final class PoligonoMovil
{
    public String L2_id_poligono;
    public long L2;
    public long id_poligono;
    public String nombre;
    public String user1;
    public float vel_res;
    public String res1;
    public String res2;
    public String res3;
    public String res4;
    public String res5;
    public int id_capa;
    public int planta;
    
    public PoligonoMovil(final String _L2_id_poligono, final long _L2, final long _id_poligono, final String _nombre, final String _user1, final float _vel_res, final String _res1, final String _res2, final String _res3, final String _res4, final String _res5, final int _id_capa, final int _planta) {
        setL2_id_poligono(_L2_id_poligono);
        setL2(_L2);
        setid_poligono(_id_poligono);
        setnombre(_nombre);
        setuser1(_user1);
        setvel_res(_vel_res);
        setres1(_res1);
        setres2(_res2);
        setres3(_res3);
        setres4(_res4);
        setres5(_res5);
        setres5(_res5);
        setid_capa(_id_capa);
        setplanta(_planta);
    }
    
    public String getL2_id_poligono() {
        return this.L2_id_poligono;
    }
    
    public void setL2_id_poligono( String L2_id_poligono) {
        this.L2_id_poligono = L2_id_poligono;
    }
    
    public long getL2() {
        return this.L2;
    }
    
    public void setL2( long L2) {
        this.L2 = L2;
    }
    
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
    
    public float getvel_res() {
        return this.vel_res;
    }
    
    public void setvel_res(float vel_res) {
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
    
    public String getres3() {
        return this.res3;
    }
    
    public void setres3(String res3) {
        this.res3 = res3;
    }
    
    public String getres4() {
        return this.res4;
    }
    
    public void setres4(String res4) {
        this.res4 = res4;
    }
    
    public String getres5() {
        return this.res5;
    }
    
    public void setres5(String res5) {
        this.res5 = res5;
    }
    
    public int getid_capa() {
        return this.id_capa;
    }
    
    public void setid_capa(int id_capa) {
        this.id_capa = id_capa;
    }
    
    public int getplanta() {
        return this.planta;
    }
    
    public void setplanta(int planta) {
        this.planta = planta;
    }
}
