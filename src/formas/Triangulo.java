package formas;

import java.awt.Polygon;


public class Triangulo extends Polygon {
    //Metodo constructor
    public Triangulo(int x1, int y1, int x2, int y2){
        addPoint(x1, y2);
        addPoint((x1+x2)/2,y1);
        addPoint(x2,y2);
    }
}
