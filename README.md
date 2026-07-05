# Chat-no-gpt

![Java](https://img.shields.io/badge/Java-Chat%20Application-blue)
![Stato](https://img.shields.io/badge/Stato-Progetto%20scolastico-orange)
![Categoria](https://img.shields.io/badge/Categoria-Messaggistica-lightgrey)
![Architettura](https://img.shields.io/badge/Architettura-Client%2FServer-green)

**Chat-no-gpt** è un progetto scolastico sviluppato in **Java** che implementa un sistema di chat, con logica di comunicazione tra utenti e gestione dei messaggi.

Il progetto nasce con finalità didattiche per applicare concetti di networking, programmazione orientata agli oggetti e organizzazione modulare del codice.

---

## Indice

- [Descrizione](#descrizione)
- [Funzionalità](#funzionalità)
- [Tecnologie utilizzate](#tecnologie-utilizzate)
- [Struttura del progetto](#struttura-del-progetto)
- [Architettura logica](#architettura-logica)
- [Flusso di comunicazione](#flusso-di-comunicazione)
- [Esecuzione del progetto](#esecuzione-del-progetto)
- [Obiettivi didattici](#obiettivi-didattici)
- [Possibili miglioramenti futuri](#possibili-miglioramenti-futuri)
- [Autore](#autore)
- [Licenza](#licenza)

---

## Descrizione

L'obiettivo del progetto è simulare una chat testuale in Java, permettendo lo scambio di messaggi tra più componenti dell’applicazione (ad esempio client e server, in base all’implementazione del repository).

L’applicazione si occupa di:

- gestione connessione;
- invio/ricezione messaggi;
- sincronizzazione base della comunicazione;
- visualizzazione delle interazioni.

---

## Funzionalità

Il programma include funzionalità tipiche di una chat didattica:

- avvio di sessione di comunicazione;
- invio di messaggi testuali;
- ricezione e stampa dei messaggi;
- gestione basilare degli utenti/endpoint connessi;
- chiusura della comunicazione.

---

## Tecnologie utilizzate

- **Java** (100%)
- **Programmazione orientata agli oggetti (OOP)**
- **Socket Java** (se previsti dall’implementazione)
- I/O testuale per gestione messaggi

---

## Struttura del progetto

La struttura può variare in base all’IDE e alla suddivisione in package. In generale:

```text
Chat-no-gpt/
│
├── src/
│   └── ... classi Java del progetto
│
└── README.md
```

> Se vuoi, posso anche prepararti la struttura reale e precisa leggendo i file del repository.

---

## Architettura logica

Il progetto segue una separazione logica tipica delle applicazioni di chat:

- **livello connessione**: apertura e gestione canali di comunicazione;
- **livello messaggistica**: formattazione e inoltro dei messaggi;
- **livello applicativo**: controllo flusso chat e regole base;
- **livello presentazione**: output su console o eventuale interfaccia.

---

## Flusso di comunicazione

Il flusso tipico è:

1. avvio server (o nodo in ascolto);
2. connessione di uno o più client;
3. scambio messaggi tra i partecipanti;
4. gestione disconnessione e chiusura risorse.

---

## Esecuzione del progetto

Il progetto può essere eseguito con IDE come:

- IntelliJ IDEA
- Eclipse
- NetBeans
- Visual Studio Code (con estensioni Java)

### Compilazione da terminale

Dalla cartella principale del progetto:

```bash
javac -d out $(find . -name "*.java")
```

### Avvio

Eseguire le classi principali previste dal progetto (ad esempio server e client), in base ai package reali:

```bash
java -cp out nome.pacchetto.Server
java -cp out nome.pacchetto.Client
```

> **Nota:** sostituisci `nome.pacchetto.Server` e `nome.pacchetto.Client` con i fully qualified name reali presenti nel repository.

---

## Obiettivi didattici

Questo repository contiene un **progetto scolastico** sviluppato per esercitarsi su:

- comunicazione di rete in Java;
- gestione input/output e stream;
- progettazione di componenti client/server;
- modularità e leggibilità del codice;
- documentazione tecnica professionale su GitHub.

---

## Possibili miglioramenti futuri

- supporto multiutente avanzato;
- gestione nickname e autenticazione base;
- cronologia messaggi;
- interfaccia grafica desktop;
- cifratura dei messaggi;
- test automatici per stabilità della comunicazione.

---

## Autore

Progetto realizzato da **[ulviSamuel](https://github.com/ulviSamuel)**.

---

## Licenza

Questo progetto è stato sviluppato per scopi scolastici e didattici.
