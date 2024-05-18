package mx.unam.ciencias.edd;
import java.util.Random;
public class Prueba {
  public static void main(String[] args) {
    Random rnd = new Random();
    Lista<ValorIndexable<String>> lista = new Lista<ValorIndexable<String>>();
    for(int i = 0; i < 5; i++) lista.agrega(new ValorIndexable<String>(Integer.toString(rnd.nextInt(10)), rnd.nextDouble()));
    MonticuloMinimo<ValorIndexable<String>> monticulo = new MonticuloMinimo<ValorIndexable<String>> (lista);
    for(int i = 0; i < 5; i++){
      System.out.println(monticulo.get(i));
      System.out.println(monticulo.get(i).getIndice());
    }
    monticulo.agrega(new ValorIndexable<String>(Integer.toString(rnd.nextInt(10)), rnd.nextDouble()));
    monticulo.haciaArriba(5);
    for(int i = 0; i < 6; i++){
      System.out.println(monticulo.get(i));
      System.out.println(monticulo.get(i).getIndice());
    }
  }
}
