package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Message;
import model.MessageImportance;
import model.MessageStakeholder;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by tschakki on 04.05.15.
 */
public class MessageController implements Initializable {

    private ObservableList<Message> messageData = FXCollections.observableArrayList();

    @FXML
    private TableView<Message> messageTable;
    @FXML
    private TableColumn<Message, MessageImportance> importanceOfMessageColumn;
    @FXML
    private TableColumn<Message, LocalDateTime> receivedAtColumn;
    @FXML
    private TableColumn<Message, Boolean> readStatusColumn;
    @FXML
    private TableColumn<Message, String> senderColumn;
    @FXML
    private TableColumn<Message, String> subjectColumn;
    @FXML
    private TextArea textArea;
    @FXML
    private Label recipientsLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label dateLabel;

    /*private final Node importanceIcon = new ImageView(
            new Image(getClass().getResourceAsStream("../cat32.png"))
    );*/

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the person table with the two columns.
        importanceOfMessageColumn.setCellValueFactory(cellData -> cellData.getValue().importanceOfMessageProperty());
        importanceOfMessageColumn.setCellFactory(cellData -> new TableCell<Message, MessageImportance>() {
            @Override
            protected void updateItem(MessageImportance item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    if (item.getImportanceAsString().equals("low")) {
                        setGraphic(new ImageView(
                                new Image(getClass().getResourceAsStream("../low.png"))
                        ));
                    } else if (item.getImportanceAsString().equals("normal")) {
                        setGraphic(new ImageView(
                                new Image(getClass().getResourceAsStream("../normal.png"))
                        ));
                    } else {
                        setGraphic(new ImageView(
                                new Image(getClass().getResourceAsStream("../high.png"))
                        ));
                    }

                }
            }
        });
        receivedAtColumn.setCellValueFactory(cellData -> cellData.getValue().receivedAtProperty());
        readStatusColumn.setCellValueFactory(cellData -> cellData.getValue().readStatusProperty().asObject());
        senderColumn.setCellValueFactory(cellData -> cellData.getValue().senderProperty().get().nameProperty());
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        generateMessages();
        System.out.println("initialize messagetable");
        messageTable.setItems(messageData);

        // Clear person details.
        showMessageDetails(null);

        // Listen for selection changes and show the person details when changed.
        messageTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMessageDetails(newValue));

    }



    private void generateMessages() {
        Message nachricht = new Message();
        nachricht.setSubject("Betreff der Nachricht");
        nachricht.setSender(new MessageStakeholder("Catz", "flying@cat.org"));
        nachricht.setImportanceOfMessage(MessageImportance.HIGH);
        nachricht.setReceivedAt(LocalDateTime.now());
        nachricht.setText("Hallo sagt die Catz!");
        nachricht.setRecipients(new MessageStakeholder("Tschakki", "tschakki@mehli.org"));
        Message nachricht2 = new Message();
        nachricht2.setSubject("zweiter Betreff der Nachricht");
        nachricht2.setSender(new MessageStakeholder("Mehli", "mehli@entogrow.org"));
        nachricht2.setImportanceOfMessage(MessageImportance.NORMAL);
        nachricht2.setRecipients(new MessageStakeholder("Tschakki", "tschakki@mehli.org"));
        nachricht2.setReceivedAt(LocalDateTime.now());
        nachricht2.setText("Viele Grüße von Mehli");
        messageData.add(nachricht);
        messageData.add(nachricht2);
        messageData.add(nachricht);
        messageData.add(nachricht2);
        messageData.add(nachricht);
        messageData.add(nachricht2);
    }

    public ObservableList<Message> getMessageData() {
        return messageData;
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param message the person or null
     */
    private void showMessageDetails(Message message) {
        if (message != null) {
            // Fill the labels with info from the person object.
            fromLabel.setText(String.valueOf(message.getSender().getMailAddress()));
            recipientsLabel.setText(String.valueOf(message.getRecipients().getMailAddress()));
            dateLabel.setText(String.valueOf(message.getReceivedAt()));
            subjectLabel.setText(message.getSubject());
            textArea.setText(message.getText());

            // TODO: We need a way to convert the birthday into a String!
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            fromLabel.setText("");
            recipientsLabel.setText("");
            dateLabel.setText("");
            subjectLabel.setText("");
            textArea.setText("");
        }
    }
}