package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.*;
import java.lang.NumberFormatException;
public class CreadorEstructuras {
  private Lista<String> lista;

  // Constructor de la clase 
  // @param recibe una lista de la estructura a generar
  
  public CreadorEstructuras(Lista<String> lista){
    this.lista = lista;
  }

  // lector de la leerEntrada, es el encargado de clasificar las estructuras, construirlas y generar el SVG 
  // para despues imprimirlo
  // @param no recibe parametros debido a que usa la lista asignada en el Constructor
  // @return no regresa nada, debido a que imprime directamente la estructura creada

  public void leerEntrada(){
    String tipo = lista.eliminaPrimero().toLowerCase().trim();
    switch (tipo) {
      case "lista":
        Lista<Integer> listaGenerada = construirLista();
        DibujadorListas dibujadorListas = new DibujadorListas(listaGenerada);
        System.out.println(dibujadorListas.dibuja());
        break;
      case "pila":
        Pila<Integer> pila = construirPila();
        DibujadorPilas dibujadorPilas = new DibujadorPilas(pila);
        System.out.println(dibujadorPilas.dibuja());
        break;
      case "cola":
        Cola<Integer> nuevacola = construirCola();
        DibujadorColas dibujadorColas = new DibujadorColas(nuevacola);
        System.out.println(dibujadorColas.dibuja());
        break;
      case "arreglo":
        Integer[] arreglo = contruirArreglo();
        try {
        DibujadorArreglos dibujadorArreglos = new DibujadorArreglos(arreglo);
        System.out.println(dibujadorArreglos.dibuja());
        } catch (Exception e) {
          System.err.println("Uno de los valores no es un numero");
        }
        break;
      case "arbolbinariocompleto":
        ArbolBinarioCompleto<Integer> arbolCompleto = construirArbolBinarioCompleto();
        DibujadorArboles dibujadorArboles = new DibujadorArboles(arbolCompleto);
        System.out.println(dibujadorArboles.dibuja());
        break;
      case "arbolbinarioordenado":
        ArbolBinarioOrdenado<Integer> arbolOrdenado = construirArbolBinarioOrdenado();
        DibujadorArbolesOrdenados dibujadorOrdenados = new DibujadorArbolesOrdenados(arbolOrdenado);
        System.out.println(dibujadorOrdenados.dibuja());
        break;
      case "arbolrojinegro":
        ArbolRojinegro<Integer> arbolRojinegro = construirArbolRojiNegro();
        DibujadorRojiNegro dibujadorRojiNegro =  new DibujadorRojiNegro(arbolRojinegro);
        System.out.println(dibujadorRojiNegro.dibuja());
        break;
      case "arbolavl":
        ArbolAVL<Integer> arbolAvl = contruirArbolAVL();
        DibujadorAVL dibujadorAvl = new DibujadorAVL(arbolAvl);
        System.out.println(dibujadorAvl.dibuja());
        break;
      case "grafica":
        try {
          Grafica<Integer> grafica = construirGrafica();
          DibujadorGraficas dibujadorGrafica = new DibujadorGraficas(grafica);
          System.out.println(dibujadorGrafica.dibuja());
        } catch (GraficaInvalidaException e) {
          System.err.println(e.getMessage());
        }
        break;
      default:
        System.err.println("El " + tipo + " no es un tipo registrado en nuestras estructuras de datos, comprueba el nombre de la estructura a graficar");
        break;
    }
  }

  //Contruye una nueva lista a partir de la lista generada en el contructor de la clase
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  //@return regresa una nueva lista de Integer 
  //Este metodo es el encargador de capturar los errores generados por archivos con formato incorrecto
  
  private Lista<Integer> construirLista(){
    Lista<Integer> nuevalista = new Lista<Integer>();
    try {
      for(String cadena : lista)
        nuevalista.agrega(Integer.parseInt(cadena));
    } catch (NumberFormatException e) {
        System.err.println("Uno de los valores ingresados no es un numero");
        System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("No has ingresado elementos");
    }
    return nuevalista;
  }

  // Construye una pila de Integer a partir de la lista generada en el Constructor 
  // @param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  // @return regresa una Pila de Integer
  // Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto
  
  private Pila<Integer> construirPila(){
    Pila<Integer> pila = new Pila<Integer>();
    try {
      for(String numero : lista)
        pila.mete(Integer.parseInt(numero));
    } catch (NumberFormatException e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("No has ingresado elementos");
    }
    return pila;
  }

  //Construye una Cola de Integer a partir de la lista generada en el Constructor
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor 
  //@return regresa una Cola de Integer 
  //Este metodo es el encargado de capturar los errores generador por archivos con formato incorrecto 
  
  private Cola<Integer> construirCola(){
    Cola<Integer> cola = new Cola<Integer>();
    try {
        while(!lista.esVacia())
          cola.mete(Integer.parseInt(lista.eliminaUltimo()));
    } catch (NumberFormatException e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch(Exception e){
      System.err.println("No has ingresado elementos");
    }
    return cola;
  }

  //Construye un arreglo de Integer a partir de la lista generada en el Constructor
  //@param  no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  //@return regresa un Arreglo de Integer
  //Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto 

  private Integer[] contruirArreglo(){
    if(lista.getElementos() == 0) System.err.println("No has ingresado elementos");
    Integer[] arreglo = new Integer[lista.getElementos()];
    try {
      for(int i = 0; i < arreglo.length; i++) 
        arreglo[i] = Integer.parseInt(lista.get(i));
    } catch (Exception e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    }
    return arreglo;
  }

  //Construye un ArbolBinarioCompleto de Integer a partir de la lista generada en el Constructor 
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  //@return regresa un ArbolBinarioCompleto de Integer 
  //Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto 

  private ArbolBinarioCompleto<Integer> construirArbolBinarioCompleto(){
    ArbolBinarioCompleto<Integer> arbolbinariocompleto = new ArbolBinarioCompleto<Integer>();
    try {
      for(String numero : lista)
        arbolbinariocompleto.agrega(Integer.parseInt(numero));
    } catch (NumberFormatException e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch(Exception e){
      System.err.println("No has ingresado elementos");
    }
    return arbolbinariocompleto;
  }

  //Construye un ArbolBinarioOrdenado de Integer a partir de la lista generada en el Constructor
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor 
  //@return regresa un ArbolBinarioOrdenado de Integer
  //Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto 
  
  private ArbolBinarioOrdenado<Integer> construirArbolBinarioOrdenado(){
    ArbolBinarioOrdenado<Integer> arbolbinarioordenado = new ArbolBinarioOrdenado<Integer>();
    try {
    for(String numero : lista)
      arbolbinarioordenado.agrega(Integer.parseInt(numero));
    } catch (NumberFormatException e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("No has ingresado elementos");
    }
    return arbolbinarioordenado;
  }
  
  //Construye un ArbolRojiNegro de Integer a partir de la lista generada en el Constructor
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  //@return regresa un ArbolRojiNegro de Integer 
  //Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto 

  private ArbolRojinegro<Integer> construirArbolRojiNegro(){
    ArbolRojinegro<Integer> arbolrojinegro = new ArbolRojinegro<Integer>();
    try {
      for(String numero : lista)
        arbolrojinegro.agrega(Integer.parseInt(numero));
    } catch (NumberFormatException e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("No has ingresado elementos");
    }
    return arbolrojinegro;
  }

  //Contruye un ArbolAVL de Integer a partir de la lista generada en el Constructor
  //@param no recibe parametros debido a que trabaja con la lista obtenida en el Constructor
  //@return regresa un ArbolAVL de Integer
  //Este metodo es el encargado de capturar los errores generados por archivos con formato incorrecto

  private ArbolAVL<Integer> contruirArbolAVL(){
    ArbolAVL<Integer> arbolAvl = new ArbolAVL<Integer>();
    try{
      for(String numero : lista)
        arbolAvl.agrega(Integer.parseInt(numero));
    } catch (NumberFormatException e){
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    } catch (Exception e){
      System.err.println("No has ingresado elementos");
    }
    return arbolAvl;
  }
  
  //Construye una Grafica de Integeer a partir de la lista generada en el Contructor 
  //@param no recibe parametros debido a que trabja con la lista obtenida en el Constructor
  //@throws GraficaInvalidaException si el numero de elementos ingresador no es par 
  //@return regresa una Grafica de Integer 
  //Este metodo es el encargado de generar la grafica, conectar los vertices y capturar los errores generados por archivos con formato incorrecto 

  private Grafica<Integer> construirGrafica() throws GraficaInvalidaException{
    Grafica<Integer> grafica = new Grafica<Integer>();
    if(lista.getElementos()%2!= 0) throw new GraficaInvalidaException("El numero de elementos ingresado no es par");
    boolean conectar = false;
    IteradorLista<String> it = lista.iteradorLista();
    try {
      Lista<VerticeGrafica<Integer>> desconectados = new Lista<VerticeGrafica<Integer>>();
      while(it.hasNext()){
        Integer numero = Integer.parseInt(it.next());
        if(!grafica.contiene(numero))
          grafica.agrega(numero);
        if(!conectar){
          conectar = true;
        } else {
          it.previous();
          it.previous();
          Integer primero = Integer.parseInt(it.next());
          Integer segundo = Integer.parseInt(it.next());
          if (primero.equals(segundo)){
            desconectados.agrega(grafica.vertice(primero));
            conectar = false;
          } else {
          grafica.conecta(primero, segundo);
          conectar = false;
          }
        }
      }
      for(VerticeGrafica<Integer> vertice : desconectados)
        for(VerticeGrafica<Integer> vecino : vertice.vecinos())
          grafica.desconecta(vertice.get(), vecino.get());
    } catch(IllegalArgumentException e){
      System.err.println("Los vertices ya se encuentran conectados o son el mismo");
    } catch (Exception e) {
      System.err.println("Uno de los valores ingresados no es un numero");
      System.err.println(e.getMessage());
    }
    return grafica;
  }
}
