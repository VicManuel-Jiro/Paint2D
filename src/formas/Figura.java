package formas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.Serializable;
 

//Creación de objetos serializables
public class Figura implements Serializable{
    //Definimos las caracteristicas de los objetos
    public Shape shape;
    public BasicStroke stroke;
    public GeneralPath path;
    public Color contorno;
    public Color relleno;
    public double alpha; 
    //Creación de los constructores

    public Figura(Shape shape, BasicStroke stroke, GeneralPath path, Color contorno, double alpha) {
        this.shape = shape;
        this.stroke = stroke;
        this.path = path;
        this.contorno = contorno;
        this.relleno = null;
        this.alpha = alpha;
    }//*/

    public Figura(Shape shape, BasicStroke stroke, GeneralPath path, Color contorno, Color relleno, double alpha) {
        this.shape = shape;
        this.stroke = stroke;
        this.path = path;
        this.contorno = contorno;
        this.relleno = relleno;
        this.alpha = alpha;
    }
    
    
    public Figura() {
    }
    //Metodos de acceso

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public BasicStroke getStroke() {
        return stroke;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
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

    @Override
    public String toString() {
        return "Figura{" + "shape=" + shape + ", stroke=" + stroke + ", path=" + path + ", contorno=" + contorno + ", relleno=" + relleno + ", alpha=" + alpha + '}';
    }
    
}
