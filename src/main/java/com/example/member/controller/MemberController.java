package com.example.member.controller;

import com.example.member.dto.MemberForm;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/members";
    }

    @GetMapping("/members")
    public String index(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/index";
    }

    @GetMapping("/members/new")
    public String newForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("pageTitle", "会員新規登録");
        model.addAttribute("buttonText", "登録");
        return "members/form";
    }

    @PostMapping("/members")
    public String create(
            @Valid @ModelAttribute("memberForm") MemberForm memberForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "会員新規登録");
            model.addAttribute("buttonText", "登録");
            return "members/form";
        }

        memberService.create(memberForm);
        return "redirect:/members";
    }

    @GetMapping("/members/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        MemberForm memberForm = memberService.toForm(member);

        model.addAttribute("memberForm", memberForm);
        model.addAttribute("pageTitle", "会員編集");
        model.addAttribute("buttonText", "更新");
        return "members/form";
    }

    @PostMapping("/members/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("memberForm") MemberForm memberForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            memberForm.setId(id);
            model.addAttribute("pageTitle", "会員編集");
            model.addAttribute("buttonText", "更新");
            return "members/form";
        }

        memberService.update(id, memberForm);
        return "redirect:/members";
    }

    @PostMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }
}