package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main {
  /** 
   * Imprime un mensaje si no has ingresado ningun archivo
   * **/
  private static void uso(){
    System.err.println("Ingresa un archivo a ordenar");
    System.exit(1);
  }
  public static void main(String[] args) {
    try {
      LectorEntradaEstandar lectorEstandar = new LectorEntradaEstandar();
      Lista<String> texto = lectorEstandar.leer();
      LectorEntrada lector = new LectorEntrada();
      texto = lector.leer(args, texto);
      if(texto.getLongitud() == 0) uso();
      lector.ordenaTextos(texto);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
