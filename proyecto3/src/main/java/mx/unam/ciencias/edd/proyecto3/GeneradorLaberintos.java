package mx.unam.ciencias.edd.proyecto3;
import java.util.Random;
import mx.unam.ciencias.edd.Cola;
import java.io.IOException;
import java.io.BufferedOutputStream;

public class GeneradorLaberintos {
  private class Casilla{
    private boolean izquierda, derecha, arriba, abajo, visitada;
    private boolean Rizq, Rder, Rarr, Rabj;
    private int peso, posX, posY; 

    /* Constructor clase interna
     * */
    private Casilla(int peso, int posX, int posY){
      izquierda = derecha = arriba = abajo = true;
      Rizq = Rder = Rarr = Rabj = false;
      this.peso = peso & 240;
      visitada = false;
      this.posX = posX;
      this.posY = posY;
    }

    /* @param No recibe parametros 
     * @return regresa un 4 si la casilla tiene muro izquierdo y un 0 si no la tiene
     * */
    private int puertaIzquierda(){
     return (izquierda) ? 4 : 0;
    }

    /* @param No recibe parametros
     * @return Regreso un 1 si la casilla tiene muro derecho y un 0 si no lo tiene 
     * */
    private int puertaDerecha(){
      return (derecha) ? 1 : 0;
    }

    /* @param No recibe parametros
     * @return Regresa un 2 si la casilla tiene un muro superior y un 0 si no lo tiene
     * */
    private int puertaArriba(){
      return (arriba) ? 2 : 0;
    }

    /* @param No recibe parametros 
     * @return Regresa un 8 si la casilla tiene un muro inferior y un 0 si no lo tiene 
     * */
    private int puertaAbajo(){
      return (abajo) ? 8 : 0;
    }

    /* @param No recibe parametros 
     * @return Regresa un entero correspondiente al valor de la casilla 
     * */
    private int getByte(){
      return puertaArriba() | puertaDerecha() | puertaIzquierda() | puertaAbajo() | peso;
    }
  }
  Casilla[][] laberinto;
  Random rnd; 

  /* Constructor de la clase 
   * @param recibe el tamaño en Y y en X del laberinto 
   * */
  public GeneradorLaberintos(int n, int m){
    laberinto = new Casilla[n][m];
    rnd = new Random();
    rellenarLaberinto();
  }

  /* Construvtor de la clase 
   * @param Recibe el tamaño en Y y en X del laberinto asi como la semilla
   * */
  public GeneradorLaberintos(int n, int m, int k){
    laberinto = new Casilla[n][m];
    rnd = new Random(k);
    rellenarLaberinto();
  }

  private void rellenarLaberinto(){
    for(int i = 0; i < laberinto.length; i++)
      for(int j = 0; j < laberinto[0].length; j++)
        laberinto[i][j] = new Casilla(rnd.nextInt(), j, i);
  }
 
  /* @param No recibe parametros 
   * @return No regresa elementos
   * Genera el labeberinto asi como asigna la entrada y salida del mismo
   * */
  public void generarLaberinto(){
    generarLaberinto(laberinto[rnd.nextInt(laberinto.length)][rnd.nextInt(laberinto[0].length-1)]);
    laberinto[rnd.nextInt(laberinto.length)][0].izquierda = false;
    laberinto[rnd.nextInt(laberinto.length)][laberinto[0].length-1].derecha = false;
  }

  /* @param Recibe una Casilla como parametro 
   * @return No regresa elementos 
   * Recorre cada direccion de la casilla recursivamente hasta que ya no haya casilla por recorrer, entonces salimos de la recursion 
   * */
  private void generarLaberinto(Casilla casilla){
    if(casilla.Rizq && casilla.Rder && casilla.Rarr && casilla.Rabj)
      return;
    int direccion = rnd.nextInt(4);

    switch(direccion){
      case 0:
        if(casilla.posX > 0 && !laberinto[casilla.posY][casilla.posX-1].visitada){
          casilla.izquierda = false;
          laberinto[casilla.posY][casilla.posX-1].derecha = false;
          laberinto[casilla.posY][casilla.posX-1].visitada = true;
          generarLaberinto(laberinto[casilla.posY][casilla.posX-1]);
        } else{
          casilla.Rizq = true;
          generarLaberinto(casilla);
        }
      case 1:
        if(casilla.posX+1 < laberinto[0].length  && !laberinto[casilla.posY][casilla.posX+1].visitada){
          casilla.derecha = false;
          laberinto[casilla.posY][casilla.posX+1].izquierda = false;
          laberinto[casilla.posY][casilla.posX+1].visitada = true;
          generarLaberinto(laberinto[casilla.posY][casilla.posX+1]);
        }else{
          casilla.Rder = true;
          generarLaberinto(casilla);
        }
      case 2:
        if(casilla.posY > 0 && !laberinto[casilla.posY-1][casilla.posX].visitada){
          casilla.arriba = false;
          laberinto[casilla.posY-1][casilla.posX].visitada = true;
          laberinto[casilla.posY-1][casilla.posX].abajo = false;
          generarLaberinto(laberinto[casilla.posY-1][casilla.posX]);
        }else{
          casilla.Rarr = true;
          generarLaberinto(casilla);
        }
      case 3: 
        if(casilla.posY+1 < laberinto.length && !laberinto[casilla.posY+1][casilla.posX].visitada){
          casilla.abajo = false;
          laberinto[casilla.posY+1][casilla.posX].visitada = true;
          laberinto[casilla.posY+1][casilla.posX].arriba = false;
          generarLaberinto(laberinto[casilla.posY+1][casilla.posX]);
        } else{
          casilla.Rabj = true;
          generarLaberinto(casilla);
        }
    }
  }

  /* @param No recibe parametros 
   * @return No regresa elementos 
   * Imprime en la entrada estandar cada byte que compone al laberinto en forma del formato solicitado 
   * */
  public void imprimirMaze(){
    try{
      BufferedOutputStream escritor = new BufferedOutputStream(System.out);
      escritor.write((byte)0x4d);
      escritor.write((byte)0x41);
      escritor.write((byte)0x5a);
      escritor.write((byte)0x45);
      escritor.write((byte)laberinto.length);
      escritor.write((byte)laberinto[0].length);
      for(int i = 0; i < laberinto.length; i++)
        for(int j = 0; j < laberinto[i].length; j++)
            escritor.write((byte)laberinto[i][j].getByte());
      escritor.close();
    }catch(Exception e){
      System.err.println("Error al escribir el laberinto");
    }
  }
  

}
