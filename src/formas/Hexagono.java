package formas;

import java.awt.geom.GeneralPath;


public class Hexagono {
    public GeneralPath myPath;
    public Hexagono(){
        int[] hexagonoX = {40, 35, 40, 50, 55, 50};
        int[] hexagonoY = {50, 55, 60, 60, 55, 50};
        myPath = new GeneralPath(GeneralPath.WIND_EVEN_ODD, hexagonoX.length);
        
        myPath.moveTo(40, 50);
        for(int i=1; i<hexagonoX.length;i++){
            myPath.lineTo(hexagonoX[i], hexagonoY[i]);
        }
        myPath.closePath();
    }

    public GeneralPath getMyPath() {
        return myPath;
    }
}
