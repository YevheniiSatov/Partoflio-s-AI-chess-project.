package chess.gui;

import javax.swing.JOptionPane;

/**
 * Class providing a dialog to select the game mode.
 *  * @Autor Yevhenii Shatov
 */
public class GameStartDialog {
    /**
     * Displays a dialog to select the game mode.
     *
     * @return An integer representing the selected game mode:
     *         0 for playing with a friend on the same computer,
     *         1 for playing against AI,
     *         2 for AI vs AI.
     */
    public static int showStartDialog() {
        Object[] options = { "Play with a friend on this computer", "Play against AI", "AI vs AI" };
        int choice = JOptionPane.showOptionDialog(null, "Select game mode:", "Start Game",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        return choice;
    }
}
