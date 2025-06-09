package com.fruitshop.service;

import com.fruitshop.model.UserDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public List<UserDTO> getUsersFromApi(int page, int perPage) {
        // 組成動態路徑設置頁數及篩選筆數
        String url = String.format("https://gorest.co.in/public-api/users?page=%d&per_page=%d", page, perPage);
        //使用restTemplate向外部api Get請求後 取得整串json格式字串資料
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        //將請求到的json格式字串資料包裝成JSONObject 轉換成可操作的Java物件(類似Map)
        JSONObject jsonObject = new JSONObject(response.getBody());

        //把data內的陣列資料包裝成JSONArray
        JSONArray data = jsonObject.getJSONArray("data");

        List<UserDTO> users = new ArrayList<>();

        //迴圈取出data內的資料
        for (int i = 0; i < data.length(); i++) {
            JSONObject user = data.getJSONObject(i);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getLong("id"));
            userDTO.setName(user.getString("name"));
            userDTO.setEmail(user.getString("email"));
            userDTO.setGender(user.getString("gender"));
            userDTO.setStatus(user.getString("status"));
            users.add(userDTO);
        }

        return users;
    }
}
