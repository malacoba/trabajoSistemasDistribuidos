package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	
	private static List<Tablero> listaTableros;
	
	public static void main(String[]args) {
		ServerSocket ss = null;
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			ss = new ServerSocket(8000);
			while(true) {
				try {
					Socket s1 = ss.accept();
					ObjectOutputStream bw1 = new ObjectOutputStream(s1.getOutputStream());
					ObjectInputStream br1 = new ObjectInputStream(s1.getInputStream());
					String s= (String) br1.readObject();
					System.out.println(s);
					int opcion = (Integer) br1.readObject();
					if(opcion==1) {
						bw1.writeObject("Comienza la partida");
						bw1.flush();
						ManejadorJugadores mj = new ManejadorJugadores(s1,br1,bw1);
						pool.execute(mj);
						
					}else {
						bw1.writeObject("Jugador conectado, esperando a que se unan más jugadores" + "\r\n");
						Socket s2 = ss.accept();
						
						ObjectOutputStream bw2 = new ObjectOutputStream(s2.getOutputStream());
						ObjectInputStream br2 = new ObjectInputStream(s2.getInputStream());
						pool.execute(new ManejadorJugadores(s1,br1,bw1,s2,br2,bw2));
					}
				}catch(IOException | ClassNotFoundException ex) {
					ex.printStackTrace();
				}
				pool.shutdown();
							
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
			if(ss!=null) {
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
