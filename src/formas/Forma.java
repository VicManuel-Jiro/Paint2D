/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formas;

import Auxiliares.Archivo;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.HashMap;

/**
 *
 * @author Jirok
 */
public class Forma {

    Shape figura;
    Archivo archivo = new Archivo();
    private GeneralPath pathTemporal = null;
    private AffineTransform transformacion;
    public double anchoOriginal;
    public double anchoActual;
    public double anchoIncremento;
    public double altoOriginal;
    public double altoActual;
    public double altoIncremento;
    public int indexModificado;

    public Forma(Point p1, Point p2, int f, String n) {
        double xInicio = Math.min(p1.getX(), p2.getX());
        double yInicio = Math.min(p1.getY(), p2.getY());
        double ancho = Math.abs(p2.getX() - p1.getX());
        double altura = Math.abs(p2.getY() - p1.getY());
        switch (f) {
            case 0://personal
                transformacion = new AffineTransform();
                HashMap<String, GeneralPath> h = archivo.leerFiguras();
                pathTemporal = h.get(n);
                anchoOriginal = pathTemporal.getBounds().getWidth();
                anchoActual = p2.getX() - p1.getX();
                anchoIncremento = anchoActual * 1.0 / anchoOriginal;
                altoOriginal = pathTemporal.getBounds().getHeight();
                altoActual = p2.getY() - p1.getY();
                altoIncremento = altoActual * 1.0 / altoOriginal;
                transformacion.translate(p1.getX(), p1.getY());
                transformacion.scale(anchoIncremento, altoIncremento);
                figura = transformacion.createTransformedShape(pathTemporal);
                break;
            case 1://linea
                figura = new Line2D.Double(p1, p2);

                break;
            case 2://cuadro
                figura = new Rectangle2D.Double(xInicio, yInicio, ancho, altura);

                break;
            case 3://circulo
                figura = new Ellipse2D.Double(xInicio, yInicio, ancho, altura);
                break;
            case 4://triangulo
                figura= new Triangulo(p2.x,p2.y,p1.x-p2.x,p1.y-p2.y);
                break;
            case 5://pentagono
                Pentagono pen = new Pentagono();
                transformacion = new AffineTransform();
                pathTemporal = pen.getMyPath();
                anchoOriginal = pathTemporal.getBounds().getWidth();
                anchoActual = p2.getX() - p1.getX();
                anchoIncremento = anchoActual * 1.0 / anchoOriginal;
                altoOriginal = pathTemporal.getBounds().getHeight();
                altoActual = p2.getY() - p1.getY();
                altoIncremento = altoActual * 1.0 / altoOriginal;
                transformacion.translate(p1.getX(), p1.getY());
                transformacion.scale(anchoIncremento, altoIncremento);
                figura= transformacion.createTransformedShape(pathTemporal);
                break;
            case 6://hexagono
                Hexagono hex = new Hexagono();
                transformacion = new AffineTransform();
                pathTemporal = hex.getMyPath();
                anchoOriginal = pathTemporal.getBounds().getWidth();
                anchoActual = p2.getX() - p1.getX();
                anchoIncremento = anchoActual * 1.0 / anchoOriginal;
                altoOriginal = pathTemporal.getBounds().getHeight();
                altoActual = p2.getY() - p1.getY();
                altoIncremento = altoActual * 1.0 / altoOriginal;
                transformacion.translate(p1.getX(), p1.getY());
                transformacion.scale(anchoIncremento, altoIncremento);
                figura= transformacion.createTransformedShape(pathTemporal);
                break;
            default:
                break;
        }
    }

    public Shape getShape() {
        return figura;
    }

}
