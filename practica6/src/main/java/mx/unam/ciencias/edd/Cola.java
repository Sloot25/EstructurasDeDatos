package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
      String res = "";
      Nodo n = cabeza;
      while( n != null){
          res += n.elemento.toString() + "," ;
          n = n.siguiente;
      }
      return res;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      if(this.rabo == null){
        this.rabo = new Nodo(elemento);
        this.cabeza = this.rabo;
      } else{
        Nodo aux = this.rabo;
        rabo = new Nodo(elemento);
        aux.siguiente = rabo;
      }
    }
}
