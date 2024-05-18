package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
          return this.indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() throws NoSuchElementException{
            // Aquí va su código.
          if(!hasNext()) throw new NoSuchElementException();
          return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
          this.elemento = elemento;
          this.indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
          return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
          this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
          return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
      arbol = nuevoArreglo(100);
    } 

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
      elementos = n;
      arbol = nuevoArreglo(n);
      int i = 0;
      for(T elemento : iterable){
        arbol[i] = elemento;
        arbol[i].setIndice(i++);
      }
      for(int j = n/2; j >= 0; j--)
        haciaAbajo(j);
    }
    public void haciaArriba(int posicion){
      if((posicion-1)/2 >= 0 && (posicion-1)/2 < elementos && arbol[(posicion-1)/2].compareTo(arbol[posicion]) > 0){
        intercambiarElementos(posicion, (posicion-1)/2);
        haciaArriba((posicion-1)/2);
      }
    }
    private void haciaAbajo(int posicion){
      if((posicion*2)+1 < elementos && posicion*2 + 1 > 0 && arbol[(posicion*2)+1].compareTo(arbol[posicion]) < 0){
        intercambiarElementos(posicion*2+1, posicion);
        haciaAbajo(posicion);
        haciaAbajo((posicion*2)+1);
      } else if((posicion*2) + 2 < elementos && posicion * 2 + 2 > 0&& arbol[(posicion*2) + 2].compareTo(arbol[posicion]) < 0){
        intercambiarElementos((posicion*2)+2, posicion);
        haciaAbajo(posicion);
        haciaAbajo((posicion*2)+2);
      } 
    }
    public void intercambiarElementos(int primero, int segundo){
      T aux = arbol[primero];
      int indice = arbol[primero].getIndice();
      int indice2 = arbol[segundo].getIndice();
      arbol[primero] = arbol[segundo];
      arbol[primero].setIndice(indice);
      arbol[segundo] = aux;
      arbol[segundo].setIndice(indice2);
    }
    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
      if(arbol.length == elementos)
        nuevoArreglo();
      arbol[elementos] = elemento;
      arbol[elementos].setIndice(elementos);
      elementos++;
      haciaArriba(elementos-1);
    }
    private void nuevoArreglo(){
      T[] nuevo = nuevoArreglo(arbol.length*2);
      for(int i = 0; i < elementos; i++)
        nuevo[i] = arbol[i];
      arbol = nuevo;
    }
    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() throws IllegalStateException{
        // Aquí va su código.
      if(elementos == 0) throw new IllegalStateException("No hay Elementos");
      intercambiarElementos(0, elementos-1);
      elementos--;
      haciaAbajo(0);
      arbol[elementos].setIndice(-1);
      return arbol[elementos];
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
      if(elemento.getIndice() < 0 || elemento.getIndice() > elementos-1)
        return; 
      int indice = elemento.getIndice();
      intercambiarElementos(indice, elementos-1);
      elementos--;
      arbol[elementos].setIndice(-1);
      reordena(arbol[indice]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
      if(elemento.getIndice() < 0 || elemento.getIndice() > elementos-1)
        return false;
      return elemento.equals(this.get(elemento.getIndice()));
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
      return elementos <= 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
      elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.
      haciaAbajo(elemento.getIndice());
      haciaArriba(elemento.getIndice());
      
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
      return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) throws NoSuchElementException{
        // Aquí va su código.
      if(i < 0 || i > elementos-1)
        throw new NoSuchElementException("Indice invalido");
      return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
      String res = "";
      for(int i = 0; i < elementos; i++)
        res += arbol[i] + ", ";
      return res;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
        if(monticulo.getElementos() != elementos) 
          return false;
        for(int i = 0; i < elementos; i++)
          if(!monticulo.contiene(arbol[i]))
            return false; 
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
      Lista<Adaptador<T>> lista = new Lista<Adaptador<T>>();
      for(T elemento : coleccion)
        lista.agrega(new Adaptador(elemento));
      MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(lista);
      Lista<T> lista2 = new Lista<T>();
      while(!monticulo.esVacia())
        lista2.agrega(monticulo.elimina().elemento);
      return lista2;
    }
}
