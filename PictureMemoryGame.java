import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;



public class PictureMemoryGame extends JFrame {
    private ArrayList<String> imagePaths;
    private ArrayList<String> cardImages;
    private JButton[] cardButtons;
    private int numberOfMatches;
    private int firstCardIndex = -1;
    private int secondCardIndex;
    public int moves;
    private Timer timer;
    public int gridSize;

    public PictureMemoryGame(int gridSize){
        this.gridSize = gridSize;
        setTitle("Sports Picture Memory Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imagePaths = new ArrayList<>();
        cardImages = new ArrayList<>();
        cardButtons = new JButton[gridSize];

        String[] allImagePaths = {
                "8Ball.png",
                "Baseball.png",
                "Basketball.png",
                "Bicycle.png",
                "f1car.png",
                "Football.png",
                "Golfball.png",
                "Hockeypuck.png",
                "Motorbike.png",
                "SoccerBall.png",
                "Tennisball.png",
                "Volleyball.png",
        };

        if (gridSize == 12) {
            for (int i = 0; i < 6; i++){
                imagePaths.add(allImagePaths[i]);
            }
        } else if (gridSize == 24) {
            Collections.addAll(imagePaths, allImagePaths);
        } else {
            JOptionPane.showMessageDialog(null, "Unacceptable Grid Proportion");
            System.exit(1);
        }

        imagePaths.addAll(imagePaths);

        Collections.shuffle(imagePaths);
        Collections.shuffle(cardImages);

        JPanel cardPanel = new JPanel(new GridLayout(6,4));

        for (int i = 0; i < cardButtons.length; i++) {
            final int index = i;
            cardButtons[i] = new JButton();
            cardButtons[i].setIcon(new ImageIcon("Question.png"));
            cardButtons[i].addActionListener(e -> handleCardClick(index));
            cardPanel.add(cardButtons[i]);
        }

        add(cardPanel);
        moves = 0;
        numberOfMatches = 0;
        timer = new Timer();

    }

    private void handleCardClick(int index) {
        if (cardButtons[index].getIcon() == null) {
            return; //Card is matched so show nothing else
        }
        if (firstCardIndex == -1) {
            firstCardIndex = index;
            cardButtons[firstCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));
        } else {
            secondCardIndex = index;
            cardButtons[secondCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));

            moves++;

            if (imagePaths.get(firstCardIndex).equals(imagePaths.get(secondCardIndex))){
                numberOfMatches++;
                firstCardIndex = -1;
                secondCardIndex = -1;

                if (numberOfMatches == imagePaths.size() / 2) {
                    handleGameWin();
                }
            } else {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        cardButtons[firstCardIndex].setIcon(new ImageIcon("Question.png"));
                        cardButtons[secondCardIndex].setIcon(new ImageIcon("Question.png"));
                        firstCardIndex = -1;
                        secondCardIndex = -1;
                    }
                }, 5000);

            }

        }

    }
    private void handleGameWin() {
        JOptionPane.showMessageDialog(this, "Congrats! You Did It! Completed the puzzle in: " + moves + " moves!");
        resetGame();
    }
    private void resetGame() {
        for (int i = 0; i < cardButtons.length; i++) {
            cardButtons[i].setIcon(new ImageIcon("Question.png"));
        }
        firstCardIndex = -1;
        secondCardIndex = -1;
        moves = 0;
        numberOfMatches = 0;
        Collections.shuffle(imagePaths);
    }
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(JOptionPane.showInputDialog("Enter a Size for the Grid (Choose 12 or 24):"));
        if (gridSize != 12 && gridSize != 24) {
            JOptionPane.showMessageDialog(null, "Incorrect Grid Size");
            System.exit(1);
        }
         SwingUtilities.invokeLater(() -> new PictureMemoryGame(gridSize).setVisible(true));
    }
}
