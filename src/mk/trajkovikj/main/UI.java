package mk.trajkovikj.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class UI {

    private static JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private File file = null;
    private JTextField divisions;



    public void create() {

        mainFrame = new JFrame("Loading Sprite Creator");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(6, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(350,100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());




        final JFileChooser  fileDialog = new JFileChooser();
        JButton showFileDialogButton = new JButton("Select image");

        showFileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileDialog.getSelectedFile();
                    statusLabel.setText("File Selected :" + file.getName());
                } else {
                    statusLabel.setText("Open command cancelled by user." );
                }
            }
        });

        controlPanel.add(showFileDialogButton);

        final JRadioButton vertical = new JRadioButton("Vertical", true);
        final JRadioButton horizontal = new JRadioButton("Horizontal");

        vertical.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.VERTICAL;
            }
        });

        horizontal.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.HORIZONTAL;
            }
        });

        ButtonGroup group = new ButtonGroup();

        group.add(vertical);
        group.add(horizontal);

        controlPanel.add(vertical);
        controlPanel.add(horizontal);


        divisions = new JTextField(6);
        controlPanel.add(divisions);

        final JButton btnProcess = new JButton("Process");
        btnProcess.addActionListener(e -> {
            if(! isFileValid()) JOptionPane.showMessageDialog(mainFrame, "You must select an image!");

            BufferedImage image = ImageUtils.readBufferedImage(file);
            Integer divisions = getDivisions();
            if(divisions == null) return;

            ImageUtils imageUtils = new ImageUtils();
            imageUtils.processImage(image, divisions);

        });
        controlPanel.add(btnProcess);

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    public boolean isFileValid() {
        if(file != null) return true;
        else return false;
    }

    public Integer getDivisions() {
        try {
            return Integer.parseInt(divisions.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Enter number into division field!");
            return null;
        }
    }
}
