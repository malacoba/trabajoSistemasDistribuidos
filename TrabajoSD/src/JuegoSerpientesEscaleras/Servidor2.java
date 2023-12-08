package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor2 {
	public static void main(String[]args) {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try(ServerSocket ss= new ServerSocket(8000)){
			List<InfoJugador> informacion = new ArrayList<InfoJugador>();
			int contadorJ=0;
			while(true) {
				try {
					while(informacion.size()<2) {
						Socket s= ss.accept();
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
						String nom = (String) ois.readObject();
						informacion.add(new InfoJugador(nom,oos,ois));
						oos.writeObject("Espere al resto de jugadores");
					}
					ManejadorJugadores2 poo = new ManejadorJugadores2(informacion);
					pool.execute(poo);
				}catch(IOException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
