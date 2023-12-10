package juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[]args) {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		List<InformacionJugador> informacion = new ArrayList<InformacionJugador>();
		try(ServerSocket ss = new ServerSocket(8000)){
			int c =0;
			Socket cliente=null;
			while(true) {
				try {
					while(informacion.size()<2) {
						cliente= ss.accept();
						DataInputStream br = new DataInputStream(cliente.getInputStream());
						DataOutputStream bw = new DataOutputStream(cliente.getOutputStream());
						bw.writeBytes("Espere a otro jugador"+ "\n");
						bw.flush();
						String nombre =br.readLine();
						informacion.add(new InformacionJugador(nombre,bw,br));
						
					}
					
					if(c ==0) {
						pool.execute(new ManejadorJugadores(informacion));
						c++;
					}
					Socket cl= ss.accept();
				
				}catch(IOException ex) {
					ex.printStackTrace();
					if(cliente!=null) {
						cliente.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
}
