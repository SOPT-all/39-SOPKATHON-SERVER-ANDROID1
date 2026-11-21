package sopt._39sopkathonserverandroid1.domain.mission.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.IncompleteMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionBasketService {

    private final MissionBasketRepository missionBasketRepository;
    private final UserRepository userRepository;

    public List<IncompleteMissionResponse> findIncompleteMissions(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        return missionBasketRepository.findIncompleteByUserId(userId).stream()
                .map(IncompleteMissionResponse::of)
                .toList();
    }
}
