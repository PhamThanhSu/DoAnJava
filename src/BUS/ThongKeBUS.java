package BUS;

import DAO.ThongKeDAO;
//import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKeTrongThangDTO;
//import DTO.ThongKe.ThongKeNhaCungCapDTO;
//import DTO.ThongKe.ThongKeTheoThangDTO;
//import DTO.ThongKe.ThongKeTonKhoDTO;
//import DTO.ThongKe.ThongKeTungNgayTrongThangDTO;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Tran Nhat Sinh
 */
public class ThongKeBUS {

    ThongKeDAO thongkeDAO = new ThongKeDAO();

    public ArrayList<ThongKeKhachHangDTO> getAllKhachHang() {
        return thongkeDAO.getThongKeKhachHang("", new Date(0), new Date(System.currentTimeMillis()));
    }

    public ArrayList<ThongKeKhachHangDTO> locKhachHang(String text, Date start, Date end) {
        return thongkeDAO.getThongKeKhachHang(text, start, end);
    }
    
    public ArrayList<ThongKeTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam){
        return thongkeDAO.getThongKeTungNgayTrongThang(thang, nam);
    }
}
