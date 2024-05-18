package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;



public class GeneradorGraficas {
  Integer[][] arreglo;
  Grafica<Integer> grafica;
  int entrada, salida;

  /* Constructor de la clase 
   * @param Recibe un arreglo con los valores correspondientes al laberinto 
   * */
  public GeneradorGraficas(Integer[][] arreglo){
    this.arreglo = arreglo;
    grafica = new Grafica<Integer>();
    entrada = -1;
    salida = -1;
  }

  /* @param No recibe parametros 
   * @return No regresa elementos
   * Genera los vertices correspondientes a cada casilla en el arreglo 
   * */
  public void generarVertices(){
    int pos = 0;
    for(int i = 0; i < arreglo.length; i++)
      for(int j = 0; j < arreglo[i].length; j++)
        grafica.agrega(pos++);
  }

  /* @param No recibe parametros 
   * @return No regresa elementos
   * Genera las aristas correspondientes a cuando no hay un muro en el laberinto 
   * */
  public void generarPuertas() throws ArchivoInvalidoException{
    int pos = 0;
    for(int i = 0; i < arreglo.length; i++){
      for(int j = 0; j < arreglo[i].length; j++){
        if(i == 0 && puertaArriba(arreglo[i][j])){
          throw new ArchivoInvalidoException("Hay puertas que no conectan a otro cuarto en el Norte del laberinto");
        }
        if(j == 0 && puertaIzquierda(arreglo[i][j]) && entrada > 0)
          throw new ArchivoInvalidoException("Hay puertas que no conectan a otro cuarto en el Oeste del laberinto");
        else if (j == 0 && puertaIzquierda(arreglo[i][j]))
          entrada = pos;
        if(j == arreglo[i].length - 1 && puertaDerecha(arreglo[i][j]) && salida > 0)
          throw new ArchivoInvalidoException("Hay puertas que no conectan a otro cuarto en el Este del laberinto");
        else if(j == arreglo[i].length -1 && puertaDerecha(arreglo[i][j]))
          salida = pos;
        if(i == arreglo.length - 1 && puertaAbajo(arreglo[i][j]))
          throw new ArchivoInvalidoException("Hay puertas que no conectan a otro cuarto en el Sur del laberinto");
        conectaNorte(j,i, pos);
        conectaDerecha(j,i,pos);
        pos++;
      }
    }
  }

  /* @param Recibe la posicion respecto al Eje X y al Eje Y de las casillas y el numero de casilla
   * @return No devuelve elementos 
   * Conecta el vertice de la posicion con el vertice una casilla superior, solo si es posible */
  private void conectaNorte(int x, int y, int pos) throws ArchivoInvalidoException{
    if( y > 0 && puertaArriba(arreglo[y][x]) && puertaAbajo(arreglo[y-1][x]))
      try {
        grafica.conecta(pos, pos-arreglo[0].length, (getPeso(arreglo[y][x]) + getPeso(arreglo[y-1][x]) + 1));
      } catch (IllegalArgumentException e) {
      } catch (Exception g){
        System.err.println(g.getMessage());
      }
    else if((y > 0 && puertaArriba(arreglo[y][x]) && !puertaAbajo(arreglo[y-1][x])) || (y > 0 && !puertaArriba(arreglo[y][x]) && puertaAbajo(arreglo[y-1][x])))
      throw new ArchivoInvalidoException("Las puertas no pueden conectarse");
  }

  /* @param Recibe la posicion respecto al Eje X y al Eje Y de las casillas y el numero de la casilla 
   * @return No devuelve elementos 
   * Conecta el vertice de la posicion con el vertice una casilla a la derecha 
   * */
  private void conectaDerecha(int x, int y, int pos) throws ArchivoInvalidoException{
    if(x < arreglo[0].length - 1 && puertaDerecha(arreglo[y][x]) && puertaIzquierda(arreglo[y][x+1]))
      try{
        grafica.conecta(pos, pos+1, (getPeso(arreglo[y][x]) + getPeso(arreglo[y][x+1]) +1));
      }catch(IllegalArgumentException e){
      } catch(Exception g){
        System.err.println(g.getMessage());
      }
    else if ((x < arreglo[0].length - 1 && puertaDerecha(arreglo[y][x]) && !puertaIzquierda(arreglo[y][x+1])) || (x < arreglo[0].length -1 && !puertaDerecha(arreglo[y][x]) && puertaIzquierda(arreglo[y][x+1]))){
      throw new ArchivoInvalidoException("Las puertas no pueden conectarse");
    }
  }

  /* @param Recibe un entero 
   * @return Regrsa un boolean correspondiente a si la casilla tiene puerta izquierda
   * */
  private boolean puertaIzquierda(Integer valor){
    return (valor & 4 )== 0;
  }

  /* @param Recibe un entero 
   * @return Regresa un boolean correspondiente a si la casilla tiene puerta superior
   * */
  private boolean puertaArriba(Integer valor){
    return (valor & 2) == 0;
  }

  /* @param Recibe un entero 
   * @return Regresa un boolean correspondiente a si la casilla tiene puerta derecha
   * */
  private boolean puertaDerecha(Integer valor){
    return (valor & 1) == 0;
  }

  /* @param Recibe un entero 
   * @return Regresa un boolean correspondiente a si la casilla tiene puerta inferior
   * */
  private boolean puertaAbajo(Integer valor){
    return (valor & 8) == 0;
  }

  /* @param Reibe un entero 
   * @return Regresa el valor de la casilla*/
  private int getPeso(Integer valor){
    return (valor&240) >>> 4;
  }

  /* @param No recibe parametros 
   * @return Regresa la trayectoria que resuelve el laberinto en forma de Lista de VerticeGrafica 
   * */
  public Lista<VerticeGrafica<Integer>> recorrido(){
    return grafica.dijkstra(entrada, salida);
  }
}
