package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
          super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
          return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
          return super.toString() + String.format(" %d/%d", this.altura, balance());
        }
        private int balance(){
          int alturaIzquierdo = -1;
          int alturaDerecho = -1;
          if(izquierdo != null)
            alturaIzquierdo = ((VerticeAVL) izquierdo).altura;
          if(derecho != null)
            alturaDerecho = ((VerticeAVL) derecho).altura;
          return alturaIzquierdo - alturaDerecho;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
            // Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
      return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
      super.agrega(elemento);
      rebalancea((VerticeAVL)ultimoAgregado.padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
      Vertice eliminar = (Vertice) super.busca(elemento);
      if(eliminar == null) 
        return;
      if(eliminar.izquierdo != null && eliminar.derecho != null)
        eliminar = intercambiaEliminable(eliminar);
      eliminaVertice(eliminar);
      elementos--;
      rebalancea((VerticeAVL)eliminar.padre);
    }
    
    private void rebalancea(VerticeAVL vertice){
      if(vertice == null)
        return;
      vertice.altura = nuevaAltura((VerticeAVL) vertice.izquierdo,(VerticeAVL) vertice.derecho);
      if(vertice.balance() == -2){
        VerticeAVL derecho = (VerticeAVL) vertice.derecho;
        VerticeAVL hijoIzquierdo = (VerticeAVL) derecho.izquierdo;
        if(derecho.balance() == 1){
          super.giraDerecha(derecho);
          derecho.altura = nuevaAltura((VerticeAVL) derecho.izquierdo, (VerticeAVL) derecho.derecho);
          hijoIzquierdo.altura = nuevaAltura((VerticeAVL) hijoIzquierdo.izquierdo, (VerticeAVL) hijoIzquierdo.derecho);
        }
        derecho = (VerticeAVL)vertice.derecho;
        hijoIzquierdo = (VerticeAVL) vertice.derecho.izquierdo;
        super.giraIzquierda(vertice);
        vertice.altura = nuevaAltura((VerticeAVL) vertice.izquierdo, (VerticeAVL) vertice.derecho);
        derecho.altura = nuevaAltura((VerticeAVL) derecho.izquierdo, (VerticeAVL) derecho.derecho);
        vertice = derecho;
      } else if(vertice.balance() == 2){
        VerticeAVL izquierdo = (VerticeAVL) vertice.izquierdo;
        VerticeAVL hijoDerecho = (VerticeAVL) izquierdo.derecho;
        if(izquierdo.balance() == -1){
          super.giraIzquierda(izquierdo);
          izquierdo.altura = nuevaAltura((VerticeAVL) izquierdo.izquierdo, (VerticeAVL) izquierdo.derecho);
          hijoDerecho.altura = nuevaAltura((VerticeAVL) hijoDerecho.izquierdo, (VerticeAVL) hijoDerecho.derecho);
        }
        izquierdo = (VerticeAVL) vertice.izquierdo;
        hijoDerecho = (VerticeAVL) vertice.izquierdo.derecho;
        super.giraDerecha(vertice);
        vertice.altura = nuevaAltura((VerticeAVL) vertice.izquierdo, (VerticeAVL) vertice.derecho);
        izquierdo.altura = nuevaAltura((VerticeAVL) izquierdo.izquierdo, (VerticeAVL) izquierdo.derecho);
        vertice = izquierdo;
      }
      rebalancea((VerticeAVL)vertice.padre);
    }
    private int balance(Vertice vertice){
      return ((VerticeAVL) vertice).balance();
    }
    private int nuevaAltura(VerticeAVL izquierdo, VerticeAVL derecho){
      int alturaIzquierdo = -1;
      int alturaDerecho = -1;
      if(izquierdo != null)
        alturaIzquierdo = izquierdo.altura;
      if(derecho != null)
        alturaDerecho = derecho.altura;
      if(alturaIzquierdo > alturaDerecho)
        return alturaIzquierdo + 1;
      return alturaDerecho + 1;
    }
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
