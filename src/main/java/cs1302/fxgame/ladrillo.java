package cs1302.fxgame;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ladrillo {

    Rectangle[] brickLayout = new Rectangle[30];
    int brickX = 35, brickY = 60;

   
    //*El constructor de ladrillos se inicia una matriz de Rectángulos 
    
    public ladrillo() {
	
		for(int i = 0; i < brickLayout.length; i++){
	    
	    	if(i % 6 == 0 && i != 0) {
				brickY += 15;
				brickX = 35;
	    	}
	    	brickLayout[i] = new Rectangle(brickX, brickY, 70, 10);
	    	brickLayout[i].setFill(Color.GREEN);
	    	brickX += 75;
	    	
		}

    }

    /**
     * Retorna el estado del ladrillo para validar su HP. 
     *
     *@param brick, la pelota choco con el ladrillo
     *@return retorna el HP del ladrillo
     */
    public int  getBrickState(Rectangle brick) {
		int HP = 3;
		if(brick.getFill() == Color.GREEN) HP = 3;
		else if (brick.getFill() == Color.YELLOW) HP = 2;
		else if (brick.getFill() == Color.RED) HP = 1;
		return HP;
    }
}