package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        int resultado = 0,i = 0;
        while(llave.length -  i >= 4)
          resultado ^= bigEndian(llave[i++], llave[i++], llave[i++], llave[i++]);
        return resultado ^= posSwitchB(llave, i);
    } 
    // regresa un byte recorrido cierta posicion 
    private static int posicion (byte a, int n){
      return (a & 0xFF) << n*8;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
      int i = 0;
      int[] abc = new int[3];
      abc[0] = 0x9E3779B9; 
      abc[1] = abc[0];
      abc[2] = 0xFFFFFFFF; 
      while( i  < llave.length){
        abc[0] += (llave.length - i < 4) ? posSwitchL(llave, i+=4) : littleEndian(llave[i++], llave[i++], llave[i++], llave[i++]);
        abc[1] += (llave.length - i < 4) ? posSwitchL(llave, i+=4) : littleEndian(llave[i++], llave[i++], llave[i++], llave[i++]);
        if(llave.length - i >= 4){
          abc[2] += littleEndian(llave[i++], llave[i++], llave[i++], llave[i++]);
          if(llave.length <= i){
            mezcla(abc);
            abc[2] += llave.length;
          }
        } else {
          abc[2] += combinadorMod(llave, i+=4);
          abc[2]+=llave.length;
        }
        mezcla(abc);
      }
      return abc[2]; 
    }
    // Evalua cuantos 0 hay en el resto de nuestro arreglo y regresa un int usando bigEndian
    private static int posSwitchB(byte[] llave, int l){
      int t = 0;
      switch (llave.length - l) {
        case 3:
          t |= (llave[l+2] & 0xFF) << 8;
        case 2: 
          t |= (llave[l+1] & 0xFF) << 16;
        case 1: 
          t |= (llave[l] & 0xFF) << 24;
      }
      return t;
    }
    // Evalua cuantos 0 hay en el resto de nuestro arreglo y regresa un int usando bigEndian
    private static int posSwitchL(byte[] llave, int l){
      int t = 0;
      l-=4;
      switch (llave.length - l) {
        case 3:
          t |= (llave[l+2] & 0xFF) << 16;
        case 2:
          t |= (llave[l+1] & 0xFF) << 8;
        case 1: 
          t |= (llave[l] & 0xFF);
      }
      return t;
    }
    // Evalua cuandos 0 quedan en el resto del arreglo y regresa un int usando el formato
    private static int combinadorMod(byte[] llave, int l){
      l -= 4;
      int t = 0;
      switch (llave.length - l) {
        case 3:
          t |= (llave[l+2] & 0xFF) << 24;
        case 2: 
          t |= (llave[l+1] & 0xFF) << 16;
        case 1: 
          t |= (llave[l] & 0xFF) << 8;
      }
      return t;
    }
    // Une los bytes segun littleEndian
    private static int littleEndian(byte a, byte b, byte c, byte d){
      return posicion(a,0) | posicion(b,1) | posicion (c,2) | posicion(d,3);
    }
    // Une los bytes segun el bigEndian
    private static int bigEndian(byte a, byte b, byte c, byte d){
      return posicion(a,3) | posicion(b,2) | posicion(c,1) | posicion(d,0);
    }
    // Mezcla los bytes a partir de las referencias y los 3 casos ocupados
    private static void mezcla(int[] abc){
      mezcla(abc,13,8,13);
      mezcla(abc,12,16,5);
      mezcla(abc,3,10,15);
    }
    // Mezcla los bytes a partir de las referencias
    private static void mezcla(int[] abc, int m, int n, int p){
      abc[0] -= abc[1];
      abc[0] -= abc[2];
      abc[0] ^= (abc[2]>>>m);
      abc[1] -= abc[2];
      abc[1] -= abc[0];
      abc[1] ^= abc[0]<<n;
      abc[2] -= abc[0];
      abc[2] -= abc[1];
      abc[2] ^= abc[1]>>>p;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
      int h = 5381;
      for(int i = 0; i < llave.length; i++){
        h += (h<<5) + (llave[i] & 0xFF);
      }
      return h;
    }
}
