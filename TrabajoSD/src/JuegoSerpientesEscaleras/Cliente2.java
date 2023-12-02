package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
	public static void main(String[]args) {
		try {
			Socket socket = new Socket("localhost",8000);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
			Scanner scanner = new Scanner(System.in);
			System.out.println("Hola, bienvenido al juego de serpientes y escaleras");
			System.out.println("Introduzca su nombre");
			String nombre =scanner.nextLine();
			Jugador jugador = new Jugador(nombre,socket.getLocalPort());
			
			out.writeObject("Te mando un jugador");
			out.flush();
			out.writeObject(jugador);
			System.out.println("Elige una opción");
			System.out.println("1. Jugar solo");
			System.out.println("2. Ver partidas ya existentes");
			System.out.println("3. Crear una nueva partida");
			int opcion = scanner.nextInt();
			out.writeObject("Te mando la opcion que ha elegido el jugador");
			out.writeObject(Integer.valueOf(opcion));
			out.flush();
			switch(opcion) {
			case 1:
				String linea = (String) in.readObject();
				System.out.println(linea);
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
				String partidas =(String) in.readObject();
				System.out.println(partidas);
				String[] partes = partidas.split(" ");
				int numTDisponibles = Integer.valueOf(partes[1]);
				for(int i=0; i< numTDisponibles; i++) {
					Tablero t = (Tablero) in.readObject();
					System.out.println(i + t.toString());
				}
				System.out.println("¿A qué mesa te quieres conectar?");
				int numMesa = scanner.nextInt();
				out.writeObject(Integer.valueOf(numMesa));
				String lineas = (String) in.readObject();
				System.out.println(lineas);
				String lineas2 = (String) in.readObject();
				System.out.println(lineas2);
				String si = scanner.nextLine();
				out.writeObject(si);
				out.flush();
				juegoT=false;
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
			case 3: 
				Tablero t = (Tablero)in.readObject();
				System.out.println(t.toString());
				System.out.println("Esperando a mas clientes");
				boolean esperar = true;
				while(esperar) {
					String s = (String)in.readObject();
					if(s.equals("EMPEZAR")) {
						esperar = false;
					}
				}
				break;
				
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
