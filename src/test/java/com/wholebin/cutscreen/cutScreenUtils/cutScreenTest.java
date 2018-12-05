package com.wholebin.cutscreen.cutScreenUtils;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
public class cutScreenTest {

    @Test
    public void cutScreen() {
        try {
            cutScreen.cutScreen("taobao","https://www.taobao.com/");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}