package com.example.member.controller;

import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public String index(@RequestParam(required = false) String keyword, Model model) {
    model.addAttribute("members", memberService.search(keyword));
    model.addAttribute("keyword", keyword);
    return "members/index";
}

    @GetMapping("/members/new")
    public String newForm(Model model) {
        model.addAttribute("member", new Member());
        return "members/form";
    }

    @PostMapping("/members")
    public String create(@ModelAttribute Member member) {
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping("/members/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/detail";
    }

    @GetMapping("/members/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "members/edit";
    }

    @PostMapping("/members/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Member member) {
        member.setId(id);
        memberService.update(member);
        return "redirect:/members/" + id;
    }

    @PostMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/members";
    }
}