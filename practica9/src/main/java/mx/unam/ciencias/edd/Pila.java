package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
      String res = "";
      Nodo n = cabeza;
      while(n != null){
        res +=  n.elemento.toString() + "\n"; 
        n = n.siguiente;
      }
      return res;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) throws IllegalArgumentException{
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      if(cabeza == null){
        this.cabeza = new Nodo(elemento);
        this.rabo = this.cabeza;
      } else{
        Nodo aux = new Nodo(elemento);
        aux.siguiente = cabeza;
        cabeza = aux;
      }
    }
    
}
