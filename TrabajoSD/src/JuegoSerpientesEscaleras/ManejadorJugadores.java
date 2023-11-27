package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManejadorJugadores implements Runnable{
	private Socket s;
	private List<Jugador> listaJugadores;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean tableroCreado;
	private Tablero tablero;
	
	public ManejadorJugadores(Socket s) {
		this.s=s;
		this.listaJugadores = new ArrayList<Jugador>();
		this.tableroCreado=false;
		try {
			this.ois=new ObjectInputStream(s.getInputStream());
			this.oos= new ObjectOutputStream(s.getOutputStream());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			int turno =0;
			if(tableroCreado==false) {
				oos.writeObject("¿Desea empezar una partida nueva?");
				oos.flush();
				String respuesta = (String) ois.readObject();
				if(respuesta.equals("Si") || respuesta.equals("SI") || respuesta.equals("si")) {
					tableroCreado=true;
					oos.writeObject("Introduce tu nombre");
					oos.flush();
					String nom = (String)ois.readObject();
					Jugador j = new Jugador(nom,turno);
					turno++;
					this.listaJugadores.add(j);
					Tablero tablero = new Tablero(this.listaJugadores);
					tablero.setPosicion(j, 0);
					oos.writeObject(tablero);
					oos.flush();
					jugar();
				}
			}
			oos.writeObject("Introduce tu nombre");
			oos.flush();
			String nom = (String)ois.readObject();
			Jugador j = new Jugador(nom,turno);
			turno++;
			this.listaJugadores.add(j);
			this.tablero.anyadirJugador(j);
			tablero.setPosicion(j, 0);
			oos.writeObject(tablero);
			oos.flush();
			jugar();
			
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void jugar() {
		System.out.println("Bienvenido partida");
		while(!this.tablero.isJuegoTerminado()) {
			int i=0;
			while(i< this.listaJugadores.size() - 1) {
				System.out.print("Turno del jugador" + this.listaJugadores.get(i).getNombre());
				System.out.println("Pulsa 1 + enter para tirar del dado");
				Random r = new Random();
				int dado = r.nextInt(1,6);
				try {
					
					oos.writeObject(dado);
					oos.flush();
					oos.writeObject(this.tablero);
					oos.flush();
					oos.writeObject(this.listaJugadores.get(i));
					this.tablero = (Tablero) ois.readObject();
					i++;
				}catch(IOException ex) {
					ex.printStackTrace();
				}catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
	}
}
