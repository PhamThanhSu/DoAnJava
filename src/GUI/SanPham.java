package GUI;

import BUS.SanPhamBUS;
import DAO.KhuVucKhoDAO;
import DAO.LoaiDAO;
import DAO.ThuongHieuDAO;
import DAO.XuatXuDAO;
import DTO.SanPhamDTO;
import GUI.SPham.ChiTietSanPham;
import GUI.SPham.SuaSanPham;
import GUI.SPham.ThemSanPham;
import GUI.SanPham;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import static java.awt.AWTEventMulticaster.add;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class SanPham extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form SanPham
     */
    DefaultTableModel tblModel;
    SanPhamBUS sanPhamBus = new SanPhamBUS();
    ThuongHieuDAO thuongHieuDAO;
    LoaiDAO loaiDAO;
    XuatXuDAO xuatXuDAO;
    KhuVucKhoDAO khuVucKhoDAO;
    ThemSanPham themSanPham;
    SuaSanPham suaSanPham;
    ChiTietSanPham chiTietSanPham;
    ArrayList<SanPhamDTO> listSanPham = sanPhamBus.getAllSanPham();

    public SanPham() throws IOException {
        initComponents();
        addIcon();
        tblSanPham.setFocusable(false);
        tblSanPham.setDefaultEditor(Object.class, null); // set ko cho sửa dữ liệu trên table
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblSanPham.setFocusable(false);
        tblSanPham.setAutoCreateRowSorter(true);

        btnThemSP.addActionListener(this);
        btnSuaSP.addActionListener(this);
        btnXoaSP.addActionListener(this);
        btnChiTietSP.addActionListener(this);
        btnXuatExcelSP.addActionListener(this);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 800));
        this.add(pnlTop, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);

        hienThiListSanPham(listSanPham);
    }

    private void timKiemSanPham(String keyword) {
        ArrayList<SanPhamDTO> ketQuaTimKiem = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String tenSanPham = (String) model.getValueAt(i, 1);
            if (tenSanPham.toLowerCase().contains(keyword.toLowerCase())) {
                ketQuaTimKiem.add(sanPhamBus.selectByID((int) model.getValueAt(i, 0))); // Thêm sản phẩm vào danh sách kết quả
            }
        }
        hienThiListSanPham(ketQuaTimKiem);
    }

    private void hienThiListSanPham(ArrayList<SanPhamDTO> listSanPham) {
        sanPhamBus = new SanPhamBUS();
        thuongHieuDAO = new ThuongHieuDAO();
        loaiDAO = new LoaiDAO();
        xuatXuDAO = new XuatXuDAO();
        khuVucKhoDAO = new KhuVucKhoDAO();
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (SanPhamDTO sanPham : listSanPham) {
            Object[] row = {
                sanPham.getMasp(),
                sanPham.getTensp(),
                sanPham.getSoluongton(),
                sanPham.getSize(),
                loaiDAO.selectById(sanPham.getLoai()).getTenloai(),
                thuongHieuDAO.selectById(sanPham.getThuonghieu()).getTenthuonghieu(),
                xuatXuDAO.selectById(sanPham.getXuatxu()).getTenxuatxu(), // Giá trị ở giữa thứ 7
                khuVucKhoDAO.selectById(sanPham.getKhuvuckho()).getTenkhuvuc(),
                formatTien(sanPham.getGianhap()),
                formatTien(sanPham.getGiaxuat())};
            model.addRow(row);
        }

        // Tạo renderer để hiển thị nội dung ở giữa ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Áp dụng renderer cho từng cột trong bảng
        for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
            tblSanPham.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public String formatTien(double tien) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0 VND");
        return decimalFormat.format(tien);
    }

    private void addIcon() {
        btnThemSP.setIcon(new FlatSVGIcon("./icon/add.svg"));
        btnSuaSP.setIcon(new FlatSVGIcon("./icon/edit.svg"));
        btnXoaSP.setIcon(new FlatSVGIcon("./icon/delete.svg"));
        btnXuatExcelSP.setIcon(new FlatSVGIcon("./icon/export_excel.svg"));
        btnChiTietSP.setIcon(new FlatSVGIcon("./icon/detail.svg"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnChiTietSP = new javax.swing.JButton();
        btnXuatExcelSP = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        pnlCenter = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1200, 800));
        setLayout(new java.awt.BorderLayout());

        pnlTop.setBackground(new java.awt.Color(255, 255, 255));
        pnlTop.setPreferredSize(new java.awt.Dimension(1200, 70));

        btnThemSP.setText("Thêm");
        btnThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemSPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemSPMouseExited(evt);
            }
        });
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });
        pnlTop.add(btnThemSP);

        btnSuaSP.setText("Sửa");
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });
        pnlTop.add(btnSuaSP);

        btnXoaSP.setText("Xóa");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });
        pnlTop.add(btnXoaSP);

        btnChiTietSP.setText("Chi tiết");
        btnChiTietSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietSPActionPerformed(evt);
            }
        });
        pnlTop.add(btnChiTietSP);

        btnXuatExcelSP.setText("Xuất excel");
        pnlTop.add(btnXuatExcelSP);

        jLabel1.setLabelFor(txtTimKiem);
        jLabel1.setText("Tìm kiếm :");
        pnlTop.add(jLabel1);

        txtTimKiem.setPreferredSize(new java.awt.Dimension(200, 30));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });
        pnlTop.add(txtTimKiem);

        btnLamMoi.setText("Làm mới");
        btnLamMoi.setPreferredSize(new java.awt.Dimension(100, 60));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        pnlTop.add(btnLamMoi);

        add(pnlTop, java.awt.BorderLayout.NORTH);

        pnlCenter.setPreferredSize(new java.awt.Dimension(1200, 700));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn", "Size", "Loại", "Thương hiệu", "Xuất xứ", "Khu vực kho", "Giá nhập", "Giá xuất"
            }
        ));
        jScrollPane2.setViewportView(tblSanPham);

        javax.swing.GroupLayout pnlCenterLayout = new javax.swing.GroupLayout(pnlCenter);
        pnlCenter.setLayout(pnlCenterLayout);
        pnlCenterLayout.setHorizontalGroup(
            pnlCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1287, Short.MAX_VALUE)
        );
        pnlCenterLayout.setVerticalGroup(
            pnlCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );

        add(pnlCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseEntered
        // TODO add your handling code here:
        btnThemSP.setBackground(Color.GRAY);
    }//GEN-LAST:event_btnThemSPMouseEntered

    private void btnThemSPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseExited
        // TODO add your handling code here:
        btnThemSP.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnThemSPMouseExited

    private void btnThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseClicked
        // TODO add your handling code here:
        if (!btnThemSP.isSelected()) {
            btnThemSP.setBackground(Color.WHITE);
        } else {
            btnThemSP.setBackground(Color.BLUE);
        }
    }//GEN-LAST:event_btnThemSPMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        hienThiListSanPham(listSanPham);
        txtTimKiem.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String keyword = txtTimKiem.getText().trim();
            timKiemSanPham(keyword);
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnChiTietSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChiTietSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void xoaSanPham() {
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow != -1) {
            int maSP = (int) tblSanPham.getValueAt(selectedRow, 0);
            String anhCanXoa = sanPhamBus.selectAnhByMaSP(maSP);
            sanPhamBus = new SanPhamBUS();
            boolean thanhCong = sanPhamBus.xoaSanPham(maSP);
            if (thanhCong) {

                JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công");
                listSanPham = sanPhamBus.getAllSanPham();
                hienThiListSanPham(listSanPham);
            } else {
                JOptionPane.showMessageDialog(null, "Xóa sản phẩm lỗi");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phảm để xóa");
        }
    }

    private SanPhamDTO selectSanPham() {
        int selectedRow = tblSanPham.getSelectedRow();
        SanPhamDTO result = null;
        if (selectedRow != -1) {
            int mathuonghieu = (int) tblSanPham.getValueAt(selectedRow, 0);
            sanPhamBus = new SanPhamBUS();
            result = sanPhamBus.selectByID(mathuonghieu);
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThemSP) {
            themSanPham = new ThemSanPham();
            themSanPham.setLocationRelativeTo(null);
            themSanPham.setVisible(true);
        } else if (e.getSource() == btnXoaSP) {
            xoaSanPham();
        } else if (e.getSource() == btnSuaSP) {
            if (selectSanPham() != null) {
                suaSanPham = new SuaSanPham(selectSanPham());
                suaSanPham.setLocationRelativeTo(null);
                suaSanPham.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm");
            }
        } else if (e.getSource() == btnXuatExcelSP) {
            XuatExcel xuatExcel = new XuatExcel();
            try {
                xuatExcel.exportJTableToExcel(tblSanPham);
                JOptionPane.showMessageDialog(null, "Xuất thành công");
            } catch (IOException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnChiTietSP) {
            if (selectSanPham() != null) {
                chiTietSanPham = new ChiTietSanPham(selectSanPham());
                chiTietSanPham.setLocationRelativeTo(null);
                chiTietSanPham.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm");
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTietSP;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JButton btnXuatExcelSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlCenter;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
