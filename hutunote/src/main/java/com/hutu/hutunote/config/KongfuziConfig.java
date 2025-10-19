package com.hutu.hutunote.config;

import com.hutu.hutunote.model.dto.StoreConfigDto;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Data
public class KongfuziConfig {

    // 8088
    private String cookie = "shoppingCartSessionId=7a5e55660d0caff56e2f133d0c4dfcdb; reciever_area=1006000000; _c_WBKFRo=4kLYLUL5pXxhm54Jgo3m8J9OOYPNotch4fVxg4kd; kfz_uuid=2831f1ed-f482-4a9c-bd05-a273539b90c7; kfz-tid=219fc44837ab320a885ce4c31d349443; PHPSESSID=2bb885f8423982b0a117ee51ce01f168493fea0b; kfz_trace=2831f1ed-f482-4a9c-bd05-a273539b90c7|22746450|0afd0b925ed8e58f|";
    // 8089
    private String cookie2 = "shoppingCartSessionId=7a5e55660d0caff56e2f133d0c4dfcdb; reciever_area=1006000000; _c_WBKFRo=4kLYLUL5pXxhm54Jgo3m8J9OOYPNotch4fVxg4kd; kfz_uuid=2831f1ed-f482-4a9c-bd05-a273539b90c7; kfz-tid=219fc44837ab320a885ce4c31d349443; PHPSESSID=b64c72e8ec94a2bb92f6491001ab759201617390; kfz_trace=2831f1ed-f482-4a9c-bd05-a273539b90c7|23793580|3e73b7fba0906e4a|";
    // 8090
//    private String cookie = "utm_source=101002001000; kfz_uuid=2fe9ac8a-4a3a-4694-ae1f-069ab8a46f2a; shoppingCartSessionId=0939dbb30ffcbeca195ac7dab4108e4c; reciever_area=1006000000; _c_WBKFRo=Rf6Hti6VGe4Dvl2V0l2X1gzRSvXRZtbQPYSRkppa; PHPSESSID=0fbe10f42014669f3bed621bb93eb2f6b53d9e54; kfz_trace=2fe9ac8a-4a3a-4694-ae1f-069ab8a46f2a|23883758|cb5e3c1a55a53271|101002001000";

    private List<StoreConfigDto> storeList = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.initStoreList();
    }

    private void initStoreList() {
        storeList.add(new StoreConfigDto("2845", new BigDecimal("1")));
        storeList.add(new StoreConfigDto("457799", new BigDecimal("1.6")));
        storeList.add(new StoreConfigDto("521005", new BigDecimal("3")));
        storeList.add(new StoreConfigDto("464363", new BigDecimal("1.6")));
        storeList.add(new StoreConfigDto("1937", new BigDecimal("3.8")));
        storeList.add(new StoreConfigDto("3669", new BigDecimal("8")));
        storeList.add(new StoreConfigDto("233701", new BigDecimal("10")));
        storeList.add(new StoreConfigDto("6713", new BigDecimal("4")));
        storeList.add(new StoreConfigDto("7471", new BigDecimal("8")));
        storeList.add(new StoreConfigDto("486366", new BigDecimal("6")));
        storeList.add(new StoreConfigDto("238523", new BigDecimal("1")));
        storeList.add(new StoreConfigDto("3092", new BigDecimal("5")));
        storeList.add(new StoreConfigDto("23893", new BigDecimal("5")));
        storeList.add(new StoreConfigDto("791250", new BigDecimal("3")));
        storeList.add(new StoreConfigDto("1181761", new BigDecimal("3.5")));
        storeList.add(new StoreConfigDto("237705", new BigDecimal("6")));
    }
}
