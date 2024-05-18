package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            // Aquí va su código.
          this.llave = llave;
          this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
          boolean bandera = false;
          for(int i = 0; i < entradas.length; i++)
            if(entradas[i] != null ){
              this.iterador = entradas[i].iterator();
              this.indice = i;
              bandera = true;
              break;
            }
          if(!bandera){
            iterador = null;
            indice = 0;
          }
            // Aquí va su código.
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.
          return iterador != null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
          // Aquí va su código.
          if(iterador == null) throw new NoSuchElementException("No hay siguiente elemento");
          if(!iterador.hasNext()){
            for(int i = indice + 1; i < entradas.length; i++){
              if(entradas[i] != null ){
                this.iterador = entradas[i].iterator();
                this.indice = i;
                break;
              }
            }
          }
          Entrada entrada = iterador.next();
          if(!iterador.hasNext()){
            boolean bandera = false;
            for(int i = indice+1; i < entradas.length; i++){
              if(entradas[i] != null ){
                bandera = true;
                break;
               }
                
            }
            if(!bandera)
              iterador = null;
          }
          return entrada;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
          return siguiente().llave;
            // Aquí va su código.
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
          return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
      this.entradas = (capacidad < 64) ? nuevoArreglo(64) : nuevoArreglo(potencia(capacidad));
      this.dispersor = dispersor;
    }
    
    private int potencia(int capacidad){
      int i = 2;
      while(i < capacidad)
        i*= 2;
      return i*2;
      
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) throws IllegalArgumentException {
        // Aquí va su código.
      if(llave == null || valor == null )
        throw new IllegalArgumentException("La llave o el valor son nulo");
      int i = getDispersion(llave);
      if(entradas[i] == null){
        entradas[i] = new Lista<Entrada>();
        entradas[i].agrega(new Entrada(llave,valor));
        elementos++;
      }else{
        boolean bandera = false;
        for(Entrada entrada : entradas[i])
          if(entrada.llave.equals(llave)){
            entrada.valor = valor;
            bandera = true;
            break;
          }
        if(!bandera){
          entradas[i].agrega(new Entrada(llave,valor));
          elementos++;
        }
      }
      if(MAXIMA_CARGA <= carga())
        copiarArreglo();
      
    }
    private int getDispersion(K llave){
      return entradas.length-1 & dispersor.dispersa(llave);
    }
    private void copiarArreglo(){
      Lista<Entrada>[] viejo = entradas;
      entradas = nuevoArreglo(viejo.length*2);
      elementos = 0;
      for(Lista<Entrada> lista : viejo)
        if(lista != null)
          for(Entrada entrada : lista)
            this.agrega(entrada.llave, entrada.valor);
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) throws IllegalArgumentException, NoSuchElementException{
        // Aquí va su código.
      if(llave == null) 
        throw new IllegalArgumentException("la llave es nula");
      if(entradas[getDispersion(llave)] == null || !contiene(llave))
        throw new NoSuchElementException("La llave no se encuentra");
      for(Entrada entrada : entradas[getDispersion(llave)])
        if(entrada.llave.equals(llave))
          return entrada.valor;
      return null;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
      if(llave == null || entradas[getDispersion(llave)] == null)
        return false;
      for(Entrada entrada : entradas[getDispersion(llave)])
        if(entrada.llave.equals(llave))
          return true;
      return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
      if(llave == null)
        throw new IllegalArgumentException("la llave es nula");
      if(entradas[getDispersion(llave)] == null || !contiene(llave)) 
        throw new NoSuchElementException("la posicion es nula");
      for(Entrada entrada : entradas[getDispersion(llave)])
        if(entrada.llave.equals(llave)){
          entradas[getDispersion(llave)].elimina(entrada);
          if(entradas[getDispersion(llave)].esVacia())
            entradas[getDispersion(llave)] = null;
          elementos--;
          }
        
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
      int i = 0;
      for(Lista<Entrada> lista : entradas)
        if(lista != null)
          i+= (lista.getElementos() > 1) ? 1 : 0;
      return i;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
      int maximo = 0;
      for(Lista<Entrada> lista : entradas)
        if(lista != null && lista.getElementos() > maximo )
          maximo = lista.getElementos();
      return maximo-1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
      return (Double.valueOf(elementos)/Double.valueOf(entradas.length));
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
      return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
      return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
      elementos = 0;
      entradas = nuevoArreglo(entradas.length);
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
      if(elementos == 0)
        return "{}";
      String res = "{ ";
      Iterador it = new Iterador();
      while(it.hasNext()){
        Entrada entrada = it.siguiente();
        res += "'" + entrada.llave + "': '" + entrada.valor +"', ";
      }
      return res + "}";
      
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        // Aquí va su código.
        if(d.getElementos() != elementos) 
          return false;
        Iterator<K> itLlave = d.iteradorLlaves();
        Iterator<V> itValor = d.iterator();
        while(itLlave.hasNext()){
          K llave = itLlave.next();
          if(!this.contiene(llave) || !this.get(llave).equals(itValor.next()))
            return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
