import javax.swing.JFrame;
public class ClientTest{
	public static void main(String[] args){
		Client rusty;
		rusty = new Client("134.87.133.222");
		rusty.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rusty.startRunning();
	}
}