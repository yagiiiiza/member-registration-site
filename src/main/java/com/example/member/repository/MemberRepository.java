package com.example.member.repository;

import com.example.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = """
                SELECT
                    id,
                    name,
                    email,
                    password,
                    age,
                    created_at,
                    updated_at
                FROM members
                ORDER BY id ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            member.setAge(rs.getInt("age"));
            member.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            member.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
            return member;
        });
    }

    public Optional<Member> findById(Long id) {
        String sql = """
                SELECT
                    id,
                    name,
                    email,
                    password,
                    age,
                    created_at,
                    updated_at
                FROM members
                WHERE id = ?
                """;

        try {
            Member member = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Member result = new Member();
                result.setId(rs.getLong("id"));
                result.setName(rs.getString("name"));
                result.setEmail(rs.getString("email"));
                result.setPassword(rs.getString("password"));
                result.setAge(rs.getInt("age"));
                result.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                result.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                return result;
            }, id);

            return Optional.ofNullable(member);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void insert(Member member) {
        String sql = """
                INSERT INTO members (
                    name,
                    email,
                    password,
                    age,
                    created_at,
                    updated_at
                )
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
                SET
                    name = ?,
                    email = ?,
                    password = ?,
                    age = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                member.getName(),
                member.getEmail(),
                member.getPassword(),
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
}