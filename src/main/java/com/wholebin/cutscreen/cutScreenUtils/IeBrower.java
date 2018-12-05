package com.wholebin.cutscreen.cutScreenUtils;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IeBrower extends JPanel {
    private static final long serialVersionUID = 1L;

    private JPanel webBrowserPanel;

    private JWebBrowser webBrowser;

    private static final long serialversionuid = 1l;
// 行分隔符
    final static public String ls = System.getProperty("line.separator", "\n");
// 文件分割符
    final static public String fs = System.getProperty("file.separator", "\\");

    private String url;
    final static StringBuffer jsdimension;
    String str = "window.scrollTo(0,document.body.clientHeight)";
    static {
        jsdimension = new StringBuffer();
        jsdimension.append("if(document.readyState == \"complete\")").append(ls);
        jsdimension.append("{").append(ls);
        jsdimension.append("var width = 0;").append(ls);
        jsdimension.append("var height = 0;").append(ls);
//        jsdimension.append("if(document.documentelement) {").append(ls);
//        jsdimension.append(
//                "  width = math.max(width, document.documentelement.scrollwidth);")
//                .append(ls);
//        jsdimension.append(
//                "  height = math.max(height, document.documentelement.scrollheight);")
//                .append(ls);
//        jsdimension.append("}").append(ls);
//        jsdimension.append("if(self.innerwidth) {").append(ls);
//        jsdimension.append("  width = math.max(width, self.innerwidth);")
//                .append(ls);
//        jsdimension.append("  height = math.max(height, self.innerheight);")
//                .append(ls);
//        jsdimension.append("}").append(ls);
//        jsdimension.append("if(document.body.scrollwidth) {").append(ls);
//        jsdimension.append(
//                "  width = math.max(width, document.body.scrollwidth);")
//                .append(ls);
//        jsdimension.append(
//                "  height = math.max(height, document.body.scrollheight);")
//                .append(ls);
//        jsdimension.append("}").append(ls);
        jsdimension.append("height = document.body.clientHeight").append(ls);
        jsdimension.append("width = document.body.clientWidth").append(ls);
        jsdimension.append("return width + ':' + height;").append(ls);
        jsdimension.append("}");
    }

    public IeBrower(String url) throws IOException {
        super(new BorderLayout());
        this.url = url;

        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowser = new JWebBrowser();
        webBrowser.navigate(url);
        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void loadingProgressChanged(WebBrowserEvent e) {
                super.loadingProgressChanged(e);
                if(e.getWebBrowser().getLoadingProgress()==100){
                    webBrowser.executeJavascript(str);
                    String  result = (String) webBrowser.executeJavascriptWithResult(jsdimension.toString());
                    int index = result == null ? -1 : result.indexOf(":");
                    NativeComponent nativecomponent = webBrowser.getNativeComponent();
                    Dimension originalsize = nativecomponent.getSize();
                    Dimension imagesize = new Dimension(Integer.parseInt(result.substring(0, index)), Integer.parseInt(result.substring(index + 1)));
                    imagesize.width = Math.max(originalsize.width, imagesize.width + 50);
                    imagesize.height = Math.max(originalsize.height,imagesize.height+50);
                    nativecomponent.setSize(imagesize);
                    BufferedImage image = new BufferedImage(imagesize.width,imagesize.height, BufferedImage.TYPE_INT_RGB);
                    nativecomponent.paintComponent(image);
                    nativecomponent.setSize(originalsize);
                    // 当网页超出目标大小时
                    if (imagesize.width > 1000000000|| imagesize.height > 2000000000) {
                        //截图部分图形
                        image = image.getSubimage(0, 0, 10000,20000);
                        /*此部分为使用缩略图
96.                        int width = image.getwidth(), height = image
97.                            .getheight();
98.                         affinetransform tx = new affinetransform();
99.                        tx.scale((double) maxwidth / width, (double) maxheight
100.                                / height);
101.                        affinetransformop op = new affinetransformop(tx,
102.                                affinetransformop.type_nearest_neighbor);
103.                        //缩小
104.                        image = op.filter(image, null);*/
                        }
                    try {
                    // 输出图像
                        ImageIO.write(image, "jpg", new File("l"));
                        } catch (IOException ex) {
                         ex.printStackTrace();
                        }
                    // 退出操作
                    System.exit(0);

                    }
                }

        });

    }
}
