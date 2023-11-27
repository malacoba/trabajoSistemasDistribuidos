package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[]args) {
		try {
			ExecutorService exS = Executors.newCachedThreadPool();
			ServerSocket ss = new ServerSocket(8000);
			System.out.println("Esperando conexiones");
			while(true) {
				try {
				Socket socketJ = ss.accept();
				System.out.println("Jugador conectado" + socketJ);
				Thread th = new Thread(new ManejadorJugadores(socketJ));
				th.start();
				try {
					th.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
