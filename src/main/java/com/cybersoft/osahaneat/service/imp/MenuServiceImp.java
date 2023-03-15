package com.cybersoft.osahaneat.service.imp;

import com.cybersoft.osahaneat.dto.FoodDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuServiceImp {
    boolean insertFood(MultipartFile file, String name, String description, double price, String instruction, int cate_res_id);
    List<FoodDto> getAllFood();
}
