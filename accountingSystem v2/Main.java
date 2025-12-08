import javax.swing.SwingUtilities;

class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           Frame frame = new Frame();
           frame.show();
        });
    }
}
