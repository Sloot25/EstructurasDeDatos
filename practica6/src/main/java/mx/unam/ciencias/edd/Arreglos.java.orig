package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
      if(arreglo.length <= 1) return;
      int i = 1;
      int j = arreglo.length-1;
      while(i < j){
        if(comparador.compare(arreglo[0], arreglo[i]) < 0 && comparador.compare(arreglo[j], arreglo[0]) <= 0){
        intercambia(arreglo, i, j);
        i++;
        j--;
        } else if(comparador.compare(arreglo[i], arreglo[0]) <= 0){
          i++;
        } else{
          j--;
        }
      }
      if(comparador.compare(arreglo[i], arreglo[0]) > 0) i--;
      intercambia(arreglo,0,i);
      quickSort(arreglo, 0, i-1, comparador);
      quickSort(arreglo, i+1, arreglo.length - 1, comparador);
    }
    private static <T> void quickSort(T[] arreglo, int a, int b,Comparator<T> comparador ){
    if(b <= a) return;
    int i = a+1;
    int j = b;
    while(i < j){
      if(comparador.compare(arreglo[a], arreglo[i]) < 0 && comparador.compare(arreglo[j], arreglo[a]) <= 0){
        intercambia(arreglo, i, j);
        i++;
        j--;
      } else if(comparador.compare(arreglo[i], arreglo[a]) <= 0){
        i++;
      } else{
        j--;
      }
    }
    if(comparador.compare(arreglo[i], arreglo[a]) > 0) i--;
    intercambia(arreglo, a, i);
    quickSort(arreglo, a, i-1, comparador);
    quickSort(arreglo, i+1, b, comparador);
  } 

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
      for(int i = 0; i < arreglo.length; i++){
        for(int j = i+1; j < arreglo.length; j++){
          if(comparador.compare(arreglo[j], arreglo[i]) < 0) intercambia (arreglo, j, i);
        }
      }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    private static <T> void intercambia(T[] arr, int a, int b){
      T aux = arr[a];
      arr[a] = arr[b];
      arr[b] = aux;
    }
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
       return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length-1);
    }
    private static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador, int a, int b){
    if(b < a) return -1;
    int m = a + ((int)((b-a)/2));
    //if(m >= arreglo.length) return -1;
    if(arreglo[m].equals(elemento)) return m;
    else if(comparador.compare(arreglo[m], elemento) < 0) return busquedaBinaria(arreglo, elemento , comparador, m+1, b);
    else return busquedaBinaria(arreglo, elemento, comparador, a, m-1);
  }
    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
