import javax.swing.JFrame;

public class ServerTest{
	public static void main(String[] args){
		Server snelgrove = new Server();
		snelgrove.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		snelgrove.startRunning();
	}
}