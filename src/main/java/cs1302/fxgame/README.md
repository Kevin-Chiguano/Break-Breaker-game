# Escuela Politécnica Nacional del Ecuador
![EPN](/resource/logoEPN.png "EPN")

## Programación II
#### Profesor: Patricio Paccha, Magister
### Grupo: 3

### Integrantes:

* Tamara Benavidez
* Kevin Chiguano
* Steven Erazo

# JUEGO **_BRICKS BREAKER_**

# INSTRUCCIONES DEL JUEGO
## configuracion de visual studio code para java fx.
* para poder usar los paquetes de librerias de java fx es necesario realizar una configuracion previa e nuestro visual studio code.
* para poder configurar de manera a decuada es recomendable descargar y seguir la siguiente guia la cual se encuentra muy detallada "click para descargar la guia" **[Aqui](https://docer.com.ar/doc/sss58x1)**.
* enlace de descarga de los paquetes de java fx   **[Aqui](https://gluonhq.com/products/javafx/)**.
## Inicio juego nuevo
![pantalla de inicio](/resource/pantallaInicio.png "Pantalla de Inicio")

Para dar inicio al juego **Bricks Breaker**, se debe presionar _enter_ para comenzar un nuevo juego, el cual muetra 5 filas de ladrillos espaciados equitativamente, arregladas en 6 columnas; donde la pelota no se moverá, hasta presionar la tecla de _espacio_ para que la pelota pueda moverse o puede esperar 10 segundos y la pelota comenzará a moverse por sí sola. Se utilizan las teclas de las _flechas izquierda y derecha_ para mover la raqueta hacia la izquierda y hacia la derecha respectivamente.
Durante el juego, si en cualquier momento desea abandonar esa partida e iniciar un nuevo juego, se presiona la tecla _escape_.

## Ladrillos
La durabilidad de los ladrillos está representada por su color:
![durabilidadLadrillos](/resource/durabilidadLadrillos.png "Durabilidad de los ladrillos")
- Verde = plena durabilidad
- Amarillo = la mitad durabilidad
- Rojo = baja durabilidad.

Después de cada colisión con la pelota, el estado o la durabilidad del ladrillo va abajo por uno. Si el ladrillo es rojo y luego se golpea, el ladrillo se rompe y desaparece.

## Objetivo del juego
Es romper todos los ladrillos interceptando la pelota con la raqueta y redirigir la pelota para chocar con los ladrillos. 

## Vidas y puntaje
Al comienzo de cada juego, se le otorga un total de 3 vidas. Cada ladrillo tiene 3 niveles de durabilidad representados por su color. Cada ladrillo requiere 3 golpes para romperlo. La puntuación se calcula por el número de ladrillos que se puedan romper. Una vida se pierde si la pelota pasa por la raqueta y choca con la pared de fondo. 

## Niveles
Para avanzar el juego al siguiente nivel, todos los ladrillos deben ser destruidos. Al comienzo de cada nuevo nivel, los ladrillos reaparecerán, la dificultad del juego aumenta con el tiempo y la velocidad de la bola también aumentará y se le otorgará una vida extra. Hay un total de 3 niveles para completar. 

## Ganador del juego
![has ganado](/resource/hasGanado.png "Felicitaciones! has ganado")
Gana el jugador que logre pasar los 3 niveles.

## Pierde el juego
![has perdido](/resource/hasPerdido.png "Lo siento, has perdido")
El jugador que acabe con las vidas otorgadas es quien pierda el juego.

# CODIFICACIÓN DEL JUEGO

### *Package o paquetes de librerias*
Este proyecto utiliza algunas librerias externas que ayudan a que el programa ejecute correctamente todo el ciclo del juego.
http://cobweb.cs.uga.edu/~mec/fxgame/
```java
package cs1302.fxgame;
import com.michaelcotterell.game.Game;
import javafx.application.Application;
```
Contamos con otra libreria que se emplea para el funcionamiento de todo el escenario que muestra la pantalla:
```java
import javafx.stage.Stage;
```
### *Clase principal*
La clase principal para la ejecución del proyecto es:
```java
public class Driver extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception { 
        
        Game game = new ladrilloRoto(primaryStage);
        primaryStage.setTitle(game.getTitle());
        primaryStage.setScene(game.getScene());
        primaryStage.show();
        game.run();
    } // Iniciar
    
    public static void main(String[] args) {
        launch(args);
    } // main
} 
```
Desde esta clase se puede iniciar con la presentación de la pantalla principal que muestra el puntaje en 0, el número de vidas con las que cuenta al iniciar son 3 y el nivel para comenzar es el 1; también se puede observar las instrucciones a las que puede acceder el jugador al presionar: ***enter*** para iniciar la partida o ***tab*** para ver los créditos.

### *Subclases*
```java
public class ladrillo {
```
La clase **Ladrillo** permite construir y diseñar el bloque de ladrillo. Al colisionar  la pelota con el ladrillo va a ir perdiendo la durabilidad el ladrillo, inicia con un ladrillo verde, al colisionar se cambia a amarillo y si vuelve a colisionar se vuelve rojo

```java
public class ladrilloRoto extends Game {
```
La clase **ladrilloRoto** presenta una dependencia de la clase ladrillo, esta clase prepara primeramente todos los textos a imprimir en la pantalla de inicio mostrando las opciones con el texto de color blanco, los puntos 0 y las 3 vidas de color rojo. Prepara los posibles mensajes si se gana o pierde el juego. Se define el tamaño de la pantalla, así como el color del fondo negro.
Se instancia la raqueta con las dimensiones de forma de un rectángulo y se le da color coral. Para la pelota las dimensiones en forma de un circulo y de color blanco. 

Se instancia un constructor de la clase ladrilloRoto 
```java
public ladrilloRoto(Stage stage) {
	super(stage, "Epic Breakout!", 60, 520,460);
```

Se crean los métodos que ayudan en el movimiento de la pelota en dirección del eje x y del eje y, dentro del escenario del juego
```java
public void moverbola() {
	bola.setCenterX(bola.getCenterX() + (xDireccion * rapBola));
	bola.setCenterY(bola.getCenterY() + (yDireccion * rapBola));
    }
```

Para provocar las colisiones con los ladrillos se da en 3 casos cuando el ladrillo cambia a color amarillo, luego cambia a rojo, se rompe o se destruye el ladrillo cuando entra a un estado nulo y se construye un try y un catch para controlar los errores en el tiempo de ejecución y pueda seguir ejecutandose y no se detenga el juego.
```java
public void checkForCollision() {

    for(int i = 0; i < bricks.brickLayout.length; i++){
        if(bricks.brickLayout[i] != null){
            if(bola.intersects(bricks.brickLayout[i].getBoundsInLocal())){ //golpes de la pelota
                switch(bricks.getBrickState(bricks.brickLayout[i])){
                    case 3: 
                    bricks.brickLayout[i].setFill(Color.YELLOW);
                    break;
                    case 2:
                    bricks.brickLayout[i].setFill(Color.RED);
                    break;
                    case 1: 
                    try{
                        bricks.brickLayout[i] = null;
                    }
                    catch (Exception e){}
                    ladrillosRotos++;
                    puntos++;
                    checkganar();
                    break;
                }
                yDireccion = yDireccion * -1;
            }
        }
    } 
```
El método de perder vidas permite quitar una vida a la vez, es decir, al caer la pelota al vacío porque no alcanzó la raqueta a lanzarla y el juego sigue en marcha mientras no haya acabado las vidas, si es así, pierde el juego.
```java
public void lifeLoss() {
    vidas -= 1;
    if(vidas >= 0){
        bola.setCenterX(260);
        bola.setCenterY(300);
        raqueta.setX(225);
        xDireccion = 1.0;
        yDireccion = 1.0;
    }
    else {
        mode = gameMode.LOSS;
    }
}
```
Contamos con la clase de update que permite al juego entrar en cuatro modos:
* Iniciar
* Jugar
* Ganar
* Perder
```java
public void update(Game game, GameTime gameTime) {
    switch (mode) {
        case START: 
        updateStart(game, gameTime);
        break;
        case PLAY:
        updatePlay(game,gameTime);
        break;
        case ganar:
        updateganar(game, gameTime);
        break;
        case LOSS:
        updateLoss(game, gameTime);
        break;
    }
}
```
#### Ganar
Para ganar el usuario debe avanzar al siguiente nivel y si avanza todos los niveles, es decir, rompió todos los ladrillos en cada nivel, gana el juego y en la pantalla se imprimirá el mensaje de ganador.
```java
public void checkganar() {
    if(ladrillosRotos == 90 && nivel == 3){
        mode = gameMode.ganar;
    }
    else if(ladrillosRotos % 30 == 0 && ladrillosRotos != 0){
        nivel++;
        rapBola += 1.0;
        newnivel = true;
        bola.setCenterX(260);
        bola.setCenterY(300);
    }
}
```
#### Perder
Se le resta una vida al perder un nivel y si al usuario le quedan más vidas, puede seguir jugando el mismo nivel; si pierde todas las vidas, pierde el juego y en la pantalla se visualizará un mensaje que perdió el juego y puede volver a iniciar una partida nueva si desea seguir jugando
```java
public void updateLoss(Game game, GameTime gameTime) {
    getSceneNodes().getChildren().setAll(fondo, perderMensaje);

    if(game.getKeyManager().isKeyPressed(KeyCode.ENTER)){
        mode = gameMode.START;
    }
    else{
        stop();
    }
}
```
#### Jugar
El juego una vez iniciado va a seguir en el tiempo de juego, es decir, mientras existan ladrillos y el usuario tenga vidas. En cada nivel hay 30 ladrillos, al romper todos los ladrillos, seguirá al siguiente nivel sin ningún error controlándolo con el try y el catch para el manejo de errores y si perdió una vida antes de completar el nivel seguirá jugando en el mismo nivel, no habrá un nuevo nivel sino hasta que rompa todos los ladrillos.
```java
public void updatePlay(Game game, GameTime gameTime) {
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
```
Mientras el usuario esté jugando, se seguirán sumando los puntos y se le agrega una vida si pasa al siguiente nivel o lo contrario, se restaran puntos y se le quitará una vida si pierde, se utiliza métodos para controlar los movimientos de la pelota y la raqueta dentro de la escena del juego.

#### Iniciar
Al presionar ENTER se entra en el modo de juego y se inicia el juego en el nivel uno con 0 puntos, 3 vidas y 30 ladrillos intactos o si se presiona TAB podrá visualizar los créditos del juego.
```java
public void updateStart(Game game, GameTime gameTime) {
    getSceneNodes().getChildren().setAll(fondo, puntosPantalla, startText);
    getSceneNodes().getChildren().addAll(paredDerecha, paredIzq, paredTop, paredBajo);
    if(game.getKeyManager().isKeyPressed(KeyCode.ENTER)){
        mode = gameMode.PLAY;
        puntos = 0;
        vidas = 3;
        bola.setCenterX(260);
        bola.setCenterY(300);
        ladrillosRotos = 0;
        nivel = 1;
    }
    else {
        if(game.getKeyManager().isKeyPressed(KeyCode.TAB)){
            startText.setText("\t \t CREDITOS: \n STEVEN ERAZO \n TAMARA BENAVIDEZ \n KEVIN CHIGUANO ");
        }
        else
        {
            if(gameTime.getTotalGameTime().getSeconds() % 2 == 1){
                startText.setText("PULSE ENTER PARA INICIAR LA PARTIDA. \nPULSE TAB PARA MIRAR LOS CREDITOS");
            }
        }
    }
}
```

## GLOSARIO
Se detalla una descripción del funcionamiento del paquete **com.michaelcotterell.game**:
* com.michaelcotterell.game - paquete com.michaelcotterell.game
Contiene clases e interfaces útiles para crear un juego simple en JavaFX.
* Juego - Clase en com.michaelcotterell.game
Proporciona la inicialización básica, la lógica del juego y el código de representación para un juego.
* Game(Stage, String, int, int, int) - Constructor para la clase com.michaelcotterell.game. Juego
Crea un nuevo Gameobjeto.
* GameTime - Clase en com.michaelcotterell.game
Instantánea del estado de tiempo del juego.
* GameTime() - Constructor para la clase com.michaelcotterell.game. Tiempo de juego
Construye un nuevo GameTimeobjeto con valores predeterminados en cero.
* GameTime(GameTime) - Constructor para la clase com.Michaelcotterell.game. Tiempo de juego
Construye un nuevo GameTimeobjeto con los mismos TimeSpanvalores que están contenidos en otro GameTimeobjeto.
* getKeyManager() - Método en la clase com.michaelcotterell.game. Juego
Devuelve el KeyManagerque está adjunto a la escena actual.
* getSceneNodes() - Método en la clase com.michaelcotterell.game. Juego
Devuelve el grupo de nodos de escena.
* getTotalGameTime() - Método en la clase com.michaelcotterell.game. Tiempo de juego
Devuelve la cantidad de tiempo de juego desde el inicio del juego.
