//package BUS;
//
//import DTO.PhieuNhapDTO;
//import DTO.SanPhamDTO;
//import GUI.Panel.TaoPhieuNhap;
//import com.mysql.cj.jdbc.ConnectionImpl;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.ResultSet;
//import config.MySQLConnection;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.SQLException;
//import javax.swing.JComboBox;
//import javax.swing.JOptionPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//
//// Tạo một lớp mới để xử lý sự kiện khi click vào một sản phẩm trong JTable
//public class TableMouseListener extends MouseAdapter {
//    private final JTable table;
//    private final JTextField textField4;
//    private final JTextField textField2;
//    private final JTextField textField3;
//    private final JComboBox<String> comboBox;
//    SanPhamBUS sanPhamBUS;
//    SanPhamDTO result;
//    public TableMouseListener(JTable table, JTextField textField2, JTextField textField4, JTextField textField3, JComboBox<String> comboBox) {
//        this.table = table;
//        this.textField4 = textField4;
//        this.textField2 = textField2;
//        this.textField3 = textField3;
//        this.comboBox = comboBox;
//    }
//    @Override
//   public void mouseClicked(MouseEvent e) {
//    int row = table.getSelectedRow();
//    // Kiểm tra nếu không có hàng nào được chọn
//    if (row == -1) {
//        return;
//    }
//    // Lấy mã sản phẩm từ cột chứa giá trị "masp"
//    int maspColumnIndex = this.getColumnIndexByName("Mã SP");
//    int masp = (int) table.getValueAt(row, maspColumnIndex);
//    sanPhamBUS = new SanPhamBUS();
//    result = sanPhamBUS.selectByID(masp);
//    if (result != null) {
//        // Cập nhật các trường tương ứng trong giao diện với thông tin sản phẩm
//        textField2.setText(String.valueOf(result.getMasp()));
//        textField4.setText(String.valueOf(result.getTensp()));
//        textField3.setText(String.valueOf(result.getSoluongton()));
//        // Thực hiện các thao tác khác nếu cần
//    }
//    //Thêm giá trị cho CheckBox
//    CheckCombobox();
//}
//
//
//
//    // Phương thức để lấy chỉ số của cột dựa trên tên cột
//    private int getColumnIndexByName(String columnName) {
//        for (int i = 0; i < table.getColumnCount(); i++) {
//            if (table.getColumnName(i).equals(columnName)) {
//                return i;
//            }
//        }
//        return -1; // Trả về -1 nếu không tìm thấy tên cột
//    }
//    
//    public void CheckCombobox(){      
//            // Tạo một mảng các giá trị size từ 30 đến 45
//            String[] sizes = new String[16];
//            for (int i = 30; i <= 45; i++) {
//            sizes[i - 30] = String.valueOf(i);
//            }        
//            // Xóa tất cả các mục trong comboBox
//            comboBox.removeAllItems();
//            // Thêm các giá trị size vào comboBox
//            for (String size : sizes) {
//            comboBox.addItem(size);          
//            }
//    }
//}