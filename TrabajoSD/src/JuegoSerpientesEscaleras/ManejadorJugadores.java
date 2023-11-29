package JuegoSerpientesEscaleras;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ManejadorJugadores implements Runnable{
	private Socket s;
	private List<Jugador> listaJugadores;
	private PrintWriter salida;
	private Scanner entrada;
	private boolean tableroCreado;
	private Jugador jugador;
	private Tablero tablero;
	private int turnos=0;
	private List<Tablero> tablerosCreados;
	
	public ManejadorJugadores(Socket s) {
		this.s=s;
		this.listaJugadores = new ArrayList<Jugador>();
		this.tableroCreado=false;
		try {
			this.entrada =new Scanner(s.getInputStream());
			this.salida= new PrintWriter(s.getOutputStream(),true);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		this.turnos++;
	}
	@Override
	public void run(){
		//primero preguntamos por su nombre y lo añadimos a la lista
		salida.println("Introduzca su nombre");
		String nombre= entrada.nextLine();
		this.jugador= new Jugador(nombre,turnos);
		salida.println("bienvenido a serpientes y escaleras" + "\n");
		if(!this.tableroCreado) {
			salida.println("¿Desea crear un nuevo juego?" + "\n");
			String nuevoJuego = entrada.nextLine();
			if(nuevoJuego.equals("Si") || nuevoJuego.equals("SI")) {
				this.tablero = new Tablero(listaJugadores);
				this.tablerosCreados.add(tablero);
				this.tablero.anyadirJugador(jugador);
			}else {
				if(this.tablerosCreados.size() ==0) {
					salida.println("No hay juegos creados");
				}else {
					this.tablero.anyadirJugador(jugador);
				}
			}
			tableroCreado=true;
		}else {
			this.tablero.anyadirJugador(this.jugador);
			jugar(this.tablero);
		}
		
		
	}
	public void jugar(Tablero t) {
		boolean ganador = false;
		while(!ganador) {
			for(Jugador j: this.listaJugadores) {
				salida.write("Turno de jugador" + j.getNombre());
				salida.write("Pulsa 1 para tirar del dado");
				String tirarDado = entrada.nextLine();
				while(Integer.valueOf(tirarDado) != 1) {
					salida.write("Pulsa 1 para tirar del dado");
					tirarDado = entrada.nextLine();
				}
				Random r = new Random();
				int dado = r.nextInt(1, 6);
				salida.write("Te ha salido un" + dado + "\n");
				int posicion = t.setPosicion(j, dado);
				salida.write("Avanzas a la posición"+ posicion);
				if(posicion ==100) {
					ganador = true;
					salida.write("El jugador" + j.getNombre() + "ha ganado");
				}
			}
			
		}
		
		
		
	}
}
