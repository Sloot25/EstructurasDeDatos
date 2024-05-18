package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

public class DibujadorLaberinto {
  Integer[][] arreglo;

  /* Constructor de la clase DibujadorLaberinto 
   * @param Recibe un arreglo con los valores del laberinto 
   * */
  public DibujadorLaberinto(Integer[][] arreglo){
    this.arreglo = arreglo;
  }

  /*@param no recibe parametros
   *@return Regresa un String con el SVG de un laberinto recibido por el constructor 
 */
  public String dibujaLaberinto(){
    String cuadricula = "";
    for(int i = 0; i < arreglo.length; i++)
      for(int j = 0; j < arreglo[0].length; j++)
        cuadricula += dibujarRecuadro(arreglo[i][j], j, i); 
    return abrirSVG(arreglo[0].length*60 + 80, arreglo.length*60 + 80) + cuadricula + cerrarSVG();
  }

  /* @param Recibe como parametro una lista de VerticeGrafica de Integer, la cual es la ruta de las posiciones que son la solucion del laberinto 
   * @return Regresa un String con el SVG de un laberinto, con la solucion del laberinto trazada
   * */
  public String dibujaLaberintoSolucion(Lista<VerticeGrafica<Integer>> lista){
    String cuadricula = "";
    for(int i = 0; i < arreglo.length; i++)
      for(int j = 0; j < arreglo[0].length; j++)
        cuadricula += dibujarRecuadro(arreglo[i][j], j, i); 
    return abrirSVG(arreglo[0].length*60 + 80, arreglo.length*60 + 80) + cuadricula + dibujaRespuesta(lista) + cerrarSVG();
  }

  /* @param Recibe un entero con el ancho y el alto del laberinto 
   * @return Regresa el String correspondiente a inicializar un archivo SVG con tamaño asignado
   * */
  private String abrirSVG(int tamanoX, int tamanoY){
    return "<?xml version='1.0' encoding='UTF-8' ?>" + '\n' +"<svg width='" + tamanoX + "' height='" + tamanoY + "' >" + '\n' + "<g>";
  }

  /* @param No recibe parametros 
   * @return Regresa el String correspondiente a finalizar un archivo SVG 
   * */
  private String cerrarSVG(){
    return "</g>" +'\n' + "</svg>" + '\n';
  }

  /* @param Recibe el valor en la casilla correspondiente, y la posicion en X y en Y de la casilla 
   * @return Regresa un String con el SVG correspondiente de la casilla con sus correspondientes muros  
   * */
  private String dibujarRecuadro(int valor, int posicionX, int posicionY){
    String res = "";
    if(puertaArriba(valor))
      res += dibujarArriba(posicionX, posicionY);
    if(puertaIzquierda(valor))
      res += dibujarIzquierda(posicionX, posicionY);
    if(puertaDerecha(valor))
      res += dibujarDerecha(posicionX, posicionY);
    if(puertaAbajo(valor))
      res += dibujarAbajo(posicionX, posicionY);
    return res; 
  }

  /* @param Recibe la posicion en X y en Y de la casilla 
   * @return Regresa un String en SVG correspondiente a dibujar el muro superior de una casilla
   * */
  private String dibujarArriba(int posicionX, int posicionY){
    return "<line x1='" + ((posicionX*60)+40)+ "' y1='" + ((posicionY*60)+40) + "' x2='" + (((posicionX+1)*60)+40) + "' y2='" + ((posicionY*60)+40) + "' stroke='black' stroke-width='8' /> " + '\n';
  }

  /* @param Recibe la posicion en X y en Y de la casilla
   * @return Regresa un String en SVG correspondiente a dibujar el muro ingerior de una casilla
   * */
  private String dibujarAbajo(int posicionX, int posicionY){
    return "<line x1='" + ((posicionX*60)+40)+ "' y1='" + (((posicionY+1)*60)+40) + "' x2='" + (((posicionX+1)*60)+40) + "' y2='" + (((posicionY+1)*60)+40) + "' stroke='black' stroke-width='8' /> " + '\n';
  }

  /* @param Recibe la posicion en X y en Y de la casilla 
   * @return Regresa un String en SVG correspondiente a dibujar el muro derecho de una casilla
   * */
  private String dibujarDerecha(int posicionX, int posicionY){
    return "<line x1='" + (((posicionX+1)*60)+40)+ "' y1='" + ((posicionY*60)+40) + "' x2='" + (((posicionX+1)*60)+40) + "' y2='" + (((posicionY+1)*60)+40) + "' stroke='black' stroke-width='8' /> " + '\n';
  }

  /* @param Recibe la posicion en X y en Y de la casilla 
   * @return Regresa un String en SVG correspondiente a dibujar el muro izquierdo de una casilla 
   * */
  private String dibujarIzquierda(int posicionX, int posicionY){
    return "<line x1='" + ((posicionX*60)+40)+ "' y1='" + ((posicionY*60)+40) + "' x2='" + ((posicionX*60)+40) + "' y2='" + (((posicionY+1)*60)+40) + "' stroke='black' stroke-width='8' /> " + '\n';
  }

  /* @param Recibe el valor de la casilla 
   * @return Regresa un boolean que indica si la casilla tiene muro superior 
   * */
  private boolean puertaArriba(int valor){
    return (valor & 2) != 0;
  }

  /* @param Recibe el valor de la casilla 
   * @return Regresa un boolean que indica si la casilla tiene muro inferior 
   * */
  private boolean puertaAbajo(int valor){
    return (valor & 8) != 0;
  }

  /* @param Recibe el valor de la casilla 
   * @return Regresa un boolean que indica si la casilla tiene muro derecho 
   * */
  private boolean puertaDerecha(int valor){
    return (valor & 1) != 0;
  }

  /* @param Recibe el valor de la casilla 
   * @return Regresa un boolean que indica si la casilla tiene un muro izquierdo
   * */
  private boolean puertaIzquierda(int valor){
    return (valor & 4) != 0;
  }

  /* @param Recibe la lista con el recorrido de la solucion 
   * @return Regresa un String con formato SVG que describe la trayectoria para la solucion del laberinto 
   * */
  private String dibujaRespuesta(Lista<VerticeGrafica<Integer>> lista){
    VerticeGrafica<Integer> ultimo = lista.eliminaPrimero();
    String respuesta ="<line x1 = '40' y1 = '" + ((getPosicionY(ultimo.get())*60)+70) + "' x2='" + ((getPosicionX(ultimo.get())*60)+70) + "' y2='" + ((getPosicionY(ultimo.get())*60)+70) + "' stroke='red' stroke-width='15' />" + '\n';
    String circulo =  "<circle cx ='" + "40" + "' cy='" + ((getPosicionY(ultimo.get())*60)+70) + "' r = '20' fill = 'blue' stroke = 'blue' />" + '\n';
    for(VerticeGrafica<Integer> actual : lista){
      respuesta+= conectaPuntos(ultimo.get(), actual.get());
      ultimo = actual;
    }
    respuesta += "<line x1 = '" + ((getPosicionX(ultimo.get())*60)+70) + "' y1 = '" + ((getPosicionY(ultimo.get())*60)+70) + "' x2='" + ((getPosicionX(ultimo.get())*60)+100) + "' y2='" + ((getPosicionY(ultimo.get())*60)+70) + "' stroke='red' stroke-width='15' />" + '\n' + "<circle cx='" + ((getPosicionX(ultimo.get())*60)+100) + "' cy='" + ((getPosicionY(ultimo.get())*60)+70) + "' r= '20' fill = 'blue' stroke = 'blue' />" + '\n';
    return respuesta + circulo; 
  }

  /* @param Recibe dos posiciones en el laberinto 
   * @return Regresa un String de una recta en SVG que conecta ambos puntos
   * */
  private String conectaPuntos(int o, int p){
    return "<line x1 = '" + ((getPosicionX(o)*60)+70) + "' y1 = '" + ((getPosicionY(o)*60)+70) + "' x2='" + ((getPosicionX(p)*60)+70) + "' y2='" + ((getPosicionY(p)*60)+70) + "' stroke='red' stroke-width='15' />" + '\n';
  }

  /* @param Recibe una posicion en el laberinto 
   * @return Regresa la posicion en X de la casilla 
   */
  private int getPosicionX(int posicion){
    return posicion%arreglo[0].length;
  }

  /* @param Recibe una posicion en el laberinto
   * @return Regresa la posicion en Y de la casilla
   * */
  private int getPosicionY(int posicion){
    return posicion/arreglo[0].length;
  }

}
