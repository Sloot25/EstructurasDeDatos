package mx.unam.ciencias.edd.proyecto2;

public abstract class Dibujador {
 protected int tamanoX, tamanoY;
  
  //@param No recibe parametros 
  //@return Regresa el String correspondiente a inicializar un archivo SVG con tamaño asignado 
  
  public String abrirDibujo(){
    return "<?xml version='1.0' encoding='UTF-8' ?>" + '\n' +"<svg width='" + tamanoX + "' height='" + tamanoY + "' >" + '\n' + "<g>";
  }
   //@param No recibe parametros 
  //@return Regresa el String correspondiente a finalziar un archivo SVG 
  public String cerrarDibujo(){
    return "</g>" +'\n' + "</svg>" + '\n';
  }
  public abstract String dibuja();
}
