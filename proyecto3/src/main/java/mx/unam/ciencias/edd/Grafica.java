package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            iterador = vertices.iterator(); 
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* El diccionario de vecinos del vértice. */
        private Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Diccionario<T, Vecino>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return this.vecinos.getElementos();
            // Aquí va su código.
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return this.vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
          this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
          return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
          if(distancia > vertice.distancia || distancia == -1)
            return 1;
          else if(distancia < vertice.distancia || vertice.distancia == -1)
            return -1;
          return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
          this.vecino = vecino;
          this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
          return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
          return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
          return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
          return vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino<T> {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica<T>.Vertice v, Grafica<T>.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices = new Diccionario<T, Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getElementos();
        // Aquí va su código.
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
        // Aquí va su código.
    }
    
    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) throws IllegalArgumentException {
        if(elemento == null || contiene(elemento))
          throw new IllegalArgumentException("EL elemento ya ha sido agregado o es nulo");
        vertices.agrega(elemento, new Vertice(elemento));
        // Aquí va su código.
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) throws NoSuchElementException, IllegalArgumentException{
      if(sonVecinos(a,b) || a.equals(b)) 
        throw new IllegalArgumentException("los elementos ya estan conectados o son iguales");
      ((Vertice)vertice(a)).vecinos.agrega(b, new Vecino((Vertice)vertice(b),1));
      ((Vertice)vertice(b)).vecinos.agrega(a, new Vecino((Vertice)vertice(a),1));
      aristas++;
        // Aquí va su código.
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
      if(sonVecinos(a,b) || a.equals(b) || peso < 0) 
        throw new IllegalArgumentException("los elementos ya estan conectados o son iguales");
      ((Vertice)vertice(a)).vecinos.agrega(b, new Vecino((Vertice)vertice(b),peso));
      ((Vertice)vertice(b)).vecinos.agrega(a, new Vecino((Vertice)vertice(a),peso));
      aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) throws IllegalArgumentException {
        // Aquí va su código.
      if(!sonVecinos(a,b))
        throw new IllegalArgumentException("Los elementos no estan conectados");
      ((Vertice)vertice(a)).vecinos.elimina(b);
      ((Vertice)vertice(b)).vecinos.elimina(a);
      aristas--;
    }
    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
      return vertices.contiene(elemento);
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) throws NoSuchElementException {
        Vertice eliminar = vertices.get(elemento);
        vertices.elimina(eliminar.elemento);
        for(Vecino vecino : eliminar.vecinos){
          vecino.vecino.vecinos.elimina(eliminar.elemento);
          aristas--;
         }
        // Aquí va su código.
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) throws NoSuchElementException{
      if(!contiene(a) || !contiene(b))
        throw new NoSuchElementException("Alguno no es elemento de la gráfica");
      return ((Vertice)vertice(a)).vecinos.contiene(b) && ((Vertice)vertice(b)).vecinos.contiene(a);
        // Aquí va su código.
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) throws NoSuchElementException, IllegalArgumentException{
        // Aquí va su código.
      if(!sonVecinos(a,b))
        throw new IllegalArgumentException("Los elementos no estan conectados");
      return ((Vertice)vertice(a)).vecinos.get(b).peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
      if(!sonVecinos(a, b) || peso <= 0)
        throw new IllegalArgumentException("Los elementos no estan conectados");
      ((Vertice)vertice(a)).vecinos.get(b).peso = peso;
      ((Vertice)vertice(b)).vecinos.get(a).peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento){
        if(!contiene(elemento))
          throw new NoSuchElementException("El elemento no es elemento de la grafica");
        return vertices.get(elemento);
        // Aquí va su código.
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) throws IllegalArgumentException{
      if(vertice == null || vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)
        throw new IllegalArgumentException("El vertice es invalido");
      if(vertice.getClass() == Vertice.class)
        ((Vertice)vertice).color = color;
      if(vertice.getClass() == Vecino.class)
        ((Vecino)vertice).vecino.color = color;
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        Cola<Vertice> cola = new Cola<Vertice>();
        Iterator<Vertice> it = vertices.iterator();
    
        if(it.hasNext()){
          Vertice v = it.next();
          recorrido(v.elemento, vertice -> vertice.get(), cola);
        }
        if(it.hasNext()){
          Vertice v = it.next();
          Color color = v.getColor();
          for(Vertice vertice : vertices)
            if(color != vertice.getColor())
              return false;
        }
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for(Vertice vertice : vertices)
          accion.actua(vertice);
        // Aquí va su código.
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        Cola<Vertice> cola = new Cola<Vertice>();
        recorrido(elemento, accion, cola);
        for(Vertice vertice : vertices)
          vertice.color = Color.NINGUNO;
    }
    private void recorrido(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> meteSaca){
        for(Vertice vertice : vertices)
          vertice.color = Color.ROJO;
        Vertice actual = (Vertice) vertice(elemento);
        actual.color = Color.NEGRO;
        meteSaca.mete(actual);
        while(!meteSaca.esVacia()){
          actual = meteSaca.saca();
          accion.actua(actual);
          for(Vecino vertice : actual.vecinos)
            if(vertice.vecino.color == Color.ROJO){
              vertice.vecino.color = Color.NEGRO;
              meteSaca.mete(vertice.vecino);
            }
        }
        // Aquí va su código.
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        Pila<Vertice> pila = new Pila<Vertice>();
        recorrido(elemento,accion,pila);
        for(Vertice vertice : vertices)
          vertice.color = Color.NINGUNO;
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
        // Aquí va su código.
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
        // Aquí va su código.
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        String ver = "{";
        String aris = "{";
        for(Vertice vertice : vertices)
          vertice.color = Color.ROJO;
        for(Vertice vertice : vertices){
          ver += vertice.get() + ", "; 
          for(Vecino vecino : vertice.vecinos){
              String impl = "(" + vertice.get() + ", " + vecino.get() + "), ";
              String impl2 = "(" + vecino.get() +", " +vertice.get() +"), ";
              if(!(aris.contains(impl) || aris.contains(impl2)))
                aris += impl;
          }
        }
        ver += "}, ";
        aris += "}";
      return ver + aris;
        // Aquí va su código.
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        if(aristas != grafica.aristas || getElementos() != grafica.getElementos()) return false;
        for(Vertice vertice : vertices){
          if(!grafica.contiene(vertice.get())) {
            return false; }
          for(Vecino vecino : vertice.vecinos){
            boolean bandera = false;
            Vertice graf = (Vertice)grafica.vertice(vertice.get());
            for(Vecino vecinito : graf.vecinos){
              if(vecinito.get().equals(vecino.get()))
                bandera = true;
            } 
            if(bandera == false) return false;
          }
        }
        return true;
        
        // Aquí va su código.
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
      for(Vertice vertice : vertices)
        vertice.distancia = -1;
      Vertice actual = (Vertice)vertice(origen);
      actual.distancia = 0;
      Cola<Vertice> cola = new Cola<Vertice>();
      cola.mete(actual);
      while(!cola.esVacia()){
        actual = cola.saca();
        for(Vecino vecino : actual.vecinos)
          if(vecino.vecino.distancia == -1){
            vecino.vecino.distancia = actual.distancia +1;
            cola.mete(vecino.vecino);
          }
      }
      return recorrido((a,b) -> a.distancia - 1 == b.vecino.distancia, origen, destino);
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
      for(Vertice vertice : vertices)
        vertice.distancia = Double.MAX_VALUE;
      Vertice actual = (Vertice)vertice(origen);
      actual.distancia = 0;
      MonticuloDijkstra<Vertice> monticulo = (((getElementos() - 1)*getElementos()/2) - getElementos() < aristas ) ? new MonticuloArreglo<Vertice>(vertices, vertices.getElementos()) : new MonticuloMinimo<Vertice>(vertices, vertices.getElementos());
      while(!monticulo.esVacia()){
        actual = monticulo.elimina();
        for(Vecino vecino : actual.vecinos)
          if(vecino.vecino.distancia == Double.MAX_VALUE || vecino.vecino.distancia > actual.distancia + vecino.peso){
            vecino.vecino.distancia = actual.distancia + vecino.peso;
            monticulo.reordena(vecino.vecino);
          }
      }
      return recorrido((a,b) -> a.distancia - b.peso == b.vecino.distancia, origen, destino);
    }
    
    private Lista<VerticeGrafica<T>> recorrido(BuscadorCamino<T> buscador, T origen, T destino){
      Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
      VerticeGrafica<T> actual = vertice(destino);
      lista.agrega(actual);
      if(((Vertice)actual).distancia == Double.MAX_VALUE || origen == destino)
        return lista;
      while((actual = lista.getPrimero()) != vertice(origen)){
        for( Vecino vecino : ((Vertice)actual).vecinos){
          if(buscador.seSiguen((Vertice)actual , vecino)){
            lista.agregaInicio(vecino.vecino);
            break;
          }}}
      return lista;
    }
}
