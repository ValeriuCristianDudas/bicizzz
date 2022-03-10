package biciZz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Main {
	/**
	 * Pre:
	 * Post: Este metodo crea los archivos reducidos
	 */
	public static void reductor(String dR, int re, File ff) {
		try {
			Formatter f2 = new Formatter(dR);
			Scanner r = new Scanner(ff);
			for(int i = 0; i < re; i++) {
				String l = r.nextLine();
				f2.format(l + "\n");
				f2.flush();
			}
			r.close();
			f2.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se puede acceder o crear");
			e.printStackTrace();
		}
	}
	
	/**
	 * Pre:
	 * Post: Este metodo lee el archivo y calcula los datos de los usuarios
	 */
	public static ArrayList<UsuarioBici> lecalc(File f, ArrayList<UsuarioBici> ub){
		try {
			int c = 0;
			int ocu = 0;
			Scanner l = new Scanner(f);
			while(l.hasNextLine()) {
				String li = l.nextLine();
				String[] line = li.split(";");
				if(c == 1) {
					UsuarioBici uB = new UsuarioBici(line);
					ub.add(uB);
				} else if (c > 1) {
					for(int i = 0; i < ub.size(); i++) {
						if(ub.get(i).getId().equals(line[0])) {
							if(line[2].equals(line[4])) {
								ub.get(i).setCirculares(ub.get(i).getCirculares() + 1);
								ub.get(i).setTotal(ub.get(i).getTotal() + 1);
							} else {
								ub.get(i).setTranslados(ub.get(i).getTranslados() + 1);
								ub.get(i).setTotal(ub.get(i).getTotal() + 1);
							}
							ocu++;
						} 
						if (ocu == 0 && i == ub.size() - 1){
							UsuarioBici bU = new UsuarioBici (line);
							ub.add(bU);
						} else if(i == ub.size() - 1) {
							ocu = 0;
						}
						
					}
				}
				c++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ub;
	}
	
	/**
	 * Pre:
	 * Post: Este metodo ordena el Array de usuarios
	 */
	public static ArrayList<UsuarioBici> ordenarUsuarios (ArrayList<UsuarioBici> ub) {
		for (int i = 0; i < ub.size(); i++) {
			UsuarioBici auxiliar = new UsuarioBici();
			for (int j = 0; j < ub.size(); j++) {
				if(ub.get(i).getTotal() > ub.get(j).getTotal()) {
					auxiliar = ub.get(i);
					ub.set(i, ub.get(j));
					ub.set(j, auxiliar);
				}
			}
		}
		return ub;
	}
	
	/**
	 * Pre:
	 * Post: Este metodo muestra el array limitandolo a 15
	 */
	public static void mostrarA(ArrayList<UsuarioBici> ub) {
		for(int i = 0; i < 15; i++) {
			System.out.println(ub.get(i).toString());
		}
	}
	
	/**
	 * Pre:
	 * Post: En el main podemos encotrar la interfaz que ve el usuario del programa
	 */
	public static void main(String[] args) {
		ArrayList<UsuarioBici> ubb = new ArrayList<UsuarioBici>();
		String c = null;
		Scanner capt = new Scanner (System.in);
		while(true) {
			try {
				System.out.println("-----BiZi-----" +"\n[1] PREPARAR DATOS"+ "\n[2] USO BIZI" + 
									"\n[3] USO USUARIOS" + "\n[0] SALIR");
				int k = capt.nextInt();
				if(k == 1) {
					File f = new File(".\\usos-16.csv");
					File ff = new File(".\\usos-17.csv");
					reductor(".\\usos-10.csv", 10, f);
					reductor(".\\usos-2000.csv", 2000, ff);
				} else if(k == 2) {
					System.out.println("Escriba el nombre de un fichero de usos del sistema Bizi: ");
					c = capt.next();
					File ub = new File(".\\" + c);
					UsoBici uubb = new UsoBici(ub);
					System.out.println(uubb.toString());
				} else if(k == 3) {
					System.out.println("Escriba el nombre de un fichero de usos del sistema Bizi: ");
					c = ".\\" + capt.next();
					File ub2 = new File(c);
					ubb = ordenarUsuarios(lecalc(ub2, ubb));
					System.out.println("Usuario" + "  " + "Translados" + "  " + "Circulares" + "  " +
										"Total");
					System.out.println("Numero de usuarios distintos: " + ubb.size());
					mostrarA(ubb);
				} else if(k == 0) {
					System.exit(0);
				}
			} catch(Exception e) {
				System.out.println("ERROR");
			}
		}
	}
}
