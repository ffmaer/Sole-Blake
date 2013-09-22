
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

///////////////////////////////////////////////////// DemoDrawing2
@SuppressWarnings("serial")
public class SoleDemo extends JFrame {

    //===================================================== fields

	private static JTextArea output = new JTextArea();
    private JTextArea input = new JTextArea();
    private static JTextArea outInput = new JTextArea();
    private JTextArea config = new JTextArea();
    private static JTextArea hash = new JTextArea();
    
    private JLabel l1 = new JLabel();
    private JLabel l2 = new JLabel();
    private JLabel l3 = new JLabel();
    private JLabel l4 = new JLabel();
    private JLabel l5 = new JLabel();
    //================================================ Constructor
    public SoleDemo() {

        
        JButton changeColorBtn = new JButton("Very SOLE!");
        changeColorBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String[] conf = config.getText().split(",");
                	int bits = Integer.parseInt(conf[0]);
                	int solemode = Integer.parseInt(conf[1]);
                	int decode = Integer.parseInt(conf[2]);
                	try {
                		
                		if(decode == 1) {
                			String inNum = outInput.getText().trim();
                			output.setText("You have entered " + inNum.split(" ").length + " numbers.");
                    		String returnString = comSole.soleDecodeString(bits,solemode,inNum);
                    		output.setText(output.getText()+ "\nSole gives you " + returnString.split(" ").length + " numbers.");
                    		output.setText(output.getText()+ "\nThey are:\n" + returnString);
                		} else {
                			String inNum = input.getText().trim();
                    		output.setText("You have entered " + inNum.split(" ").length + " numbers.");
                    		String returnString = comSole.soleEncodeString(bits,solemode,inNum);
                    		output.setText(output.getText()+ "\nSole gives you " + returnString.split(" ").length + " numbers.");
                    		output.setText(output.getText()+ "\nThey are:\n" + returnString);
                    		hash.setText(comSole.getHash());
                		}
                		
                		
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });

        //... Add components to layout
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        
        changeColorBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(changeColorBtn);
        
        
        l1.setText("Input(enter at least 4 numbers): ");
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(l1);
        
        input.setLineWrap(true);
        input.setText("8 57 17 33 4");
        content.add(input);
        
        l2.setText("Output: ");
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(l2);
        
        output.setLineWrap(true);
        output.setText(" ");
        content.add(output);
        
        l3.setText("Convert ouput back to input: ");
        l3.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(l3);
        
        outInput.setLineWrap(true);
        outInput.setText(" ");
        content.add(outInput);
        
        l4.setText("Hash(SHA-3 finalist Blake 256-bit version): ");
        l4.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(l4);
        
        hash.setLineWrap(true);
        hash.setText(" ");
        content.add(hash);
        
        l5.setText("Config(#bits, mode=1,2, decode=0,1): ");
        l5.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(l5);
        
        config.setLineWrap(true);
        config.setText("6,1,0");
        content.add(config);

        setPreferredSize(new Dimension(800, 600));
        setContentPane(content);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SOLE Demo");
        pack();
    }



    //========================================================== main
    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame window = new SoleDemo();
        window.setVisible(true);;
    }
}
