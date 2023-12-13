import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController implements Initializable {

    @FXML
    private TextField portNumber;

    @FXML
    private Button connect;

    @FXML
    private ListView<String> listView;

    static Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void connectMethod(ActionEvent e) throws IOException {
        String s = portNumber.getText();
        int port = Integer.parseInt(s);

        server = new Server(port, data -> {
            Platform.runLater(() -> {
                listView.getItems().add(data.toString());
            });
        });


        connect.setDisable(true);
        portNumber.setDisable(true);
    }

}
