package com.example.tpasus.foodyapp.Controller.Interfaces;

import com.example.tpasus.foodyapp.Model.QuanAnModel;

import java.util.List;

//Khi model kích hoạt interface dữ liệu sẽ được truyền từ model sang controller
public interface AddressInterface {
    void getDanhSachQuanAn(QuanAnModel quanAnModel);
}
