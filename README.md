# Multi-Threaded Java TCP Server (Base Structure)

Un'architettura robusta in Java per la gestione di comunicazioni di rete basate sul protocollo **TCP**. Questo progetto implementa un server multi-thread in grado di gestire più connessioni simultanee, fungendo da solida base per lo sviluppo di sistemi di messaggistica, chat room o sistemi distribuiti.

## 🏛️ Architettura del Progetto
Il sistema è diviso in tre componenti principali per garantire la separazione delle responsabilità (*Separation of Concerns*):

1. **`ServerTCP.java`**: Il cuore del sistema. Gestisce l'apertura della porta, l'ascolto delle connessioni in entrata e il mantenimento di una lista sincronizzata di client attivi.
2. **`ConnessioneClient.java`**: Un modulo thread-safe che isola la logica di ogni singola sessione. Ogni client ha il proprio thread dedicato, evitando che il blocco di un utente influenzi gli altri.
3. **`ClientTCP.java`**: Un'interfaccia CLI per l'utente finale che permette di stabilire una connessione e comunicare in tempo reale con il server.



## ✨ Caratteristiche Tecniche
- **Multithreading**: Utilizzo della classe `Thread` per la gestione parallela dei client.
- **Sincronizzazione**: Gestione sicura delle risorse condivise (ArrayList dei client) tramite blocchi `synchronized` per prevenire *Race Conditions*.
- **Protocollo Echo**: Implementazione di base che valida la corretta ricezione e l'integrità dei pacchetti dati.
- **Graceful Shutdown**: Gestione della chiusura dei socket e dei flussi di I/O per prevenire leak di memoria o porte occupate.

## 🛠️ Tecnologie Utilizzate
- **Linguaggio**: Java (JDK 8 o superiore)
- **Networking**: `java.net.Socket`, `java.net.ServerSocket`
- **I/O**: `java.util.Scanner`, `java.io.PrintWriter`

## 🚀 Come Eseguire il Progetto
Assicurati di avere il JDK installato sul tuo sistema.

1. **Compilazione**:
   Apri il terminale nella cartella del progetto e compila tutti i file:
   ```bash
   javac *.java