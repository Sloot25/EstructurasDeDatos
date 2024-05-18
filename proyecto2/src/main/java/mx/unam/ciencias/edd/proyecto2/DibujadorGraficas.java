package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Lista;

public class DibujadorGraficas extends Dibujador{
  private class Tupla{
    private VerticeGrafica<Integer> vertice;
    private int indice;

  //Constructor de la clase 
  //@param Recibe VerticeGrafica y su indice asociado y los asigna a los atributos correspondientes 
  public Tupla(VerticeGrafica<Integer> vertice, int indice){
      this.vertice = vertice;
      this.indice = indice;
    }
  }
  private Grafica<Integer> grafica;
  private Lista<Tupla> tuplas;
  private double posicionador;

  //Constructor de la clase 
  // @param Recibe una Grafica de Integer, creamos una Lista de Tuplas 
  // y a partir de los elementos de la grafica calculamos el posicionador y el 
  // tamaño del SVG
  
  public DibujadorGraficas(Grafica<Integer> grafica){
    this.grafica = grafica;
    this.tuplas = new Lista<Tupla>();
    posicionador = 2*Math.PI/grafica.getElementos();
    tamanoX = tamanoY = grafica.getElementos() * 50 + 100;
  }

  // Obtiene el numero de digitos que tiene un numero 
  // @param Recibe un numero entero 
  // @return Regresa un entero con el numero de digitos que tiene el numero recibido

  private int radio(int numero){
    int i = 0;
    while((numero /= 10) != 0)
      i++;
    return i;
  }

  // Crea un objeto de la Clase Tupla a partir de un vertice de Integer y un indice asociado 
  // @param Recibe un Vertice de Integer y un indice 
  // @return No regresa nada el Metodo, dado que solo crea el objeto y lo agrega a nuestra lista de tuplas
  
  private void generaTupla(VerticeGrafica<Integer> vertice, int indice){
    tuplas.agrega(new Tupla(vertice, indice));
  } 
  
  // Obtiene el indice asociado a un vertice de Integer 
  // @param Recibe un Vertice de Integer y lo busca la coincidencia en la lista de tuplas 
  // @return Regresa el indice asociado al vertice en la Tupla 
  private int getIndice(VerticeGrafica<Integer> vertice){
    for(Tupla tupla : tuplas)
      if(tupla.vertice.equals(vertice)) 
        return tupla.indice;
    return -1;
  }

  // Obtiene el dibujo en SVG correspondiente a la estructura de datos
  // @param No recibe parametros debido a que trabaja sobre la grafica recibida en el constructor 
  // Recorre sobre ella los Vertices para asignar una tupla a cada vertice y dibujar los vertices
  // seguido de ello recorre las tuplas y los vecinos de los vertices asociados en cada tupla, a partir 
  // de ellos dibujamos nuestras conexiones  
  // @return un String con el contenido del SVG 

  @Override public String dibuja(){
    String vertices = "", arista = "";
    int i = 0;
    for(Integer entero : grafica){
      VerticeGrafica<Integer> vertice = grafica.vertice(entero);
      generaTupla(vertice, i);
      vertices += dibujarVertice(vertice, i++);
    }
    for(Tupla tupla : tuplas){
      for(VerticeGrafica<Integer> vecino : tupla.vertice.vecinos()){
        arista += dibujarAristas(vecino, tupla.vertice);
      }
    }
    return abrirDibujo() + arista + vertices +cerrarDibujo();
  }

  // Obtiene la posicion en el Eje X de cada vertice 
  // @param Recibe el indice asociado al Vertice 
  // @return Regresa un entero correspondiente a la posicion calculado a partir del indice, el posicionador
  // y el tamaño de nuestro SVG

  private int obtenerPosX(int indice){
    return (int)(grafica.getElementos()*25*Math.sin(indice * posicionador)) + tamanoX/2;
  }

  // Obtiene la posicion en el Eje Y de cada vertice 
  // @param Recibe el indice asociado al Vertice 
  // @return Regresa un entero correspondiente a la posicion calculada a partir del indice, el posicionador 
  // y el tamaño de nuestro SVG

  private int obtenerPosY(int indice){
    return (int)(grafica.getElementos()*25*Math.cos(indice * posicionador)) + tamanoY/2;
  }

  // Obtiene un String correspondiente a la conexion de dos vertices
  // @param Recibe dos Vertices a ser conectados 
  // @return Regresa un String en formato de SVG correspondiente a la conexion de ambos vertices 

  private String dibujarAristas(VerticeGrafica<Integer> primerVertice, VerticeGrafica<Integer> segundoVertice){
    return "<line x1='" + (obtenerPosX(getIndice(primerVertice))) + "' y1='" + (obtenerPosY(getIndice(primerVertice))) + "' x2='" + (obtenerPosX(getIndice(segundoVertice))) + "' y2='" + (obtenerPosY(getIndice(segundoVertice))) + "' stroke='black' stroke-width='3' />" + '\n';
  }

  // Obtiene un String correspondiente a un Vertice 
  // @param Recibe un vertice y un indice 
  // @return Regresa un String en formato de SVG correspondiente a un Vertice 

  private String dibujarVertice(VerticeGrafica<Integer> vertice, int indice){
    int posX = obtenerPosX(indice);
    int posY = obtenerPosY(indice);
    String circulo = "<circle cx='" + posX + "' cy='" + posY + "' r='25' fill= 'white' stroke='black' stroke-width='2.5' /> ";
    String elemento = "<text fill='black' font-family='sans-serif' font-size='" + ((radio(vertice.get()) < 4) ? 21 : (17-radio(vertice.get()))) + "' x='" + posX + "' y='" + (posY+5) + "' text-anchor='middle' >" +  vertice.get() + "</text>";
    return circulo + '\n' + elemento  + '\n';
  }
}
