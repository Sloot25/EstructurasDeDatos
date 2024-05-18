package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Cola;

public class DibujadorArboles extends Dibujador{
  protected ArbolBinario<Integer> arbol;
  protected int elementos;

  //Constructor de la clase 
  //@param recibe un ArbolBinario de Integer 
  //genera las dimensiones del archivo SVG y asigna algunos atributos que serán 
  //utilizados por metodos posteriormente 

  public DibujadorArboles(ArbolBinario<Integer> arbol){
    this.arbol = arbol;
    elementos = arbol.getElementos();
    tamanoX = (arbol.altura()) * (elementos*18) + 300;
    tamanoY = (arbol.altura()+1) * 80 + 30;
  }
  
  //Calcula el radio dependiento del tamaño del Entero ingresado
  //@param recibe el entero al cual se le calculara el tamaño 
  //@return regresa un entero del numero de digitos que posee el numero 

  protected int radio(int numero){
    int i = 0;
    while((numero /= 10) != 0)
      i++;
    return i;
  }

  //Dibuja la estructura de datos usando el recorrido BFS 
  //@param no recibe parametros, usa el atributo arbol generado en el Constructor
  //@return regresa el contenido del SVG en forma de String 

  @Override public String dibuja(){
    String vertices = "", aristas = "";
    Cola<VerticeArbolBinario<Integer>> cola = new Cola<VerticeArbolBinario<Integer>>();
    cola.mete(arbol.raiz());
    while(!cola.esVacia()){
      VerticeArbolBinario<Integer> vertice = cola.saca();
      if(vertice.hayIzquierdo()) cola.mete(vertice.izquierdo());
      if(vertice.hayDerecho()) cola.mete(vertice.derecho());
      aristas += dibujarAristas(vertice);
      vertices += dibujarVertice(vertice);
    }
    return abrirDibujo() + aristas + vertices + cerrarDibujo();
  }

  //Obtiene la posicion en el eje Y de los vertices 
  //@param Recibe un vertice de Integer al cual se le calculara la posicion 
  //@return Regresa un entero correspondiente a la posicion en Y del vertice 

  protected int obtenerPosY(VerticeArbolBinario<Integer> vertice){
    return vertice.profundidad()*80 + 50;
  }

  // Determina si el Vertice recibido es izquierdo o derecho 
  // @param Recibe un vertice de Integer, se evalua si tiene padre, en caso de que lo tenga se evalua si el vertice 
  // recibido es el vertice izquierdo de este vertice padre 
  // @return True si el vertice es el hijo Izquierdo del vertice Padre del vertice recibido, false en caso de que no tenga Padre
  // o no sea el hijo izquierdo 

  protected boolean esIzquierdo(VerticeArbolBinario<Integer> vertice){
    return vertice.hayPadre() && vertice.padre().hayIzquierdo() && vertice.padre().izquierdo() == vertice;
  }

  // Obtiene la posicion del Eje X del vertice recibido por parametro 
  // @param Recibe un vertice de Integer, si el vertice no tiene padre se regresa el tamaño del archivo entre 2 
  // Si el vertice tiene padre, revisamos si el vertice es izquierdo, en cuyo caso se regresa la posicion del vertice padre
  // menos el desplazamiento del mismo. 
  // Si el vertice es derecho, se regresa la posicion del vertice padre mas el desplazamiento del mismo 
  // El metodo es recursivo hasta la raiz del Arbol 
  // @return un entero con la posicion del vertice recibido por parametro 

  protected int obtenerPosX(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return tamanoX/2;
    if(esIzquierdo(vertice))
      return obtenerPosX(vertice.padre()) - desplazamiento(vertice);
    return obtenerPosX(vertice.padre()) + desplazamiento(vertice);
  }

  //Obtiene el desplazamiento del Eje X del vertice recibido por parametro 
  //@param Recibe un vertice de Integer, si el vertice no tiene padre, se regresa la altura del vertice*5*(numero de elementos+18)
  //en caso de que tenga padre, se regresa el desplazamiento del padre entre 2 
  //El metodo es recursivo hasta la raiz del arbol 
  //@return un entero con el desplazamiento del vertice recibido por parametro 

  protected int desplazamiento(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return vertice.altura()*5*(elementos+10);
    return desplazamiento(vertice.padre())/2;
  }

  //Obtiene un String con el SVG correspondiente a una arista 
  //@param recibe un vertice de Integer, si el vertice no tiene padre, se regresa el String vacio 
  //si el vertice tiene padre, se enlaza el vertice actual con su padre a partir de sus posiciones 
  //@return un String con el formato SVG correspondiente a una arista 

  protected String dibujarAristas(VerticeArbolBinario<Integer> vertice){
    if(!vertice.hayPadre())
      return "";
    return "<line x1='" + (obtenerPosX(vertice)) + "' y1='" + (obtenerPosY(vertice)) + "' x2='" + (obtenerPosX(vertice.padre())) + "' y2='" + (obtenerPosY(vertice.padre())) + "' stroke='black' stroke-width='3' />" + '\n';
  }

  //Obtiene un String con el SVG correspondiente a un vertice 
  //@param recibe un vertice de Integer, las posiciones del vertice son calculadas con obtenerPosY y obtenerPosX respectivamente 
  //el tamaño del texto es calculado a partir del radio del mismo 
  //@return un String con el formato SVG correspondiente a una arista

  protected String dibujarVertice(VerticeArbolBinario<Integer> vertice){
    int posX = obtenerPosX(vertice);
    int posY = obtenerPosY(vertice);
    String circulo = "<circle cx='" + posX + "' cy='" + posY + "' r='25' fill= 'white' stroke='black' stroke-width='2.5' /> ";
    String elemento = "<text fill='black' font-family='sans-serif' font-size='" + ((radio(vertice.get()) < 4) ? 21 : (17-radio(vertice.get()))) + "' x='" + posX + "' y='" + (posY+5) + "' text-anchor='middle' >" +  vertice.get() + "</text>";
    return circulo + '\n' + elemento  + '\n';
  }
} 
