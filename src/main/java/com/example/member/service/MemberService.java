package com.example.member.service;

import com.example.member.dto.MemberForm;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("会員が見つかりません。id=" + id));
    }

    public void create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        member.setAge(form.getAge());

        memberRepository.insert(member);
    }

    public void update(Long id, MemberForm form) {
        Member member = new Member();
        member.setId(id);
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());
        member.setAge(form.getAge());

        memberRepository.update(member);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberForm toForm(Member member) {
        return new MemberForm(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getAge()
        );
    }
}