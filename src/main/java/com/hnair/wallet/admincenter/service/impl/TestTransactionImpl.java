package com.hnair.wallet.admincenter.service.impl;

import com.hnair.wallet.admincenter.dao.service.ICommonService;
import com.hnair.wallet.admincenter.model.Merchant;
import com.hnair.wallet.admincenter.service.TestTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 7/21/2018 12:58 PM
 */
@Service
public class TestTransactionImpl implements TestTransaction {

    @Autowired
    private ICommonService commonService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, transactionManager = "transactionManager")
    public void testInsert(String name) throws Exception {
        final Merchant dddd = new Merchant();
        dddd.setMerchantName(UUID.randomUUID().toString());
        commonService.save(dddd);
        final Merchant merchant = new Merchant();
        merchant.setMerchantName(name);
        if (name.equals("err")) {
            throw new Exception("模拟错误");
        }
        commonService.save(merchant);
    }
}
