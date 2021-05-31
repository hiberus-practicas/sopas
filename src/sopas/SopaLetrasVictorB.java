package sopas;
import java.util.Scanner;
/**
 * Programa que te resuelve sopas de letras, diciendo cuantas veces se encuentra una palabra en ella
 * @author vburon (Víctor Burón)
 */
public class SopaLetrasVictorB {
	static char[][] rectangulo;
	static int contadorPalabra;
	static int longitudPal;
	public static void main(String[] args) {
		Scanner sc = new Scanner(""/*Añadir los datos aquí*/);
		rectangulo = new char[sc.nextInt()][sc.nextInt()];
		String letras;
		char palabras[][];
		//Rellenamos la sopa de letras
		sc.nextLine();
		for (int i = 0; i < rectangulo.length; i++) {
			letras=sc.nextLine();
			rectangulo[i]= letras.toCharArray();
		}
		// Pedimos la cantidad de palabras a comprobar y las vamos pidiendo y comprobando
		palabras = new char[sc.nextInt()][];
		
		sc.nextLine();
		for (int i = 0; i < palabras.length; i++) {
			palabras[i] = sc.next().toCharArray();	
		}
		
		long inicio = System.currentTimeMillis();
		for (char[] aux : palabras) {
			comprobarPalabra(aux);
		}
		long fin = System.currentTimeMillis();
		
		sc.close();
		System.out.println((fin - inicio)+"ms");
	}
	
	//Por cada palabra, revisamos en cada posicion inicial si se encuentra la palabra en todas las direcciones
	//Antes que nada revisamos si la primera letra coincide, si no lo hace, no intentaremos las comprobaciones de direcciones
	private static void comprobarPalabra(char[] aux) {
		contadorPalabra = 0;
		longitudPal=aux.length;
			for (int i = 0; i < rectangulo.length; i++) {
				for (int j = 0; j < rectangulo[i].length; j++) {
					if (aux[0]==rectangulo[i][j]) {
						comprobarVerticalArriba(i, j, aux);
						comprobarVerticalAbajo(i, j, aux);
						comprobarHorizontalDerecha(i, j, aux);
						comprobarHorizontalIzquierda(i, j, aux);
						comprobarDiagonalArribaIAbajoD(i, j, aux);		
						comprobarDiagonalArribaDAbajoI(i, j, aux);
						comprobarDiagonalAbajoIArribaD(i, j, aux);
						comprobarDiagonalAbajoDArribaI(i, j, aux);
					}
				}
			}	
		System.out.println(String.valueOf(aux) +"="+contadorPalabra);
	}
	//Comprobamos la vertical yendo hacia arriba (primer if evita OutOfBounds)
	private static void comprobarVerticalArriba(int x, int y, char[] aux) {
		if (y-longitudPal<-1) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		//Dado que un String esta formado por un array de char, haremos la combrobacion aqui,
		//ya que haciendolo al final, hay algunas condiciones adicionales.
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y--;
			} else {
				return;
			}
		}
		contadorPalabra++;
	}
	
	//Comprobamos la vertical yendo hacia abajo (primer if evita OutOfBounds)
	private static void comprobarVerticalAbajo(int x, int y, char[] aux) {
		if (y+longitudPal>=rectangulo[0].length) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y++;
			} else {
				return;
			}
		}
		contadorPalabra++;
	}
	
	//Comprobamos la horizontal yendo hacia izquierda (primer if evita OutOfBounds)
	private static void comprobarHorizontalIzquierda(int x, int y, char[] aux) {
		if (x-longitudPal<-1){
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				x--;
			} else {
				return;
			}
		}
		contadorPalabra++;
	}
	
	//Comprobamos la horizontal yendo hacia derecha (primer if evita OutOfBounds)
	private static void comprobarHorizontalDerecha(int x, int y, char[] aux) {
		if (x+longitudPal>=rectangulo.length) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				x++;
			} else {
				return;
			}
		}
		contadorPalabra++;
	}
	
	// Comprobamos una de las cuantro diagonales, evitando OutOfBounds con el primer if
	private static void comprobarDiagonalArribaIAbajoD(int x, int y, char[] aux) {
		if (y+longitudPal>rectangulo[0].length || x+longitudPal>rectangulo.length) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y++;
				x++;
			} else {
				return;
			}
		}	
		contadorPalabra++;
	}
	
	// Comprobamos una de las cuantro diagonales, evitando OutOfBounds con el primer if
	private static void comprobarDiagonalArribaDAbajoI(int x, int y, char[] aux) {
		if (y+longitudPal>rectangulo[0].length || x-longitudPal<-1) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y++;
				x--;
			} else {
				return;
			}
		}	
		contadorPalabra++;
	}
	
	// Comprobamos una de las cuantro diagonales, evitando OutOfBounds con el primer if
	private static void comprobarDiagonalAbajoIArribaD(int x, int y, char[] aux) {
		if (y-longitudPal<-1 || x+longitudPal>rectangulo.length) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y--;
				x++;
			} else {
				return;
			}
		}	
		contadorPalabra++;
	}
	// Comprobamos una de las cuantro diagonales, evitando OutOfBounds con el primer if
	private static void comprobarDiagonalAbajoDArribaI(int x, int y, char[] aux) {
		if (y-longitudPal<-1 || x-longitudPal<-1) {
			return;
		}
		//Comprueba que los caracteres vayan coincidiendo, cuando no lo haga sale del metodo sin sumar
		for (char c : aux) {
			if (c==rectangulo[x][y]) {
				y--;
				x--;
			} else {
				return;
			}
		}	
		contadorPalabra++;
	}
}