package com.lstfight.systemutil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>日志切面测试</p>
 * @author 李尚庭
 *
 */
public class LogTest {

    @Test
    public void logTest() {
        List<Integer> plist = new ArrayList<>();
        plist.add(1);
        plist.add(4);
        plist.add(3);
        plist.add(2);
        List<Integer> olist = new LinkedList<>();

        olist.add(1);
        olist.add(2);
        olist.add(3);
        olist.add(4);
        olist.add(5);
        olist.removeAll(plist);

        //测试批量移除
        olist.forEach(System.out::println);
    }
}
