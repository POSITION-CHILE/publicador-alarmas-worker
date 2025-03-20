/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.position.publicador_alarmas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author egatica
 */
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Registros_poligonos {
    
        @XmlElement(name="id_poligono") public long id_poligono;
        @XmlElement(name="nombre") public String nombre;
        @XmlElement(name="user1") public String user1;
        @XmlElement(name="vel") public float vel;
        @XmlElement(name="res_1") public String res_1;
        @XmlElement(name="res_2") public String res_2;
        @XmlElement(name="res_3") public String res_3;
        @XmlElement(name="res_4") public String res_4;
        @XmlElement(name="res_5") public String res_5;
        @XmlElement(name="id_capa") public int id_capa;
        @XmlElement(name="planta") public int planta;
        
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
        public float getvel() {
            return this.vel;
        }

        public String getres_1() {
            return this.res_1;
        }
        
        public void setres_1(String res_1) {
            this.res_1 = res_1;
        }
        public String getres_2() {
            return this.res_2;
        }
        public void setres_2(String res_2) {
            this.res_2 = res_2;
        }     
        public String getres_3() {
            return this.res_3;
        }  
        public void setres_3(String res_3) {
            this.res_3 = res_3;
        } 
        public String getres_4() {
            return this.res_4;
        }  
        public void setres_4(String res_4) {
            this.res_4 = res_4;
        }   
        public String getres_5() {
            return this.res_5;
        }  
        public void setres_5(String res_5) {
            this.res_5= res_5;
        }             
        public void setvel(float vel) {
            this.vel = vel;
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
