package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.FeedbackQuestionDao;
import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Entity.FeedbackForQuestion;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.JSON.QuestionJSON;
import com.example.qa_backend.Service.WordCloudService;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.filter.CompositeFilter;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Service
public class WordCloudServiceimpl implements WordCloudService {
    private String wordCloudString = "";
    private List<Question> hotQuestions = new ArrayList<>();
    @Autowired
    QuestionDao questionDao;
    @Autowired
    FeedbackQuestionDao feedbackQuestionDao;
    @PostConstruct
    public void setTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    generate();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long period = 1000 * 3600 * 24;
        timer.schedule(task, delay, period);
    }

    public void generate() throws IOException {
        System.out.println("刷新词云");
        System.out.println(Paths.get("").toAbsolutePath());

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        FileReader fileReader = new FileReader("src/main/resources/CloudStopWords.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> stopWords = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stopWords.add(line);
        }
        frequencyAnalyzer.setStopWords(stopWords);
        List<Question> questions = questionDao.listAll();
        List<String> words = new ArrayList<>();
        for (Question question : questions) {
            words.add(question.getTitle());
        }
        final List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(words);
        Dimension dimension = new Dimension(500, 500);// 此处的设置采用内置常量即可，生成词云对象
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        java.awt.Font font = new java.awt.Font("STSong-Light", Font.ITALIC, 18);
        wordCloud.setKumoFont(new KumoFont(font));wordCloud.setPadding(2);
        wordCloud.setColorPalette(new ColorPalette(new Color(0xed1941), new Color(0xf26522), new Color(0x845538),new Color(0x8a5d19),new Color(0x7f7522),new Color(0x5c7a29),new Color(0x1d953f),new Color(0x007d65),new Color(0x65c294)));
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));// 生成词云
        wordCloud.build(wordFrequencyList);
        OutputStream output = new ByteArrayOutputStream();
        wordCloud.writeToStream("png", output);
        byte[] outputByte = ((ByteArrayOutputStream)output).toByteArray();
        wordCloudString = encodeBase64String(outputByte);
        System.out.println("词云刷新完毕");

        System.out.println("刷新热榜");
        questions.sort((a, b) -> {
            int hotIndexA = a.getLike() + a.getDislike() + a.getMark();
            int hotIndexB = b.getLike() + b.getDislike() + b.getMark();
            return Integer.compare(hotIndexB, hotIndexA);
        });
        if(questions.size() < 100) {
            hotQuestions = new ArrayList<>(questions);
        }
        else {
            List<Question> hotTmp = new ArrayList<>();
            for(int i = 0; i < 100; i++) {
                hotTmp.add(questions.get(i));
            }
            hotQuestions = hotTmp;
        }
        System.out.println("热榜刷新完毕");
    }

    @Override
    public String get() {
        return wordCloudString;
    }

    @Override
    public List<QuestionJSON> getHotQuestion(int uid, int page_id) {
        List<QuestionJSON> resList = new ArrayList<>();
        for(int i = page_id * 10; i < (page_id + 1) * 10; i++) {
            if(hotQuestions.size() <= i) break;
            Question tmp = hotQuestions.get(i);
            Question question = questionDao.getQuestion(tmp.getId());
            QuestionJSON res = new QuestionJSON();
            res.setId(question.getId());
            res.setContent(question.getContent());
            res.setCreateTime(question.getCreateTime());
            res.setTags(question.getTags());
            res.setTitle(question.getTitle());
            res.setUser(question.getUser());
            List<FeedbackForQuestion> feedback = feedbackQuestionDao.findFeedback(question.getId());
            int like = 0, dislike = 0, mark = 0, likeFlag = 0, markFlag = 0;
            for(int j = 0; j < feedback.size(); j++) {
                if(feedback.get(j).getLike() == -1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = -1;
                    dislike++;
                }
                else if(feedback.get(j).getLike() == 1){
                    if(feedback.get(j).getUserId() == uid)likeFlag = 1;
                    like++;
                }
                if(feedback.get(j).getBookmark() == 1){
                    if(feedback.get(j).getUserId() == uid)markFlag = 1;
                    mark++;
                }
            }
            res.setLike(like);
            res.setDislike(dislike);
            res.setMark(mark);
            res.setLikeFlag(likeFlag);
            res.setMarkFlag(markFlag);
            resList.add(res);
        }
        return resList;
    }
}
