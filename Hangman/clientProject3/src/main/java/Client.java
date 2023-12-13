import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {

    //data members needed for each Client
    Socket clientSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    GameData gameData = new GameData();
    Consumer<Serializable> callback;
    int portNumber;

    //setCallback() takes a Consumer object and sets it to be the Client's callback variable
    //which changes what elements of the GUI the callback will update
    public void setCallback(Consumer<Serializable> call) {
        callback = call;
    }

    //Client() takes an integer to represent the port number, and a Consumer object
    //to set the Client's callback variable to
    Client(int port, Consumer<Serializable> call) {
        portNumber = port;
        callback = call;
    }

    //run() is the abstract method that we implement when we extend Thread. In run,
    //we first connect the Socket to the Server, and open up the in / out stream. Then,
    //we wait for the Server to send back data, and then update the GUI through callback.
    public void run() {
        try {
            clientSocket = new Socket("127.0.0.1", portNumber);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            clientSocket.setTcpNoDelay(true);
        } catch(Exception exception) {
            //send to UI that something went wrong when creating Client
            exception.printStackTrace();
            System.exit(1);

        }

        while(true) {
            try {
                gameData = (GameData) in.readObject();
                //update UI
                callback.accept(gameData);
            } catch(Exception exception) {
                //error in reading data from Server
                exception.printStackTrace();
                System.exit(1);
            }
        }

    }

    //send() is used to send data back to the Server. It writes the data to the out stream.
    public void send(GameData newGameData) {
        try {
            out.writeObject(newGameData);
        } catch(Exception exception) {
            //print to UI error in sending data to Server
            exception.printStackTrace();
            System.exit(1);
        }
    }

    //getData() returns the state of the gameData at that moment.
    public GameData getData() {
        return this.gameData;
    }

    //useCallback() is a way for the Client to update the GUI without receiving
    //data from the Server. It invoked callback.accept() to change the GUI elements.
    public void useCallback(GameData gameData) {
        callback.accept(gameData);
    }

}
