package kr.inhatc.pip.basic.controller;

import kr.inhatc.pip.basic.dto.BasicFormDto;
import kr.inhatc.pip.basic.entity.Basic;
import kr.inhatc.pip.basic.service.BasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final BasicService basicService;

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

    @GetMapping("/basic/new")
    public String createForm() {
        return "basic/createForm";
    }

    @PostMapping("/basic/new")
    public String create(BasicFormDto basicDto) {
        Basic basic = Basic.builder().name(basicDto.getName()).build();
        basicService.join(basic);
        return "redirect:/";
    }

    @GetMapping("/basic")
    public String list(Model model) {
        List<Basic> basics = basicService.findBasic();
        model.addAttribute("basics", basics);
        return "basic/basicList";
    }
}
