package com.example.member.service;

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
        return memberRepository.findById(id);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void update(Member member) {
        memberRepository.update(member);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}