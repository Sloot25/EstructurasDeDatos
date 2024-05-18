package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;

public class DibujadorRojiNegro extends DibujadorArboles {

  // Constructor de la clase 
  // @param Recibe un ArbolRojinegro y llama al contructor de la clase padre 

  public DibujadorRojiNegro(ArbolRojinegro<Integer> arbol){
    super(arbol);
  }

  // Dibuja las aristas en forma de SVG 
  // @param Recibe un Vertice de Integer 
  // @return Si el vertice no tiene padre regresa la cadena vacia, en cualquier otro caso
  // regresa un String en forma de SVG correspondiente a la arista entre el vertice y su padre 

  @Override protected String dibujarAristas(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return "";
    return "<line x1='" + obtenerPosX(vertice) + "' y1='" + obtenerPosY(vertice) + "' x2='" + obtenerPosX(vertice.padre()) + "' y2='" + obtenerPosY(vertice.padre()) + "' stroke='blue' stroke-width='3' />" + '\n';
  }

  // Dibuja un Vertice en forma de SVG 
  // @param Recibe un Vertice de Integer 
  // @return Regresa un String en forma de SVG correspondiente al Vertice recibido 
  
  @Override 
  protected String dibujarVertice(VerticeArbolBinario<Integer> vertice){
    int posY = obtenerPosY(vertice);
    int posX = obtenerPosX(vertice);
    String color = "";
    if(((ArbolRojinegro<Integer>) arbol).getColor(vertice) == Color.ROJO)
      color = "red";
    else 
      color = "black";
    
    String circulo = "<circle cx='" + posX + "' cy='" + posY + "' r='25' stroke='" + color + "' stroke-width='3' fill='" + color + "' />";
    String elemento = "<text fill='white' font-family='sans-serif' font-size='"+ ((radio(vertice.get()) < 4 )? 21 : (17-radio(vertice.get()))) + "' x='" + posX + "' y='" + (posY+5) + "' text-anchor='middle' >" +  vertice.get() + "</text>";
    return circulo + '\n' + elemento + '\n';
  }
}
