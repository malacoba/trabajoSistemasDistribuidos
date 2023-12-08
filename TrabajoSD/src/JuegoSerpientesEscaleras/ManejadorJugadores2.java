package JuegoSerpientesEscaleras;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManejadorJugadores2 extends Thread{
	private List<InfoJugador> listaTurnos;
	private static List<Tablero> tablerosCreados = new ArrayList<Tablero>();
	
	public ManejadorJugadores2(List<InfoJugador> listaT) {
		this.listaTurnos= listaT;
	}
	public void run() {
		Tablero t = new Tablero();
		for(int i=0; i< this.listaTurnos.size(); i++) {
			Jugador jugador= new Jugador(listaTurnos.get(i).getNombre(),i);
			t.anyadirJugador(jugador);
		}
		tablerosCreados.add(t);
		jugar(t);
	}
	public void jugar(Tablero t) {
		try {
			boolean isTerminado = t.isJuegoTerminado();
			while(!isTerminado) {
				for(int i =0; i < t.getListaJugadores().size(); i++) {
					Jugador j = t.getListaJugadores().get(i);
						ObjectOutputStream oos = listaTurnos.get(i).getOutput();
						ObjectInputStream ois = listaTurnos.get(i).getInput();
						oos.writeObject("Es tu turno");
						oos.writeObject("Estas en la posicion" +j.getPosicion());
						oos.flush();
						int dado =0;
						while(dado !=1) {
							oos.writeObject("Pulsa 1 para tirar del dado");
							oos.flush();
							dado = (Integer) ois.readObject();
						}
						Random r = new Random();
						int d = r.nextInt(1,6);
						oos.writeObject("Has obtenido un " + d);
						int pos = t.setPosicion(j, d);
						if(t.isEscalera(pos)) {
							pos = t.setPosicion(j, t.devuelveFin(pos));
							oos.writeObject("Has caido en una escalera, avanzas hasta la posicion" + pos);
							oos.flush();
						}else if(t.isSerpiente(pos)) {
							pos= t.setPosicion(j, t.devuelveCola(pos));
							oos.writeObject("oooh, has caido en una serpientes, vuelves a la posicion" + pos);
							oos.flush();
						}else {
							oos.writeObject("Avanzas a la posición " + pos);
							oos.flush();
						}
						if(pos==100) {
							oos.writeObject("Juego terminado");
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
}
