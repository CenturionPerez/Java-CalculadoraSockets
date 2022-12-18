import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CalculadoraClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		//Pedir la IP al cliente por consola
		System.console().readLine("Iniciando calculadora...");
		String serverAddress = System.console().readLine("Introduce la dirección IP: ");
		
		//Bucle infinito a no ser que salgamos con control c
		while(true) {
			//Solicitamos que necesita realizar
			System.console().readLine("Preparando operaciones disponibles...");
			System.console().readLine(""
					+ "o Multiplicar -> pulse 1\n"
					+ "o Sumar -> pulse 2\n"
					+ "o Restar -> pulse 3\n"
					+ "o Dividir -> pulse 4");
			String opcion = System.console().readLine("Introduce la opcion que desea: ");
			
			//Creamos el socket
			Socket socket = new Socket(serverAddress,9999);
			
			//Enviamos el dato al servidor
			ObjectOutputStream inputObj = new ObjectOutputStream(socket.getOutputStream());
			//Obtenemos los datos del cliente
			String numberU = System.console().readLine("Introduce el primer numero entero: ");
			String numberD = System.console().readLine("Introduce el segundo numero entero: ");
			ObjectNum obj = new ObjectNum(opcion,numberU,numberD);
			//Realizamos envio
			inputObj.writeObject(obj);
	
			//Recibimos respuesta del servidor
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String answer = input.readLine();
			
			//Mostramos por consola la respuesta del servidor
			System.out.println(answer);
			
			//Cerrar el socket
			socket.close();
		}

	}

}
