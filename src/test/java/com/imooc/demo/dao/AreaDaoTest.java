package com.imooc.demo.dao;

import com.imooc.demo.entity.Area;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class AreaDaoTest {

    //通过spring容器注入Dao的实现类
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testAQueryArea() {
        List<Area> areaList = areaDao.queryArea();
        // 验证预期值和实际值是否相符
        assertEquals(4, areaList.size());
    }

    @Test
    public void testBInsertArea() {
        //创建一个区域对象
        Area area = new Area();
        area.setAreaName("测试区域");
        area.setCreateTime(new Date());
        area.setPriority(1);
        //将该对象实例添加入库
        int effectedNum = areaDao.insertArea(area);
        //检测影响行数
        assertEquals(1, effectedNum);
        //校验总数是否+1
        List<Area> areaList = areaDao.queryArea();
        assertEquals(5, areaList.size());
    }

    @Test
    public void testCQueryAreaById() {
        Area area = areaDao.queryAreaById(2);
        assertEquals("西苑", area.getAreaName());
    }

    @Test
    public void testDUpateArea() {
        List<Area> areaList = areaDao.queryArea();
        for (Area area : areaList) {
            if ("测试区域".equals(area.getAreaName())) {
                // 对比之前的priority值
                assertEquals(2, area.getPriority().intValue());
                area.setPriority(9);
                area.setLastEditTime(new Date());
                int effectedNum = areaDao.updateArea(area);
                assertEquals(1, effectedNum);
            }
        }
    }

    @Test
    public void testEDeleteArea() {
        List<Area> areaList = areaDao.queryArea();
        for (Area area : areaList) {
            if ("测试区域".equals(area.getAreaName())) {
                int effectedNum = areaDao.deleteArea(area.getAreaId());
                assertEquals(1, effectedNum);
            }
        }
        // 重新获取一次列表，看看总数是否少1
        areaList = areaDao.queryArea();
        assertEquals(4, areaList.size());
    }
}