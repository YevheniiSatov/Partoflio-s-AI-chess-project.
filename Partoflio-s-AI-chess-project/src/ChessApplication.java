import chess.gui.ChessGUI;
import javax.swing.SwingUtilities;

/**
 * Main class to launch the Chess Application.
 * This class contains the main method which is the entry point of the application.
 * It uses SwingUtilities.invokeLater to ensure that the GUI creation and updates are done on the Event Dispatch Thread (EDT).
 * @Autor Yevhenii Shatov
 */
public class ChessApplication {
    /**
     * The main method to start the Chess Application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
