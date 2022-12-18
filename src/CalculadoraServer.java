
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//Iniciamos un servidor que escuche en el puerto 9999
		ServerSocket listener = new ServerSocket(9999);

		System.out.println("Servicidor iniciado");
		try {
			//Obligamos que el servidor este escuchando siempre a noser que se cerremos consola control c
			while(true) {
				//Creamos un socket que estara escuchando(acepatara las peticiones que reciba el servidor
				Socket socket = listener.accept();
				
				//Leer el dato del cliente que llega al socket
				ObjectInputStream inputObj = new ObjectInputStream(socket.getInputStream());
				ObjectNum obj = (ObjectNum) inputObj.readObject();
				
				int clientDataU = 0;
				int clientDataD = 0;
				float result = 0f;
				boolean containsZeros = false;
				
				//Comprobamos si el objeto contiene numeros
				if(isNumeric(obj)) {
					clientDataU = Integer.parseInt(obj.getNumeroUno().toString());
					clientDataD = Integer.parseInt(obj.getNumeroDos().toString());
					//Comprobamos si la opcion elegida es division y los numeros no son 0 y 0
					containsZeros = comprobateIndet(clientDataU,clientDataD);
					//Preparamos operaciones en funcion de la opcion seleccionada
					if(!obj.getOpcion().contains("4") ||
							(obj.getOpcion().contains("4") && !containsZeros)) {
						//Evitamos que quiera dividir y sea zero ambos datos
						result = generateFinalResult(obj.getOpcion(),clientDataU,clientDataD);
					}
				}
				
				//Creamos el String de salida
				PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
				String finalText = generateLiteral(obj.getOpcion(),containsZeros, result);
				if(finalText != "") {
					out.println("Su opción seleccionada fue "+finalText);
				}else {
					out.println("Por favor, debe introducir una opción válida");
				}
				//Cerramos el socket
				socket.close();
			}
		}finally {
			//Cerramos el server que esta escuchando
			listener.close();
		}
	}
	
	public static boolean isNumeric(ObjectNum data){
		try {
			Integer.parseInt(data.getNumeroUno().toString());
			Integer.parseInt(data.getNumeroDos().toString());
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	public static boolean comprobateIndet(int numU, int numD) {
		if(numD == 0) {
			return true;
		}
		return false;
	}
	
	public static float generateFinalResult(String opcion, int first, int second) {
		//Casteamos a Float
		float numU = first;
		float numD = second;
		if(opcion.contains("1")) {//Multiplicar
			return numU * numD;
		}else if(opcion.contains("2")) {//Sumar
			return numU + numD;
		}else if(opcion.contains("3")) {//Restar
			return numU - numD;
		}else if(opcion.contains("4")) {//Dividir
			return numU / numD;
		}else {
			return 0f;
		}
	}
	
	public static String generateLiteral(String opcion, boolean containsZeros, float result) {
		if(opcion.contains("1")) {//Multiplicar
			return "multiplicar y el resultado es "+result;
		}else if(opcion.contains("2")) {//Sumar
			return "sumar y el resultado es "+result;
		}else if(opcion.contains("3")) {//Restar
			return "restar y el resultado es "+result;
		}else if(opcion.contains("4")) {//Dividir
			if(containsZeros) {
				return "dividir y no puede generarse el resultado"
						+ " porque los numeros introducidos generan indeterminacion";
			}
			return "dividir y el resultado es "+result;
		}else {
			return "";
		}
	}
}
