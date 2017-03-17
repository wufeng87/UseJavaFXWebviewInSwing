import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.Date;

/**
 * Created by delll on 2017/3/17.
 */
public class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser(String url) {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
//        webEngine.load("http://www.oracle.com/products/index.html");
//        webEngine.load("./html/index.html");
        webEngine.load(url);

        //add the web view to the scene
        getChildren().add(browser);

    }

    /**
     * 执行浏览器html页面中的脚本
     * @param functionName 脚本名字
     * @param param 参数（json string）
     */
    public void executeScript(final String functionName, final String param) {
        // 到javaFX线程中执行
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // 获取浏览器中window对象
                JSObject jsObject = (JSObject) webEngine.executeScript("window");
                // 调用window对象上的方法，传参数
                jsObject.call(functionName, param);
            }
        });

//        webEngine.executeScript(functionName);
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 750;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
