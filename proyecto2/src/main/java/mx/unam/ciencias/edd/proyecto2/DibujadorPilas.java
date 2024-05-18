package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Pila;

public class DibujadorPilas extends DibujadorNodos {
  private Pila<Integer> pila; 

  //Constructor de la clase 
  //@param Recibe una Pila de Integer;

  public DibujadorPilas(Pila<Integer> pila){
    this.pila = pila;
    tamanoX = 100;
  }

  // Dibuja la estructura de datos en forma de SVG 
  // @param no recibe ningun parametro, dado que trabaja sobre la Pila recibida en el Constructor
  // @return Un String del contenido del SVG 
  
  public String dibuja(){
    String nodos = "";
    int posY = 45;
    while(!pila.esVacia()){
      nodos += dibujarNodo(25, posY, pila.saca());
      posY += 30;
    }
    tamanoY = posY+15;
    return abrirDibujo() + nodos + cerrarDibujo();
  }
}
