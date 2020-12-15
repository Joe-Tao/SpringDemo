package com.example.demo.Controller;

import com.example.demo.dto.AccesstokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubService gitHubService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @ResponseBody
    @GetMapping("/callback")
    public String callback(@RequestParam(name= "code") String code,
                           @RequestParam(name = "state") String state){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        String accessToken = gitHubService.getAccessToken(accesstokenDTO);
        GithubUser githubUser =gitHubService.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
