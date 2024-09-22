package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/")
    public String userController(){
        //Business Logic
        //Call a method in a Service Class
        //Add some data to the Model
        return "profa";
    }
}
