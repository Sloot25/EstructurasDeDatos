package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
/**
 * <p> Clase para Leer los archivos Ingresador por el usuario como parametros por linea de comandos </p> 
 *
 *
 * **/
public class LectorArchivos {
  public LectorArchivos(){
  }

  /**
   * Recibe las rutas de los archivos y una lista con el Texto obtenido hasta el momento
   *
   * @return La lista con las lineas totales del texto a ordenar 
   * **/
  public Lista<String> leer(Lista<String> rutas, Lista<String> texto){
    for(String ruta : rutas){
      try {
        leer(ruta, texto);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return texto;
  }
  /** Recibe la ruta de un archivo de texto y la lista de lineas totales de texto, al terminar el metodo, todas las lineas dell archivo en la ruta deben estar en la lista
   *
   * **/
  private void leer(String ruta, Lista<String> texto) throws IOException{
    FileReader archivo = new FileReader(ruta);
    BufferedReader lector = new BufferedReader(archivo);
    String cadena;
    while((cadena = lector.readLine()) != null)
      texto.agrega(cadena);
  }
  /**
   * Escribe el String texto en un archivo con la rutaDeLlegada recibida, si no hay archivo, creara uno, si hay un archivo, lo sobreescribira
   * **/

  public void escribir(String texto, String rutaDeLlegada){
    try {
      FileWriter archivoWrite = new FileWriter(rutaDeLlegada);
      PrintWriter escritor = new PrintWriter(archivoWrite);
      escritor.write(texto);
      archivoWrite.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  

}
