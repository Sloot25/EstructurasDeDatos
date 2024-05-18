package mx.unam.ciencias.edd.proyecto2;

public class GraficaInvalidaException extends Exception{
  public GraficaInvalidaException(String mensaje){
    super(mensaje);
  } 
  public String getMessage(){
    return "La grafica ingresada es invalida";
  }
}
