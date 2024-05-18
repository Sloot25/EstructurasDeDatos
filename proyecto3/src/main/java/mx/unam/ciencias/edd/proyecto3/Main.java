package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

public class Main {
  /* Main del proyecto
   * Si no recibe parametros, leera un archivo por la entrada estandar y regresara un laberinto resuelto en forma de SVG
   * Si recibe parametros, imprime bytes que pueden ser almacenados en un archivo mze, corresponden a un laberinto
   * */
  public static void main(String[] args) {
    try {
      if(args.length == 0){
        Lector lector = new Lector();
        Integer[][] laberinto = lector.leer();
        GeneradorGraficas grafica = new GeneradorGraficas(laberinto);
        grafica.generarVertices();
        grafica.generarPuertas();
        Lista<VerticeGrafica<Integer>> solucion = grafica.recorrido();
        DibujadorLaberinto dibujador = new DibujadorLaberinto(laberinto);
        System.out.println(dibujador.dibujaLaberintoSolucion(solucion));
      } else{
        boolean gen = false;
        int sem = -1;
        int tamx = -1;
        int tamy = -1;
        for(int i = 0; i < args.length; i++)
          if(args[i].equals("-g"))
            gen = true;
          else if(args[i].equals("-s"))
            if(i+1 >= args.length)
              uso();
            else
              sem = Integer.parseInt(args[++i]);
          else if(args[i].equals("-w"))
            if(i + 1 >= args.length)
              uso();
            else 
              tamx = Integer.parseInt(args[++i]);
          else if(args[i].equals("-h"))
            if(i +1 >= args.length)
              uso();
            else 
              tamy = Integer.parseInt(args[++i]);
        if(!gen || tamx == -1 || tamy == -1)
          uso();
        if(tamx < 2 ){
          System.err.println("El numero de columnas del laberinto tiene que ser mayor o igual a 2");
          System.exit(1);
        }
        if(tamy < 2){
          System.err.println("El numero de renglones del laberinto tiene que ser mayor o igual a 2");
          System.exit(1);
        }
        GeneradorLaberintos generador = (sem != -1) ? new GeneradorLaberintos(tamy, tamx, sem) :  new GeneradorLaberintos(tamy, tamx);
        generador.generarLaberinto();
        generador.imprimirMaze();
      }
    } catch(NumberFormatException e){
      uso();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

  }

  /* @param No recibe parametros 
   * @return No regresa elementos 
   * Imprime en consola el formato de uso del programa asi como una salida del mismo*/
  private static void uso(){
      System.err.println("Uso: java -jar proyecto3.jar -g -s N -h N -w N \nUso Alternativo: proyecto3.jar -g -h N -w N");
      System.exit(1);
  } 
}
