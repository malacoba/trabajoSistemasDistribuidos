package juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class ManejadorJugadores extends Thread{
	private List<InformacionJugador> listaJugadoresS;
	private Tablero t;
	
	public ManejadorJugadores(List<InformacionJugador> list) {
		this.listaJugadoresS=list;
		Tablero t = new Tablero();
		this.t=t;
		for(int i =0; i<listaJugadoresS.size();i++) {
			Jugador j = new Jugador(listaJugadoresS.get(i).getNombre(),i);
			t.anyadirJugador(j);
		}
	}
	public void run() {
		jugar(this.t);
	}
	public void jugar(Tablero t) {
		try {
			boolean isTerminado = t.isJuegoTerminado();
			while(!isTerminado) {
				for(int i =0; i < t.getNumJugadores(); i++) {
					Jugador j = t.getListaJugadores().get(i);
						DataOutputStream oos = listaJugadoresS.get(i).getOutput();
						DataInputStream ois = listaJugadoresS.get(i).getInput();
						oos.writeBytes("Es tu turno" + "\r\n");
						oos.writeBytes("Estas en la posicion" +j.getPosicion()+"\r\n");
						oos.flush();
						int dado =0;
						while(dado !=1) {
							oos.writeBytes("Pulsa 1 para tirar del dado"+"\r\n");
							oos.flush();
							dado = Integer.valueOf(ois.readLine());
						}
						Random r = new Random();
						int d = r.nextInt(1,6);
						oos.writeBytes("Has obtenido un " + d + "\n");
						int pos = t.setPosicion(j, d);
						if(t.isEscalera(pos)) {
							pos = t.setPosicion(j, t.devuelveFin(pos));
							oos.writeBytes("Has caido en una escalera, avanzas hasta la posicion" + pos+ "\n");
							oos.flush();
						}else if(t.isSerpiente(pos)) {
							pos= t.setPosicion(j, t.devuelveCola(pos));
							oos.writeBytes("oooh, has caido en una serpientes, vuelves a la posicion" + pos+ "\n");
							oos.flush();
						}else {
							oos.writeBytes("Avanzas a la posición " + pos+ "\n");
							oos.flush();
						}
						if(pos==100) {
							oos.writeBytes("Juego terminado"+ "\n");
						}
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	
	}
}
