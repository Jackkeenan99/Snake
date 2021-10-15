import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	GameFrame(){
		this.add(new GamePanel()); // adds panel to the JFrame
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // fits JFrame around added components
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
