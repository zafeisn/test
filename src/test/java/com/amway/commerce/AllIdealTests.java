package com.amway.commerce;

import com.amway.commerce.calculation.BigDecimalUtilTest;
import com.amway.commerce.calculation.MathUtilTest;
import com.amway.commerce.calculation.MoneyUtilTest;
import com.amway.commerce.crypto.*;
import com.amway.commerce.datetime.DateTimeUtilTest;
import com.amway.commerce.http.HttpUtilTest;
import com.amway.commerce.infrastructure.PropertiesUtilTest;
import com.amway.commerce.ip.IpUtilTest;
import com.amway.commerce.serialization.JsonUtilTest;
import com.amway.commerce.serialization.XMLUtilTest;
import com.amway.commerce.spring.SpringContextUtilTest;
import com.amway.commerce.string.ByteUtilTest;
import com.amway.commerce.string.StringUtilTest;
import com.amway.commerce.validation.EnumValidUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author: Jason.Hu
 * @date: 2023-10-13
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        // calculation
        BigDecimalUtilTest.class,
        MathUtilTest.class,
        MoneyUtilTest.class,
        // crypto
        AESUtilTest.class,
        Base64UtilTest.class,
        DESUtilTest.class,
        MD5UtilTest.class,
        RSAUtilTest.class,
        // datetime
        DateTimeUtilTest.class,
        // http
        HttpUtilTest.class,
        // infrastructure
        PropertiesUtilTest.class,
        // ip
        IpUtilTest.class,
        // serialization
        JsonUtilTest.class,
        XMLUtilTest.class,
        // spring
        SpringContextUtilTest.class,
        // string
        ByteUtilTest.class,
        StringUtilTest.class,
        // validation
        EnumValidUtilTest.class

})
public class AllIdealTests {
}
