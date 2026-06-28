package com.example.member.repository;

import com.example.member.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = """
                SELECT id, name, email, password, age, created_at, updated_at
                FROM members
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public Member findById(Long id) {
        String sql = """
                SELECT id, name, email, password, age, created_at, updated_at
                FROM members
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, memberRowMapper(), id);
    }

    public void save(Member member) {
        String sql = """
                INSERT INTO members (name, email, password, age, created_at, updated_at)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                """;

        jdbcTemplate.update(
                sql,
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getAge()
        );
    }

    public void update(Member member) {
        String sql = """
                UPDATE members
                SET name = ?,
                    email = ?,
                    age = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                member.getName(),
                member.getEmail(),
                member.getAge(),
                member.getId()
        );
    }

    public void deleteById(Long id) {
        String sql = """
                DELETE FROM members
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            member.setAge(rs.getInt("age"));
            member.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            member.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return member;
        };
    }
}