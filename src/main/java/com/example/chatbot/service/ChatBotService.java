package com.example.chatbot.service;

import com.example.chatbot.constant.Constant;
import com.example.chatbot.dto.AnswerUser;
import com.example.chatbot.dto.BmiRequest;
import com.example.chatbot.dto.ResponseBmi;
import com.example.chatbot.model.*;
import com.example.chatbot.repository.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class ChatBotService {

    private final Chat chatSession;

    private final StageRepository stageRepository;

    private final BmiRepository bmiRepository;

    private final CaseBaseRepository caseBaseRepository;

    private final ExerciseIntensityRepository exerciseIntensityRepository;

    private final HabitRepository habitRepository;

    private final OtherSportRepository otherSportRepository;

    private final SimilarWeightsRepository similarWeightsRepository;

    private String questionPre = "";

    private AnswerUser answerUser = new AnswerUser();

    private Stack<String> questions = new Stack<>();

    private Integer flap = -1;

    public ChatBotService(Bot alice, StageRepository stageRepository, BmiRepository bmiRepository,
                          CaseBaseRepository caseBaseRepository, ExerciseIntensityRepository exerciseIntensityRepository,
                          HabitRepository habitRepository, OtherSportRepository otherSportRepository,
                          SimilarWeightsRepository similarWeightsRepository) {
        chatSession = new Chat(alice);
        this.stageRepository = stageRepository;
        this.bmiRepository = bmiRepository;
        this.caseBaseRepository = caseBaseRepository;
        this.exerciseIntensityRepository = exerciseIntensityRepository;
        this.habitRepository = habitRepository;
        this.otherSportRepository = otherSportRepository;
        this.similarWeightsRepository = similarWeightsRepository;
    }

    //    public String sendMessage(String text) {
//        String answer = chatSession.multisentenceRespond(text);
//        System.out.print(answer);
//        return answer;
//    }
    private void init() {
        questions = new Stack<>();
        questions.push(Constant.Question.SPORT);
        questions.push(Constant.Question.HABIT);
        questions.push(Constant.Question.EXERCISE_INTENSITY);
        questions.push(Constant.Question.HEIGHT);
        questions.push(Constant.Question.WEIGHT);
        questions.push(Constant.Question.SEX);
        questions.push(Constant.Question.AGE);
        questions.push(Constant.Question.STAGE);
        flap = 0;
        questionPre = "";
        answerUser = new AnswerUser();
    }

    public String sendMessage(String text) {
        String response = text.toLowerCase();
        if (flap == -1) {
            if (Constant.Command.READY.equals(response)) {
                init();
                questionPre = questions.pop();
                return Constant.Command.WELCOME + "\n" + questionPre;
            }
            return Constant.Command.START;
        } else if (flap == 0) {
            switch (questionPre) {
                case Constant.Question.STAGE:
                    answerUser.setStage(response);
                    break;
                case Constant.Question.AGE:
                    answerUser.setAge(Integer.parseInt(response));
                    break;
                case Constant.Question.SEX:
                    answerUser.setSex(Integer.parseInt(response));
                    break;
                case Constant.Question.WEIGHT:
                    answerUser.setWeight(Float.parseFloat(response));
                    break;
                case Constant.Question.HEIGHT:
                    answerUser.setHeight(Integer.parseInt(response));
                    break;
                case Constant.Question.EXERCISE_INTENSITY:
                    answerUser.setExerciseIntensity(response);
                    break;
                case Constant.Question.HABIT:
                    answerUser.setHabit(response);
                    break;
                case Constant.Question.SPORT:
                    answerUser.setSport(response);
                    break;
                default:
                    break;
            }
            if (questions.size() == 0) {
                flap = -1;
                return handleInput();
            }
            questionPre = questions.peek();
            return questions.pop();
        }
        return "";
    }

    private String handleInput() {
        float maxCb = 0;
        CaseBase caseBase = new CaseBase();
        Stage stage = stageRepository.findByDescriptionContaining(answerUser.getStage());
        Bmi bmi = bmiRepository.findByValue(getBmi(new BmiRequest(answerUser.getSex(), answerUser.getAge(), answerUser.getWeight(), answerUser.getHeight())));
        ExerciseIntensity exerciseIntensity = exerciseIntensityRepository.findByDescriptionContaining(answerUser.getExerciseIntensity());
        Habit habit = habitRepository.findByDescriptionContaining(answerUser.getHabit());
        if( habit == null){
            habit = habitRepository.findById(Long.valueOf(4)).get();
        }
        OtherSport otherSport = otherSportRepository.findByDescriptionContaining(answerUser.getSport());
        List<CaseBase> caseBaseList = (List<CaseBase>) caseBaseRepository.findAll();
        for (CaseBase cb : caseBaseList) {
            float valueStage = (6 * similarWeightsRepository.findByNameAndCaseFromIdAndCaseToId(Constant.NameElement.STAGE,
                    stage.getId(), cb.getStageId()).getValue());
            float valueBmi = (4 * similarWeightsRepository.findByNameAndCaseFromIdAndCaseToId(Constant.NameElement.BMI,
                    bmi.getId(), cb.getBmiId()).getValue());
            float valueExi = (2 * similarWeightsRepository.findByNameAndCaseFromIdAndCaseToId(Constant.NameElement.EXI,
                    exerciseIntensity.getId(), cb.getExerciseId()).getValue());
            float valueHabit = (1 * similarWeightsRepository.findByNameAndCaseFromIdAndCaseToId(Constant.NameElement.HABIT,
                    habit.getId(), cb.getHabitId()).getValue());
            float valueSport = (1 * similarWeightsRepository.findByNameAndCaseFromIdAndCaseToId(Constant.NameElement.SPORT,
                    otherSport.getId(), cb.getOtherSportId()).getValue());
            float sum = (valueStage + valueBmi + valueExi + valueHabit + valueSport) / 14;
            if (sum > maxCb) {
                maxCb = sum;
                caseBase = cb;
            }
        }
        return caseBase.getNutrition() + '\n' + "Cảm ơn bạn đã tham gia tư vấn!" ;
    }


    public Float getBmi(BmiRequest bmi) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/bmi-perdic";
        Map<String, Object> body = new HashMap<>();
        body.put("bmi", bmi);
        String jsonRequest = null;
        try {
            jsonRequest = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert jsonRequest != null;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, httpHeaders);
        Map<String, String> result = restTemplate.postForObject(url, request, HashMap.class);
        return Float.valueOf(result.get("result"));
    }


    private final ObjectMapper mapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

}
