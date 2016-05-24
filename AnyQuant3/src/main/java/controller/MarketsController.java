package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
@Controller
public class MarketsController {

    @RequestMapping(value = "*.markets", method = {RequestMethod.GET})
    public ModelAndView getStock(HttpServletRequest httpServletRequest){
        return new ModelAndView("markets",null);
    }

}
