//ConnessioneClient.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

/** class ConnessioneClient extends Thread
 *  Gestisce la logica di comunicazione per un singolo Client.
 *  estente Thread per permettere la gestione asincrona di molteplici connessioni simultanee.
 **/
public class ConnessioneClient extends Thread {
    private Socket client;
	private ServerTCP server;
	private Scanner inDalClient;
	private PrintWriter outVersoClient;

    /** ConnessioneClient(Socket socket, ServerTCP server)
     *  COSTRUTTORE
     *  param <- socket: il socket specifico del client.
     *  param <- server: riferimento al server padre per la gestione dello stato.
     **/
    public ConnessioneClient(Socket socket, ServerTCP server) {
		this.client = socket;
		this.server = server;
	}

    /** getClient()
     *  GETTER di 'client'
     *  output -> restituisce l'oggetto Socket del client.
     **/
    public Socket getClient() {
		return client;
	}

    /** run()
     *  Metodo core, eseguito all'avvio del Thread.
     *  Gestisce l'intero ciclo di vita della comunicazione.
     **/
    @Override
	public void run() {
		preparazione();
		risposta();
		chiusura();
		server.chiudiConnessione(this);
	}

    /** preparazione()
     *  Inizializza i flussi di comunicazione (InputStream e OutputStream).
     *  param <- nessuno.
     *  output -> prepara lo Scanner e il PrintWriter per lo scambio dati.
     **/
    public void preparazione() {
		try {
			inDalClient = new Scanner(client.getInputStream());
			outVersoClient = new PrintWriter(client.getOutputStream(), true);
		} catch (Exception e) {
			System.out.println("Errore nella preparazione della connessione");
			chiusura();
		}
	}

    /** risposta()
     *  Ciclo di ascolto: legge i messaggi in entrata e invia una risposta (Echo).
     *  param <- nessuno (ascolta dal socket).
     *  output -> stampa i log sul server e invia stringhe di risposta al client.
     **/
    public void risposta() {
		try {
			while (inDalClient.hasNextLine()) {
				String inputLine = inDalClient.nextLine(); //INPUT dal Client
				System.out.println("LOG [" + client.getInetAddress() + "]: " + inputLine);

                if (inputLine.equalsIgnoreCase("exit")) {
                    outVersoClient.println("Connessione in chiusura... Arrivederci!");
                    break;
                }

                //---LOGICA DI RISPOSTA---
                outVersoClient.println("Echo: " + inputLine);
			}
			
		} catch (Exception e) {
			System.out.println("Errore durante la comunicazione con il client");
			chiusura();
		}
	}

    /** chiusura()
     *  Rilascia le risorse chiudendo i flussi e il socket individuale del client.
     *  param <- nessuno.
     *  output -> chiude gli oggetti I/O e termina il socket.
     **/
    public void chiusura() {
		try {
			if (inDalClient != null)
				inDalClient.close();
			if (outVersoClient != null)
				outVersoClient.close();
			if (client != null)
				client.close();
		} catch (Exception e) {
			System.out.println("Errore nella chiusura della connessione");
		}
	}
}