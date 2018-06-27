import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    static Stage stage;
    static boolean exitAnswer;
    
    public static boolean exitDisplay(String title, String message) {
        stage = new Stage();
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(250);
        stage.setHeight(100);
        stage.setResizable(false);
        
        Label label = new Label();
        label.setText(message);
        
        Button yes = new Button("Yes");
        Button no = new Button("No");
        
        yes.setOnAction(e -> {
            exitAnswer = true;
            stage.close();
        });
        no.setOnAction(e -> {
            exitAnswer = false;
            stage.close();
        });
        
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(yes,no);
        buttons.setAlignment(Pos.CENTER);
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
        
        return exitAnswer;
    }
}
