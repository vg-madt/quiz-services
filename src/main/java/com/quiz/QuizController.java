package com.quiz;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Answers;
import com.quiz.entity.Question;
import com.quiz.entity.Result;
import com.quiz.entity.Score;
import com.quiz.entity.Topic;
import com.quiz.util.RandomPicker;
import com.quiz.util.RequestHandler;
import com.quiz.util.ResponseHandler;

@RestController
@Slf4j
public class QuizController {

    @Autowired
    private RequestHandler requestHandler;

    @Autowired
    private ResponseHandler responseHandler;

    @Value("${topic.location}")
    private String path;

    @CrossOrigin
    @RequestMapping(value="/health", produces={"text/html"})
    public String healthCheck() {
        return "Ok!!";
    }
    

    @RequestMapping(value="/topic/list",
            produces={"application/json"})
    public String list() {
        log.info("Accessing list for topics from: {}", path);
        final File dir = new File(path);
        final String[] topics = dir.list();
        final List<String> finalTopics = new ArrayList<>();
        for (@NotBlank final String topic : topics) {
            if (topic.indexOf(".topic") == -1) {
                continue;
            }
            finalTopics.add(topic.substring(0, topic.length() - 6));
        }
        return responseHandler.serialize(finalTopics);
    }

    @RequestMapping(value="/topic/{topic}/get",
            produces={"application/json"})
    public String get(@PathVariable("topic") final String topic) {
        try(final Reader reader = new FileReader(path + "/" + topic + ".topic");) {
            final Topic deserialized = requestHandler.deserialize(Topic.class, reader);
            final List<Question> random = RandomPicker.pickRandom(deserialized.getQuestions(), 10);
            return responseHandler.serialize(random);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return error(e);
        }
    }

    @RequestMapping(value="/topic/{topic}/student/{name}/answer",
            produces={"application/json"},
            consumes={"application/json"},
            method=RequestMethod.POST)
    public String answer(
            @PathVariable("topic") final String topic,
            @PathVariable("name") final String student,
            @RequestBody final String body) {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        final String formatted = df.format(new Date());
        final String filename = topic + "_" + student + "_" + formatted;
        final String resultPath = path + "/results/" + topic + ".json";
        final File resultFile = new File(resultPath);
        try(final Writer answerWriter = new FileWriter(path + "/answers/" + filename + ".json");
                final Writer resultWriter = new FileWriter(resultFile);
                final Reader resultReader = new FileReader(resultFile);) {
            final Answers answers = requestHandler.deserialize(Answers.class, body);
            log.debug("Deserialized answers: {}", answers);
            final float score = answers.evaluate();
            final Result result;
            if (resultFile.exists()) {
                result = requestHandler.deserialize(Result.class, resultReader);
            } else {
                result = new Result();
                final Score studentScore = new Score();
                studentScore.setStudent(student);
                studentScore.setAttemptedOn(formatted);
                studentScore.setScore(score);
                result.add(studentScore);
            }
            resultWriter.write(responseHandler.serialize(result));
            resultWriter.flush();
            answerWriter.write(body);
            answerWriter.flush();
            return success("Successfully stored for Student: " + student + " for Topic: " + topic);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return error(e);
        }
    }

    @RequestMapping(value="/topic/{topic}/result",
            produces={"application/json"})
    public String result(@PathVariable("topic") final String topic) {
        final String resultPath = path + "/results/" + topic + ".json";
        try(final Reader reader = new FileReader(resultPath);) {
            final Result deserialized = requestHandler.deserialize(Result.class, reader);
            return responseHandler.serialize(deserialized);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return error(e);
        }
    }

    private String error(final Throwable t) {
        return "{\"error\": \"" + t.getMessage() + "\"}";
    }

    private String success(final String message){
        return "{\"message\": \"" + message + "\"}";
    }

}
