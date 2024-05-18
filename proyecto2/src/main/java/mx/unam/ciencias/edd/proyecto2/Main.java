package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
import java.util.NoSuchElementException;

public class Main {
    // @param args recibe rutas de archivos para la creacion de Estructuras
    // metodo Main del proyecto
    public static void main(String[] args) {
    try{
      Cola<Lista<String>> colas = new Cola<Lista<String>>();
      if(args.length == 0){
        LectorEntradaEstandar lectorEstandar = new LectorEntradaEstandar();
        colas.mete(lectorEstandar.leer());
      }
      LectorArchivos lectorArchivos = new LectorArchivos();
      for(String ruta : args)
        colas.mete(lectorArchivos.leer(ruta));
      while(!colas.esVacia()){
        CreadorEstructuras constructor = new CreadorEstructuras(colas.saca());
        constructor.leerEntrada();
      }
    }catch(NoSuchElementException e){
      System.err.println("Debes ingresar elementos para que el programa funcione");
    }catch(Exception e){
      System.err.println(e.getMessage());
    } 
  }
}
