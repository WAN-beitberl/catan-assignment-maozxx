package Foo.Bar.Hexagon;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class Board extends JPanel{

    private static final long serialVersionUID = 1L;

    public static int rolltemp = 0;
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    private final int W2 = WIDTH / 2;
    private final int H2 = HEIGHT / 2;

    private Font font = new Font("Arial", Font.BOLD, 24);
    FontMetrics metrics;
    public int matrixOfNumHex[][] = new int[5][];
    public int matrixOfColor[][] = new int[5][];

    public Board() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        drawCircle(g2d, W2, H2, 660, true, true, 0x4488FF, 0);

        drawHexGridAdvanced(g2d, 5, 60);
    }
    private void drawHexGridAdvanced(Graphics g, int n, int r) {
        double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * r;
        double yOff = Math.sin(ang30) * r;
        int h = n / 2;
        int cols = 0;
        int row = 0;
        int col = 0;
        int ran = 0;

        int arrOfTypes[]=new int[6];
        arrOfTypes[0]=1;//Desert
        arrOfTypes[1]=4;//Tree
        arrOfTypes[2]=4;//Sheep
        arrOfTypes[3]=4;//White
        arrOfTypes[4]=3;//Brick
        arrOfTypes[5]=3;//Iron
        Random randy=new Random();

        matrixOfNumHex[0]=new int[3];
        matrixOfNumHex[1]=new int[4];
        matrixOfNumHex[2]=new int[5];
        matrixOfNumHex[3]=new int[4];
        matrixOfNumHex[4]=new int[3];

        matrixOfColor[0]=new int[3];
        matrixOfColor[1]=new int[4];
        matrixOfColor[2]=new int[5];
        matrixOfColor[3]=new int[4];
        matrixOfColor[4]=new int[3];

        int arrOfNumHex[]={1,2,2,2,2,0,2,2,2,2,1};//all the numbers are need to be +2

        for (row = 0; row < h; row ++) {
            cols = h + row + 1;
            for (col = 0; col < cols; col++) {

                while (true)
                {
                    ran = randy.nextInt(6);
                    if(arrOfTypes[ran]!=0)
                    {
                        break;
                    }
                }
                arrOfTypes[ran]--;
                g.setColor(new Color(ColorToHexByNumber(ran)));
                matrixOfColor[row][col] = ran;

                if(ran!=0) {
                    while (true) {
                        ran = randy.nextInt(11);
                        if (arrOfNumHex[ran] != 0) {
                            break;
                        }
                    }
                    arrOfNumHex[ran]--;
                    matrixOfNumHex[row][col] = ran + 2;

                    drawHex(g, (int) (W2 + xOff * (-cols + (col * 2 + 1))), (int) (H2 - yOff * (n - cols) * 3), r, ran + 2);
                }
                else {
                    matrixOfNumHex[row][col] = 0;
                    drawHex(g, (int) (W2 + xOff * (-cols + (col * 2 + 1))), (int) (H2 - yOff * (n - cols) * 3), r, 0);
                }
            }
        }
        for (row = h; row < n; row++) {
            cols = n - row + h;
            for (col = 0; col < cols; col++) {
                while (true)
                {
                    ran = randy.nextInt(6);
                    if(arrOfTypes[ran]!=0)
                    {
                        break;
                    }
                }
                arrOfTypes[ran]--;
                g.setColor(new Color(ColorToHexByNumber(ran)));
                matrixOfColor[row][col] = ran;

                if(ran!=0) {
                    while (true) {
                        ran = randy.nextInt(11);
                        if (arrOfNumHex[ran] != 0) {
                            break;
                        }
                    }
                    arrOfNumHex[ran]--;
                    matrixOfNumHex[row][col] = ran + 2;

                    drawHex(g, (int) (W2 + xOff * (-cols + (col * 2 + 1))), (int) (H2 + yOff * (n - cols) * 3), r, ran + 2);
                }
                else {
                    matrixOfNumHex[row][col] = 0;
                    drawHex(g, (int) (W2 + xOff * (-cols + (col * 2 + 1))), (int) (H2 + yOff * (n - cols) * 3), r, 0);
                }
            }
        }
    }

    private int ColorToHexByNumber(int x)
    {
        switch (x)
        {
            case 0:
                return 0xBDAE6A;
            case 1:
                return 0x04521E;
            case 2:
                return 0x44EE7A;
            case 3:
                return 0xFFED41;
            case 4:
                return 0x910505;
            case 5:
                return 0x444D4C;
        }
        return 0;
    }

    private void drawHex(Graphics g,  int x, int y, int r,int number) {
        Hexagon hex = new Hexagon(x, y, r);
        String text = ""+number;
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        g.fillPolygon(hex);
        g.setColor(new Color(0xFFDD88));
        g.drawPolygon(hex);
        g.setColor(new Color(0xFFFFFF));
        if(number!=0 )
            g.drawString(text, x - w/2, y + h/2);
    }

    public void drawCircle(Graphics2D g, int x, int y, int diameter,
                           boolean centered, boolean filled, int colorValue, int lineThickness) {
        drawOval(g, x, y, diameter, diameter, centered, filled, colorValue, lineThickness);
    }

    public void drawOval(Graphics2D g, int x, int y, int width, int height,
                         boolean centered, boolean filled, int colorValue, int lineThickness) {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        int x2 = centered ? x - (width / 2) : x;
        int y2 = centered ? y - (height / 2) : y;

        if (filled)
            g.fillOval(x2, y2, width, height);
        else
            g.drawOval(x2, y2, width, height);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

    public static void main(String[] args) {
        // new SplashScreenDemo(); // make work after all its the loading screen gesture control
        JFrame f = new JFrame();
        Board p = new Board();
        //drawButton(f);
        JButton button = new JButton("ROLL DICE"); // setting a button for dice roll
        //button.setBackground(Color.blue);
        p.add(button);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(200,200);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        JLabel num1 = new JLabel(); // show to roll
        //num1.setSize(100,100);
        p.add(num1);
        button.addActionListener(e -> cliker(num1));



        f.setContentPane(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public static void cliker(JLabel num1)
    {
        int dise1 = (int)(Math.random()*(6-1)+1);
        int dise2 = (int)(Math.random()*(6-1)+1);
        System.out.println(dise1+","+dise2);
        int sum = dise1+dise2;
        num1.setText(" "+sum+" ");
        rolltemp = sum;
        System.out.println(rolltemp);

    }

    public static void getplayers()
    {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter name and color");
        String userName1 = myObj.nextLine();
        String userColor1 = myObj.nextLine();
        System.out.println("Enter name and color");
        String userName2 = myObj.nextLine();
        String userColor2 = myObj.nextLine();
    }
    public static void drawButton(JFrame frame)
    {
        //frame = new JFrame();
        JButton button = new JButton("ROLL DICE");
        button.setBounds(50,50,101,101);
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100,100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}