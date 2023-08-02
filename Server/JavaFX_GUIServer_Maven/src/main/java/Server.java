import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server {

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    int port = 5555;
    private Consumer<Serializable> callback;
    private Consumer<Serializable> callback2;

    int playAgain = 0;


    Server(Consumer<Serializable> call, Consumer<Serializable> call2) {

        callback = call;
        callback2 = call2;
        server = new TheServer();
//		server.start();
    }

    public void startGame() {
        clients.removeAll(Collections.singleton(null));
        if(clients.size() < 2)
            return;
        try {
            playAgain = 0;
            CFourLogic.resetBoard();
            CFourLogic.c.gameStarted = true;
            System.out.println(clients);

            clients.get(0).player = 0;
            clients.get(1).player = 1;
            clients.get(0).out.writeObject(CFourLogic.currentCFI(0));
            clients.get(1).out.writeObject(CFourLogic.currentCFI(1));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public class TheServer extends Thread {

        public void run() {

            try (ServerSocket mysocket = new ServerSocket(port);) {
                System.out.println("Server is waiting for a client!");


                while (true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();


                    count++;
                    CFourLogic.c.playerCount++;

                    clients.removeAll(Collections.singleton(null));
                    if(clients.size() > 1) {
                        try
                        {
                            Thread.sleep(100);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                        startGame();
                    }

                    callback2.accept("");
                }
            }//end of try
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread {


        Socket connection;
        int count;
        int player = -1;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count) {
            this.connection = s;
            this.count = count;
        }

        public void sendBackBoard() {
            try {
                out.writeObject(CFourLogic.currentCFI(player));
            } catch (IOException e) {
//                throw new RuntimeException(e);
            }
        }

        public void updateClients(String message) {
            for (int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                } catch (Exception e) {

                }
            }
        }

        public void updateClientBoards() {
            for (int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.sendBackBoard();
                } catch (Exception e) {

                }
            }
        }

        public void run() {

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open");
            }

            updateClients("new client on server: client #" + count);


            while (true) {
                try {
                    Object o = in.readObject();
                    String data;
                    if(o instanceof CFourInfo) {
                        CFourInfo c = ((CFourInfo) o);
                        data = "" + ((CFourInfo) o).turn;
                        CFourLogic.c = c;
                        callback.accept(String.format("client %d , player: %d: has placed at (%d, %d)", count, c.currentPlayer, 5 - c.lastMove.getKey(), c.lastMove.getValue()));
                        if(CFourLogic.playerWin(player)) {
                            CFourLogic.c.winner = player;
                            CFourLogic.c.gameEnded = true;
                            callback.accept(String.format("client %d , player: %d: has WON!", count, c.currentPlayer));
                            callback2.accept(1);
                        } else if (CFourLogic.allTied()) {
                            CFourLogic.c.winner = -1;
                            CFourLogic.c.gameEnded = true;
                            callback.accept("No more moves remain, game is tied");
                            callback2.accept(1);
                        }
                        updateClientBoards();
                    }
                    else {
                        data = o.toString();

                        if(data.equals("play")) {
                            playAgain++;
                            CFourLogic.resetBoard();
                            CFourLogic.c.playAgain = true;
                            CFourLogic.c.gameStarted = true;
                            callback2.accept(1);
                            if(playAgain > 1) {
                                playAgain = 0;
                                startGame();
                            }
                        } else {
                            callback.accept("client: " + count + " sent: " + data);
                            updateClients("client #" + count + " said: " + data);
                        }
                    }


                } catch (Exception e) {
                    callback.accept(" client " + count + "....closing down!");
                    updateClients("Client #" + count + " has left the server!");
                    clients.remove(this);
                    CFourLogic.c.playerCount--;
                    callback2.accept("");
                    break;
                }
            }
        }//end of run


    }//end of client thread
}


	
	

	
