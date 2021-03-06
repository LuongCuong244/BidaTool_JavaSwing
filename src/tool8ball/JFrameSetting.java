/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tool8ball;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Administrator
 */
public class JFrameSetting extends JFrame {

    private JButton btnSave;
    private JButton btnCancel;

    private JScrollPane jScPane;
    private JPanel container;
    private ToolJFrame toolJFrame;

    private JPanel jpnOuterBorder;
    private JLabel txtColorOuter, txtLineSizeOuter;
    private JSpinner jSlLineSizeOuter;
    private JButton btnColorOuter;

    private JPanel jpnLineBall;
    private JLabel txtColor, txtLineSize;
    private JSpinner jSlLineSize;
    private JButton btnColor;

    private JPanel jpnRatioBall;
    private JTextField edtRatioBall;

    private JPanel jpnFocus;
    private ButtonGroup groupFocus;
    private JRadioButton jrbAuto;
    private JRadioButton jrbMousePoint;

    private JPanel jpnSoDuongDapBang;
    private JTextField edtSoDuongDapBang;
    
    private JPanel jpnSpaceMenuAndTable;
    private JTextField edtSpaceMenuAndTable;
    
    private JPanel jpnOnTop;
    private ButtonGroup groupOnTop;
    private JRadioButton jrbYes;
    private JRadioButton jrbNo;
    
    private JPanel jpnAlwaysFocus;
    private ButtonGroup groupAlwaysFocus;
    private JRadioButton jrbYesFocus;
    private JRadioButton jrbNoFocus;

    public JFrameSetting(ToolJFrame toolJFrame) {
        this.toolJFrame = toolJFrame;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(((int) dimension.getWidth() - 350) / 2, ((int) dimension.getHeight() - 440) / 2, 350, 440);
        this.setResizable(false);
        this.setLayout(null);
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        jScPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScPane.setBounds(0, 0, 330, 350);
        container.setBackground(Color.darkGray);
        this.add(jScPane);

        setJButton();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelOuterBorder();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelLineBall();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelRatioBall();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelFocus();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelSoDuongDapBang();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelSpaceMenuAndTable();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelOnTop();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        setJPanelAlwaysFocus();
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        
        readTime();
        this.addWindowListener(new WindowAdapter() {            
            @Override
            public void windowClosing(WindowEvent e) {
                if(Properties.isOnTop){
                    toolJFrame.setAlwaysOnTop(true);                    
                }
                if(JPanelMenu.isFocus){
                    Properties.isAlwaysFocus = true;
                    JPanelMenu.isFocus = false;
                }
            }
        });
    }

    private void setJButton() {
        btnSave = new JButton("L??u l???i");
        btnSave.setBackground(Color.green);
        btnSave.setBounds(170, 360, 80, 30);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkEdtRatioBall().isEmpty() == false){
                    JOptionPane.showMessageDialog(JFrameSetting.this, checkEdtRatioBall());
                    return;
                }
                if(checkEdtSoDuong().isEmpty() == false){
                    JOptionPane.showMessageDialog(JFrameSetting.this, checkEdtSoDuong());
                    return;
                }
                if(checkEdtSpace().isEmpty() == false){
                    JOptionPane.showMessageDialog(JFrameSetting.this, checkEdtSpace());
                    return;
                }
                Properties.LineSizeOuterBorder = (int) jSlLineSizeOuter.getValue();
                Properties.colorOuterBorder = btnColorOuter.getBackground();
                Properties.LineSize = (int) jSlLineSize.getValue();
                Properties.colorLine = btnColor.getBackground();
                Properties.ratioBall = Double.parseDouble(edtRatioBall.getText().toString().trim());
                Properties.soDuongDapBang = Integer.parseInt(edtSoDuongDapBang.getText().toString().trim());
                Properties.isAutoBall = jrbAuto.isSelected();
                JPanelTable.saiSoCanBang =  (int) (toolJFrame.getjPanelTable().getHeight() / (2*Properties.ratioBall));
                Properties.space = Integer.parseInt(edtSpaceMenuAndTable.getText().toString().trim());
                Properties.isOnTop = jrbYes.isSelected();
                Properties.isAlwaysFocus = jrbYesFocus.isSelected();
                Data.writeData();
                JFrameSetting.this.setVisible(false);
                toolJFrame.setAlwaysOnTop(Properties.isOnTop);
                toolJFrame.getjPanelTable().setBorder(new LineBorder(Properties.colorOuterBorder, Properties.LineSizeOuterBorder));
                toolJFrame.getjPanelTable().getjPanelImage().repaint();

                toolJFrame.getjPanelTable().setVisible(false); // m??o hi???u sao ph???i c?? 2 d??ng n??y th?? n?? m???i x??a ???????c.
                toolJFrame.getjPanelTable().setVisible(true);
            }
        });
        this.add(btnSave);

        btnCancel = new JButton("H???y");
        btnCancel.setBackground(Color.pink);
        btnCancel.setBounds(50, 360, 80, 30);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameSetting.this.setVisible(false);
                if(Properties.isOnTop){
                    toolJFrame.setAlwaysOnTop(true);
                }
                if(JPanelMenu.isFocus){
                    Properties.isAlwaysFocus = true;
                    JPanelMenu.isFocus = false;
                }
            }
        });
        this.add(btnCancel);
    }

    private void setJPanelOuterBorder() {
        jpnOuterBorder = new JPanel();
        jpnOuterBorder.setBounds(10, 10, 250, 80);
        jpnOuterBorder.setLayout(new GridLayout(2, 2, 5, 5));
        jpnOuterBorder.setBorder(BorderFactory.createTitledBorder("???????ng vi???n b??n"));
        Border border = jpnOuterBorder.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnOuterBorder.setBorder(new CompoundBorder(border, margin));
        container.add(jpnOuterBorder);

        txtLineSizeOuter = new JLabel("K??ch th?????c (pixel):");
        txtLineSizeOuter.setFont(txtLineSizeOuter.getFont().deriveFont(15.f));
        jpnOuterBorder.add(txtLineSizeOuter);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);
        jSlLineSizeOuter = new JSpinner(model);
        jpnOuterBorder.add(jSlLineSizeOuter);

        txtColorOuter = new JLabel("M??u: ");
        txtColorOuter.setFont(txtColorOuter.getFont().deriveFont(15.f));
        jpnOuterBorder.add(txtColorOuter);

        btnColorOuter = new JButton("");
        btnColorOuter.setBackground(Properties.colorOuterBorder);
        btnColorOuter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Ch???n m??u vi???n ngo??i", Color.red);
                if (color != null) {
                    btnColorOuter.setBackground(color);
                }
            }
        });
        jpnOuterBorder.add(btnColorOuter);
    }

    private void setJPanelLineBall() {
        jpnLineBall = new JPanel();
        jpnLineBall.setBounds(10, 100, 250, 80);
        jpnLineBall.setLayout(new GridLayout(2, 2, 5, 5));
        jpnLineBall.setBorder(BorderFactory.createTitledBorder("???????ng ??i b??ng"));
        Border border = jpnLineBall.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnLineBall.setBorder(new CompoundBorder(border, margin));
        container.add(jpnLineBall);

        txtLineSize = new JLabel("K??ch th?????c (pixel):");
        txtLineSize.setFont(txtLineSize.getFont().deriveFont(15.f));
        jpnLineBall.add(txtLineSize);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);
        jSlLineSize = new JSpinner(model);
        jpnLineBall.add(jSlLineSize);

        txtColor = new JLabel("M??u: ");
        txtColor.setFont(txtColor.getFont().deriveFont(15.f));
        jpnLineBall.add(txtColor);

        btnColor = new JButton("");
        btnColor.setBackground(Properties.colorLine);
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Ch???n m??u ???????ng b??ng", Color.red);
                if (color != null) {
                    btnColor.setBackground(color);
                }
            }
        });
        jpnLineBall.add(btnColor);
    }

    private void setJPanelRatioBall() {
        jpnRatioBall = new JPanel();
        jpnRatioBall.setBounds(10, 190, 250, 60);
        jpnRatioBall.setLayout(new BorderLayout());
        jpnRatioBall.setBorder(BorderFactory.createTitledBorder("Chi???u cao b??n / ???????ng k??nh b??ng"));
        Border border = jpnRatioBall.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnRatioBall.setBorder(new CompoundBorder(border, margin));
        container.add(jpnRatioBall);

        edtRatioBall = new JTextField("17");
        edtRatioBall.setFont(edtRatioBall.getFont().deriveFont(18.f));
        jpnRatioBall.add(edtRatioBall);
    }

    private void setJPanelFocus() {
        jpnFocus = new JPanel();
        jpnFocus.setBounds(10, 260, 250, 80);
        jpnFocus.setLayout(new GridLayout(2, 1, 5, 5));
        jpnFocus.setBorder(BorderFactory.createTitledBorder("L???y ti??u ??i???m b??ng"));
        Border border = jpnFocus.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnFocus.setBorder(new CompoundBorder(border, margin));
        container.add(jpnFocus);

        groupFocus = new ButtonGroup();

        jrbAuto = new JRadioButton("T??? ?????ng l???y bao quanh b??ng");
        jrbAuto.setSelected(true);
        groupFocus.add(jrbAuto);
        jpnFocus.add(jrbAuto);

        jrbMousePoint = new JRadioButton("L???y v??? tr?? con tr??? chu???t");
        groupFocus.add(jrbMousePoint);
        jpnFocus.add(jrbMousePoint);

    }

    private void setJPanelSoDuongDapBang() {
        jpnSoDuongDapBang = new JPanel();
        jpnSoDuongDapBang.setBounds(10, 350, 250, 60);
        jpnSoDuongDapBang.setLayout(new BorderLayout());
        jpnSoDuongDapBang.setBorder(BorderFactory.createTitledBorder("S??? ???????ng ?????p b??ng"));
        Border border = jpnSoDuongDapBang.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnSoDuongDapBang.setBorder(new CompoundBorder(border, margin));
        container.add(jpnSoDuongDapBang);

        edtSoDuongDapBang = new JTextField("5");
        edtSoDuongDapBang.setFont(edtSoDuongDapBang.getFont().deriveFont(18.f));
        jpnSoDuongDapBang.add(edtSoDuongDapBang);
    }
    
    private void setJPanelSpaceMenuAndTable() {
        jpnSpaceMenuAndTable = new JPanel();
        jpnSpaceMenuAndTable.setBounds(10, 420, 250, 60);
        jpnSpaceMenuAndTable.setLayout(new BorderLayout());
        jpnSpaceMenuAndTable.setBorder(BorderFactory.createTitledBorder("Kho???ng tr???ng Menu - B???ng"));
        Border border = jpnSpaceMenuAndTable.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnSpaceMenuAndTable.setBorder(new CompoundBorder(border, margin));
        container.add(jpnSpaceMenuAndTable);

        edtSpaceMenuAndTable = new JTextField("5");
        edtSpaceMenuAndTable.setFont(edtSpaceMenuAndTable.getFont().deriveFont(18.f));
        jpnSpaceMenuAndTable.add(edtSpaceMenuAndTable);
    }
    
    private void setJPanelOnTop() {
        jpnOnTop = new JPanel();
        jpnOnTop.setBounds(10, 490, 250, 80);
        jpnOnTop.setLayout(new GridLayout(2, 1, 5, 5));
        jpnOnTop.setBorder(BorderFactory.createTitledBorder("Lu??n hi???n th??? ??? tr??n"));
        Border border = jpnOnTop.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnOnTop.setBorder(new CompoundBorder(border, margin));
        container.add(jpnOnTop);

        groupOnTop = new ButtonGroup();

        jrbYes = new JRadioButton("C??");
        jrbYes.setSelected(true);
        groupOnTop.add(jrbYes);
        jpnOnTop.add(jrbYes);

        jrbNo = new JRadioButton("Kh??ng");
        groupOnTop.add(jrbNo);
        jpnOnTop.add(jrbNo);

    }
    
    private void setJPanelAlwaysFocus(){
        jpnAlwaysFocus = new JPanel();
        jpnAlwaysFocus.setBounds(10, 580, 250, 80);
        jpnAlwaysFocus.setLayout(new GridLayout(2, 1, 5, 5));
        jpnAlwaysFocus.setBorder(BorderFactory.createTitledBorder("Lu??n lu??n nh???n b??n ph??m"));
        Border border = jpnAlwaysFocus.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        jpnAlwaysFocus.setBorder(new CompoundBorder(border, margin));
        container.add(jpnAlwaysFocus);

        groupAlwaysFocus = new ButtonGroup();

        jrbYesFocus = new JRadioButton("C??");
        jrbYesFocus.setSelected(true);
        groupAlwaysFocus.add(jrbYesFocus);
        jpnAlwaysFocus.add(jrbYesFocus);

        jrbNoFocus = new JRadioButton("Kh??ng");
        groupAlwaysFocus.add(jrbNoFocus);
        jpnAlwaysFocus.add(jrbNoFocus);

    }
    
    private String checkEdtRatioBall(){
        String ratio = edtRatioBall.getText().trim();
        if(ratio.isEmpty()){
            return "Ch??a nh???p t??? l??? b??ng !";
        }
        int dem = 0;
        for(int i = 0;i<ratio.length();i++){
            int kyTu = ratio.charAt(i);
            if((kyTu != 46)&&((kyTu < 48)||(kyTu > 57))){
                return "T??? l??? b??ng nh???p sai quy c??ch !";
            }
            if(kyTu == 46){
                dem++;
                if(dem >= 2){
                    return "T??? l??? b??ng nh???p sai quy c??ch !";
                }
            }
        }
        if(Double.parseDouble(ratio) > 200){
            return "T??? l??? b??ng nh??? h??n 100 !";
        }
        return "";
    }
    
    private String checkEdtSoDuong(){
        String soDuong = edtSoDuongDapBang.getText().trim();
        if(soDuong.isEmpty()){
            return "Ch??a nh???p s??? ???????ng ?????p b??ng !";
        }
        for(int i = 0;i<soDuong.length();i++){
            int kyTu = soDuong.charAt(i);
            if((kyTu < 48)||(kyTu > 57)){
                return "S??? ???????ng ?????p b??ng nh???p sai quy c??ch !";
            }
        }
        if(soDuong.length() > 4){
            return "S??? ???????ng ?????p b??ng nh??? h??n 1000 !";
        }
        if(Integer.parseInt(soDuong) > 1000){
            return "S??? ???????ng ?????p b??ng nh??? h??n 1000 !";
        }
        return "";
    }
    
    private String checkEdtSpace(){
        String space = edtSpaceMenuAndTable.getText().trim();
        if(space.isEmpty()){
            return "Ch??a nh???p kho???ng tr???ng !";
        }
        for(int i = 0;i<space.length();i++){
            int kyTu = space.charAt(i);
            if((kyTu < 48)||(kyTu > 57)){
                return "Kho???ng tr???ng nh???p sai quy c??ch !";
            }
        }
        if(space.length() > 4){
            return "Kho???ng tr???ng nh??? h??n 1000 !";
        }
        if(Integer.parseInt(space) > 1000){
            return "Kho???ng tr???ng nh??? h??n 1000 !";
        }
        return "";
    }

    private void readTime() {
        edtRatioBall.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(checkEdtRatioBall().isEmpty()){
                    edtRatioBall.setForeground(Color.black);
                }else{
                    edtRatioBall.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(checkEdtRatioBall().isEmpty()){
                    edtRatioBall.setForeground(Color.black);
                }else{
                    edtRatioBall.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(checkEdtRatioBall().isEmpty()){
                    edtRatioBall.setForeground(Color.black);
                }else{
                    edtRatioBall.setForeground(Color.red);
                }
            }
        });
        
        edtSoDuongDapBang.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(checkEdtSoDuong().isEmpty()){
                    edtSoDuongDapBang.setForeground(Color.black);
                }else{
                    edtSoDuongDapBang.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(checkEdtSoDuong().isEmpty()){
                    edtSoDuongDapBang.setForeground(Color.black);
                }else{
                    edtSoDuongDapBang.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(checkEdtSoDuong().isEmpty()){
                    edtSoDuongDapBang.setForeground(Color.black);
                }else{
                    edtSoDuongDapBang.setForeground(Color.red);
                }
            }
        });
        
        edtSpaceMenuAndTable.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(checkEdtSpace().isEmpty()){
                    edtSpaceMenuAndTable.setForeground(Color.black);
                }else{
                    edtSpaceMenuAndTable.setForeground(Color.red);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(checkEdtSpace().isEmpty()){
                    edtSpaceMenuAndTable.setForeground(Color.black);
                }else{
                    edtSpaceMenuAndTable.setForeground(Color.red);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(checkEdtSpace().isEmpty()){
                    edtSpaceMenuAndTable.setForeground(Color.black);
                }else{
                    edtSpaceMenuAndTable.setForeground(Color.red);
                }
            }
        });
    }
    
    public void update() {
        jSlLineSizeOuter.setValue(Properties.LineSizeOuterBorder);
        btnColorOuter.setBackground(Properties.colorOuterBorder);

        jSlLineSize.setValue(Properties.LineSize);
        btnColor.setBackground(Properties.colorLine);

        edtRatioBall.setText(Properties.ratioBall + "");

        edtSoDuongDapBang.setText(Properties.soDuongDapBang + "");

        if (Properties.isAutoBall) {
            jrbAuto.setSelected(true);
            jrbMousePoint.setSelected(false);
        } else {
            jrbAuto.setSelected(false);
            jrbMousePoint.setSelected(true);
        }
        
        edtSpaceMenuAndTable.setText(Properties.space + "");
        
        if (Properties.isOnTop) {
            jrbYes.setSelected(true);
            jrbNo.setSelected(false);
        } else {
            jrbYes.setSelected(false);
            jrbNo.setSelected(true);
        }
        
        if (Properties.isAlwaysFocus) {
            jrbYesFocus.setSelected(true);
            jrbNoFocus.setSelected(false);
        } else {
            jrbYesFocus.setSelected(false);
            jrbNoFocus.setSelected(true);
        }  
    }
}
