package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;

public class DibujadorListas extends DibujadorNodos {
  private Lista<Integer> lista;

  // Constructor de la clase 
  // @param Recibe como parametro una lista de Integer, asigna tamaños y lista

  public DibujadorListas(Lista<Integer> lista){
    tamanoX = lista.getLongitud()*100 + 40;
    tamanoY = 90;
    this.lista = lista;
  }

  // Dibuja la estructura de Datos como un SVG
  // @param No recibe parametros, usa la lista en el atributo
  // @return Regresa un String del contenido del SVG

  public String dibuja(){
    int posX = 30, ultimo = 0;
    String imagen = "";
    for(Integer numero : lista){
      if(lista.getLongitud()-1 == ultimo)
        imagen += dibujarNodo(posX, 30, numero);
      else
        imagen += dibujarNodo(posX, 30, numero) + dibujarFlechaDoble(posX+55,45);
      posX += 100;
      ultimo++;
    }
    return abrirDibujo() + imagen + cerrarDibujo();
  }
}
