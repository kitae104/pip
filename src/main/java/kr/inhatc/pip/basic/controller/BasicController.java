package kr.inhatc.pip.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("/hello-string")
    @ResponseBody  // return "hello" -> "hello" (view resolver 대신에 HttpMessageConverter가 동작)
    public String helloString(@RequestParam("name") String name) {
        return "hello-" + name;
    }

    @GetMapping("/hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;   // 객체를 반환하면 JSON 형태로 반환 (HttpMessageConverter가 동작)
    }

}
