package arom_semo.server.domain.joinrequest.repository;

import arom_semo.server.domain.group.domain.Group;
import arom_semo.server.domain.joinrequest.domain.GroupJoinRequest;
import arom_semo.server.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {
    boolean existsByGroupAndMemberAndStatus(Group group, Member member, GroupJoinRequest.Status status);
    List<GroupJoinRequest> findAllByGroupAndStatus(Group group, GroupJoinRequest.Status status);
    List<GroupJoinRequest> findAllByMemberAndStatus(Member member, GroupJoinRequest.Status status);
}
