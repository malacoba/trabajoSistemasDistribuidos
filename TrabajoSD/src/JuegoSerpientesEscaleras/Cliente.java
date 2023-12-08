package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[]args) {
		try {
			Scanner scan = new Scanner(System.in);
			Socket socket = new Socket("localhost",8000);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
			Scanner scanner = new Scanner(System.in);
			System.out.println("Hola, bienvenido al juego de serpientes y escaleras");
			System.out.println("Introduzca su nombre");
			String nombre =scanner.nextLine();
			System.out.println("Elige una opción");
			System.out.println("1. Jugar solo");
			System.out.println("2. Unirse a una partida");
			int opcion = scanner.nextInt();
			out.writeObject("Te mando la opcion que ha elegido el jugador");
			out.writeObject(Integer.valueOf(opcion));
			out.flush();
			switch(opcion) {
			case 1: 
				String linea = (String) in.readObject();
				System.out.println(linea);
				String line = (String) in.readObject();
				System.out.println(line);
				String jugarSolo= scanner.nextLine();
				out.writeObject(jugarSolo);
				out.flush();
				String linea2 = (String) in.readObject();
				System.out.println(linea2);
				String linea4 = (String) in.readObject();
				System.out.println(linea4);
				String dado = (String) in.readObject();
				System.out.println(dado);
				Integer numD= scanner.nextInt();
				out.writeObject(numD);
				out.flush();
				boolean juegoT=false;
				while(!juegoT) {
					String linea3 = (String) in.readObject();
					if(linea3.equals("DESCONECTAR")) {
						juegoT=true;
						System.out.println("Has ganado");
					}else {
						System.out.println(linea3);
					}
					if(linea3.equals("Pulsa 1 para tirar del dado")) {
						numD= scanner.nextInt();
						out.writeObject(numD);
					}
					
				}
				break;
			case 2: 
				
				String empieza = (String) in.readObject();
				System.out.println(empieza);
				out.writeObject(nombre);
				out.flush();
				boolean juegoTr=false;
				while(!juegoTr) {
					String linea3 = (String) in.readObject();
					if(linea3.equals("DESCONECTAR")) {
						juegoTr=true;
						System.out.println("Has ganado");
						
					}else {
						System.out.println(linea3);
					}
					if(linea3.equals("Pulsa 1 para tirar del dado")) {
						numD= scanner.nextInt();
						out.writeObject(numD);
					}
					
				}
				break;
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
