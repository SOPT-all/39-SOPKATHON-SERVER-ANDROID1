package sopt._39sopkathonserverandroid1.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.user.dto.response.UserDetailResponse;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository.UserDetailProjection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserDetailResponse findUserDetailOrThrow(Long userId) {
        UserDetailProjection detail = userRepository.findDetailById(userId).orElseThrow(UserNotFoundException::new);
        return UserDetailResponse.of(detail.getUser(), detail.getCompletedMissionCount(), detail.getReceivedLikeCount());
    }
}
