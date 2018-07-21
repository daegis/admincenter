package com.hnair.wallet.admincenter.dao.service.impl;


import com.hnair.wallet.admincenter.dao.spi.ICommonDao;

/**
 * @author XianYingda
 */
public class CommonServiceImpl extends DefaultServiceImpl {

    private ICommonDao commonDao;

    @Override
    public ICommonDao getCommonDao() {
        return commonDao;
    }

    @Override
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }


}
