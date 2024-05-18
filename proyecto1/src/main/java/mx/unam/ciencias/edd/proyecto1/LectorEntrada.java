package mx.unam.ciencias.edd.proyecto1;
import mx.unam.ciencias.edd.Lista;
public class LectorEntrada {
  private boolean reversa, escribe;
  private String rutaEscritura;
  private LectorArchivos lector;
  private Lista<String> rutas;
  // Constructor Vacio
  public LectorEntrada(){}
  /**
   * Metodo para leer la entrada por parametros en consola del usuario, distingue entre rutas de archivos y ordenes para el programa 
   *
   * Recibe los args recibidos en consola, y una lista con los String totales de los textos
   *
   * Regresa una lista con las lineas de cada texto en las rutas pasadas en los args
   * **/
  public Lista<String> leer(String[] args, Lista<String> texto) throws NoHayRutaEscrituraException{
    rutas = new Lista<String>();
    lector = new LectorArchivos();
    for(int i = 0; i < args.length; i++){
      if(args[i].equals("-r")){
        reversa = true;
      } else if(args[i].equals("-o")){
        escribe = true;
        if(i+1 >= args.length) throw new NoHayRutaEscrituraException();
        rutaEscritura = args[++i];
      } else{
        rutas.agrega(args[i]);
      }
    }
    return lector.leer(rutas, texto);
  }

  /**Recibe una lista con las cadenas de texto a ordenar e imprime en orden las cadenas de texto en la terminal 
   *
   * Si reversa es true, entonces seran impresas en reversa
   *
   * Si escribe es true, sobre escribe el texto en la ruta proporcionada
   * **/
  public void ordenaTextos(Lista<String> lista){
    String texto = "";
    Ordenador orden = new Ordenador(lista);
    orden.ordenar();
    if(reversa)
      texto = orden.imprimirReversa();
    else
      texto = orden.imprimir();
    System.out.print(texto);
    if(escribe) lector.escribir(texto, rutaEscritura);
  }
}
