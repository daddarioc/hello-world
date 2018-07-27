package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
	/** Holder of a switchable vista. */
    @FXML
    private StackPane windowHolder;

    /**
     * Replaces the window displayed in the window holder with a new vista.
     *
     * @param node the window node to be swapped in.
     */
    public void setWindow(Node node) {
        windowHolder.getChildren().setAll(node);
    }
}
