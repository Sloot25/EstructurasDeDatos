package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
public class LectorArchivos {
  private Lista<String> lista;

  // Constructor de la clase 
  // @param No recibe parametros y solamente inicializa un atributo 

  public LectorArchivos(){
    lista = new Lista<String>();
  }

  // Lector de archivos para devolverlo en el formato requerido 
  // @param Recibe un String correspondiente a la ruta del archivo 
  // @return Regresa una Lista de String con el formato requerido

  public Lista<String> leer(String ruta){
    try {
      FileReader archivo = new FileReader(ruta);
      BufferedReader lector = new BufferedReader(archivo);
      String cadena;
      while((cadena = lector.readLine()) != null)
        agrega(cadena);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return limpia();
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
  
  private Lista<String> limpia(){
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
