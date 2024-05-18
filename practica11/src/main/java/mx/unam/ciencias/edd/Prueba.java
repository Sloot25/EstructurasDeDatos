package mx.unam.ciencias.edd;
public class Prueba {
  public static void main(String[] args) {
    Conjunto<Integer> conjunto = new Conjunto<Integer>(58);
    String respuesta = "{ ";
    for(int i = 55; i < 61; i++ ){
      respuesta += i + ", ";
      conjunto.agrega(i);
    }
    conjunto.agrega(100);
  
    System.out.println("Conjunto: " + conjunto.toString());
    System.out.println("String test: " + respuesta + "}");

  }
}
