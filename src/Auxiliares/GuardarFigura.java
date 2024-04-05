package Auxiliares;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.io.Serializable;


public class GuardarFigura implements Serializable{
    //variables
    public float grosor;
    public int cap;
    public int join;
    public float qtr;
    public float[] fl;
    public float last;
    public GeneralPath path;
    public Color contorno;
    public Color relleno;
    public double alpha;
    //Constructor

    public GuardarFigura(float grosor, int cap, int join, float qtr, float[] fl, float last, GeneralPath path, Color contorno, double alpha) {
        this.grosor = grosor;
        this.cap = cap;
        this.join = join;
        this.qtr = qtr;
        this.fl = fl;
        this.last = last;
        this.path = path;
        this.contorno = contorno;
        this.relleno = null;
        this.alpha = alpha;
    }//*/
    
    public GuardarFigura(float grosor, int cap, int join, float qtr, float[] fl, float last, GeneralPath path, Color contorno, Color relleno, double alpha) {
        this.grosor = grosor;
        this.cap = cap;
        this.join = join;
        this.qtr = qtr;
        this.fl = fl;
        this.last = last;
        this.path = path;
        this.contorno = contorno;
        this.relleno = relleno;
        this.alpha = alpha;
    }

    public GuardarFigura() {
    }
    //Metodos de acceso set y get
    public float getGrosor() {
        return grosor;
    }

    public void setGrosor(float grosor) {
        this.grosor = grosor;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public float getQtr() {
        return qtr;
    }

    public void setQtr(float qtr) {
        this.qtr = qtr;
    }

    public float[] getFl() {
        return fl;
    }

    public void setFl(float[] fl) {
        this.fl = fl;
    }

    public float getLast() {
        return last;
    }

    public void setLast(float last) {
        this.last = last;
    }

    public GeneralPath getPath() {
        return path;
    }

    public void setPath(GeneralPath path) {
        this.path = path;
    }

    public Color getContorno() {
        return contorno;
    }

    public void setContorno(Color contorno) {
        this.contorno = contorno;
    }

    public Color getRelleno() {
        return relleno;
    }

    public void setRelleno(Color relleno) {
        this.relleno = relleno;
    }
    
    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    
}
