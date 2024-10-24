package es.uva.inf.poo.VendingCo;
import java.util.ArrayList;
import java.util.Random;

/**
 * Prueba VendingSystem
 * @author Pablo Bueno
 * @author Diego Valladolid
 *
 */
public class VendingSystem {
		private ArrayList<VendingMachine> maquinas;
		private int	operativas;
		private ArrayList<VendingMachine> lineasVacias;
		
		/**
		 * Inicialización por defecto.
		 */
		public VendingSystem() {
				this.operativas = 0;
				this.maquinas = new ArrayList<VendingMachine>();
				this.lineasVacias = new ArrayList<VendingMachine>();
		}
		/**
		 * Devuelve un número random entre 0 y 999.999
		 * @return	Numero Random entre 100.000 y 999.999,
		 */
		private int getRandom() {
			return new Random().nextInt(900000);
		}
		
		/**
		 * Añade una nueva máquina vending al sistema.
		 */
		public void addMachine(int noLineas, int noObjLineas) {
			int random =getRandom();
			for(VendingMachine maquina : this.maquinas) {
				while(random == maquina.getId()) {
					random = getRandom();
				}
			}
			this.maquinas.add(new VendingMachine(random, noLineas, noObjLineas));
		}
		
		/**
		 * Añade una nueva máquina al sistema.
		 * @param id
		 * 			ID de la máquina.
		 * @throws IllegalArgumentException si el ID es negativo.
		 * @throws SecurityException si la Máquina ya existe,
		 */
		public void addMachine(int id, int noLineas, int noObjLineas) {
			if(id < 0) {
				throw new IllegalArgumentException("ID no válido");
			}
			else {
				for(VendingMachine maquina : this.maquinas) {
					if(maquina.getId() == id) {
						throw new SecurityException("La máquina ya existe.");
					}
				}
			}
			this.maquinas.add(new VendingMachine(id, noLineas, noObjLineas));
		}
		
		/**
		 * Elimina una máquina vending a partir de su identificador
		 * @param id 
		 * 			Identificador de la máquiha vending.
		 * @throws IllegalArgumentException si la máquina no existe.
		 */
		public void removeMachine(int id) {
			boolean existe = false;
			for(VendingMachine machine : this.maquinas) {
				if(machine.getId() == id) {
					this.maquinas.remove(machine);
					existe = true;
				}
			}
			if(!existe) {
				throw new IllegalArgumentException("No existe la máquina.");
			}
		}
		
		/**
		 * Verifica que máquinas tienen alguna linea vacia y la añade o elimina de la lista de máquinas vacias.
		 */
		public void checkLineasVacias() {
			for(VendingMachine machine : this.maquinas) {
				if(machine.lineaVacia() && !this.lineasVacias.contains(machine)) {
					this.lineasVacias.add(machine);
				}
				else if(!machine.lineaVacia() && this.lineasVacias.contains(machine)){
					this.lineasVacias.remove(machine);
				}
			}
		}
		
		/**
		 * Devuelve un entero con el número de maquinas Vending Operativas.
		 * @return total de máquinas operativas
		 */
		public int howManyOperative() {
			this.operativas = 0;
			for(VendingMachine machine : this.maquinas) {
				if(machine.isOperative() == true) this.operativas++;
			}
			return this.operativas;
		}
		
		/**
		 * Devuelve una lista con todas las máquinas vending
		 * @return	ArrayList de todas las máquinas vending.
		 */
		public ArrayList<VendingMachine> getMaquinas() {
			return this.maquinas;
		}
		
		/**
		 * Devuelve una lista con los ID de todas las máquinas.
		 * @return Lista con los ID de todas las máquinas.
		 */
		public ArrayList<Integer> getMachinesId() {
			ArrayList<Integer> lista = new ArrayList<Integer>();
			for(VendingMachine elem : this.maquinas) {
				lista.add(elem.getId());
			}
			return lista;
		}
		
		/**
		 * Devuelve una lista de todas las máquinas vending con alguna linea vacia.
		 * @return ArrayList de todas las máquinas vending con lineas vacias,
		 */
		public ArrayList<VendingMachine> getLineasVacias() {
			checkLineasVacias();
			return this.lineasVacias;
		}
		
		/**
		 * Deshabilita la máquina vending a  partir de su ID.
		 * @param id de la máquina.
		 * @throws SecurityException si la máquina ya estaba apagada.
		 */
		public void shutDown(int id) {
			for(VendingMachine machine : this.maquinas) {
				try {
					if(id == machine.getId()) machine.shutDown();
				}
				catch (SecurityException machExc) {
					throw machExc;
				}
			}
		}
		
		/**
		 * habilita la máquina vending a  partir de su ID.
		 * @param id de lamáquina.
		 * @throws SecurityException si la máquina ya estaba encendida.
		 */
		public void turnOn(int id) {
			for(VendingMachine machine : this.maquinas) {
				try {
					if(id == machine.getId()) machine.turnOn();
				}
				catch (SecurityException machExc) {
					throw machExc;
				}
			}
		}

}
