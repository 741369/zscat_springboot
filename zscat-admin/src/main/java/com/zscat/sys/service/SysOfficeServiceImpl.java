 /*
  * Powered By zsCat, Since 2014 - 2020
  */
package com.zscat.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zscat.base.ServiceMybatis;
import com.zscat.sys.mapper.SysOfficeMapper;
import com.zscat.sys.model.SysOffice;

/**
 * 
 * @author zsCat 2017-4-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 */
@Service
public class SysOfficeServiceImpl extends ServiceMybatis<SysOffice> implements SysOfficeService {

    @Autowired
    private SysOfficeMapper SysOfficeMapper;

    
}
