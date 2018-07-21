package com.hnair.wallet.admincenter;

import com.alibaba.fastjson.JSON;
import com.hnair.wallet.admincenter.dao.service.ICommonService;
import com.hnair.wallet.admincenter.model.Merchant;
import com.hnair.wallet.admincenter.model.OrderConsumeInfo;
import com.hnair.wallet.admincenter.service.TestTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AdmincenterApplicationTests {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private TestTransaction testTransaction;

    @Test
    public void testTransactional() throws Exception {
        testTransaction.testInsert("ddd");
    }

    @Test
    public void contextLoads() {
        final List<Merchant> list = commonService.getList(Merchant.class);
        for (Merchant merchant : list) {
            System.out.println(JSON.toJSONString(merchant));
        }
    }

    @Test
    public void test01() {
        final List<OrderConsumeInfo> list = commonService.getList(OrderConsumeInfo.class);
        log.info("list size:{}", list.size());
        for (OrderConsumeInfo info : list) {
            log.info(JSON.toJSONString(info));
        }
    }

}
