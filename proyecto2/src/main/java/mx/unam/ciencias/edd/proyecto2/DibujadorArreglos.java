package mx.unam.ciencias.edd.proyecto2;
public class DibujadorArreglos extends DibujadorNodos{
  private Integer arreglo[];

  // Constructor de la clase 
  // @param recibe un Arreglo de Enteros, asigna los tamaños del archivo y el arreglo con el cual 
  // se trabajara 
  
  public DibujadorArreglos(Integer arreglo[]){
    tamanoX = arreglo.length * 50 + 80;
    tamanoY = 90;
    this.arreglo = arreglo;
  }

  // dibuja la estructura de datos 
  // @param no recibe parametros debido a que trabaja sobre el Arreglo recibido en el constructor 
  // @ return regresa un String en formato SVG de nuestra estructura

  @Override
  public String dibuja(){
    int posX = 40;
    String imagen = "";
    for(int i = 0;i < arreglo.length; i++){
      imagen += dibujarNodo(posX, 30, arreglo[i]) + dibujarIndice(posX, 60, i);
      posX += 50;
    }
    return abrirDibujo() + imagen + cerrarDibujo();
  }
  
  // Genera un String en formato SVG del indice de un elemento del arreglo 
  // @param recibe 3 enteros, las posiciones  de nuestro indice y el indice del elemento 
  // @return regresa un String en formato SVG del indice del elemento del arreglo 

  private String dibujarIndice(int posX, int posY, int numero){
    return "<text fill='black' font-family='sans-serif' font-size='15' stroke-width='1.5' x='" + (posX+25) + "' y='" + (posY-35) + "' text-anchor='middle' >" + numero + "</text>" + '\n';
  }
}
