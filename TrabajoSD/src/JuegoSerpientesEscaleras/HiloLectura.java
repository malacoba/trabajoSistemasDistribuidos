package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;

public class HiloLectura implements Runnable{
	private Socket s;
	
	public HiloLectura(Socket s) {
		this.s=s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			DataInputStream dis = new DataInputStream(s.getInputStream());
			boolean seguirConectado=true;
			String m = "desconectar";
			while(seguirConectado) {
				String linea = dis.readLine();
				while(linea !=null) {
					System.out.println(linea);
					if(linea.contains(m)) {
						seguirConectado=false;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
