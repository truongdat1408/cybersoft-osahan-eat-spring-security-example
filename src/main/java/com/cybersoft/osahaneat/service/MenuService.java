package com.cybersoft.osahaneat.service;

import com.cybersoft.osahaneat.dto.FoodDto;
import com.cybersoft.osahaneat.entity.CategoryRestaurant;
import com.cybersoft.osahaneat.entity.Food;
import com.cybersoft.osahaneat.repository.FoodRepository;
import com.cybersoft.osahaneat.service.imp.MenuServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService implements MenuServiceImp {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public boolean insertFood(
            MultipartFile file,
            String name,
            String description,
            double price,
            String instruction,
            int cate_res_id) {
        boolean isSuccess = fileStorageService.saveFiles(file);

        if (isSuccess) {
            //lưu dữ liệu khi file đã được lưu thành công
            Food food = new Food();
            food.setName(name);
            food.setDesc(description);
            food.setPrice(price);
            food.setIntruction(instruction);
            food.setImage(file.getOriginalFilename());

            CategoryRestaurant categoryRestaurant = new CategoryRestaurant();
            categoryRestaurant.setId(cate_res_id);

            food.setCategoryRestaurant(categoryRestaurant);

            try {
                foodRepository.save(food);
                return true;
            } catch (Exception e) {
                System.out.println("Error save Food: " + e.getMessage());
                return false;
            }
        }

        return false;
    }

    @Override
//    @Cacheable("food")
    public List<FoodDto> getAllFood() {
        List<Food> list;
        List<FoodDto> dtoList = new ArrayList<>();
        Gson gson = new Gson();

        String data = (String) redisTemplate.opsForValue().get("foods");
        if (data == null) {
            list = foodRepository.findAll();
            for (Food food : list) {
                FoodDto foodDto = new FoodDto();
                foodDto.setImage(food.getImage());
                foodDto.setName(food.getName());
                foodDto.setDesc(food.getDesc());

                dtoList.add(foodDto);
            }
            redisTemplate.opsForValue().set("foods", gson.toJson(dtoList));
        }else {
            dtoList = gson.fromJson(data, new TypeToken<List<FoodDto>>(){}.getType());
        }
        System.out.println("Redis: " + data);

        return dtoList;
    }
}
