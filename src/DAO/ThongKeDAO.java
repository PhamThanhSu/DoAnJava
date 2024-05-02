package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import DTO.ThongKe.ThongKeKhachHangDTO;
import DTO.ThongKeTrongThangDTO;
import config.MySQLConnection;

public class ThongKeDAO {

    public ArrayList<ThongKeKhachHangDTO> getThongKeKhachHang(String text, Date timeStart, Date timeEnd) {
        ArrayList<ThongKeKhachHangDTO> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeEnd.getTime());
        // Đặt giá trị cho giờ, phút, giây và mili giây của Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT khachhang.makh, khachhang.tenkhachhang, COUNT(phieuxuat.maphieuxuat) AS soluong, IFNULL(SUM(phieuxuat.tongtien), 0) AS total "
                    + "FROM khachhang, phieuxuat "
                    + "WHERE khachhang.makh = phieuxuat.makh AND phieuxuat.thoigian BETWEEN ? AND ? "
                    + "GROUP BY khachhang.makh, khachhang.tenkhachhang "
                    + "HAVING ( khachhang.tenkhachhang LIKE ? OR khachhang.makh LIKE ? ) AND soluong > 0";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
            pst.setString(3, "%" + text + "%");
            pst.setString(4, "%" + text + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int makh = rs.getInt("makh");
                String tenkh = rs.getString("tenkhachhang");
                int soluong = rs.getInt("soluong");
                long tongtien = rs.getInt("total");
                ThongKeKhachHangDTO x = new ThongKeKhachHangDTO(makh, tenkh, soluong, tongtien);
                result.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ThongKeTrongThangDTO> getThongKeTungNgayTrongThang(int thang, int nam) {
        ArrayList<ThongKeTrongThangDTO> result = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String ngayString = nam + "-" + thang + "-" + "01";
            System.out.println(ngayString);

            String sql = "WITH RECURSIVE dates AS (\n"
                    + "    SELECT DATE('" + ngayString + "') AS date\n"
                    + "    UNION ALL\n"
                    + "    SELECT date + INTERVAL 1 DAY\n"
                    + "    FROM dates\n"
                    + "    WHERE date + INTERVAL 1 DAY <= LAST_DAY('" + ngayString + "')\n"
                    + ")\n"
                    + "SELECT \n"
                    + "    dates.date AS ngay, \n"
                    + "    COALESCE(SUM(phieuxuat.tongtien), 0) AS doanhthu,\n"
                    + "    COALESCE(SUM(chiphi), 0) AS chiphi\n"
                    + "FROM dates\n"
                    + "LEFT JOIN phieuxuat ON DATE(phieuxuat.thoigian) = dates.date\n"
                    + "LEFT JOIN (\n"
                    + "    SELECT ctphieuxuat.maphieuxuat,\n"
                    + "        SUM(ctphieuxuat.soluong * sanpham.gianhap) AS chiphi\n"
                    + "    FROM ctphieuxuat\n"
                    + "    LEFT JOIN sanpham ON ctphieuxuat.masp = sanpham.masp\n"
                    + "    GROUP BY ctphieuxuat.maphieuxuat\n"
                    + ") AS chi_phi_table ON phieuxuat.maphieuxuat = chi_phi_table.maphieuxuat\n"
                    + "GROUP BY dates.date\n"
                    + "ORDER BY dates.date;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                Date ngay = rs.getDate("ngay");
                int chiphi = rs.getInt("chiphi");
                int doanhthu = rs.getInt("doanhthu");
                int loinhuan = doanhthu - chiphi;
                ThongKeTrongThangDTO tn = new ThongKeTrongThangDTO(ngay, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ThongKeTrongThangDTO> getThongKeTuNgayDenNgay(String star, String end) {
        ArrayList<ThongKeTrongThangDTO> result = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();

            String setStar = "SET @start_date = '" + star + "'";
            String setEnd = "SET @end_date = '" + end + "'  ;";

            String numbersQuery = "";
            for (int i = 0; i <= 364; i++) {
                numbersQuery += "SELECT " + i + " AS number";
                if (i < 364) {
                    numbersQuery += " UNION ALL ";
                }
            }

            String sqlSelect = "SELECT \n"
                    + "    dates.date AS ngay, \n"
                    + "    COALESCE(SUM(phieuxuat.tongtien), 0) AS doanhthu,\n"
                    + "    COALESCE(SUM(chiphi), 0) AS chiphi\n"
                    + "FROM \n"
                    + "    (\n"
                    + "        SELECT DATE_ADD(@start_date, INTERVAL c.number DAY) AS date\n"
                    + "        FROM (" + numbersQuery + ") AS c\n"
                    + "        WHERE DATE_ADD(@start_date, INTERVAL c.number DAY) <= @end_date\n"
                    + "    ) AS dates\n"
                    + "LEFT JOIN phieuxuat ON DATE(phieuxuat.thoigian) = dates.date\n"
                    + "LEFT JOIN (\n"
                    + "            SELECT ctphieuxuat.maphieuxuat,\n"
                    + "            SUM(ctphieuxuat.soluong * sanpham.gianhap) AS chiphi\n"
                    + "           FROM ctphieuxuat\n"
                    + "            LEFT JOIN sanpham ON ctphieuxuat.masp = sanpham.masp\n"
                    + "           GROUP BY ctphieuxuat.maphieuxuat\n"
                    + "            ) AS chi_phi_table ON phieuxuat.maphieuxuat = chi_phi_table.maphieuxuat\n"
                    + "GROUP BY dates.date\n"
                    + "ORDER BY dates.date;";

            PreparedStatement pstStart = con.prepareStatement(setStar);
            PreparedStatement pstEnd = con.prepareStatement(setEnd);
            PreparedStatement pstSelect = con.prepareStatement(sqlSelect);

            pstStart.execute();
            pstEnd.execute();
            ResultSet rs = pstSelect.executeQuery();
            while (rs.next()) {
                Date ngay = rs.getDate("ngay");
                int chiphi = rs.getInt("chiphi");
                int doanhthu = rs.getInt("doanhthu");
                int loinhuan = doanhthu - chiphi;
                ThongKeTrongThangDTO tn = new ThongKeTrongThangDTO(ngay, chiphi, doanhthu, loinhuan);
                result.add(tn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
