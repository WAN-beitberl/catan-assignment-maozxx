import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.*;

public class CatanBoard extends JComponent {
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;
    private static final int HEX_SIZE = 50;
    private static final int HEX_WIDTH = (int) (HEX_SIZE * Math.sqrt(3) / 2);

    private Color[][] hexColors = new Color[NUM_ROWS][NUM_COLS];

    public CatanBoard() {
        setPreferredSize(new Dimension(NUM_COLS * HEX_WIDTH + HEX_WIDTH / 2, NUM_ROWS * HEX_SIZE + HEX_SIZE / 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                drawHexagon(g2d, col, row, hexColors[row][col]);
            }
        }
    }

    private void drawHexagon(Graphics2D g2d, int col, int row, Color color) {
        int xPos = (int) (col * HEX_WIDTH + (row % 2 == 0 ? HEX_WIDTH / 2 : 0));
        int yPos = row * HEX_SIZE / 2 + HEX_SIZE / 2;

        AffineTransform transform = g2d.getTransform();
        g2d.translate(xPos, yPos);

        Path2D hex = new Path2D.Double();
        hex.moveTo(0, -HEX_SIZE / 2);
        for (int i = 1; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            hex.lineTo(HEX_WIDTH / 2 * Math.cos(angle), -HEX_SIZE / 2 * Math.sin(angle));
        }
        hex.closePath();

        g2d.setColor(color);
        g2d.fill(hex);

        g2d.setTransform(transform);
    }

    public void setHexColor(int row, int col, Color color) {
        hexColors[row][col] = color;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Catan Universe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        CatanBoard board = new CatanBoard();
        board.setHexColor(0, 0, Color.RED);
        board.setHexColor(0, 1, Color.ORANGE);
        board.setHexColor(1, 0, Color.YELLOW);
        board.setHexColor(2, 0, Color.RED);

        frame.add(board, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}

