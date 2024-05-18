package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class LectorEntradaEstandar {
  Lista<String> lista = new Lista<String>();
  // Constructor vacio
  public LectorEntradaEstandar(){}
  /**
   * Lee las cadenas ingresadas por la entrada estandar de la terminal
   *
   * @Return regresa una lista con todas las cadenas a ordenar
   *
   * **/
  public Lista<String> leer() throws IOException{
    BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
    String cadena;
    while((cadena = lector.readLine() )!= null) lista.agrega(cadena);
    return lista;
  }

}
