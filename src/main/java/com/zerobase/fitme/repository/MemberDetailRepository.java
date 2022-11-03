package com.zerobase.fitme.repository;

import com.zerobase.fitme.entity.Member;
import com.zerobase.fitme.entity.MemberDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
}
