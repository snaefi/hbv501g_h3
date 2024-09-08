package is.hi.hbv501g.hbv501g_h3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!!!!!!!!!!!!";
    }
}
