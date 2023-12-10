//María Lacoba Martinez
package juego;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//Clase cliente que introducirá su nombre, esperará a que empiece el juego e interaccionará para 
//jugar
public class Cliente {
	public static void main(String[]args) {
		try {
			Socket cliente = new Socket("localhost",8000);
			Scanner scaner = new Scanner(System.in);
			DataInputStream br = new DataInputStream(cliente.getInputStream());
			DataOutputStream bw = new DataOutputStream(cliente.getOutputStream());
			System.out.println("Bienvenido al juego de serpientes y escaleras");
			System.out.println("Introduce tu nombre");
			String nombre = scaner.nextLine();
			bw.writeBytes(nombre+ "\n");
			bw.flush();
			String lineaEsperar = br.readLine();
			System.out.println(lineaEsperar);
			boolean terminado = false;
			while(!terminado) {
				String linea = br.readLine();
				System.out.println(linea);
				if(linea.equals("Pulsa 1 para tirar del dado")) {
					int n = scaner.nextInt();
					bw.writeBytes(n + "\n");
				}else if(linea.equals("Juego terminado")) {
					terminado = true;
				}
				
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

}
