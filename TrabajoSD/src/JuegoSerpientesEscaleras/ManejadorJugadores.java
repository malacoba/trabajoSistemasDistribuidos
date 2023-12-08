package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ManejadorJugadores extends Thread{
	private static List<Jugador> listaJugadores;
	private Socket s1;
	private Socket s2;
	private ObjectInputStream br1;
	private ObjectInputStream br2;
	private ObjectOutputStream bw1;
	private ObjectOutputStream bw2;
	private static List<Tablero> tablerosCreados;
	
	public ManejadorJugadores(Socket s1, ObjectInputStream br1, ObjectOutputStream bw1) {
		this.s1=s1;
		this.br1=br1;
		this.bw1=bw1;
		this.s2=null;
		this.br2 = null;
		this.bw2 = null;
	}
	public ManejadorJugadores(Socket s1, ObjectInputStream br1, ObjectOutputStream bw1,Socket s2, ObjectInputStream br2, ObjectOutputStream bw2 ) {
		this.s1=s1;
		this.br1=br1;
		this.bw1=bw1;
		this.s2=s2;
		this.br2 = br2;
		this.bw2 = bw2;
	}
	public void run() {
		try {
			if(s2==null) {
				String s = (String) br1.readObject();
				System.out.println(s);
				Jugador j = (Jugador) br1.readObject();
				Tablero t = new Tablero();
				t.anyadirJugador(j);
				jugarSolo(t);
				
			}else {
				bw1.writeObject("Empieza la partida");
				Tablero t = new Tablero();
				String jugador = (String)br1.readObject();
				String jugador2 =(String) br2.readObject();
				Jugador j1 = new Jugador(jugador,s1.getLocalPort());
				Jugador j2 = new Jugador(jugador2,s2.getLocalPort());
				t.anyadirJugador(j1);
				t.anyadirJugador(j2);
				j1.setTurno(1);
				j2.setTurno(2);
				jugar(t);
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void jugarSolo(Tablero t) {
		try {
			Jugador j = t.getListaJugadores().get(0);
			bw1.writeObject("Ha iniciado una partida solo");
			bw1.writeObject("Tu casilla inicial es" + j.getPosicion());
			bw1.flush();
			boolean juegoT=t.isJuegoTerminado();
			while(!juegoT) {
				boolean dado=false;
				while(!dado) {
					bw1.writeObject("Pulsa 1 para tirar del dado");
					bw1.flush();
					Integer i = (Integer) br1.readObject();
					if(i==1) {
						dado =true;
					}
				}
				Random r = new Random();
				int num = r.nextInt(1, 6);
				bw1.writeObject("Te ha salido un" + num);
				bw1.flush();
				if(t.setPosicion(j, num) !=100) {
					bw1.writeObject("Avanzas a la casilla " + t.setPosicion(j, num));
					bw1.flush();
					
				}else {
					juegoT=true;
				}
				
			}
			bw1.writeObject("Has terminado el juego");
			bw1.writeObject("DESCONECTAR");
			bw1.flush();
		}catch(IOException ex) {
			ex.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void jugar(Tablero t) {
		boolean terminado = t.isJuegoTerminado();
		try {
			while(!terminado) {
				for(Jugador j : t.getListaJugadores()) {
					if(j.getTurno()==1) {
						bw1.writeObject("Es tu turno");
						bw1.writeObject("Pulsa 1 para tirar del dado");
						bw1.flush();
						int uno = (Integer) br1.readObject();
						while(uno != 1) {
							bw1.writeObject("Pulsa 1 para tirar del dado");
							bw1.flush();
							uno = (Integer) br1.readObject();
						}
						Random r = new Random();
						int dado = r.nextInt(1,6);
						bw1.writeObject("Te ha salido un " + dado);
						bw1.flush();
						int pos= t.setPosicion(j, dado);
						if(t.isSerpiente(pos)) {
							bw1.writeObject("oooh!, has caido en una serpiente, te deslizas hasta su cola");
							int posicionA = j.getPosicion();
							int posicionN= t.devuelveCola(posicionA);
							t.setPosicion(j, posicionN);
						} else if(t.isEscalera(pos)){
							bw1.writeObject("¡Has caido en una escalera! " + "\r\n" + "Subes hasta la cima");
							int posicionA= j.getPosicion();
							int posicionN = t.devuelveFin(posicionA);
							t.setPosicion(j, posicionN);
						}else {
							bw1.writeObject("Avanzas hasta la casilla" + pos);
						}
						if(pos ==100) {
							bw1.writeObject("Has ganado");
						}
					}else {
						bw2.writeObject("Es tu turno");
						int uno = 0;
						while(uno != 1) {
							bw2.writeObject("Pulsa 1 para tirar del dado");
							bw2.flush();
							uno = (Integer) br2.readObject();
						}
						Random r = new Random();
						int dado = r.nextInt(1,6);
						bw2.writeObject("Te ha salido un " + dado);
						bw2.flush();
						int pos= t.setPosicion(j, dado);
						if(t.isSerpiente(pos)) {
							bw2.writeObject("oooh!, has caido en una serpiente, te deslizas hasta su cola");
							int posicionA = j.getPosicion();
							int posicionN= t.devuelveCola(posicionA);
							t.setPosicion(j, posicionN);
						} else if(t.isEscalera(pos)){
							bw2.writeObject("¡Has caido en una escalera! " + "\r\n" + "Subes hasta la cima");
							int posicionA= j.getPosicion();
							int posicionN = t.devuelveFin(posicionA);
							t.setPosicion(j, posicionN);
						}else {
							bw2.writeObject("Avanzas hasta la casilla" + pos);
						}
						if(pos ==100) {
							bw2.writeObject("Has ganado");
						}
					}
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void jugar2(Tablero t) {
		
	}

}
