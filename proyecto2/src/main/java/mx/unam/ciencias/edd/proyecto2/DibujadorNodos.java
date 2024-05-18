package mx.unam.ciencias.edd.proyecto2;

public abstract class DibujadorNodos extends Dibujador{

  // Dibuja un nodo en formato SVG 
  // @param Recibe dos posiciones en forma de entero y un Integer que representa el elemento a dibujar 
  // @return Regresa un Nodo en forma de String correspondiente al elemento recibido y en las posiciones
  // que se han recibido

  protected String dibujarNodo(int posX, int posY, Integer elemento){
    String nodo = "<rect x='" + posX + "' y='" + posY + "' width = '50' height='30' stroke='black' stroke-width='2' fill='white'/>";
    String elem = "<text fill='black' font-family='sans-serif' font-size=' "+ ((tamanoNumero(elemento) < 3) ? 20 : 17-tamanoNumero(elemento)) + "' x='" + (posX+25) + "' y='" + (posY+21) + "' text-anchor='middle' >" +  elemento + "</text>";
    return nodo + '\n' + elem + '\n';
  }

  // Dibuja una Flecha en forma de SVG
  // @param Recibe la posicion inicial y la posicion final que tendra la flecha 
  // @return Regresa un String en formato de SVG de una flecha apuntando a la derecha 

  protected String dibujarFlecha(int posX, int posY){
    return "<polyline fill='none' stroke='black' stroke-width='4' points='" + posX + "," + posY + " " + (posX+40) + "," + posY + " " + (posX+30) + "," + (posY-10) + " " + (posX+40) + "," + posY + " " + (posX+30) + "," + (posY+10) + "' />" + '\n';
  }

  // Dibuja una Flecha en forma de SVG
  // @param Recibe la posicion inicial y la posicion final que tendra la flecha 
  // @return Regresa un String en formato de SVG de una flecha apuntando a la izquierda
  
  protected String dibujarFlechaRegreso(int posX, int posY){
    return "<polyline fill='none' stroke='black' stroke-width='4' points='" + (posX+10) + "," + (posY+10) + " " + posX + "," + posY + " " + (posX+10) + "," + (posY-10) + " " + posX + "," + posY + " " + (posX+30) + "," + posY + "' />" + '\n';
  }

  // Dibuja una Flecha 
  // @param Recibe la posicion inicial y la posicion final que tendra la Flecha 
  // @return Regresa un String en formato de SVG de una flecha apuntando a la izquierda y a la derecha 

  protected String dibujarFlechaDoble(int posX, int posY){
    return dibujarFlecha(posX, posY) + dibujarFlechaRegreso(posX, posY);
  }

  // Regresa el numero de digitos que tiene un numero 
  // @param Recibe un numero a evaluar 
  // @return Regresa el numero de digitos que tiene el numero recibido

  protected int tamanoNumero(int numero){
    int res = 0;
    while((numero /= 10) != 0)
      res++;
    return res;
  }
} 
