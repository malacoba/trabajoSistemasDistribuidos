package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
	public static void main(String[] args) {
		try {
			boolean terminado =false;
			while(!terminado) {
				Scanner scan = new Scanner(System.in);
				Socket cliente = new Socket("localhost",8000);
				ObjectOutputStream oos= new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream ois= new ObjectInputStream(cliente.getInputStream());
				System.out.println("Bienvenido al juego de serpientes y escaleras");
				System.out.println("¿Cual es su nombre?");
				String nombre = scan.nextLine();
				oos.writeObject(nombre);	
				String espera = (String)ois.readObject();
				System.out.println(espera);
				String linea = (String) ois.readObject();
				System.out.println(linea);
				String linea2 = (String) ois.readObject();
				System.out.println(linea2);
				int d =0;
				while(d != 1) {
					String linea3 = (String) ois.readObject();
					System.out.println(linea3);
					d = scan.nextInt();
					oos.writeObject(Integer.valueOf(d));
					oos.flush();
				}
				String linea4 = (String) ois.readObject();
				System.out.println(linea4);
				String linea5 = (String) ois.readObject();
				System.out.println(linea5);
				String linea6 = (String) ois.readObject();
				if(linea6.equals("Juego terminado")) {
					terminado = true;
				}
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
