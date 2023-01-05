package com.example.chatbot.service;

import com.example.chatbot.constant.Constant;
import com.example.chatbot.dto.AnswerUser;
import com.example.chatbot.model.*;
import com.example.chatbot.repository.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class ChatBotService {


    private final ConditionRepository conditionRepository;

    private final FootSymptomsRepository footSymptomsRepository;

    private final CaseBaseRepository caseBaseRepository;

    private final KneeSymptomsRepository kneeSymptomsRepository;

    private final HabitRepository habitRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    private final PainAreaRepository painAreaRepository;
    private final WeightsRepository weightsRepository;

    private String questionPre = "";

    private AnswerUser answerUser = new AnswerUser();

    private Stack<String> questions = new Stack<>();

    private Integer flap = -1;

    public ChatBotService(ConditionRepository conditionRepository, FootSymptomsRepository footSymptomsRepository, CaseBaseRepository caseBaseRepository, KneeSymptomsRepository kneeSymptomsRepository, HabitRepository habitRepository, MedicalRecordRepository medicalRecordRepository, PainAreaRepository painAreaRepository, WeightsRepository weightsRepository) {
        this.conditionRepository = conditionRepository;
        this.footSymptomsRepository = footSymptomsRepository;
        this.caseBaseRepository = caseBaseRepository;
        this.kneeSymptomsRepository = kneeSymptomsRepository;
        this.habitRepository = habitRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.painAreaRepository = painAreaRepository;
        this.weightsRepository = weightsRepository;
    }

    //    public String sendMessage(String text) {
//        String answer = chatSession.multisentenceRespond(text);
//        System.out.print(answer);
//        return answer;
//    }
    private void init() {
        questions = new Stack<>();
        questions.push(Constant.Question.SEX);
        questions.push(Constant.Question.PAIN_AREA);
        questions.push(Constant.Question.HABIT);
        questions.push(Constant.Question.CONDITION);
        questions.push(Constant.Question.KNEE_SYMPTOMS);
        questions.push(Constant.Question.FOOT_SYMPTOMS);
        questions.push(Constant.Question.MEDICAL_RECORD);
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
                case Constant.Question.SEX:
                    answerUser.setSex(Long.parseLong(response));
                    break;
                case Constant.Question.PAIN_AREA:
                    answerUser.setPainArea(response);
                    break;
                case Constant.Question.HABIT:
                    answerUser.setHabit(response);
                    break;
                case Constant.Question.CONDITION:
                    answerUser.setCondition((response));
                    break;
                case Constant.Question.KNEE_SYMPTOMS:
                    answerUser.setKneeSymptoms(response);
                    break;
                case Constant.Question.FOOT_SYMPTOMS:
                    answerUser.setFootSymptoms(response);
                    break;
                case Constant.Question.MEDICAL_RECORD:
                    answerUser.setMedicalRecord(response);
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
        int countError = 0;
        CaseBase caseBase = new CaseBase();
        PainArea painArea = painAreaRepository.findByNameContaining(answerUser.getPainArea());
        if (painArea == null)
            countError += 1;
        Habit habit = habitRepository.findByNameContaining(answerUser.getHabit());
        if (habit == null)
            countError += 1;
        Condition condition = conditionRepository.findConditionByNameContaining(answerUser.getCondition());
        if (condition == null)
            countError += 1;
        FootSymptoms footSymptoms = footSymptomsRepository.findByNameContaining(answerUser.getFootSymptoms());
        if (footSymptoms == null)
            countError += 1;
        KneeSymptoms kneeSymptoms = kneeSymptomsRepository.findByNameContaining(answerUser.getKneeSymptoms());
        if (kneeSymptoms == null)
            countError += 1;
        MedicalRecord medicalRecord = medicalRecordRepository.findByNameContaining(answerUser.getMedicalRecord());
        if (medicalRecord == null)
            countError += 1;
        if (answerUser.getSex() != 1 && answerUser.getSex() != 0)
            countError += 1;
        if (countError >= 3)
            return Constant.Question.WRONG_ANSWER;
        List<CaseBase> caseBaseList = (List<CaseBase>) caseBaseRepository.findAll();
        for (CaseBase cb : caseBaseList) {
            float valuePA;
            if (painArea == null)
                valuePA = 0;
            else
                valuePA = (6 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.PAIN_AREA,
                        painArea.getId(), cb.getPainArea().getId()).getValue());
            float valueHabit;
            if(habit == null)
                valueHabit=0;
            else
                valueHabit = (4 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.HABIT,
                    habit.getId(), cb.getHabit().getId()).getValue());
            float valueFS;
            if(footSymptoms == null)
                valueFS = 0;
            else
                valueFS = (3 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.FOOT_SYMPTOMS,
                    footSymptoms.getId(), cb.getFootSymptoms().getId()).getValue());
            float valueKS;
            if(kneeSymptoms == null)
                valueKS = 0;
            else
                valueKS = (3 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.KNEE_SYMPTOMS,
                    kneeSymptoms.getId(), cb.getKneeSymptoms().getId()).getValue());
            float valueCondition;
            if(condition == null)
                valueCondition = 0;
            else
                valueCondition = (2 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.CONDITION,
                    condition.getId(), cb.getCondition().getId()).getValue());
            float valueSex;
            if (cb.getSex() == 2)
                valueSex = 2;
            else
                valueSex = (2 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.SEX,
                        answerUser.getSex(), cb.getSex()).getValue());
            float valueMR;
            if(medicalRecord == null)
                valueMR = 0;
            else
                valueMR = (2 * weightsRepository.findByNameAndFeatureIdAndFeatureCompareId(Constant.NameElement.MEDICAL_RECORD,
                    medicalRecord.getId(), cb.getMedicalRecord().getId()).getValue());
            float sum = (valuePA + valueFS + valueHabit + valueKS + valueSex + valueCondition + valueMR) / 22;
            if (sum > maxCb) {
                maxCb = sum;
                caseBase = cb;
            }
        }
        if (maxCb > 0.7)
            return "Theo bot chuẩn đoán bạn đã bị " + caseBase.getName() + '\n' + "Các phương pháp điều trị sau: " + '\n' + caseBase.getTreatment() + '\n' + "Cảm ơn bạn đã tham gia tư vấn!";
        else
            return Constant.Question.OVER;
    }

    private final ObjectMapper mapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

}
