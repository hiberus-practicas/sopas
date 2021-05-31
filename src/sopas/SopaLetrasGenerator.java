package sopas;
import java.io.*;
import java.util.*;
/**
 * Programa que genera sopas de letras
 * @author vburon (V�ctor Bur�n)
 */
public class SopaLetrasGenerator {
	static char[][] sopaLetras;
	static int tama�o;
	public static void main(String[] args) {
		// Inicio de la entrada, pide el tama�o de la sopa de letras, la cantidad de palabras diferentes, y cuales son esas palabras
		Scanner sc = new Scanner(System.in);
		String palabra;
		ArrayList<String> todasPalabras = new ArrayList<>();
		int cantidadPalabras, repeticiones;
		System.out.println("Introduce el tama�o de la sopa de letras que quieres (ser� un cuadrado)");
		tama�o = sc.nextInt();
		sopaLetras = new char[tama�o][tama�o];
		System.out.println("�Cuantas palabras diferentes quieres a�adir?");
		cantidadPalabras = sc.nextInt();
		sc.nextLine();
		HashMap<String, Integer> palabras = new HashMap<>();
		System.out.println("Introduce las palabras y cuantas veces quieres que se repitan");
		//A�adimos en un mapa la palabra (clave) y el numero de veces que se encuentra (valor)
		for (int i = 0; i < cantidadPalabras; i++) {
			palabra=sc.next();
			repeticiones = sc.nextInt();
			palabras.put(palabra, repeticiones);
			sc.nextLine();
		}
		System.out.println("Para cada palabra a a�adir hay un maximo numero de intentos.\n"+
		"Si la sopa de letras es muy peque�a, es posible que algunas no se puedan a�adir, o que se escriban sobre otras.");
		sc.close();
		// Sacamos un keySet para iterar todas las palabras
		Set<String> s= palabras.keySet();
		Iterator<String> it = s.iterator();
		String aux;
		int auxNum;
		// Por cada palabra, sacamos sus repeticiones, y a�adimos esa palabra tantas veces como se pide en un array list
		while (it.hasNext()) {
			aux=it.next();
			auxNum = palabras.get(aux);
			for (int i = 0; i < auxNum; i++) {
				todasPalabras.add(aux);
			}
		}

		// Recorremos todo el ArrayList insertando las palabras en posiciones aleatorias
		for (int i = 0; i < todasPalabras.size(); i++) {
			a�adirALaSopa(todasPalabras.get(i).toUpperCase());
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
	 * @param pal La palabra que sera a�adida a la sopa de letras
	 */
	private static void a�adirALaSopa(String pal) {
		int forma = (int)(Math.random()*8);
		switch (forma) {
			case 0:
				a�adirVerticalReversa(pal);
				break;
			case 1:
				a�adirVerticalNormal(pal);
				break;
			case 2:
				a�adirHorizontalReversa(pal);
				break;
			case 3:
				a�adirHorizontalNormal(pal);
				break;
			case 4:
				a�adirDiagonalArribaIReversa(pal);
				break;
			case 5:
				a�adirDiagonalArribaINormal(pal);
				break;
			case 6:
				a�adirDiagonalArribaDReverse(pal);
				break;
			case 7:
				a�adirDigaonalArribaDNormal(pal);
				break;
		}
	}
	
	/**
	 * Este metodo rellenar� en una de las diagonales la palabra
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirDigaonalArribaDNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			/*Si la palabra se sale de los limites de la sopa no es posible a�adirla */
			if (x-pal.length()<0 || y+pal.length()>=tama�o) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
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
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� en una de las diagonales la palabra escrita del reves
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirDiagonalArribaDReverse(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tama�o || y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
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
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� en una de las diagonales la palabra
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirDiagonalArribaINormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tama�o || y+pal.length()>=tama�o) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
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
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� en una de las diagonales la palabra del reves
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirDiagonalArribaIReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {		
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x-pal.length()<0 || y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
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
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� la palabra horizontalmente
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirHorizontalNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x+pal.length()>=tama�o) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					x++;
				}
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� la palabra horizontalmente y del reves
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirHorizontalReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {		
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (x-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					x--;
				}
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� la palabra en vertical
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirVerticalNormal(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (y+pal.length()>=tama�o) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y++;
				}
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
	 * Este metodo rellenar� la aplabra en vertical, y del reves
	 * @param pal la palabra a a�adir
	 */
	private static void a�adirVerticalReversa(String pal) {
		int count=0;
		boolean posible=false;
		// Si la palabra es mas grande que la sopa no intentara a�adirla porque no entra
		if (pal.length()>tama�o) {
			return;
		}
		/* Con un maximo de 500 intentos, se intentar� a�adir la palabra a la sopa en una posicion aleatoria,
		 * si no es posible y no se ha intentado 500 veces, lo vuelve a intentar*/
		do {	
			/* Generamos una posicion(x,y) aleatoria, y la alamcenamos en dos sitios, una cambiar� 
			 * al ver si es posible a�adirla, y si es posible necesitamos de nuevo el sitio inicial */
			int y = (int)(Math.random()*tama�o) , x = (int)(Math.random()*tama�o), palL=pal.length();
			int xIni = x, yIni=y; 
			if (y-pal.length()<0) {
				posible=false;
			} else {
				/* Mientras el hueco este vacio, o la letra que haya coincida con la que se tendria que 
				 * colocar de nuestra palabra ser� posible, si en algun momento deja de serlo no comprobamos mas */
				for (int i = 0; i < palL; i++) {
					if (sopaLetras[y][x]=='\u0000' || sopaLetras[y][x]==pal.charAt(i)) {
						posible=true;
					} else {
						posible = false;
						break;
					}
					y--;
				}
				/*Si es posible, basandonos en el punto inicial a�adimos la palabra */
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
		 *Al principio escribimos el tama�o, y al final la lista de las palabras a buscar*/
		try {
			f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(tama�o+" "+tama�o);
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