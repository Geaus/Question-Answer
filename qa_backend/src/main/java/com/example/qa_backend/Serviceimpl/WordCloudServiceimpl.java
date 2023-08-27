package com.example.qa_backend.Serviceimpl;

import com.example.qa_backend.Dao.QuestionDao;
import com.example.qa_backend.Entity.Question;
import com.example.qa_backend.Service.WordCloudService;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.CircleBackground;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Service
public class WordCloudServiceimpl implements WordCloudService {
    private String wordCloudString = "";

    @Autowired
    QuestionDao questionDao;

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

    @Override
    public void generate() throws IOException {
        System.out.println("刷新词云");
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
        wordCloud.setBackground(new CircleBackground(200));wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));// 生成词云
        wordCloud.build(wordFrequencyList);
        OutputStream output = new ByteArrayOutputStream();
        wordCloud.writeToStream("png", output);
        byte[] outputByte = ((ByteArrayOutputStream)output).toByteArray();
        wordCloudString =  encodeBase64String(outputByte);
        System.out.println("词云刷新完毕");
    }

    @Override
    public String get() {
        return wordCloudString;
    }
}
