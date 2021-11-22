import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author NGUYEN TRUNG HAU
 */
public class Wave extends JPanel implements ChangeListener, Runnable {

    Thread t;
    JFrame f, ctr;
    int s_pos_x, s_pos_y, e_pos_x, e_pos_y;
    int wndw_x = 1000, wndw_y = 500;
    int a = 0, intensty = 150, density = 5, dnst_mul = 5;

    JSlider Intst, Dnst, D_mul;

    Wave() {
        f = new JFrame("Wave Generator");
        t = new Thread(this);

        ctr = new JFrame("controller");
        ctr.setSize(400, 300);
        Intst = new JSlider(1, 300);
        Intst.setPaintTrack(true);
        Intst.setPaintTicks(true);
        Intst.setPaintLabels(true);
        Intst.setMajorTickSpacing(299);
        Intst.setMinorTickSpacing(10);
        Intst.addChangeListener(this);

        Dnst = new JSlider(1, 10);
        Dnst.setPaintTrack(true);
        Dnst.setPaintTicks(true);
        Dnst.setPaintLabels(true);
        Dnst.setMajorTickSpacing(1);
        Dnst.setMinorTickSpacing(9);
        Dnst.addChangeListener(this);

        D_mul = new JSlider(1, 10);
        D_mul.addChangeListener(this);

        ctr.setLayout(new GridLayout(6, 1));
        ctr.add(new JLabel("Intensity"));
        ctr.add(Intst);
        ctr.add(new JLabel("Density"));
        ctr.add(Dnst);
        ctr.add(new JLabel("Density Multiplier"));
        ctr.add(D_mul);
        t.start();

        f.getContentPane().add(this);

        f.setSize(wndw_x, wndw_y);
        f.setVisible(true);

        ctr.setLocationRelativeTo(null);
        ctr.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void stateChanged(ChangeEvent e) {

        intensty = Intst.getValue();
        density = Dnst.getValue();
        dnst_mul = D_mul.getValue();
        repaint();
    }

    public void run() {
        while (true) {
            try {
                a += 1;
                t.sleep(100);
                wndw_x = f.getWidth();
                wndw_y = f.getHeight();
                repaint();
            } catch (InterruptedException e) {;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        int St_x = wndw_x / 2;
        int St_y = wndw_y / 2;

        int off_x = -wndw_x / 2;

        int Lst_x = 0, Lst_y = 0;
        Lst_x = St_x + off_x;
        Lst_y = St_y + (int) Math.round(intensty * Math.sin(off_x + a));

        while (off_x <= wndw_x / 2) {
            off_x += density * dnst_mul;
            g.drawLine(Lst_x, Lst_y, St_x + off_x, St_y + (int) Math.round(intensty * Math.sin(off_x + a)));

            Lst_x = St_x + off_x;
            Lst_y = St_y + (int) Math.round(intensty * Math.sin(off_x + a));

        }

    }

    public static void main(String[] arg) {
        Wave w = new Wave();
    }
}
