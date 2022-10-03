package com.huhoot.host.manage.challenge;

import com.huhoot.converter.ListConverter;
import com.huhoot.dto.ChallengeResponse;
import com.huhoot.exception.NotYourOwnException;
import com.huhoot.model.Challenge;
import com.huhoot.model.Customer;
import com.huhoot.repository.ChallengeRepository;
import com.huhoot.repository.ParticipantRepository;
import com.huhoot.repository.StudentAnswerRepository;
import com.huhoot.vue.vdatatable.paging.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManageChallengeServiceImpl implements ManageChallengeService {
    private final ChallengeRepository challengeRepository;

    private final ListConverter listConverter;

    private final ChallengeMapper challengeMapper;
    private final StudentAnswerRepository studentAnswerRepository;

    private final ParticipantRepository participantRepo;



    @Override
    public PageResponse<ChallengeResponse> findAllChallenge(Pageable pageable) {
        Page<ChallengeResponse> challenges = challengeRepository.findAllChallengeResponse(pageable);
        return listConverter.toPageResponse(challenges);
    }

    @Override
    public PageResponse<ChallengeResponse> findAllOwnChallenge(int adminId, Pageable pageable) {
        Page<ChallengeResponse> challenges = challengeRepository.findAllByAdminId(adminId, pageable);
        return listConverter.toPageResponse(challenges);
    }


    @Override
    public ChallengeResponse addOneChallenge(Customer customer, ChallengeAddRequest request) {

        Challenge challenge = challengeMapper.toEntity(request);
        challenge.setCustomer(
                customer);

        Challenge saved = challengeRepository.save(challenge);

        return challengeMapper.toDto(saved);

    }


    @Override
    public void updateOneChallenge(ChallengeUpdateRequest request, Challenge challenge) throws NullPointerException {
        challengeMapper.update(request, challenge);
        challengeRepository.save(challenge);
    }

    @Override
    public Challenge findChallengeWithOwner(int challengeId, int userId) throws NotYourOwnException {
        Challenge challenge = challengeRepository.findOneById(challengeId).orElseThrow(() -> new NullPointerException("Challenge not found"));

        if (challenge.getCustomer().getId() == userId) {
            return challenge;
        } else {
            throw new NotYourOwnException("Challenge not own");
        }

    }

    @Override
    public Challenge findChallenge(int challengeId) {
        return challengeRepository.findOneById(challengeId).orElseThrow(() -> new NullPointerException("Challenge not found"));
    }

    @Override
    public ChallengeResponse findChallengeResponse(int challengeId) {
        return challengeRepository.findOneChallengeResponse(challengeId).orElseThrow(() -> new NullPointerException("Challenge not found"));
    }

    @Override
    public int findStudentRank(int studentId, int challengeId) {

        return 0;
    }

    @Override
    public double getStudentTotalPoint(int studentId, int challengeId) {

        return studentAnswerRepository.getTotalPointInChallenge(challengeId, studentId);
    }

    @Override
    public PageResponse<ChallengeResponse> findAllParticipate(int id, Pageable pageable) {
        Page<ChallengeResponse> allByKeyCustomerId = participantRepo.findAllByKeyCustomerId(id, pageable);

        return listConverter.toPageResponse(allByKeyCustomerId);
    }


}
