/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.typingspeedcalculator;

//import com.typingspeed.dao.ResultDAO;
//import com.typingspeed.model.Result;
//import com.typingspeed.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class HistoryFrame extends JFrame {
    private User currentUser;
    private ResultDAO resultDAO;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnBack;

    // Premium Web UI Design Palette matching all other updated frames
    private final Color CANVAS_BG = new Color(250, 250, 252);
    private final Color CARD_BG = Color.WHITE;
    private final Color ACCENT_BLUE = new Color(0, 102, 255);
    private final Color DARK_TEXT = new Color(30, 30, 35);
    private final Color MUTED_TEXT = new Color(120, 120, 128);
    private final Color BORDER_COLOR = new Color(235, 235, 240);

    public HistoryFrame(User user) {
        this.currentUser = user;
        this.resultDAO = new ResultDAO();
        initComponents();
        loadHistoryData();
    }

    private void initComponents() {
        setTitle("Your Typing Performance Logs");
        setSize(750, 520); // Expanded slightly for better row proportions
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(CANVAS_BG);
        setLayout(new BorderLayout(20, 20));

        // 1. TOP HEADER CONTAINER PANEL
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 5, 30));

        JLabel lblTitle = new JLabel("Your Test History");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(DARK_TEXT);

        JLabel lblSubtitle = new JLabel("Track your personal speed progression and consistency scores over time.");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(MUTED_TEXT);

        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);
        add(headerPanel, BorderLayout.NORTH);

        // 2. CENTER PANEL TABLE CONTAINER
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // Column setup with cell modification access disabled cleanly
        String[] columns = {"Test Date & Time", "Speed (WPM)", "Accuracy (%)"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        styleCustomTableElements(); // Instantly overrides native system table layout rendering

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(CARD_BG);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        // 3. BOTTOM FOOTER NAVIGATION PANEL
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 25, 30));

        btnBack = new JButton("Return to Dashboard");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setFocusPainted(false);
        btnBack.setBackground(CARD_BG);
        btnBack.setForeground(DARK_TEXT);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 205), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        footerPanel.add(btnBack);
        add(footerPanel, BorderLayout.SOUTH);

        // Navigation Actions utilizing clean implicit types
        btnBack.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            this.dispose();
        });
    }

    /**
     * Reusable flat styling algorithm overriding generic system JTable lookups
     */
    private void styleCustomTableElements() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(DARK_TEXT);
        table.setBackground(CARD_BG);
        table.setRowHeight(38); // Comfortable text row padding bounds
        table.setShowGrid(true);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(240, 245, 255));
        table.setSelectionForeground(DARK_TEXT);
        table.setFillsViewportHeight(true);

        // Format column row tracking header panel bounds
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(245, 245, 248));
        header.setForeground(DARK_TEXT);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Render cell contents aligned precisely down the middle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(noFocusBorder); // Wipe selection focus frame marks
                return this;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Space columns parameters to cleanly account for detailed timestamps
        table.getColumnModel().getColumn(0).setPreferredWidth(260); // Timestamp width expansion
        table.getColumnModel().getColumn(1).setPreferredWidth(205); 
        table.getColumnModel().getColumn(2).setPreferredWidth(205); 
    }

    private void loadHistoryData() {
        List<Result> history = resultDAO.getUserHistory(currentUser.getId());
        for (Result r : history) {
            // Clean up accuracy presentation string dynamically
            String cleanAccuracy = String.format("%.2f%%", r.getAccuracy()).replace(".00%", "%");
            
            Object[] row = { 
                r.getTestDate(), 
                r.getWpm(), 
                cleanAccuracy 
            };
            model.addRow(row);
        }
    }
}