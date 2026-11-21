package sopt._39sopkathonserverandroid1.domain.mission.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt._39sopkathonserverandroid1.domain.image.service.MissionImageUrlService;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.IncompleteMissionResponse;
import sopt._39sopkathonserverandroid1.domain.mission.dto.response.MissionBasketCreateResponse;
import sopt._39sopkathonserverandroid1.domain.mission.entity.MissionBasket;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionAlreadyInBasketException;
import sopt._39sopkathonserverandroid1.domain.mission.exception.MissionNotFoundException;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionBasketRepository;
import sopt._39sopkathonserverandroid1.domain.mission.repository.MissionRepository;
import sopt._39sopkathonserverandroid1.domain.user.exception.UserNotFoundException;
import sopt._39sopkathonserverandroid1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionBasketService {

    private final MissionBasketRepository missionBasketRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final MissionImageUrlService missionImageUrlService;

    @Transactional
    public MissionBasketCreateResponse addMissionToBasket(Long userId, Long missionId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!missionRepository.existsById(missionId)) {
            throw new MissionNotFoundException();
        }
        if (missionBasketRepository.existsByUserIdAndMissionId(userId, missionId)) {
            throw new MissionAlreadyInBasketException();
        }

        MissionBasket basket = MissionBasket.create(userId, missionId);
        try {
            return MissionBasketCreateResponse.of(missionBasketRepository.saveAndFlush(basket));
        } catch (DataIntegrityViolationException exception) {
            if (exception.getCause() instanceof ConstraintViolationException violation) {
                String constraint = violation.getConstraintName();
                String uniqueConstraint = MissionBasket.USER_MISSION_UNIQUE_CONSTRAINT;
                // MySQL may qualify the constraint name with its table name.
                if (constraint != null
                        && (constraint.equals(uniqueConstraint) || constraint.endsWith("." + uniqueConstraint))) {
                    throw new MissionAlreadyInBasketException();
                }
            }
            throw exception;
        }
    }

    public List<IncompleteMissionResponse> findIncompleteMissions(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        return missionBasketRepository.findIncompleteByUserId(userId).stream()
                .map(basket -> IncompleteMissionResponse.of(
                        basket, missionImageUrlService.resolve(basket.getMission().getImageUrl())
                ))
                .toList();
    }
}
