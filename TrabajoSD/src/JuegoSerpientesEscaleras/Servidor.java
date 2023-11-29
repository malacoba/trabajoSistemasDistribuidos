package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	
	public static void main(String[]args) {
		ServerSocket ss = null;
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			ss = new ServerSocket(8000);
			ArrayList<Cliente> cliente = new ArrayList<Cliente>();
			while(true) {
				Socket s = ss.accept();
				ManejadorJugadores mj = new ManejadorJugadores(s);
				pool.execute(mj);
				
						
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
