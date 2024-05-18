package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
      /* El elemento del nodo. */
      private T elemento;
      /* El nodo anterior. */
      private Nodo anterior;
      /* El nodo siguiente. */
      private Nodo siguiente;

      /* Construye un nodo con un elemento. */
      private Nodo(T elemento) {
         // Aquí va su código.
         this.elemento = elemento;
      }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
      private Nodo anterior;
        /* El nodo siguiente. */
      private Nodo siguiente;

        /* Construye un nuevo iterador. */
      private Iterador() {
            // Aquí va su código.
        anterior = null;
        siguiente = cabeza;
      }

        /* Nos dice si hay un elemento siguiente. */
      @Override public boolean hasNext() {
            // Aquí va su código.
        return siguiente != null;
      }

        /* Nos da el elemento siguiente. */
      @Override public T next() throws NoSuchElementException {
            // Aquí va su código.
        if(!(this.hasNext())) throw new NoSuchElementException();
        anterior = siguiente;
        siguiente = siguiente.siguiente;
        return anterior.elemento;
      }

        /* Nos dice si hay un elemento anterior. */
      @Override public boolean hasPrevious() {
            // Aquí va su código.
        return anterior != null;
      }

        /* Nos da el elemento anterior. */
      @Override public T previous() {
            // Aquí va su código.
        if(!(this.hasPrevious())) throw new NoSuchElementException();
        siguiente = anterior;
        anterior = anterior.anterior;
        return siguiente.elemento;
      }

        /* Mueve el iterador al inicio de la lista. */
      @Override public void start() {
            // Aquí va su código.
        anterior = null;
        siguiente = cabeza;
      }

        /* Mueve el iterador al final de la lista. */
      @Override public void end() {
            // Aquí va su código.
        anterior = rabo;
        siguiente = null;
      }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
      return this.getElementos();
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
      return longitud;
        // Aquí va su código.
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
      return longitud == 0;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      longitud++;
      if(cabeza == null){
        cabeza = new Nodo(elemento);
        rabo = cabeza;
      }else{
        Nodo aux = new Nodo(elemento);
        aux.anterior = rabo;
        rabo.siguiente = aux;
        rabo = aux;
      }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      this.agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      longitud++;
      if(cabeza == null ){
        Nodo aux = new Nodo(elemento);
        cabeza = aux;
        rabo = aux;
      } else{
          Nodo aux = new Nodo(elemento);
          aux.siguiente = cabeza;
          cabeza.anterior = aux;
          cabeza = aux;
      }
      
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) throws IllegalArgumentException {
        // Aquí va su código.
      if(elemento == null) throw new IllegalArgumentException();
      if(i < 1){
        this.agregaInicio(elemento);
      } else if(i > this.getLongitud()-1){
        this.agregaFinal(elemento);
      }else{
        longitud++;
        Nodo m = this.recorre(i);
        Nodo n = new Nodo(elemento);
        n.anterior = m.anterior;
        n.siguiente = m;
        m.anterior.siguiente = n;
        m.anterior = n;
      }
    }
    private Nodo recorre(int a){
      Nodo n = cabeza;
      int i = 0;
      while(n != null){
        if(i == a) return n;
        n = n.siguiente;
        i++;
      }
      return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
      Nodo b = this.encuentra(elemento);
      this.elimina(b);
    }
    private void elimina(Nodo b){
      if(b == null) return;
      longitud--;
      if(cabeza == rabo){
        cabeza = null;
        rabo = cabeza;
        return;
      }else if(cabeza == b){
        cabeza = cabeza.siguiente;
        cabeza.anterior = null;
      } else if(rabo == b){
        rabo = rabo.anterior;
        rabo.siguiente = null;
      } else {
        Nodo a = b.anterior;
        Nodo c = b.siguiente;
        a.siguiente = c;
        c.anterior = a;
      }
    }
    private Nodo encuentra(T elemento){
      Nodo n = cabeza;
      while(n != null){
        if(n.elemento.equals(elemento)) return n;
        n = n.siguiente;
      }
      return null;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() throws NoSuchElementException {
        // Aquí va su código.
      if(cabeza == null) throw new NoSuchElementException();
      longitud--;
      if(this.longitud == 0){
        T aux = cabeza.elemento;
        cabeza = null;
        rabo = null;
        return aux;
      }
      T a = cabeza.elemento;
      cabeza = cabeza.siguiente;
      cabeza.anterior = null;
      return a;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() throws NoSuchElementException {
        // Aquí va su código.
      if(cabeza == null) throw new NoSuchElementException();
      longitud--;
      if(longitud == 0){
        T aux = cabeza.elemento;
        cabeza = null;
        rabo = null;
        return aux;
      }
      T a = rabo.elemento;
      rabo = rabo.anterior;
      rabo.siguiente = null;
      return a;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
      return this.encuentra(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
      Lista<T> reversa = new Lista<T>();
      IteradorLista<T> it = this.iteradorLista();
      it.end();
      while(it.hasPrevious()){
        reversa.agrega(it.previous());
      }
      return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
      Lista<T> copia = new Lista<T>();
      IteradorLista<T> it = this.iteradorLista();
      while(it.hasNext()){
        copia.agrega(it.next());
      }
      return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
      cabeza = null;
      rabo = cabeza;
      longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() throws NoSuchElementException {
        // Aquí va su código.
      if(cabeza == null ) throw new NoSuchElementException();
      return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() throws NoSuchElementException {
        // Aquí va su código.
      if(rabo == null) throw new NoSuchElementException();
      return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) throws ExcepcionIndiceInvalido {
        // Aquí va su código.
      if( i < 0 || this.longitud -1 < i) throw new ExcepcionIndiceInvalido();
      return this.recorre(i).elemento;
    }
    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
      IteradorLista<T> it = this.iteradorLista();
      int i = 0;
      while(it.hasNext()){
        T a = it.next();
        if(a == elemento) return i;
        i++;
      }
      return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
      if(cabeza == null) return "[]";
      String res = "";
      IteradorLista<T> a = this.iteradorLista();
      while(a.hasNext()){
        res += a.next().toString() + ", ";
        }
      res = res.substring(0, res.length() -2);
      res = "[" + res + "]";
      return res;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
      if (objeto == null || getClass() != objeto.getClass())
        return false;
      @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
      if(this.longitud != lista.getLongitud()) return false;
      IteradorLista<T> a = this.iteradorLista();
      IteradorLista<T> b = lista.iteradorLista();
      boolean res = true;
      while(a.hasNext()){
        if(!(a.next().equals( b.next()))){
          res = false;
        }
      }
      return res;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
