package com.wholebin.cutscreen.cutScreenUtils;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class cutScreen {
    public static void cutScreen(String title,String url) throws InvocationTargetException, InterruptedException {
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(title);
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//                //不显示标题栏,最大化,最小化,退出按钮
//                frame.setUndecorated(true);
                IeBrower brower = null;
                try {
                    brower = new IeBrower(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.getContentPane().add(brower, BorderLayout.CENTER);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();

    }
}
