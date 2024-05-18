package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.VerticeArbolBinario;
public class DibujadorArbolesOrdenados extends DibujadorArboles {
  
  //Constructor de la clase
  //@param recibe un ArbolBinarioOrdenado de Integer 
  //llama al constructor de la clase padre, y modifica el tamanoX del SVG para poder capturar arboles 
  //con tamaño lineal 
  public DibujadorArbolesOrdenados(ArbolBinarioOrdenado<Integer> arbol){
    super(arbol);
    tamanoX = (arbol.altura() > arbol.getElementos()/2) ?  (arbol.altura()+1) * (elementos*25) + 300: (arbol.altura()+1)*(elementos*20) + 100;
    tamanoY = (arbol.altura()+1)*80;
  }

  // Obtiene la posicion X del vertice 
  // @param recibe un vertice de Integer, si el vertice no tiene padre, regresamos el tamaño del archivo entre 2 
  // si el vertice tiene padre y es izquierdo, tenemos que ademas revisar si el desplazamiento es menor a 50, en caso de que 
  // lo sea, regresamos la posicion del Padre - la profundidad del vertice por 2 - 2, en otro caso regresamos la posicion del padre menos el desplazamiento 
  // si el vertice tiene padre y es derecho, tenemos que ademas revisar si el desplazamiento es menor a 50, en caso de que lo sea, regresamos 
  // la posicion del padre + la profundidad del vertice por 2 y le sumamos 2, en otro caso regresamos la posicion del padre mas el desplazamiento
  // @return un entero con la posicion del vertice recibido 
  @Override 
  protected int obtenerPosX(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return tamanoX/2;
    if(esIzquierdo(vertice))
      return (desplazamiento(vertice) < 50) ? obtenerPosX(vertice.padre()) - vertice.profundidad()*5 - 3 : obtenerPosX(vertice.padre()) - desplazamiento(vertice);
    return (desplazamiento(vertice) < 50 ) ? obtenerPosX(vertice.padre()) + vertice.profundidad()*5 + 3 : obtenerPosX(vertice.padre()) + desplazamiento(vertice);
  } 

  //Obtiene el desplazamiento del Eje X del vertice recibido por parametro 
  //@param Recibe un vertice de Integer, si el vertice no tiene padre, se regresa la altura del arbol multiplicado por los elementos multiplicado por 40 + 400 / 4
  //en caso de que tenga padre, se regresa el desplazamiento del padre entre 2 
  //El metodo es recursivo hasta la raiz del arbol 
  //@return un entero con el desplazamiento del vertice recibido por parametro

  protected int desplazamiento(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return (((arbol.altura())* elementos * 40 )+400)/4;
    return desplazamiento(vertice.padre())/2 ;
  }
}
