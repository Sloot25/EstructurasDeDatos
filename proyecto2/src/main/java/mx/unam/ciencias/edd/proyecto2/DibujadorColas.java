package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Cola;

public class DibujadorColas extends DibujadorNodos {
  private Cola<Integer> cola;

  //Constructor de la clase 
  //@param Recibe una Cola de Integer 

  public DibujadorColas(Cola<Integer> cola){
    this.cola = cola;
    tamanoY = 90;
  }

  // Dibuja un SVG de la estructura de Datos 
  // @param No recibe parametros dado que trabaja sobre la cola que fue recibida en el Constructor
  // @return Regresa el contenido del SVG en formato de String 
  @Override public String dibuja(){
    String nodos = "";
    int posX = 30;
    while(!cola.esVacia()){
      nodos += dibujarNodo(posX, 30, cola.saca());
      if(!cola.esVacia())
        nodos += dibujarFlecha(posX + 55, 45);
      posX += 100;
    }
    tamanoX = posX;
    return abrirDibujo() + nodos + cerrarDibujo();
  }
}
