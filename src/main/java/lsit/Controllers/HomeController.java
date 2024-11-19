package lsit.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity get(){
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/user")
    public String getUser(OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(!groups.contains("lsit-ken3239/roles/petkeepers")){
            //throw new Exception("Access Denied...");
        };

        var userAttributes = authentication.getPrincipal().getAttributes();

        // StringBuilder sb = new StringBuilder();
        // for(var entry : userAttributes.entrySet()){
        //     var s = entry.getKey() + ": " + entry.getValue();
        //     sb.append("\n").append(s);
        // }

        return "<pre> \n" +
            userAttributes.entrySet().parallelStream().collect(
                StringBuilder::new,
                (s, e) -> s.append(e.getKey()).append(": ").append(e.getValue()),
                (a, b) -> a.append("\n").append(b)
            ) +
            "</pre>";
    }

}
