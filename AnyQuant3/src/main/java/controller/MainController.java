package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kylin on 16/5/12.
 */
@Controller
public class MainController {

    /**
     * 请求首页请求:显示FrontPage
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "FrontPage";
    }

}
