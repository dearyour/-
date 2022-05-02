package com.seonbi.api.service;

import com.seonbi.api.model.FriendDto;
import com.seonbi.api.model.FriendFollowDto;
import com.seonbi.db.entity.Friend;
import com.seonbi.db.entity.Member;
import com.seonbi.db.repository.FriendRepository;
import com.seonbi.db.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ImageService imageService;



    @Override
    public int followFriend(Long memberId, Long friendId) {
        if (memberId.equals(friendId)){     // 자기 자신에게
            return 406;
        }
        if (memberRepository.findByMemberIdAndIsDeleted(friendId, false)==null){    // 벗 요청받은 사람 계정이 없음
            return 401;
        }
        Friend before=friendRepository.findByFollowerIdAndFolloweeIdAndIsDeleted(memberId, friendId, false);
        if (before!=null&&"BEFORE".equals(before.getIsAllowed())){
            return 402;
        } else if (before!=null&&"OK".equals(before.getIsAllowed())){
            return 405;
        }

        // before==null 이거나 요청을 한번 거절한 경우
        Friend friend = new Friend();
        friend.setFollowerId(memberId);
        friend.setFolloweeId(friendId);
        friendRepository.save(friend);
        return 200;

    }

    @Override
    public int followFriendAllow(Long followeeId, Long followerId, String allow) {
        Friend friend = friendRepository.findByFollowerIdAndFolloweeIdAndIsAllowedAndIsDeleted(followerId, followeeId, "BEFORE", false);
        if (friend==null){
            return 401;
        }
        friend.setIsAllowed(allow);
        friendRepository.save(friend);
        return 200;
    }

    @Override
    public List<FriendFollowDto> getFollowFriendAll(Long followeeId) {
        List<Friend> friends=friendRepository.findAllByFolloweeIdAndIsAllowedAndIsDeleted(followeeId, "BEFORE", false);
        List<FriendFollowDto> friendFollowDtoList=new ArrayList<>();
        for (Friend friend: friends){
            Member member= memberRepository.findByMemberIdAndIsDeleted(friend.getFollowerId(), false);
            if (member==null){
                continue;
            }
            String imageString=imageService.getImage(member.getImageId());
            FriendFollowDto friendFollowDto=new FriendFollowDto(member.getMemberId(), member.getNickname(), imageString);
            friendFollowDtoList.add(friendFollowDto);
        }
        return friendFollowDtoList;
    }

}
