package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[]args) {
		try {
			Socket socket = new Socket("localhost", 8000);
			Scanner s2 = new Scanner(System.in);
			ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());
			
			String s = (String)ois.readObject();
			System.out.println(s);
			oos.writeObject(s2.nextLine());
			oos.flush();
			String RequestNombreJ = (String) ois.readObject();
			System.out.println(RequestNombreJ);
			String nom = s2.nextLine();
			oos.writeObject(nom);
			oos.flush();
			Tablero tablero = (Tablero) ois.readObject();
			tablero.setPosicion(j, 0);
			int dado =(int) ois.readObject();
			System.out.println("Te ha tocado el" + dado);
			
			
			System.out.println("Avanzas hasta la casilla" );
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
