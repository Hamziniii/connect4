import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;

	int port = 5555;
	String ip = "127.0.0.1";

	ObjectOutputStream out;
	ObjectInputStream in;

	static SimpleObjectProperty<CFourInfo> c = new SimpleObjectProperty<>(new CFourInfo());
	
	private Consumer<Serializable> callback;
	private static Consumer<Serializable> callback2;
	private Consumer<Serializable> callback3;
	Client(Consumer<Serializable> call, Consumer<Serializable> call2, Consumer<Serializable> call3){
	
		callback = call;
		callback2 = call2;
		callback3 = call3;
		c.addListener((o, oV, nV) -> {
			System.out.println("C has been changed!!");
			// WHY DOES THIS ONLY RUN ONCE??????
		});
	}

	static void setV(CFourInfo _c) {
		c.set(_c);
		callback2.accept(_c);
	}
	
	public void run() {
		
		try {
		socketClient= new Socket(ip, port);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				Object o = in.readObject();
				System.out.println(o.getClass());
				if(o instanceof String)
					callback.accept((String) o);
				else if (o instanceof  CFourInfo) {
					boolean shouldEnd = c.get().gameEnded;
					if(c.get().currentPlayer != -1)
						((CFourInfo) o).currentPlayer = c.get().currentPlayer;
					c.set((CFourInfo) o);
					if(!shouldEnd && ((CFourInfo) o).gameEnded) {
						try
						{
							Thread.sleep(1000 * 4);
						}
						catch(InterruptedException ex)
						{
							Thread.currentThread().interrupt();
						}
						callback3.accept(3);
						c.set(new CFourInfo());
					}
				}
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(CFourInfo data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playAgain() {
		try {
			out.writeObject("play");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
