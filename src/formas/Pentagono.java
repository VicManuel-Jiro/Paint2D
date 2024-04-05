package formas;

import java.awt.geom.GeneralPath;


public class Pentagono {
    public GeneralPath myPath;
    
    public Pentagono() {
        int[] pentagonoX = {600, 500, 550, 650,700};
        int[] pentagonoY = {200, 300, 400, 400, 300};
        myPath = new GeneralPath(GeneralPath.WIND_EVEN_ODD, pentagonoX.length);
        
        myPath.moveTo(600, 200);
        for(int i=1; i<pentagonoX.length;i++){
            myPath.lineTo(pentagonoX[i], pentagonoY[i]);
        }
        myPath.closePath();
    }

    public GeneralPath getMyPath() {
        return myPath;
    }
}
