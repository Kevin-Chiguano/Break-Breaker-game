package cs1302.fxgame;
import com.michaelcotterell.game.Game;
import com.michaelcotterell.game.GameTime;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class ladrilloRoto extends Game {

    //modos de juego.
    private enum modoJuego {Iniciar, Jugar, Ganar, Perder}
    modoJuego mode = modoJuego.Iniciar;

    boolean newnivel = true;
    double rapBola = 2.0;
    int puntos = 0, vidas = 3, ladrillosRotos = 0, nivel = 1;
    double xDireccion, yDireccion;
    int x = 30;

    Text IniciarText = new Text(){{
		setFill(Color.WHITE);
		setX(180);
		setY(220);
    }};
	   
    Text puntosPantalla = new Text(){{
		setFill(Color.RED);
		setX(450);
		setY(10);
		setText("Puntos: " + puntos + "\nVidas: " + vidas);
    }};

    Text GanarMensaje = new Text(){{
		setText("FELICITACIONES! HAS GANADO! \n PRESIONE ESPACIO PARA JUGAR OTRA VEZ.");
		setX(200);
		setY(220);
    }};
    Text perderMensaje = new Text(){{
		setText("Lo siento has perdido " + "\nPulse Enter para jugar otra vez");
		setX(180);
		setY(220);
		setFill(Color.WHITE);
    }};
    Rectangle paredTop = new Rectangle(0,0,520,1);
    Rectangle paredBajo = new Rectangle(0,460,520,1);
    Rectangle paredDerecha = new Rectangle(520,0,1,460);
    Rectangle paredIzq = new Rectangle(0,0,1,460);
	
    Rectangle fondo = new Rectangle(0,0,520,460){{
	setFill(Color.BLACK);	//color de fondo de pantalla
    }};

    Rectangle raqueta = new Rectangle(225,400,90,15){{
	setFill(Color.CORAL);
    }};
    
    Circle bola = new Circle(260,300,5){{
	setFill(Color.WHITE);
    }};

    ladrillo bricks;
    
    /**Construye La nueva instancia del juego.
     *
     * @param stage, el principal esenario "stage"
     */
    public ladrilloRoto(Stage stage) {
	super(stage, "BRICK BREAK!", 60, 520,460);
    }


    // Mueve la pelota cambiando las propiedades del centro en el eje X y Y    
    public void moverbola() {
	bola.setCenterX(bola.getCenterX() + (xDireccion * rapBola));
	bola.setCenterY(bola.getCenterY() + (yDireccion * rapBola));
    }

    // Comprueba las colisiones de la pelota con los ladrillos y raqueta
	// y maneja los HP de los ladrillos.
    public void checkForCollision() {

		for(int i = 0; i < bricks.brickLayout.length; i++){
	    	if(bricks.brickLayout[i] != null){ 
				if(bola.intersects(bricks.brickLayout[i].getBoundsInLocal())){ 	//golpes de la pelota
		    		switch(bricks.getBrickState(bricks.brickLayout[i])){
		    			case 3: 
						bricks.brickLayout[i].setFill(Color.YELLOW);
						break;
		    			case 2:
						bricks.brickLayout[i].setFill(Color.RED);
						break;
		    			case 1: 
						try{													//cuando un ladrillo rojo pase a ser un valor nulo el programa colapsaria
			    			bricks.brickLayout[i] = null;						//pero el try hace que eso no pase y el programa siga su curso
						}														//con el contador de ladrillos rotos y el puntaje	
						catch (Exception e){} 									//pero sin dar error
						ladrillosRotos++;												
						puntos++;
						checkGanar();
						break;
		    		}
		    		yDireccion = yDireccion * -1;
				}
	    	}
		}
		if(bola.intersects(paredDerecha.getBoundsInLocal()) || bola.intersects(paredIzq.getBoundsInLocal())){
		    xDireccion = xDireccion * -1;
	    }
		if(bola.intersects(paredTop.getBoundsInLocal())){
	    	yDireccion = yDireccion * -1;
		}
	
		if(bola.intersects(paredBajo.getBoundsInLocal())){
	    	lifePerder();
		}
		if(bola.intersects(raqueta.getBoundsInLocal())){
	    	if(bola.getCenterX() < (raqueta.getX() + raqueta.getWidth()/2)){ //mov. de la raqueta izq
				if(xDireccion > 0){
		    		xDireccion = xDireccion * -1;
				}
	   	 	}
	    	else{ //mov. de la raqueta lado der
				if(xDireccion < 0){
		    		xDireccion = xDireccion * -1;
				}
	    	}
	    	yDireccion = yDireccion * -1;
		}
    }//finaliza los chequeos de colicion

    /**
     controla las vidas del jugador
	 */
    public void lifePerder() {
		vidas -= 1;
		if(vidas >= 0){
	    	bola.setCenterX(260);
	   		bola.setCenterY(300);
	    	raqueta.setX(225);
	    	xDireccion = 1.0;
	    	yDireccion = 1.0;
		}
		else {
	    	mode = modoJuego.Perder;
		}
    }

    //actualiza los metodos dependiendo del modo de juego mediante un bucle
    public void update(Game game, GameTime gameTime) {
		switch (mode) {
			case Iniciar: 
	    	updateIniciar(game, gameTime);
	    	break;
			case Jugar:
	    	updateJugar(game,gameTime);
	    	break;
			case Ganar:
	    	updateGanar(game, gameTime);
	    	break;
			case Perder:
	    	updatePerder(game, gameTime);
	    	break;
	    }
	
    }

    //Modo ganar del juego.

    public void updateGanar(Game game, GameTime gameTime) {
		getSceneNodes().getChildren().setAll(GanarMensaje, fondo);
		if(game.getKeyManager().isKeyPressed(KeyCode.SPACE)){
	    	mode = modoJuego.Jugar;
		}
    }
    
	//Comprueba si el jugador a ganado
    public void checkGanar() {
		if(ladrillosRotos == 90 && nivel == 3){
	    	mode = modoJuego.Ganar;
		}
		else if(ladrillosRotos % 30 == 0 && ladrillosRotos != 0){
	    	nivel++;
	    	rapBola += 1.0;
	    	newnivel = true;
	    	bola.setCenterX(260);
	    	bola.setCenterY(300);
		}
    }
    
	
    // actualiza el metodo cuando el jugador pierde y permite iniciar un juego nuevo
    
    public void updatePerder(Game game, GameTime gameTime) {

		getSceneNodes().getChildren().setAll(fondo, perderMensaje);
	
		if(game.getKeyManager().isKeyPressed(KeyCode.ENTER)){
	    	mode = modoJuego.Iniciar;
		}
    }

    //Actualiza  el metodo jugar mientras el juego corre y controla la informacion dentro de la pantalla
	
    public void updateJugar(Game game, GameTime gameTime) {
	
		getSceneNodes().getChildren().setAll(fondo, raqueta, bola, puntosPantalla);
		if(newnivel){
	    	bricks = new ladrillo();
	    	newnivel = false;
		}
		try{
	    	for(int i = x - 30; i < x; i++){
				if(bricks.brickLayout[i] != null){
		    		getSceneNodes().getChildren().addAll(bricks.brickLayout[i]);
				}
	    	}
		}catch(Exception e) {}

		//Iniciar movimiento the bola
		if(gameTime.getTotalGameTime().getSeconds() <= 10 && game.getKeyManager().isKeyPressed(KeyCode.SPACE)){
	    	rapBola = 2.0;
	    	xDireccion = 1.0;
	    	yDireccion = 1.0;
	    	ladrillosRotos = 0;
	    
		}
	
		//mueve la raqueta hacia la derecha
		if(game.getKeyManager().isKeyPressed(KeyCode.RIGHT)){
	    	raqueta.setX(raqueta.getX() + 4.5);
	    
		}
		//mueve la raquet hacia la izquierda
		if(game.getKeyManager().isKeyPressed(KeyCode.LEFT)){
	    	raqueta.setX(raqueta.getX() - 4);
		}

		//permite iniciar un juego nuevo
		if(game.getKeyManager().isKeyPressed(KeyCode.ESCAPE)){
	    	newnivel = true;
	    	puntos = 0;
	    	vidas = 3;
	    	mode = modoJuego.Iniciar;
	    	ladrillosRotos = 0;
	    	nivel = 1;
		}
		puntosPantalla.setText("puntos: " + puntos + "\nvidas: " + vidas + "\nnivel: " + nivel);
		checkForCollision();
		moverbola();
    }

    //Inicia todo el juego, juego nuevo

    public void updateIniciar(Game game, GameTime gameTime) {
		getSceneNodes().getChildren().setAll(fondo, puntosPantalla, IniciarText);
		getSceneNodes().getChildren().addAll(paredDerecha, paredIzq, paredTop, paredBajo);
		if(game.getKeyManager().isKeyPressed(KeyCode.ENTER)){
	    	mode = modoJuego.Jugar;
	    	puntos = 0;
	    	vidas = 3;
	    	bola.setCenterX(260);
	    	bola.setCenterY(300);
	    	ladrillosRotos = 0;
	    	nivel = 1;
		}
		else {
			if(game.getKeyManager().isKeyPressed(KeyCode.TAB)){
				IniciarText.setText("\t \t CREDITOS: \n STEVEN ERAZO \n TAMARA BENAVIDEZ \n KEVIN CHIGUANO ");
			}
			else
			{
				if(gameTime.getTotalGameTime().getSeconds() % 2 == 1){
					IniciarText.setText("PULSE ENTER PARA INICIAR LA PARTIDA. \nPULSE TAB PARA MIRAR LOS CREDITOS");
				}
			}
	    }
	}
}

