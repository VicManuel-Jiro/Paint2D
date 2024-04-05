package Auxiliares;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;


public class DescribePath {
    GeneralPath path;
    public GeneralPath obtenerPath (Shape s){
        path = new GeneralPath();
        PathIterator piterator = s.getPathIterator(null);
        while(piterator.isDone() == false){
            obtenerCurrentSegment(piterator);
            piterator.next();
        }
        return path;
    }
    public void obtenerCurrentSegment(PathIterator piterator){
        double[] coordenadas = new double[6];
        int type = piterator.currentSegment(coordenadas);
        switch(type){
            case PathIterator.SEG_MOVETO:
                path.moveTo(coordenadas[0], coordenadas[1]);
                break;
            case PathIterator.SEG_LINETO:
                path.lineTo(coordenadas[0], coordenadas[1]);
                break;
            case PathIterator.SEG_QUADTO:
                path.quadTo(coordenadas[0], coordenadas[1], coordenadas[2], coordenadas[3]);
                break;
            case PathIterator.SEG_CUBICTO:
                path.curveTo(coordenadas[0], coordenadas[1], coordenadas[2], coordenadas[3], coordenadas[4], coordenadas[5]);
                break;
            case PathIterator.SEG_CLOSE:
                path.closePath();
                break;
            default:
                break;
        }
    }
    public void describePath(Shape s){
        PathIterator piteIterator = s.getPathIterator(null);
        while(piteIterator.isDone() == false){
            describeCurrentSegment(piteIterator);
            piteIterator.next();
        }
    }
    public void describeCurrentSegment(PathIterator piterator){
        double[] coordenadas = new double[6];
        int type = piterator.currentSegment(coordenadas);
        switch(type){
            case PathIterator.SEG_MOVETO:
                System.out.println("move to"+coordenadas[0]+","+coordenadas[1]);
                break;
            case PathIterator.SEG_LINETO:
                System.out.println("line to"+coordenadas[0]+","+coordenadas[1]);
                break;
            case PathIterator.SEG_QUADTO:
                System.out.println("quadratic to"+coordenadas[0]+","+coordenadas[1]
                                        +","+coordenadas[2]+","+coordenadas[3]);
                break;
            case PathIterator.SEG_CUBICTO:
                System.out.println("quadratic to"
                            +coordenadas[0]+","+coordenadas[1]+","
                            +coordenadas[2]+","+coordenadas[3]+","
                            +coordenadas[4]+","+coordenadas[5]);
                break;
            case PathIterator.SEG_CLOSE:
                System.out.println("close");
                break;
            default:
                break;
                
        }
    }
}
