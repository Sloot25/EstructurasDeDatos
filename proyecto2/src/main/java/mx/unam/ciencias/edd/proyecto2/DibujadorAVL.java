package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Cola;

public class DibujadorAVL extends DibujadorArboles {

  //Constructor de la clase 
  //@param Recibe un ArbolAVL y llama al constructor de la clase padre 

  public DibujadorAVL(ArbolAVL<Integer> arbol){
    super(arbol);
  }

  // Dibuja la estructura de datos usando el recorrido BFS
  // @param No recibe parametros, usa el arbol obtenido en el constructor 
  // @return regresa el contenido del SVG en forma de String 

  @Override 
  public String dibuja(){
    String aristas = "", vertices = "", alturas = "";
    Cola<VerticeArbolBinario<Integer>> cola = new Cola<VerticeArbolBinario<Integer>>();
    cola.mete(arbol.raiz());
    while(!cola.esVacia()){
      VerticeArbolBinario<Integer> vertice = cola.saca();
      if(vertice.hayIzquierdo()) cola.mete(vertice.izquierdo());
      if(vertice.hayDerecho()) cola.mete(vertice.derecho());
      aristas += dibujarAristas(vertice);
      vertices += dibujarVertice(vertice);
      alturas += dibujarAlturas(vertice);
    }
    return abrirDibujo() + aristas + vertices + alturas + cerrarDibujo();
  }

  // Obtiene el balance del Vertice 
  // @param Recibe un Vertice al cual se le calcula su balance 
  // @return Un entero correspondiente al balance del vertice recibido

  private int obtenerBalance(VerticeArbolBinario<Integer> vertice){
    int alturaIzquierda = (vertice.hayIzquierdo()) ? vertice.izquierdo().altura() : -1;
    int alturaDerecha = (vertice.hayDerecho()) ? vertice.derecho().altura() : -1;
    return alturaIzquierda - alturaDerecha;
  }

  // Obtiene un String en formato SVG con la altura correspondiente a un vertice 
  // @param Recibe el vertice al cual se le obtendra la altura 
  // @return regresa un String en formato SVG el cual describe la altura del vertice y su balance 

  private String dibujarAlturas(VerticeArbolBinario<Integer> vertice){
    int posX = (esIzquierdo(vertice)) ? obtenerPosX(vertice) - 30 : obtenerPosX(vertice) + 30;
    int posY = obtenerPosY(vertice) - 25;
    String altura = "<text fill='black' font-family='sans-serif' font-size='15' x='" + (posX) + "' y='" + (posY) + "' text-anchor='middle' >" + "[" + vertice.altura() + "/" + obtenerBalance(vertice) +"]" + "</text>";
    return altura + '\n';
  }
}
