# Chat-no-gpt

Java 11 console-based client/server chat application that exchanges text messages over TCP sockets.

[![Java](https://img.shields.io/badge/Java-11-blue?style=flat-square)](#technology-stack)
[![Category](https://img.shields.io/badge/Category-Client%2FServer-lightgrey?style=flat-square)](#architecture)
[![Status](https://img.shields.io/badge/Academic%20Project-2023-orange?style=flat-square)](#academic-context)
[![Transport](https://img.shields.io/badge/Transport-TCP-green?style=flat-square)](#architecture)
![Year](https://img.shields.io/badge/Year-2024-6c757d?style=flat-square)

> [!NOTE]
> This repository contains an academic project originally developed during earlier programming studies. It is preserved as a record of the technical knowledge, design decisions, and development experience acquired at the time rather than presented as a current production-ready system.

## Overview

The repository contains a console chat system composed of a server and multiple Eclipse client workspaces. The server accepts socket connections and manages connected clients; each client prompts for a username, connects to the server, and exchanges messages through a small text protocol.

## Features

- Accepts multiple client connections on the server’s configured TCP port.
- Validates usernames for length, spaces, `@` characters, and uniqueness among connected users.
- Sends messages to all other connected clients.
- Sends targeted messages to one or more named users using `@username` prefixes.
- Displays and broadcasts the current list of connected users.
- Allows a client to leave the session with the `EXIT` command.
- Provides console feedback for login errors, connection failures, received messages, and disconnections.

## Technology Stack

- **Language:** Java
- **Runtime and compiler target:** Java 11
- **User interface:** `java.util.Scanner` and standard console input/output
- **Networking:** `java.net.Socket` and `java.net.ServerSocket`
- **Project tooling:** Eclipse Java project metadata (`.project`, `.classpath`)

## Architecture

The implementation uses a threaded client/server design:

- `Server/chat-nogpt-server` starts a `ServerSocket` on port `3333` and creates a `GestoreUtente` thread for each accepted connection.
- `Client1/chat-nogpt-client`, `Client2/chat-nogpt-client`, and `Client3/chat-nogpt-client` contain client workspace copies with the same package structure and console entry point.
- Client input and output are handled by separate thread classes, while the server stores connected `Client` instances in its runtime configuration.
- Client and server command enums define the text protocol used for login, message delivery, broadcasts, targeted delivery, and user-list updates.

## Project Structure

```text
Progetto Client-Server chat no-gpt/
├── Client1/chat-nogpt-client/
│   ├── src/it/volta/ts/ulivisamuel/chat_nogpt_client/
│   └── .classpath
├── Client2/chat-nogpt-client/
├── Client3/chat-nogpt-client/
└── Server/chat-nogpt-server/
    ├── src/it/volta/ts/ulivisamuel/nogpt_server/
    └── .classpath
```

The `bin` directories contain Eclipse-generated compiled classes. The three client directories are separate workspace copies rather than separate client implementations documented by different commands.

## Getting Started

### Prerequisites

- A Java Development Kit (JDK) 11 installation.
- A POSIX shell with `find` and `xargs` for the command-line compilation examples below, or Eclipse configured to use Java 11.

### Compile from the command line

Run these commands from the repository root. They compile the server and the first client into temporary directories outside the repository:

```bash
mkdir -p /tmp/chat-no-gpt-build/server /tmp/chat-no-gpt-build/client

find 'Progetto Client-Server chat no-gpt/Server/chat-nogpt-server/src' \
  -name '*.java' -print0 |
  xargs -0 javac -d /tmp/chat-no-gpt-build/server

find 'Progetto Client-Server chat no-gpt/Client1/chat-nogpt-client/src' \
  -name '*.java' -print0 |
  xargs -0 javac -encoding ISO-8859-1 -d /tmp/chat-no-gpt-build/client
```

The client compilation specifies `ISO-8859-1` because the committed Java source contains legacy encoded characters. The Eclipse project metadata declares Java 11 as its source, compliance, and target level.

### Run the server and a client

Start the server in one terminal:

```bash
java -cp /tmp/chat-no-gpt-build/server it.volta.ts.ulivisamuel.nogpt_server.Main
```

Start one or more clients in separate terminals:

```bash
java -cp /tmp/chat-no-gpt-build/client it.volta.ts.ulivisamuel.chat_nogpt_client.Main
```

The client configuration defaults to `127.0.0.1:3333`, matching the server’s default port. When prompted, enter a username between 4 and 20 characters, without spaces or `@`. After login:

- enter ordinary text to broadcast it to the other connected clients;
- enter `@username message` to target a user, or combine multiple `@username` prefixes;
- enter `LIST` to request the connected-user list;
- enter `EXIT` to disconnect.

## Testing

No automated test sources or test runner configuration are included in the repository. The verified command-line compilation steps above provide a basic build check for the server and the `Client1` source tree.

## Project Status and Scope

The Git history records the main development sequence from October 18 through October 29, 2023, ending with a commit titled `Versione pre test`. The repository is therefore documented as a historical academic implementation. It does not include a dependency manifest, automated test suite, deployment configuration, or documented production hardening.

## License

This project is shared for educational and portfolio purposes. All rights reserved unless otherwise stated.
