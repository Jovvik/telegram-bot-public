package bot.app.service;

import bot.app.utils.data.DataBlock;

import java.util.List;

public class EventBuilderService {

    public void handleDataAndStartBuild(List<DataBlock<?>> answerData) {
        System.out.println("Data: ");
        for (var answer: answerData) {
            System.out.println("Q:" + answer.getQuestion() + ", A:" + answer.getAnswer());
        }
    }
}
