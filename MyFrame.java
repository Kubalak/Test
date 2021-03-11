import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFrame extends JFrame implements ActionListener
{
    JPanel Panel, First, Second, Third;
    JMenuBar Bar;
    JMenu File, Help;
    JMenuItem Save,About;
    JLabel Finfo, Fpx, Sinfo, Sbit,Tinfo, Thz;
    JTextField W,H,HZ;
    String [] bits = {"8","16","24","32"};
    JComboBox combo;
    JButton Calculate;
    MyFrame()
    {
        Panel = new JPanel();
        First = new JPanel();
        Second = new JPanel();
        Third = new JPanel();
        Finfo = new JLabel("Screen resolution");
        Fpx = new JLabel("px");
        Sinfo =  new JLabel("Color depth");
        Sbit = new JLabel("bits");
        Tinfo = new JLabel("Refresh rate");
        Thz = new JLabel("Hz");
        Calculate = new JButton("Calculate");
        Calculate.setPreferredSize(new Dimension(250,40));
        W = new JTextField();
        W.setPreferredSize(new Dimension(100,25));
        W.setHorizontalAlignment(JTextField.RIGHT);
        H = new JTextField();
        H.setPreferredSize(new Dimension(100,25));
        H.setHorizontalAlignment(JTextField.LEFT);
        HZ = new JTextField();
        HZ.setPreferredSize(new Dimension(35,25));
        combo = new JComboBox(bits);
        combo.setSelectedIndex(3);

        First.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        First.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        First.add(Finfo);
        First.add(W);
        First.add(new JLabel("x"));
        First.add(H);
        First.add(Fpx);

        Second.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        Second.setAlignmentY(JPanel.TOP_ALIGNMENT);
        Second.add(Sinfo);
        Second.add(combo);
        Second.add(Sbit);

        Third.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        Third.setAlignmentY(JPanel.TOP_ALIGNMENT);
        Third.add(Tinfo);
        Third.add(HZ);
        Third.add(Thz);
        Third.add(Calculate);

        Bar = new JMenuBar();
        File = new JMenu("File");
        Help = new JMenu("Help");
        Save = new JMenuItem("Save");
        Save.setIcon(new ImageIcon(this.getClass().getResource("/UI/save.png")));
        About = new JMenuItem("About");
        About.setIcon(new ImageIcon(this.getClass().getResource("/UI/info.png")));
        Help.add(About);
        File.add(Save);
        Bar.add(File);
        Bar.add(Help);

        Save.addActionListener(this);
        About.addActionListener(this);
        Calculate.addActionListener(this);

        Panel.setAlignmentY(JPanel.UNDEFINED_CONDITION);
        Panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        Panel.setLayout(new GridLayout(3,1));
        Panel.add(First);
        Panel.add(Second);
        Panel.add(Third);
        this.setTitle("Calculate v1.0");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/UI/smile.png")).getImage());
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(Bar);
        this.add(Panel);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        String information;
        int depth;
        int w,h;
        int freq;
        boolean OK = true;
        if(e.getSource()==Save)
        {
            if(W.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write screen width !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(H.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write screen height !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(HZ.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write refresh frequency !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(OK)
            {
                try {
                    w = Integer.parseInt(W.getText());
                    if(w<0)w=-w;
                    h = Integer.parseInt(H.getText());
                    if(h<0)h=-h;
                    freq = Integer.parseInt(HZ.getText());
                    if(freq<0)freq=-freq;
                    depth = (combo.getSelectedIndex() + 1) * 8;
                    information = "<!DOCTYPE html><head><meta charset=\"UTF-8\"><title>Summary</title></head><body>";
                    information += "Resolution: " + w + "x" + h + " px<br/>\nColor depth: " + depth + " bits<br/>\nFrequency: " + freq + " Hz<br/>\n";
                    information += "Total number of pixells: "+w*h+"<br/>\n";
                    information += "Data per frame: " + ((double) (depth / 8) * w * h / 1048576) + " MB<br/>\n";
                    information += "Data transfer speed required: " + ((double) (depth) * w * h * freq / 1073741824) + " Gb/s<br/>";
                    information += "</body></html>";
                    JFileChooser fileChooser = new JFileChooser(".");

                    if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
                    {
                        FileWriter file;
                        String path = "";
                        try
                        {
                            path = fileChooser.getSelectedFile().getAbsolutePath();
                            if(!path.endsWith(".html"))path+=".html";
                            file = new FileWriter(path);
                            file.write(information);
                            file.close();
                            JOptionPane.showMessageDialog(null,"File saved successfully!","Success!",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/UI/info.png")));
                        }
                        catch(IOException exception)
                        {
                            JOptionPane.showMessageDialog(null,"Could not create"+path+" file!","Save error!",JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
                catch (NumberFormatException exception)
                {
                    JOptionPane.showInternalMessageDialog(null,exception,"Parsing error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource()==About)
        {
            JOptionPane.showMessageDialog(null,"Simple program to calculate data usage by different graphic settings.","Information",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/UI/info.png")));
        }
        else if(e.getSource()==Calculate)
        {
            if(W.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write screen width !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(H.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write screen height !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(HZ.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null,"You should write refresh frequency !","Error!", JOptionPane.WARNING_MESSAGE);
                OK = false;
            }
            if(OK) {
                try {
                    w = Integer.parseInt(W.getText());
                    if(w<0)w=-w;
                    h = Integer.parseInt(H.getText());
                    if(h<0)h=-h;
                    freq = Integer.parseInt(HZ.getText());
                    if(freq<0)freq=-freq;
                    depth = (combo.getSelectedIndex() + 1) * 8;
                    information = "Resolution: " + w + "x" + h + "\nColor depth: " + depth + " bits\nFrequency: " + freq + "Hz\n";
                    information+= "Total number of pixells: "+w*h+"\n";
                    information += "Data per frame: " + ((double) (depth / 8) * w * h / 1048576) + " MB\n";
                    information += "Data transfer speed required: " + ((double) (depth) * w * h * freq / 1073741824) + " Gb/s";
                    JOptionPane.showMessageDialog(null, information, "Info", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("/UI/info.png")));
                }
                catch (NumberFormatException exception)
                {
                    JOptionPane.showInternalMessageDialog(null,exception,"Parsing error",JOptionPane.ERROR_MESSAGE);
                }
            }
        }




    }
}
