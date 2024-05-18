package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class LectorEntradaEstandar {
  private Lista<String> lista;

  // Constructor de la clase 
  // @param No recibe parametros y solamente inicializa un atributo 

  public LectorEntradaEstandar(){
    lista = new Lista<String>();
  }

  // Lector de la entrada estandar 
  // @param No recibe parametros 
  // @return Regresa una lista de String siguiendo el formato requerido 

  public Lista<String> leer() throws IOException{
    BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
    String cadena;
    while((cadena = lector.readLine()) != null )
      agrega(cadena);
    lector.close();
    return limpiar();
  }

  // Agrega una cadena a nuestra lista 
  // @param Recibe una cadena a evaluar 
  // @return no tiene Regreso, el metodo agrega a la lista la cadena siguiendo 
  // siguiendo las especificaciones de formato 

  private void agrega(String cadena){
    if(cadena.contains("#"))
      lista.agrega(cadena.substring(0, cadena.indexOf("#")).trim());
    else 
      lista.agrega(cadena);
  }

  // crea una nueva lista de acuerdo a nuestro formato 
  // @param No recibe parametros, ya que trabaja con la lista en el atributo 
  // @return Regresa una nueva lista siguiendo las especificacionesde formato
  
  private Lista<String> limpiar(){
    Lista<String> resultado = new Lista<String>();
    for(String cadena : lista){
      String[] caracteres = cadena.split(" ");
      for(String caracter : caracteres){
        if(!caracter.equals(""))
          resultado.agrega(caracter);
      }
    }
    return resultado;
  }
  
}
