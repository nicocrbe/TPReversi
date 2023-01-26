package juego;

/**
 * Juego Reversi
 * 
 * Reglas:
 * 
 * 	https://es.wikipedia.org/wiki/Reversi
 *  https://es.wikihow.com/jugar-a-Othello
 * 
 */
public class Reversi {

	private int filas;
	private int columnas;
	private Casillero[][] tablero;
	private boolean noPuedeMover;
	private String jugadorBlancas;
	private String jugadorNegras;

	public String getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(String jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

	private String jugadorActual;

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public Casillero[][] getTablero() {
		return tablero;
	}

	public void setTablero(Casillero[][] tablero) {
		this.tablero = tablero;
	}

	public boolean isNoPuedeMover() {
		return noPuedeMover;
	}

	public void setNoPuedeMover(boolean noPuedeMover) {
		this.noPuedeMover = noPuedeMover;
	}

	public String getJugadorBlancas() {
		return jugadorBlancas;
	}

	public void setJugadorBlancas(String jugadorBlancas) {
		this.jugadorBlancas = jugadorBlancas;
	}

	public String getJugadorNegras() {
		return jugadorNegras;
	}

	public void setJugadorNegras(String jugadorNegras) {
		this.jugadorNegras = jugadorNegras;
	}
	/**
	 * pre : 'dimension' es un número par, mayor o igual a 4.
	 * post: empieza el juego entre el jugador que tiene fichas negras, identificado como 
	 * 		 'fichasNegras' y el jugador que tiene fichas blancas, identificado como
	 * 		 'fichasBlancas'. 
	 * 		 El tablero tiene 4 fichas: 2 negras y 2 blancas. Estas fichas están 
	 * 		 intercaladas en el centro del tablero.
	 * 
	 * @param dimensionTablero : cantidad de filas y columnas que tiene el tablero.
	 * @param fichasNegras : nombre del jugador con fichas negras.
	 * @param fichasBlancas : nombre del jugador con fichas blancas.
	 */
	public Reversi(int dimensionTablero, String fichasNegras, String fichasBlancas) {

		int filaMitadDelTablero = dimensionTablero/2;
		int columnaMitadDelTablero = dimensionTablero/2;

		setFilas(dimensionTablero);
		setColumnas(dimensionTablero);
		setJugadorBlancas(fichasBlancas);
		setJugadorNegras(fichasNegras);
		setNoPuedeMover(false);

		setJugadorActual(getJugadorBlancas());

		setTablero(new Casillero[getFilas()][getColumnas()]);
		inicializarTablero(filaMitadDelTablero,columnaMitadDelTablero);
	}

	private void inicializarTablero(int filaMitad, int columnaMitad) {
		for(int i = 0; i<tablero.length; i++){
			for(int j= 0; j<tablero[i].length; j++){
				tablero[i][j] = Casillero.LIBRE;
			}
		}

		tablero[filaMitad-1][columnaMitad-1] = Casillero.NEGRAS;
		tablero[filaMitad][columnaMitad-1] = Casillero.BLANCAS;
		tablero[filaMitad-1][columnaMitad] = Casillero.BLANCAS;
		tablero[filaMitad][columnaMitad] = Casillero.NEGRAS;

	}

	/**
	 * post: devuelve la cantidad de filas que tiene el tablero.
	 */
	public int contarFilas() {
		
		return getFilas();
	}

	/**
	 * post: devuelve la cantidad de columnas que tiene el tablero.
	 */
	public int contarColumnas() {
		
		return getColumnas();
	}
	
	/**
	 * post: devuelve el nombre del jugador que debe colocar una ficha o null si
	 *       terminó el juego.
	 */
	public String obtenerJugadorActual() {
		
		return getJugadorActual();
	}

	/**
	 * pre : fila está en el intervalo [1, contarFilas()],
	 * 		 columnas está en el intervalo [1, contarColumnas()].
	 * post: indica quién tiene la posesión del casillero dado 
	 * 		 por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {
		
		return this.tablero[fila - 1][columna - 1];
	}
	
	public boolean puedeColocarFicha(int fila, int columna) {

		int filaArray = fila -1;
		int columnaArray = columna -1;

		if(!termino()) {
			if(tablero[filaArray][columnaArray] == Casillero.LIBRE){
				if(getJugadorActual().equalsIgnoreCase(getJugadorNegras())){
					System.out.println("==== ANALIZANDO PUNTO PARA NEGRAS: " + " FILA : +" + filaArray + " COLUMNA: " + columnaArray + "=========" );
					return checkMovimientosNegro(filaArray,columnaArray);
				} else if (getJugadorActual().equalsIgnoreCase(getJugadorBlancas())){
					System.out.println("==== ANALIZANDO PUNTO PARA BLANCAS: " + " FILA : +" + filaArray + " COLUMNA: " + columnaArray + "=========" );
					return checkMovimientosBlanco(filaArray, columnaArray);
				}
			}

		}
		return false;
	}

	private boolean checkMovimientosBlanco(int filaArray, int columnaArray) {
		return checkFilaNegroIzq(filaArray, columnaArray) || checkFilaNegroDer(filaArray, columnaArray)
				|| checkColNegroAbajo(filaArray, columnaArray) || checkColNegroArriba(filaArray, columnaArray)
				|| checkDiagNegroAbajoDer(filaArray, columnaArray) || checkDiagNegroAbajoIzq(filaArray, columnaArray)
				|| checkDiagNegroArribaDer(filaArray, columnaArray) || checkDiagNegroArribaIzq(filaArray, columnaArray);
	}

	private boolean checkMovimientosNegro(int filaArray, int columnaArray) {
		return checkFilaBlancoIzq(filaArray, columnaArray) || checkFilaBlancoDer(filaArray, columnaArray)
				|| checkColBlancoAbajo(filaArray, columnaArray) || checkColBlancoArriba(filaArray, columnaArray)
				|| checkDiagBlancoAbajoDer(filaArray, columnaArray) || checkDiagBlancoAbajoIzq(filaArray, columnaArray)
				|| checkDiagBlancoArribaDer(filaArray, columnaArray) || checkDiagBlancoArribaIzq(filaArray, columnaArray);
	}

	private boolean checkFilaBlancoIzq(int fila, int col) {
		if(col-1 < 0){
			return false;
		}
		if(tablero[fila][col-1] == Casillero.BLANCAS){
			return encontrarPrimerNegroIzq(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerNegroIzq(int fila, int col){
		for(int j=col-2; j>0; j--){
			if(tablero[fila][j] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[fila][j] == Casillero.NEGRAS){
				System.out.println("Primer negro fila hacia la izq. Fila: " + fila + "Columna: " + j);
				return j;
			}
		}
		return -1;
	}



	private boolean checkFilaNegroIzq(int fila, int col) {
		if(col-1 < 0){
			return false;
		}
		if(tablero[fila][col-1] == Casillero.NEGRAS) {
			return encontrarPrimerBlancoIzq(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerBlancoIzq(int fila, int col){
		for(int j=col-2; j>0; j--){
			if(tablero[fila][j] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[fila][j] == Casillero.BLANCAS){
				System.out.println("Primer blanco fila hacia la izq. Fila: " + fila + "Columna: " + j);
				return j;
			}
		}
		return -1;
	}

	private boolean checkFilaBlancoDer(int fila, int col) {
		if(col+1 == contarColumnas()){
			return false;
		}
		if(tablero[fila][col+1] == Casillero.BLANCAS){
			return encontrarPrimerNegroDer(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerNegroDer(int fila, int col){
		for(int j=col+2; j<getColumnas(); j++){
			if(tablero[fila][j] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[fila][j] == Casillero.NEGRAS){
				System.out.println("Primer negro fila hacia la der. Fila: " + fila + "Columna: " + j);
				return j;
			}
		}
		return -1;
	}



	private boolean checkFilaNegroDer(int fila, int col) {
		if(col+1 == contarColumnas()){
			return false;
		}
		if(tablero[fila][col+1] == Casillero.NEGRAS) {
			return encontrarPrimerBlancoDer(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerBlancoDer(int fila, int col){
		for(int j=col+2; j<getColumnas(); j++){
			if(tablero[fila][j] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[fila][j] == Casillero.BLANCAS){
				System.out.println("Primer blanco fila hacia la der. Fila: " + fila + "Columna: " + j);
				return j;
			}
		}
		return -1;
	}



	private boolean checkColBlancoArriba(int fila, int col) {
		if(fila-1 < 0){
			return false;
		}
		if(tablero[fila-1][col] == Casillero.BLANCAS){
			return encontrarPrimerNegroArriba(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerNegroArriba(int fila, int col){
		for(int i=fila-2; i>0; i--){
			if(tablero[i][col] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[i][col] == Casillero.NEGRAS){
				System.out.println("Primer negro columna hacia arriba. Fila: " + i + "Columna: " + col);
				return i;
			}
		}
		return -1;
	}



	private boolean checkColNegroArriba(int fila, int col) {
		if(fila-1 < 0){
			return false;
		}
		if(tablero[fila-1][col] == Casillero.NEGRAS) {
			return encontrarPrimerBlancoArriba(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerBlancoArriba(int fila, int col){
		for(int i=fila-2; i>0; i--){
			if(tablero[i][col] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[i][col] == Casillero.BLANCAS){
				System.out.println("Primer blanco columna hacia arriba. Fila: " + i + "Columna: " + col);
				return i;
			}
		}
		return -1;
	}

	private boolean checkColBlancoAbajo(int fila, int col) {
		if(fila+1 == contarFilas()){
			return false;
		}
		if(tablero[fila+1][col] == Casillero.BLANCAS){
			return encontrarPrimerNegroAbajo(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerNegroAbajo(int fila, int col){
		for(int i=fila+2; i<contarFilas(); i++){
			if(tablero[i][col] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[i][col] == Casillero.NEGRAS){
				System.out.println("Primer negro columna hacia abajo. Fila: " + i + "Columna: " + col);
				return i;
			}
		}
		return -1;
	}

	private boolean checkColNegroAbajo(int fila, int col) {
		if(fila+1 == contarFilas()){
			return false;
		}
		if(tablero[fila+1][col] == Casillero.NEGRAS) {
			return encontrarPrimerBlancoAbajo(fila, col) > 0;
		}
		return false;
	}

	private int encontrarPrimerBlancoAbajo(int fila, int col){
		for(int i=fila+2; i<contarFilas(); i++){
			if(tablero[i][col] == Casillero.LIBRE){
				return -1;
			}

			if(tablero[i][col] == Casillero.BLANCAS){
				System.out.println("Primer blanco columna hacia abajo. Fila: " + i + "Columna: " + col);
				return i;
			}
		}
		return -1;
	}


	private boolean checkDiagBlancoArribaIzq(int fila, int col) {
		if(fila+ 1 == contarFilas() || col +1 == contarColumnas()){
			return false;
		}
		if(tablero[fila+1][col+1] == Casillero.BLANCAS){
			System.out.println("Encontrada blanca en diagonal arriba izq. fila: " + (fila+1) + " columna: " + (col+1));
			if(encontrarPrimerNegroDiagArribaIzq(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerNegroDiagArribaIzq(int fila, int col){
		int j= col+2;
		for (int i= fila+2; i< tablero.length; i++){
				if(j >= tablero[i].length){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.NEGRAS){
					System.out.println("Primer negro diagonal arriba izq. Fila: " + i + "Columna: " + j);
					return j;
				}

			if(!(j+1 == contarColumnas())){
				j++;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagBlancoArribaDer(int fila, int col) {
		if(fila+1 == contarFilas() || col - 1 < 0){
			return false;
		}
		if(tablero[fila+1][col-1] == Casillero.BLANCAS){
			System.out.println("Encontrada blanca en diagonal arriba der. fila: " + (fila+1) + " columna: " + (col - 1));
			if(encontrarPrimerNegroDiagArribaDer(fila,col) > 0){
				return true;
			}
		}
		return false;
	}
	private int encontrarPrimerNegroDiagArribaDer(int fila, int col){
		int j= col-2;
		for (int i= fila+2; i< tablero.length; i++){
				if(j < 0){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.NEGRAS){
					System.out.println("Primer negro diagonal arriba der. Fila: " + i + "Columna: " + j);
					return j;
				}

			if(!(j-1 < 0)){
				j--;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagNegroArribaIzq(int fila, int col) {
		if(fila + 1 == contarFilas() || col + 1 == contarColumnas()){
			return false;
		}
		if(tablero[fila+1][col+1] == Casillero.NEGRAS) {
			System.out.println("Encontrada negra en diagonal arriba izq. fila: " + (fila+1) + " columna: " + (col+1));
			if(encontrarPrimerBlancoDiagArribaIzq(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerBlancoDiagArribaIzq(int fila, int col){
		int j= col+2;
		for (int i= fila+2; i< tablero.length; i++){

				if(j >= tablero[i].length){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.BLANCAS){
					System.out.println("Primer blanco diagonal arriba izq. Fila: " + i + "Columna: " + j);
					return j;
				}

			if(!(j+1 == contarColumnas())){
				j++;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagNegroArribaDer(int fila, int col) {
		if(fila+1 == contarFilas() || col - 1 < 0){
			return false;
		}
		if(tablero[fila+1][col-1] == Casillero.NEGRAS){
			System.out.println("Encontrada negra en diagonal arriba der. fila: " + (fila+1) + " columna: " + (col-1));
			if(encontrarPrimerBlancoDiagArribaDer(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerBlancoDiagArribaDer(int fila, int col){
		int j = col-2;
		for (int i= fila+2; i< tablero.length; i++){

				if(j < 0){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.BLANCAS){
					System.out.println("primer blanco diagonal arriba der. Fila: " + i + "Columna: " + j);
					return j;
				}
			if(!(j-1 < 0 )){
				j--;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagBlancoAbajoIzq(int fila, int col) {
		if(fila - 1 < 0 || col +1 == contarColumnas()){
			return false;
		}
		if(tablero[fila-1][col+1] == Casillero.BLANCAS){
			System.out.println("Encontrada blanca en diagonal abajo izq. fila: " + (fila-1) + " columna: " + (col+1));
			if(encontrarPrimerNegroDiagAbajoIzq(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerNegroDiagAbajoIzq(int fila, int col){
		int j = col+2;
		for (int i= fila-2; i >= 0; i--){

				if(j >= tablero[i].length){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.NEGRAS){
					System.out.println("primer negro diagonal abajo izq. Fila: " + i + "Columna: " + j);
					return j;
				}
			if(!(j+1 == contarColumnas())){
				j++;
			}else{
				return -1;
			};
		}
		return -1;
	}
	private boolean checkDiagBlancoAbajoDer(int fila, int col) {
		if(fila-1 < 0 || col - 1 < 0){
			return false;
		}
		if(tablero[fila-1][col-1] == Casillero.BLANCAS){
			System.out.println("Encontrada blanca en diagonal abajo der. fila: " + (fila-1) + " columna: " + (col-1));
			if(encontrarPrimerNegroDiagAbajoDer(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerNegroDiagAbajoDer(int fila, int col){
		int j = col-2;
		for (int i= fila-2; i >= 0; i--){
				if(j < 0){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.NEGRAS){
					System.out.println("primer negra diagonal abajo der. Fila: " + i + "Columna: " + j);
					return j;
				}
			if(!(j-1 < 0)){
				j--;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagNegroAbajoIzq(int fila, int col) {
		if(fila - 1 < 0 || col + 1 == contarColumnas()){
			return false;
		}
		if(tablero[fila-1][col+1] == Casillero.NEGRAS) {
			System.out.println("Encontrada negra en diagonal abajo izq. fila: " + (fila-1) + " columna: " + (col+1));
			if(encontrarPrimerBlancoDiagAbajoIzq(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerBlancoDiagAbajoIzq(int fila, int col){
		int j=col+2;
		for (int i= fila-2; i >= 0; i--){

				if(j >= tablero[i].length){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.BLANCAS){
					System.out.println("primer blanca diagonal abajo izq. Fila: " + i + "Columna: " + j);
					return j;
				}
			if(!(j+1 == contarColumnas())){
				j++;
			}else{
				return -1;
			};
		}
		return -1;
	}

	private boolean checkDiagNegroAbajoDer(int fila, int col) {
		if(fila-1 < 0 || col - 1 < 0){
			return false;
		}
		if(tablero[fila-1][col-1] == Casillero.NEGRAS){
			System.out.println("Encontrada negra en diagonal abajo der. fila: " + (fila-1) + " columna: " + (col-1));
			if(encontrarPrimerBlancoDiagAbajoDer(fila,col) > 0){
				return true;
			}
		}
		return false;
	}

	private int encontrarPrimerBlancoDiagAbajoDer(int fila, int col){
		int j= col -2;
		for (int i= fila-2; i >= 0; i--){

				if(j < 0 ){
					return -1;
				}

				if(tablero[i][j] == Casillero.LIBRE){
					return -1;
				}

				if(tablero[i][j] == Casillero.BLANCAS){
					System.out.println("Primer blanco diagonal abajo der. Fila: " + i + "Columna: " + j);
					return j;
				}
			if(!(j-1 < 0)){
				j--;
			}else{
				return -1;
			};
		}
		return -1;
	}




	/**
	 * pre : la posición indicada por (fila, columna) puede ser 
	 *		 ocupada por una ficha. 
	 *       'fila' está en el intervalo [1, contarFilas()].
	 * 		 'columna' está en el intervalor [1, contarColumnas()].
	 * 		 y aún queda un Casillero.VACIO en la columna indicada. 
	 * post: coloca una ficha en la posición indicada.

	 * @param fila
	 * @param columna
	 */
	public void colocarFicha(int fila, int columna) {
		System.out.println("================================================");
			int filaArray = fila-1;
			int columnaArray = columna-1;
			if(getJugadorActual().equalsIgnoreCase(getJugadorBlancas())) {
				tablero[filaArray][columnaArray] = Casillero.BLANCAS;
				comerNegras(filaArray,columnaArray);
				setJugadorActual(getJugadorNegras());
			} else if (getJugadorActual().equalsIgnoreCase(getJugadorNegras())) {
				tablero[filaArray][columnaArray] = Casillero.NEGRAS;
				comerBlancas(filaArray,columnaArray);
				setJugadorActual(getJugadorBlancas());
			}
		System.out.println("================================================");
	}

	private void comerNegras(int fila, int columna) {
		comerNegrasFilaHaciaIzq(fila, columna);
		comerNegrasFilaHaciaDer(fila,columna);
		comerNegrasColumnaHaciaArriba(fila,columna);
		comerNegrasColumnaHaciaAbajo(fila,columna);
		comerNegrasDiagHaciaAbajoIzq(fila,columna);
		comerNegrasDiagHaciaAbajoDer(fila,columna);
		comerNegrasDiagHaciaArribaIzq(fila,columna);
		comerNegrasDiagHaciaArribaDer(fila,columna);
	}

	private void comerBlancas(int fila, int columna){
		comerBlancasFilaHaciaIzq(fila,columna);
		comerBlancasFilaHaciaDer(fila,columna);
		comerBlancasColumnaHaciaArriba(fila,columna);
		comerBlancasColumnaHaciaAbajo(fila,columna);
		comerBlancasDiagHaciaAbajoIzq(fila,columna);
		comerBlancasDiagHaciaAbajoDer(fila,columna);
		comerBlancasDiagHaciaArribaIzq(fila,columna);
		comerBlancasDiagHaciaArribaDer(fila,columna);
	}

	private void comerNegrasFilaHaciaIzq(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoIzq(fila, columna);
		if(posFinal > 0){
			for(int j = columna -1; j > posFinal; j--){
				if(tablero[fila][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[fila][j] == Casillero.NEGRAS){
					tablero[fila][j] = Casillero.BLANCAS;
				}
			}
		}
	}

	private void comerNegrasFilaHaciaDer(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoDer(fila, columna);
		if(posFinal > 0){
			for(int j = columna +1; j < posFinal; j++){
				if(tablero[fila][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[fila][j] == Casillero.NEGRAS){
					tablero[fila][j] = Casillero.BLANCAS;
				}
			}
		}
	}

	private void comerNegrasColumnaHaciaArriba(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoArriba(fila, columna);
		if(posFinal > 0){
			for(int i = fila -1; i > posFinal; i--){
				if(tablero[i][columna] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][columna] == Casillero.NEGRAS){
					tablero[i][columna] = Casillero.BLANCAS;
				}
			}
		}
	}

	private void comerNegrasColumnaHaciaAbajo(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoAbajo(fila, columna);
		if(posFinal > 0){
			for(int i = fila +1; i < posFinal; i++){
				if(tablero[i][columna] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][columna] == Casillero.NEGRAS){
					tablero[i][columna] = Casillero.BLANCAS;
				}
			}
		}
	}

	private void comerBlancasFilaHaciaIzq(int fila, int columna) {
		int posFinal = encontrarPrimerNegroIzq(fila, columna);
		if(posFinal > 0){
			for(int j = columna -1; j > posFinal; j--){
				if(tablero[fila][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[fila][j] == Casillero.BLANCAS){
					tablero[fila][j] = Casillero.NEGRAS;
				}
			}
		}
	}

	private void comerBlancasFilaHaciaDer(int fila, int columna) {
		int posFinal = encontrarPrimerNegroDer(fila, columna);
		if(posFinal > 0){
			for(int j = columna +1; j < posFinal; j++){
				if(tablero[fila][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[fila][j] == Casillero.BLANCAS){
					tablero[fila][j] = Casillero.NEGRAS;
				}
			}
		}
	}

	private void comerBlancasColumnaHaciaArriba(int fila, int columna) {
		int posFinal = encontrarPrimerNegroArriba(fila, columna);
		if(posFinal > 0){
			for(int i = fila -1; i > posFinal; i--){
				if(tablero[i][columna] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][columna] == Casillero.BLANCAS){
					tablero[i][columna] = Casillero.NEGRAS;
				}
			}
		}
	}

	private void comerBlancasColumnaHaciaAbajo(int fila, int columna) {
		int posFinal = encontrarPrimerNegroAbajo(fila, columna);
		if(posFinal > 0){
			for(int i = fila +1; i < posFinal; i++){
				if(tablero[i][columna] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][columna] == Casillero.BLANCAS){
					tablero[i][columna] = Casillero.NEGRAS;
				}
			}
		}
	}

	private void comerNegrasDiagHaciaAbajoIzq(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoDiagArribaIzq(fila,columna);
		if(posFinal > 0){
			int i=fila+1;
			int j= columna+1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.NEGRAS){
					tablero[i][j] = Casillero.BLANCAS;
				}
				j++;
				i++;
			}
		}
	}

	private void comerNegrasDiagHaciaAbajoDer(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoDiagArribaDer(fila,columna);
		if(posFinal > 0){
			int i=fila+1;
			int j= columna-1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.NEGRAS){
					tablero[i][j] = Casillero.BLANCAS;
				}
				j--;
				i++;
			}
		}
	}

	private void comerNegrasDiagHaciaArribaIzq(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoDiagAbajoIzq(fila,columna);
		if(posFinal > 0){
			int i=fila-1;
			int j= columna+1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.NEGRAS){
					tablero[i][j] = Casillero.BLANCAS;
				}
				j++;
				i--;
			}
		}
	}

	private void comerNegrasDiagHaciaArribaDer(int fila, int columna) {
		int posFinal = encontrarPrimerBlancoDiagAbajoDer(fila,columna);
		if(posFinal > 0){
			int i=fila-1;
			int j= columna-1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.NEGRAS){
					tablero[i][j] = Casillero.BLANCAS;
				}
				j--;
				i--;
			}
		}
	}

	private void comerBlancasDiagHaciaAbajoIzq(int fila, int columna) {
		int posFinal = encontrarPrimerNegroDiagArribaIzq(fila,columna);
		if(posFinal > 0){
			int i=fila+1;
			int j= columna+1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.BLANCAS){
					tablero[i][j] = Casillero.NEGRAS;
				}
				j++;
				i++;
			}
		}
	}

	private void comerBlancasDiagHaciaAbajoDer(int fila, int columna) {
		int posFinal = encontrarPrimerNegroDiagArribaDer(fila,columna);
		if(posFinal > 0){
			int i=fila+1;
			int j= columna-1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.BLANCAS){
					tablero[i][j] = Casillero.NEGRAS;
				}
				j--;
				i++;
			}
		}
	}

	private void comerBlancasDiagHaciaArribaIzq(int fila, int columna) {
		int posFinal = encontrarPrimerNegroDiagAbajoIzq(fila,columna);
		if(posFinal > 0){
			int i=fila-1;
			int j= columna+1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.BLANCAS){
					tablero[i][j] = Casillero.NEGRAS;
				}
				j++;
				i--;
			}
		}
	}

	private void comerBlancasDiagHaciaArribaDer(int fila, int columna) {
		int posFinal = encontrarPrimerNegroDiagAbajoDer(fila,columna);
		if(posFinal > 0){
			int i=fila-1;
			int j= columna-1;
			while(j != posFinal){
				if(tablero[i][j] == Casillero.LIBRE){
					return;
				}
				if(tablero[i][j] == Casillero.BLANCAS){
					tablero[i][j] = Casillero.NEGRAS;
				}
				j--;
				i--;
			}
		}
	}


	/**
	 * post: devuelve la cantidad de fichas negras en el tablero.
	 */
	public int contarFichasNegras() {
		int contador = 0;
		for(int i = 0; i<tablero.length; i++){
			for(int j= 0; j<tablero[i].length; j++){
				if(tablero[i][j] == Casillero.NEGRAS){
					contador++;
				};
			};
		};
		return contador;
	}
	
	/**
	 * post: devuelve la cantidad de fichas blancas en el tablero.
	 */
	public int contarFichasBlancas() {

		int contador = 0;
		for(int i = 0; i<tablero.length; i++){
			for(int j= 0; j<tablero[i].length; j++){
				if(tablero[i][j] == Casillero.BLANCAS){
					contador++;
				};
			};
		};
		return contador;
	}
	
	/**
	 * post: indica si el juego terminó porque no existen casilleros vacíos o
	 *       ninguno de los jugadores puede colocar una ficha.
	 */

	public boolean termino() {
		if(tableroLleno() || noPuedeMover){
			return true;
		} else {
			return false;
		}
	}

	private boolean tableroLleno() {
		for(int i = 0; i<tablero.length; i++){
			for(int j= 0; j<tablero[i].length; j++){
				if(tablero[i][j] == Casillero.LIBRE){
					return false;
				}
			};
		};
		return true;
	}

	/**
	 * post: indica si el juego terminó y tiene un ganador.
	 */
	public boolean hayGanador() {
		if(termino()){
			if(contarFichasBlancas() == contarFichasNegras()){
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * pre : el juego terminó.
	 * post: devuelve el nombre del jugador que ganó el juego.
	 */
	public String obtenerGanador() {
		if(hayGanador()){
			if(contarFichasBlancas() > contarFichasNegras()) {
				return jugadorBlancas;
			}else{
				return jugadorNegras;
			}
		}
		return null;
	}
}
