package mx.unam.ciencias.edd.proyecto3;
public class ArchivoInvalidoException extends Exception{
  /* Constructor de la clase
   * @param Recibe un String correspondiente al mensaje
   * */
  public ArchivoInvalidoException(String mensaje){
    super(mensaje);
  }
}
