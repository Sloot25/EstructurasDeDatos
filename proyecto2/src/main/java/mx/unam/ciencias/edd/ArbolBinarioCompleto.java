package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
          cola = new Cola<Vertice>();
          if(raiz != null) cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
          if(this.cola.esVacia()) return false;
          return true;
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
          Vertice vertice = this.cola.saca();
          if(vertice.izquierdo != null) this.cola.mete(vertice.izquierdo);
          if(vertice.derecho != null) this.cola.mete(vertice.derecho);
          return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      Vertice vertice = nuevoVertice(elemento);
      elementos++;
      if(raiz == null){
        this.raiz = vertice;
        return;
      }

      int[] recorrido = recorrido();
      Vertice posicion = raiz;
      for(int i = recorrido.length-1; i > 0; i--){
        if(recorrido[i] == 0) 
          posicion = posicion.izquierdo;
        else 
          posicion = posicion.derecho;
      }
      if(recorrido[0] == 0){
        posicion.izquierdo = vertice;
        vertice.padre = posicion;
       } else{
        posicion.derecho = vertice;
        vertice.padre = posicion;
      }
    }


    private int[] recorrido(){
      int[] recorrido = new int[altura()];
      int n = elementos;
      for(int i = 0; i < recorrido.length; i++){
        recorrido[i] = n & 1;
        n >>= 1;
      } 
      return recorrido;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
      Vertice eliminar = (Vertice)busca(elemento);
      if(eliminar == null) return;
      this.elementos--;
      if(this.elementos == 0){
        raiz = null;
        return;
      }
      Cola<Vertice> cola = new Cola<Vertice>();
      cola.mete(raiz);
      Vertice vertice = null;
      while(!(cola.esVacia())){
        vertice = cola.saca();
        if(vertice.izquierdo != null) cola.mete(vertice.izquierdo);
        if(vertice.derecho != null) cola.mete(vertice.derecho);
      }
      eliminar.elemento = vertice.elemento;
      Vertice padre = vertice.padre;
      if(padre.derecho == null)
        padre.izquierdo = null;
      else 
        padre.derecho = null;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
     @Override public int altura() {
        // Aquí va su código.
      if(raiz == null) return -1;
      int n = elementos;
      int contador = 0;
      while(n > 1){
        n >>= 1;
        contador++;
      }
      return contador;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
      if(raiz == null) return;
      Cola<Vertice> c = new Cola<Vertice>();
      c.mete(raiz);
      while(!(c.esVacia())){
        Vertice v;
        try {
          v = c.saca();
          if(v.izquierdo != null) c.mete(v.izquierdo);
          if(v.derecho != null) c.mete(v.derecho);
          accion.actua(v);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
