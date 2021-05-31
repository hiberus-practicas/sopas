package sopas;
import java.io.*;
import java.util.*;
/**
 * Programa que genera sopas de letras
 * @author vburon (Víctor Burón)
 */
public class SopaLetrasGenerator {
	static char[][] sopaLetras;
	static int tamaño;
	public static void main(String[] args) {
		// Inicio de la entrada, pide el tamaño de la sopa de letras, la cantidad de palabras diferentes, y cuales son esas palabras
		Scanner sc = new Scanner(System.in);
		String palabra;
		ArrayList<String> todasPalabras = new ArrayList<>();
		int cantidadPalabras, repeticiones;
		System.out.println("Introduce el tamaño de la sopa de letras que quieres (será un cuadrado)");
		tamaño = sc.nextInt();
		sopaLetras = new char[tamaño][tamaño];
		System.out.println("¿Cuantas palabras diferentes quieres añadir?");
		cantidadPalabras = sc.nextInt();
		sc.nextLine();
		HashMap<String, Integer> palabras = new HashMap<>();
		System.out.println("Introduce las palabras y cuantas veces quieres que se repitan");
		//Añadimos en un mapa la palabra (clave) y el numero de veces que se encuentra (valor)
		for (int i = 0; i < cantidadPalabras; i++) {
			palabra=sc.next();
			repeticiones = sc.nextInt();
			palabras.put(palabra, repeticiones);
			sc.nextLine();
		}
		System.out.println("Para cada palabra a añadir hay un maximo numero de intentos.\n"+
		"Si la sopa de letras es muy pequeña, es posible que algunas no se puedan añadir, o que se escriban sobre otras.");
		sc.close();
		// Sacamos un keySet para iterar todas las palabras
		Set<String> s= palabras.keySet();
		Iterator<String> it = s.iterator();
		String aux;
		int auxNum;
		// Por cada palabra, sacamos sus repeticiones, y añadimos esa palabra tantas veces como se pide en un array list
		while (it.hasNext()) {
			aux=it.next();
			auxNum = palabras.get(aux);
			for (int i = 0; i < auxNum; i++) {
				todasPalabras.add(aux);
			}
		}

		// Recorremos todo el ArrayList insertando las palabras en posiciones aleatorias
		for (int i = 0; i < todasPalabras.size(); i++) {
			añadirALaSopa(todasPalabras.get(i).toUpperCase());
		}
		
		//Rellenamos los espacios vacios con letras aleatorias
		for (char[] aux1: sopaLetras) {
			for (int i = 0; i < aux1.length; i++) {
				if (aux1[i]=='\u0000') {
					aux1[i]=letraAleatoria();
				}
			}
		}

		//Guardamos la sopa en un fichero de texto
		escribirSopaEnFichero(sopaLetras, palabras);	
	}

	/**
	 * Genera una letra aleatoria basandose en la generacion de sus codigos ASCII
	 * @return Un char que contiene una letra
	 */
	private static char letraAleatoria() {
		return (char)(int)(Math.random()*(25)+65);
	}
	
	/**
	 * Este metodo escoge en que direccion generar la palabra de forma aleatoria
	 * @param pal La palabra que sera añadida a la sopa de letras
	 */
	private static void añadirALaSopa(String pal) {
		int forma = (int)(Math.random()*8);
		switch (forma) {
			case 0:
				añadirVerticalReversa(pal);
				break;
			case 1:
				añadirVerticalNormal(pal);
				break;
			case 2:
				añadirHorizontalReversa(pal);
				break;
			case 3:
				añadirHorizontalNormal(pal);
				break;
			case 4:
				añadirDiagonalArribaIReversa(pal);
				break;
			case 5:
				añadirDiagonalArribaINormal(pal);
				break;
			case 6:
				añadirDiagonalArribaDReverse(pal);
				break;
			case 7:
				añadirDigaonalArribaDNormal(pal);
				break;
		}
	}
	
	/**
	 * Este metodo rellenará en una de las diagonales la palabra
	 * @param pal la palabra a añadir
	 */
	private static void añadirDigaonalArribaDNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			/*Si la palabra se sale de los limites de la sopa no es posible añadirla */
			if (x-pal.length()<0 || y+pal.length()>=tamaño) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y++;
					x--;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni--;
						yIni++;
					}
				}
			}
			count++; 
		} while (!posible && count<500);
	}

	/**
	 * Este metodo rellenará en una de las diagonales la palabra escrita del reves
	 * @param pal la palabra a añadir
	 */
	private static void añadirDiagonalArribaDReverse(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tamaño || y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y--;
					x++;
				}			
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni++;
						yIni--;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}
	
	/**
	 * Este metodo rellenará en una de las diagonales la palabra
	 * @param pal la palabra a añadir
	 */
	private static void añadirDiagonalArribaINormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tamaño || y+pal.length()>=tamaño) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y++;
					x++;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni++;
						yIni++;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}
	
	/**
	 * Este metodo rellenará en una de las diagonales la palabra del reves
	 * @param pal la palabra a añadir
	 */
	private static void añadirDiagonalArribaIReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {		
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x-pal.length()<0 || y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y--;
					x--;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni--;
						yIni--;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}
	
	/**
	 * Este metodo rellenará la palabra horizontalmente
	 * @param pal la palabra a añadir
	 */
	private static void añadirHorizontalNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tamaño) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					x++;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni++;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}
	
	/**
	 * Este metodo rellenará la palabra horizontalmente y del reves
	 * @param pal la palabra a añadir
	 */
	private static void añadirHorizontalReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {		
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					x--;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						xIni--;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}
	
	/**
	 * Este metodo rellenará la palabra en vertical
	 * @param pal la palabra a añadir
	 */
	private static void añadirVerticalNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (y+pal.length()>=tamaño) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y++;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						yIni++;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}

	
	/**
	 * Este metodo rellenará la aplabra en vertical, y del reves
	 * @param pal la palabra a añadir
	 */
	private static void añadirVerticalReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara añadirla porque no entra
		if (pal.length()>tamaño) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentará añadir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiará 
			 * al ver si es posible añadirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tamaño) , x = (int)(Math.random()*tamaño), palL=pal.length();
			int xIni = x, yIni=y; 
			if (y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra será posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y--;
				}
				/*Si es posible, basandonos en el punto inicial añadimos la palabra */
				if (posible) {
					for (int i = 0; i < palL; i++) {
						sopaLetras[yIni][xIni]=pal.charAt(i);
						yIni--;
					}
				}
			}
			count++;
		} while (!posible && count<500);
	}		
	
	private static void escribirSopaEnFichero(char[][] sopa, HashMap<String, Integer> palabras) {
		File f = new File("sopaLetras.txt");
		/*Si el fichero no existe lo creamos, y en cada linea escribimos cada linea de la sopa de letras.
		 *Al principio escribimos el tamaño, y al final la lista de las palabras a buscar*/
		try {
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(tamaño+" "+tamaño);
			for (char[] aux : sopa) {
				bw.newLine();
				bw.write(aux);				
			}
			bw.newLine();
			bw.write(palabras.size()+"");
			Set<String> s = palabras.keySet();
			for (String string : s) {
				bw.newLine();
				bw.write(string.toUpperCase());
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Error al tratar el archivo de guardado");
		}	
	}
}