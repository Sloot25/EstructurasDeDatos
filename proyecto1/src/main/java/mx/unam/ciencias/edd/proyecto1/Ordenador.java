package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
import java.text.Normalizer;
import mx.unam.ciencias.edd.IteradorLista;
public class Ordenador {
  private Lista<String> lista;
  // Constructor vacio
  private Ordenador(){}
  // Constructor con parametro Lista, asigna el atributo como el parametro
  public Ordenador(Lista<String> lista){
    this.lista = lista;
  }
  /**
   * Ordena la lista usando el algoritmo mergeSort, se usa el metodo que recibe una lambda, para especificar 
   * que las cadenas tienen que ser limpiadas antes de compararlas
   *
   *
   * **/
  public void ordenar(){
    lista = lista.mergeSort((a,b) -> limpiarLineas(a).compareTo(limpiarLineas(b)));
  }
  
  /**
   * Recibe una cadena de texto y regresa la misma cadena una vez aplicado un estandar
   * El estandar en este caso es eliminar los espacios en blanco, eliminar todo caracter que difiera de 
   * A-Z, a-z y los digitos, al igual que hace todos los caracteres minuscula
   *
   * @Return regresa la misma cadena una vez estandarizada
   * **/

  private String limpiarLineas(String linea){
    linea = Normalizer.normalize(linea, Normalizer.Form.NFD);
    linea = linea.replaceAll("[^\\dA-Za-z]", "");
    return linea.trim().toLowerCase();
  } 

    /**
     * Almacena las cadenas de la lista en un String, empezando por el ultimo elemento y terminando en el primer elemento
     * @Return String formado de la concatenacion de cada String en lista, seguido de un salto de linea
     * **/
  public String imprimirReversa(){
    IteradorLista<String> it = lista.iteradorLista();
    it.end();
    String res = "";
    while(it.hasPrevious())
      res += it.previous() + '\n';
    
    return res;
  }
  /**
   * Almacena las cadenas de la lista en un String, siguiendo empezando por el primer elemento y terminando en el ultimo elemento 
   *
   * @Return String formado de la concatenacion de cada String en lista, seguido de un salgo de linea
   *
   * **/
  public String imprimir(){
    String res = "";
    for(String cadena: lista) res += cadena + '\n';
    return res;
  }

}
