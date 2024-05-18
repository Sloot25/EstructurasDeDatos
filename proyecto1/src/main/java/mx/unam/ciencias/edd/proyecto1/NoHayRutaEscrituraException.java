package mx.unam.ciencias.edd.proyecto1;
public class NoHayRutaEscrituraException extends Exception{
  public NoHayRutaEscrituraException(){
    super();
  }
  public String toString(){
    return "No quedan elementos en tu lista";
  }
}
