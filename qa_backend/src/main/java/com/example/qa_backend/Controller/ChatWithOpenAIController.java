package com.example.qa_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import retrofit2.Retrofit;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.theokanning.openai.service.OpenAiService.*;

@CrossOrigin
@RestController
public class ChatWithOpenAIController {

    @Value("${openai.api.key}")
    private String token;

    private OpenAiApi api;
    private OpenAiService service;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @PostConstruct
    public void init() {
        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress( "127.0.0.1",15236));
        OkHttpClient client = defaultClient(token, Duration.ofSeconds(100000))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        api = retrofit.create(OpenAiApi.class);
        service = new OpenAiService(api);
    }

    @RequestMapping("/chat")
    public String chat(@RequestBody Map<String, String> requestBody) {
        String question = requestBody.get("question");

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), question);
        messages.add(userMessage);

//        OpenAiService service = new OpenAiService("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", Duration.ofSeconds(100000));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo-0613")
                .messages(messages)
                .temperature(0.5)
                .maxTokens(2048)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
        System.out.println(question);
        try {
            Future<ChatMessage> future = executorService.submit(() -> service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage());
            ChatMessage text = future.get();
            return text.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
