package com.huhoot.config.init;

import com.github.javafaker.Faker;
import com.huhoot.enums.ChallengeStatus;
import com.huhoot.model.Answer;
import com.huhoot.model.Challenge;
import com.huhoot.model.Customer;
import com.huhoot.model.Question;
import com.huhoot.repository.AnswerRepository;
import com.huhoot.repository.ChallengeRepository;
import com.huhoot.repository.CustomerRepository;
import com.huhoot.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;


@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {


    private final CustomerRepository customerRepository;
    private final ChallengeRepository challengeRepository;
    private final PasswordEncoder pwEnc;
    private final QuestionRepository questRepo;
    private final AnswerRepository ansRepo;


    public void run(ApplicationArguments args) {


        Random random = new Random();

        Faker faker = new Faker(new Locale("vi-VN"));


        long date = System.currentTimeMillis();

        // start time
        long t0 = System.nanoTime();


        List<Customer> customers = customerRepository.saveAll(IntStream.range(0, 25).mapToObj(index -> Customer.builder().username("user" + index).fullName(faker.name().fullName()).password(pwEnc.encode("pw")).isNonLocked(true).build()).toList());

        List<Challenge> challenges = challengeRepository.saveAll(IntStream.range(0,7).mapToObj(index -> Challenge.builder().title(faker.lorem().paragraph()).customer(customers.get(0)).challengeStatus(ChallengeStatus.BUILDING).isNonDeleted(true).build()).toList());

        List<Question> quests = new ArrayList<>();

        challenges.forEach(c-> {
            List<Question> questions = IntStream.range(0, 20).mapToObj(index -> Question.builder()
                    .ordinalNumber(index)
                    .challenge(c)
                    .content(faker.lorem().paragraph())
                    .image(getRandomImgUrl())
                    .answerTimeLimit(7)
                    .isNonDeleted(true)
                    .build()).toList();
            quests.addAll(questions);
        });


        List<Question> questions = questRepo.saveAll(quests);


        List<Answer> answers = new ArrayList<>();

        questions.forEach(e->{
            answers.addAll(IntStream.range(0,4).mapToObj(index->Answer.builder().content(faker.lorem().paragraph()).isNonDeleted(true).question(e).isCorrect(faker.number().numberBetween(1,4) == index).build()).toList());
        });

        ansRepo.saveAll(answers);


    }

    private String getRandomImgUrl() {
        List<String> imgUrls = new ArrayList<>();

        imgUrls.add("1da20102-db0f-4820-bb19-ed7d07e54cdb.jpg");
        imgUrls.add("5d2500ce-8c22-4b43-a3c9-842bb866204a.jpg");
        imgUrls.add("7b6f4ba8-347e-4452-b66f-1a00b99b5c8a.jpg");
        imgUrls.add("26e49ab7-0752-44b8-9aba-915937064e00.jpg");
        imgUrls.add("285a4e82-2ad4-462a-bfe3-e9b9ab846e7f.png");
        imgUrls.add("9795ce36-e07b-438a-ba88-428d11588398.jpg");
        imgUrls.add("68820dbd-b439-45cc-bb2a-5a50a758b725.jpg");
        imgUrls.add("a22a4558-039e-4522-bb15-af863e95eab1.jpg");
        imgUrls.add("ab804242-d08e-4f9d-9ec0-42c9082e945f.jpeg");
        imgUrls.add("b9759822-f86a-4fae-a06d-aaef15a162a0.jpg");
        imgUrls.add("ce84e707-af4f-40d1-920f-e17d26a39ce7.png");
        imgUrls.add("f57f24e9-69d2-4d26-9ac7-e05ee9dcdae3.svg");

        Random random = new Random();
        int rand = random.nextInt(6);

        return imgUrls.get(rand);
    }

}