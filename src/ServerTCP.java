//ServerTCP.java
import java.net.*;
import java.util.ArrayList;

/** class ServerTCP
 *  Rappresenta il Server principale che rimane in ascolto sulla porta specificata.
 *  Gestisce un elenco di client attivi e delega la comunicazione ai Thread.
 **/
public class ServerTCP {
    private int porta;
	private ServerSocket server;
	private ArrayList<ConnessioneClient> clientConnessi;

    /** ServerTCP(int porta)
     *  COSTRUTTORE
     *  param <- porta: il numero della porta su cui il server si metterà in ascolto.
     **/
	public ServerTCP(int porta) {
		this.porta = porta;
		clientConnessi = new ArrayList<>();
	}

    /** attendi()
     *  Inizializza il ServerSocket e avvia il ciclo infinito di accettazione client.
     *  param <- nessuno.
     *  output -> per ogni connessione riuscita, istanzia e avvia un nuovo Thread.
     **/
    public void attendi() {
		try {
			server = new ServerSocket(porta);
			System.out.println("Server avviato e in attesa di connessioni...");

			while (true) {
				Socket client = server.accept();
				System.out.println("Connessione accettata da " + client.getInetAddress());

				ConnessioneClient clientThread = new ConnessioneClient(client, this);
				clientConnessi.add(clientThread);
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /** chiudiConnessione(ConnessioneClient connessione)
     *  Rimuove in modo sicuro un client dalla lista di quelli attualmente connessi.
     *  L'uso di 'synchronized' previene conflitti tra thread diversi.
     *  param <- connessione: l'istanza del thread da rimuovere dalla lista.
     **/
	public synchronized void chiudiConnessione(ConnessioneClient connessione) {
		clientConnessi.remove(connessione);
		System.out.println("Connessione rimossa: " + connessione.getClient().getInetAddress());
	}

    /** chiusura()
     *  Termina il server e chiude tutte le connessioni client ancora aperte.
     *  param <- nessuno.
     *  output -> arresta il server e libera la porta occupata.
     **/
    public void chiusura() {
		try {
			for (int i = 0; i < clientConnessi.size(); i++) {
				ConnessioneClient client = clientConnessi.get(i);
				client.chiusura();
			}
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    /** main(String[] args)
     *  Punto di ingresso dell'applicazione lato Server.
     **/
    public static void main(String[] args) {
		ServerTCP s = new ServerTCP(1234);
		s.attendi();
		s.chiusura();
	}
}
