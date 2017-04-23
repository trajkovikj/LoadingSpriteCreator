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
    private File outDirectory = null;
    private JTextField divisions;



    public void create() {

        mainFrame = new JFrame("Loading Sprite Creator");
        mainFrame.setSize(400,400);
        //mainFrame.setLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(350,100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(8, 1));




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

        final JRadioButton verticalTop = new JRadioButton("Vertical from Top", true);
        final JRadioButton verticalBottom = new JRadioButton("Vertical from Bottom");
        final JRadioButton horizontalLeft = new JRadioButton("Horizontal from Left");
        final JRadioButton horizontalRight = new JRadioButton("Horizontal from Right");

        verticalTop.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.VERTICAL_TOP;
            }
        });

        verticalBottom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.VERTICAL_BOTTOM;
            }
        });

        horizontalLeft.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.HORIZONTAL_LEFT;
            }
        });

        horizontalRight.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                ImageUtils.sliceType = SliceType.HORIZONTAL_RIGHT;
            }
        });

        ButtonGroup group = new ButtonGroup();

        group.add(verticalTop);
        group.add(verticalBottom);
        group.add(horizontalLeft);
        group.add(horizontalRight);

        controlPanel.add(verticalTop);
        controlPanel.add(verticalBottom);
        controlPanel.add(horizontalLeft);
        controlPanel.add(horizontalRight);


        divisions = new JTextField("Divisions",6);
        controlPanel.add(divisions);


        final JFileChooser  outDirectoryFileDialog = new JFileChooser();
        outDirectoryFileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton btnshowOutDirectoryFileDialog = new JButton("Select output directory");

        btnshowOutDirectoryFileDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = outDirectoryFileDialog.showOpenDialog(mainFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    outDirectory = outDirectoryFileDialog.getSelectedFile();
                }
            }
        });

        controlPanel.add(btnshowOutDirectoryFileDialog);


        final JButton btnProcess = new JButton("Process");
        btnProcess.addActionListener(e -> {
            if(! isFileValid()) JOptionPane.showMessageDialog(mainFrame, "You must select an image!");

            BufferedImage image = ImageUtils.readBufferedImage(file);
            Integer divisions = getDivisions();
            if(divisions == null) return;

            ImageUtils imageUtils = new ImageUtils();
            String outDirectoryPath = outDirectory == null || outDirectory.getAbsolutePath().isEmpty() ? "C:\\LoadingSpriteCreator" : outDirectory.getAbsolutePath();
            imageUtils.processImage(image, divisions, outDirectoryPath);

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
