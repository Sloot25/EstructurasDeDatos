package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.Grafica;
import java.util.NoSuchElementException;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Lector {
  int tamX, tamY;
  /* Constructor de la clase */
  public Lector(){}

  /* @param No recibe parametros 
   * @return Regresa un arreglo de enteros con los datos recibidos en la entrada estandar 
   * revisando si lo recibido concuerda con formato
   * */
  public Integer[][] leer() throws ArchivoInvalidoException, IOException{
    int bite, contador = 0;
    int posX = 0; 
    int posY = 0;
    BufferedInputStream lector = new BufferedInputStream(System.in);
    bite = lector.read();
    while(bite != -1 && contador < 6){
      switch(contador++){
        case 0: 
          if((bite & 0xFF) != 0x4d)
            throw new ArchivoInvalidoException("El archivo de lectura es Invalido");
        break;
        case 1:
          if((bite & 0xFF) != 0x41)
            throw new ArchivoInvalidoException("El archivo de lectura es Invalido");
          break;
        case 2:
          if((bite & 0xFF) != 0x5a)
            throw new ArchivoInvalidoException("El archivo de lectura es Invalido");
        break;
        case 3:
          if((bite & 0xFF) != 0x45)
            throw new ArchivoInvalidoException("El archivo de lectura es Invdalido");
          break;
        case 4: 
          if((bite & 0xFF) < 2)
            throw new ArchivoInvalidoException("El numero de renglones del laberinto tiene que ser mayor o igual a 2");
          tamY = bite;
          break;
        case 5: 
          if((bite & 0xFF)<2)
            throw new ArchivoInvalidoException("El numero de columnas del laberinto tiene que ser mayor o igual a 2");
          tamX = (bite & 0xFF);
          break;
      }
      if(contador != 6)
        bite = lector.read();
      else 
        bite = -1;
      
    } 
    Integer[][] laberinto = new Integer[tamY][tamX];
    while((bite = lector.read()) != -1){
      try{
        laberinto[posY][posX++] = (bite & 0xFF);
        if(posX >= laberinto[posY].length){
          posX = 0;
          posY++;
        }
      }catch(Exception e){
        System.err.println(e.getMessage());
      }
    }
    return laberinto;
  }

}
