import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * SchoolManagementSystem - Swing version
 * - Gradient buttons
 * - Scaled background image
 * - Draggable icon with shadow
 * - Add / View student dialogs
 *
 * Put background.jpg and icon.png in the same directory (or change the paths).
 */
public class SchoolManagementSystem extends JFrame {
    private final ArrayList<Student> students = new ArrayList<>();
    private BufferedImage backgroundImage;
    private DraggableImage draggableImage;

    public SchoolManagementSystem() {
        super("School Management System");
        loadImages();
        initUI();
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("background.jpg"));
        } catch (IOException e) {
            System.out.println("Warning: background.jpg not found. Using plain background.");
            backgroundImage = null;
        }
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 650);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        // layered pane so we can put background, buttons and draggable image easily
        JLayeredPane layered = new JLayeredPane();
        layered.setLayout(null);
        setContentPane(layered);

        // background panel (paints scaled background)
        BackgroundPanel bgPanel = new BackgroundPanel(backgroundImage);
        bgPanel.setBounds(0, 0, getWidth(), getHeight());
        layered.add(bgPanel, JLayeredPane.DEFAULT_LAYER);

        // button panel (transparent)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        GradientButton addBtn = new GradientButton("Add Student", new Color(72, 201, 176), new Color(46, 134, 193));
        GradientButton viewBtn = new GradientButton("View Students", new Color(241, 196, 15), new Color(230, 126, 34));
        GradientButton exitBtn = new GradientButton("Exit", new Color(231, 76, 60), new Color(192, 57, 43));

        Dimension btnDim = new Dimension(240, 52);
        addBtn.setMaximumSize(btnDim);
        viewBtn.setMaximumSize(btnDim);
        exitBtn.setMaximumSize(btnDim);

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(viewBtn);
        buttonPanel.add(Box.createVerticalStrut(16));
        buttonPanel.add(exitBtn);

        // initial center position for buttonPanel; will be re-centered on resize
        buttonPanel.setBounds((getWidth() - 300) / 2, (getHeight() - 220) / 2, 300, 220);
        layered.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        // draggable image component
        draggableImage = new DraggableImage("icon.png", 80, 80);
        int iconW = draggableImage.getPreferredWidth();
        int iconH = draggableImage.getPreferredHeight();
        draggableImage.setBounds(40, 40, iconW, iconH);
        layered.add(draggableImage, JLayeredPane.DRAG_LAYER);

        // button actions
        addBtn.addActionListener(e -> new AddStudentDialog(this, students));
        viewBtn.addActionListener(e -> new ViewStudentsDialog(this, students));
        exitBtn.addActionListener(e -> System.exit(0));

        // keep components positioned on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                bgPanel.setBounds(0, 0, getWidth(), getHeight());

                // center button panel
                int w = buttonPanel.getWidth();
                int h = buttonPanel.getHeight();
                buttonPanel.setLocation((getWidth() - w) / 2, (getHeight() - h) / 2);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SchoolManagementSystem win = new SchoolManagementSystem();
            win.setVisible(true);
        });
    }

    // --------------------------
    // Student data class
    // --------------------------
    static class Student {
        private final String name;
        private final String rollNumber;
        private final String grade;

        public Student(String name, String rollNumber, String grade) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return String.format("%s  |  Roll: %s  |  Grade: %s", name, rollNumber, grade);
        }
    }

    // --------------------------
    // Background panel draws scaled background image (keeps aspect fill)
    // --------------------------
    static class BackgroundPanel extends JPanel {
        private final BufferedImage image;

        public BackgroundPanel(BufferedImage image) {
            this.image = image;
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                // draw scaled to fill
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                g2.dispose();
            } else {
                // fallback background gradient
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 245, 250), 0, getHeight(), new Color(220, 230, 240));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        }
    }

    // --------------------------
    // GradientButton - nice rounded gradient button
    // --------------------------
    static class GradientButton extends JButton {
        private final Color startColor;
        private final Color endColor;

        public GradientButton(String text, Color startColor, Color endColor) {
            super(text);
            this.startColor = startColor;
            this.endColor = endColor;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD, 15f));
        }

        @Override
        protected void paintComponent(Graphics g) {
            int arc = 18;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // shadow
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            g2.setColor(Color.black);
            g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 6, arc, arc);

            // gradient bg
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight() - 2, arc, arc);

            // subtle border
            g2.setColor(new Color(255, 255, 255, 40));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 3, arc, arc);

            g2.dispose();

            // draw text and other button content
            super.paintComponent(g);
        }

        // keep button text painted on top (super paintComponent will draw content when contentAreaFilled = false it's still drawn)
        @Override
        public void updateUI() {
            super.updateUI();
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
        }
    }

    // --------------------------
    // DraggableImage component (JComponent with mouse drag)
    // --------------------------
    static class DraggableImage extends JComponent {
        private BufferedImage image;
        private int prefW = 100, prefH = 100;
        private int pressX, pressY;

        public DraggableImage(String imagePath, int defaultW, int defaultH) {
            try {
                image = ImageIO.read(new File(imagePath));
                prefW = image.getWidth();
                prefH = image.getHeight();
            } catch (IOException e) {
                System.out.println("Warning: " + imagePath + " not found. Using placeholder.");
                image = null;
                prefW = defaultW;
                prefH = defaultH;
            }

            setSize(prefW, prefH);
            setOpaque(false);

            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pressX = e.getX();
                    pressY = e.getY();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point p = getLocation();
                    int newX = p.x + e.getX() - pressX;
                    int newY = p.y + e.getY() - pressY;
                    // optional bounds check:
                    Container parent = getParent();
                    if (parent != null) {
                        newX = Math.max(0, Math.min(newX, parent.getWidth() - getWidth()));
                        newY = Math.max(0, Math.min(newY, parent.getHeight() - getHeight()));
                    }
                    setLocation(newX, newY);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(prefW, prefH);
        }

        public int getPreferredWidth() {
            return prefW;
        }

        public int getPreferredHeight() {
            return prefH;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // draw shadow
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
            g2.setColor(Color.black);
            g2.fillRoundRect(6, 8, getWidth() - 12, getHeight() - 12, 14, 14);

            // draw image (scaled to component size if necessary)
            g2.setComposite(AlphaComposite.SrcOver);
            if (image != null) {
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            } else {
                // placeholder
                g2.setColor(new Color(120, 140, 160));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(Color.white);
                g2.setFont(getFont().deriveFont(Font.BOLD, 12f));
                FontMetrics fm = g2.getFontMetrics();
                String t = "ICON";
                g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2, (getHeight() + fm.getAscent()) / 2 - 4);
            }
            g2.dispose();
        }
    }

    // --------------------------
    // AddStudentDialog: modal dialog to add a student
    // --------------------------
    static class AddStudentDialog extends JDialog {
        public AddStudentDialog(JFrame owner, ArrayList<Student> list) {
            super(owner, "Add New Student", true);
            JPanel p = new JPanel(new GridBagLayout());
            p.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel nameLbl = new JLabel("Name:");
            JTextField nameField = new JTextField(20);
            JLabel rollLbl = new JLabel("Roll:");
            JTextField rollField = new JTextField(20);
            JLabel gradeLbl = new JLabel("Grade:");
            JTextField gradeField = new JTextField(10);

            gbc.gridx = 0; gbc.gridy = 0; p.add(nameLbl, gbc);
            gbc.gridx = 1; gbc.gridy = 0; p.add(nameField, gbc);
            gbc.gridx = 0; gbc.gridy = 1; p.add(rollLbl, gbc);
            gbc.gridx = 1; gbc.gridy = 1; p.add(rollField, gbc);
            gbc.gridx = 0; gbc.gridy = 2; p.add(gradeLbl, gbc);
            gbc.gridx = 1; gbc.gridy = 2; p.add(gradeField, gbc);

            JButton submit = new JButton("Add");
            submit.addActionListener(e -> {
                String name = nameField.getText().trim();
                String roll = rollField.getText().trim();
                String grade = gradeField.getText().trim();
                if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                list.add(new Student(name, roll, grade));
                dispose();
            });

            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            p.add(submit, gbc);

            setContentPane(p);
            pack();
            setLocationRelativeTo(owner);
            setResizable(false);
            setVisible(true);
        }
    }

    // --------------------------
    // ViewStudentsDialog: modal dialog to show students
    // --------------------------
    static class ViewStudentsDialog extends JDialog {
        public ViewStudentsDialog(JFrame owner, ArrayList<Student> list) {
            super(owner, "Student List", true);
            JTextArea area = new JTextArea(12, 40);
            area.setEditable(false);
            area.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
            StringBuilder sb = new StringBuilder();
            if (list.isEmpty()) {
                sb.append("No students added yet.\n");
            } else {
                int i = 1;
                for (Student s : list) {
                    sb.append(String.format("%2d. %s%n", i++, s.toString()));
                }
            }
            area.setText(sb.toString());

            JScrollPane sp = new JScrollPane(area);
            sp.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            getContentPane().add(sp, BorderLayout.CENTER);

            JPanel bottom = new JPanel();
            JButton close = new JButton("Close");
            close.addActionListener(e -> dispose());
            bottom.add(close);
            getContentPane().add(bottom, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(owner);
            setVisible(true);
        }
    }
}
