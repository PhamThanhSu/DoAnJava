/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;
import GUI.SPham.SuaSanPham;
import java.util.ArrayList;
        


/**
 *
 * @author ADMIN
 */
public class SanPhamBUS {
    SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private ArrayList<SanPhamDTO> listSP = getAllSanPham();
    public SanPhamBUS() {
    }
    
    public ArrayList<SanPhamDTO> getAllSanPham(){
        return sanPhamDAO.getAllSanPham();
    }
    
    public boolean themSanPham(SanPhamDTO sanPhamDTO){
        return sanPhamDAO.themSanPham(sanPhamDTO);
    }
    public boolean xoaSanPham(int masp){
        return sanPhamDAO.xoaSanPham(masp);
    }
    
    public boolean suaSanPham(SanPhamDTO sanPhamDTO){
        return sanPhamDAO.suaSanPham(sanPhamDTO);
    }
    public String selectAnhByMaSP(int masp){
        return sanPhamDAO.selectHinhAnhByMaSP(masp);
    }
    
    public SanPhamDTO selectByID(int masp){
        return sanPhamDAO.selectById(masp);
    }
    //Thêm hàm search vào đây
    public ArrayList<SanPhamDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : listSP) {
            if (Integer.toString(i.getMasp()).toLowerCase().contains(text) || i.getTensp().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }
}
