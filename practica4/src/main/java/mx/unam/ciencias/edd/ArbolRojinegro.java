package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
          super(elemento);
          this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            // Aquí va su código.
          if(color == Color.NEGRO) return "N{" + elemento.toString() + "}";
          return "R{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return color == vertice.color && super.equals(objeto);
            // Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
      return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
      return ((VerticeRojinegro) vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
      super.agrega(elemento);
      VerticeRojinegro ultimo = (VerticeRojinegro) ultimoAgregado;
      ultimo.color = Color.ROJO;
      rebalancearAgrega(ultimo);
    }
    private void rebalancearAgrega(VerticeRojinegro vertice){
      VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;
      if(padre == null){
        vertice.color = Color.NEGRO;
      } else if(padre.color == Color.NEGRO){
        return;
      } else if(obtenerHermano(padre) != null && obtenerHermano(padre).color == Color.ROJO){
        VerticeRojinegro tio = obtenerHermano(padre);
        tio.color = Color.NEGRO;
        padre.color = Color.NEGRO;
        ((VerticeRojinegro) padre.padre).color = Color.ROJO;
        rebalancearAgrega((VerticeRojinegro)padre.padre);
      } else{
        if(esIzquierdo(padre) && !esIzquierdo(vertice)){
          super.giraIzquierda(padre);
          VerticeRojinegro aux = padre;
          padre = vertice;
          vertice = aux;
        }
        else if(!esIzquierdo(padre) && esIzquierdo(vertice)){
          super.giraDerecha(padre);
          VerticeRojinegro aux = padre;
          padre = vertice;
          vertice = aux;
        }
        padre.color = Color.NEGRO;
        ((VerticeRojinegro) padre.padre).color = Color.ROJO;
        if(esIzquierdo(vertice))
          super.giraDerecha(padre.padre);
        else 
          super.giraIzquierda(padre.padre);
      }
    }
    private VerticeRojinegro obtenerHermano(VerticeRojinegro vertice){
      if(esIzquierdo(vertice))
        return (VerticeRojinegro)vertice.padre.derecho;
      return (VerticeRojinegro)vertice.padre.izquierdo;
    }
    private boolean esIzquierdo(VerticeRojinegro vertice){
      Vertice padre = vertice.padre;
      if(padre.izquierdo == vertice ) return true;
      return false;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
      VerticeRojinegro eliminar = (VerticeRojinegro) this.busca(elemento);
      if(eliminar == null) return;
      elementos--;
      if(eliminar.izquierdo != null && eliminar.derecho != null){
        eliminar = (VerticeRojinegro)super.intercambiaEliminable(eliminar);
      }
      if(eliminar.izquierdo == null && eliminar.derecho == null) 
        creaFantasma(eliminar);
      VerticeRojinegro hijo = obtenerHijo(eliminar);
      eliminaVertice(eliminar);
      if(hijo.color == Color.ROJO){
        hijo.color = Color.NEGRO;
        return; 
      } else if(eliminar.color == Color.ROJO){
        if(hijo.elemento == null) eliminaVertice(hijo);
        return;
      } else{
        rebalancearElimina(hijo);
        if(hijo.elemento == null) eliminaVertice(hijo);
      }
    }
    private void creaFantasma(Vertice vertice){
      VerticeRojinegro fantasma = (VerticeRojinegro) nuevoVertice(null);
      fantasma.padre = vertice;
      vertice.izquierdo = fantasma;
      fantasma.color = Color.NEGRO;
    }
    private VerticeRojinegro obtenerHijo(Vertice vertice){
      if(vertice.hayIzquierdo())
        return (VerticeRojinegro)vertice.izquierdo;
      else 
        return (VerticeRojinegro)vertice.derecho;
    }
    private void rebalancearElimina(VerticeRojinegro vertice){
      if(vertice.padre == null) return;
      VerticeRojinegro padre = (VerticeRojinegro)vertice.padre;
      VerticeRojinegro hermano = (VerticeRojinegro)obtenerHermano(vertice);
      if(hermano.color == Color.ROJO){
        padre.color = Color.ROJO;
        hermano.color = Color.NEGRO;
        if(esIzquierdo(vertice))
          super.giraIzquierda(padre);
        else 
          super.giraDerecha(padre);
        hermano = obtenerHermano(vertice);
      }
      VerticeRojinegro sobrinoIzquierdo = (VerticeRojinegro)hermano.izquierdo;
      VerticeRojinegro sobrinoDerecho = (VerticeRojinegro)hermano.derecho;
      //if((sobrinoDerecho == null && sobrinoIzquierdo == null && padre.color == Color.NEGRO && hermano.color == Color.NEGRO) || (sobrinoIzquierdo.color == Color.NEGRO && sobrinoDerecho.color == Color.NEGRO && padre.color == Color.NEGRO && hermano.color == Color.NEGRO)){
      if(sonNegros(sobrinoDerecho, sobrinoIzquierdo) && padre.color == Color.NEGRO && hermano.color == Color.NEGRO){
        hermano.color = Color.ROJO;
        rebalancearElimina(padre);
        return;
      } else if (sonNegros(sobrinoDerecho, sobrinoIzquierdo) && padre.color == Color.ROJO && hermano.color == Color.NEGRO){
        hermano.color = Color.ROJO;
        padre.color = Color.NEGRO;
        return;
      } else if(esIzquierdo(vertice) && sobrinoIzquierdo != null && sobrinoIzquierdo.color == Color.ROJO && (sobrinoDerecho == null || sobrinoDerecho.color == Color.NEGRO)){
        hermano.color = Color.ROJO;
        sobrinoIzquierdo.color = Color.NEGRO;
        super.giraDerecha(hermano);
      } else if(!esIzquierdo(vertice) && sobrinoDerecho != null &&sobrinoDerecho.color == Color.ROJO && (sobrinoIzquierdo == null || sobrinoIzquierdo.color == Color.NEGRO)){
        hermano.color = Color.ROJO;
        sobrinoDerecho.color = Color.NEGRO;
        super.giraIzquierda(hermano);
      }
      hermano = obtenerHermano(vertice);
      sobrinoDerecho = (VerticeRojinegro) hermano.derecho;
      sobrinoIzquierdo = (VerticeRojinegro) hermano.izquierdo;
      hermano.color = padre.color;
      padre.color = Color.NEGRO;
      if(esIzquierdo(vertice)){
        sobrinoDerecho.color = Color.NEGRO;
        super.giraIzquierda(padre);
      } else{
        sobrinoIzquierdo.color = Color.NEGRO;
        super.giraDerecha(padre);
      }
    }
    private boolean sonNegros(VerticeRojinegro sobrinoDerecho, VerticeRojinegro sobrinoIzquierdo){
      if(sobrinoDerecho == null && sobrinoIzquierdo == null)
        return true;
      else if(sobrinoIzquierdo == null)
        return sobrinoDerecho.color == Color.NEGRO;
      else if(sobrinoDerecho == null)
        return sobrinoIzquierdo.color == Color.NEGRO;
      else 
        return sobrinoIzquierdo.color == Color.NEGRO && sobrinoDerecho.color == Color.NEGRO;
     }
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
