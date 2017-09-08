import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 06.06.2017.
 */
public class GUI {
        private JFrame main_frame;
        private JMenuBar menuBar;
        private JTextField teachers;
        private JTextField flows;
        private JTextField nagruzka;
    public void createGUI() {
            main_frame = new JFrame("MAIN FRAME");
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Font font = new Font("Verdana", Font.PLAIN, 11);
            menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            fileMenu.setFont(font);

            JMenuItem updateItem = new JMenuItem("UpdateDatabase");
            updateItem.setFont(font);
            fileMenu.add(updateItem);

            ActionListener on_update = new onUpdateItem();
            updateItem.addActionListener(on_update);

            JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.setFont(font);
            fileMenu.add(exitItem);

            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            menuBar.add(fileMenu);
            main_frame.setJMenuBar(menuBar);
            main_frame.setPreferredSize(new Dimension(640, 480));
            main_frame.pack();
            main_frame.setLocationRelativeTo(null);
            main_frame.setVisible(true);
        }
    public class onUpdateItem implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            final JFrame choose_frame=new JFrame("CHOOSE FILE");
            choose_frame.setPreferredSize(new Dimension(500, 360));
            choose_frame.pack();
            choose_frame.setLocationRelativeTo(null);
            choose_frame.setVisible(true);
            JPanel p = new JPanel();

            flows = new JTextField();
            JButton flows_button = new JButton("Обзор");
            flows_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        flows.setText(fileopen.getSelectedFile().toString());
                    }
                }
            });

            teachers = new JTextField();
            JButton teachers_button = new JButton("Обзор");
            teachers_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        teachers.setText(fileopen.getSelectedFile().toString());
                    }

                }
            });

            nagruzka = new JTextField();
            JButton nagruzka_button = new JButton("Обзор");
            nagruzka_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        nagruzka.setText(fileopen.getSelectedFile().toString());
                    }
                }
            });

            final JButton button_OK = new JButton("OK");
            button_OK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ParcerDB pdb = new ParcerDB(flows.getText(),teachers.getText(), nagruzka.getText());
                    pdb.initConnection("c##nir", "4pm");
                    pdb.deleteDB();
                    pdb.creadeDB();
                    pdb.finilizeConnection();
                    System.out.println("OK!");
                    choose_frame.dispose();
                }
            });

            JButton button_Cancel = new JButton("Cancel");
            button_Cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    choose_frame.dispose();
                }
            });

            choose_frame.add(p);
            p.setLayout(new GridLayout(7,2));
            p.add(new JLabel("Выберите Flows"));
            p.add(new JLabel(""));
            p.add(flows);
            p.add(flows_button);
            p.add(new JLabel("Выберите Teachers"));
            p.add(new JLabel(""));
            p.add(teachers);
            p.add(teachers_button);
            p.add(new JLabel("Выберите Nagruzka"));
            p.add(new JLabel(""));
            p.add(nagruzka);
            p.add(nagruzka_button);
            p.add(button_OK);
            p.add(button_Cancel);
        }
    }
}

