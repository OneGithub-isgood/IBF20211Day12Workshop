package sg.edu.nus.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.workshop12.model.Generate; //Import class in same package

@Controller
public class GenerateController {
    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @GetMapping("/generate")
    public String showGenerateForm(Model model) {
        Generate generate = new Generate();
        //generateRandom.setNumberVal(8);
        model.addAttribute("generateRandom", generate);
        return "generate"; //generate.html
    }

    @PostMapping("/generate")
    public String generateNumber(@ModelAttribute Generate generate, Model model) {
        logger.info("From the form " + generate.getNumberVal());
        int numRandNum = generate.getNumberVal();
        if (numRandNum > 11) {
            //throw new RandomNumberExpection();
            model.addAttribute("errMessage", "Warning: Exceed maximum number 11");
            return "error";
        }
        String[] imgNumbers = {
            "number0.jpg", "number1.jpg", "number2.jpg", "number3.jpg", "number4.jpg", "number5.jpg",
            "number6.jpg", "number7.jpg", "number8.jpg", "number9.jpg", "number10.jpg"
        };
        List<String> selectedImg = new ArrayList<String>();
        Random randNum = new Random();
        Set<Integer> generatedRandNumSet = new LinkedHashSet<Integer>();
        while(generatedRandNumSet.size() < numRandNum) {
            Integer resultRandNum = randNum.nextInt(generate.getNumberVal() +1);
            generatedRandNumSet.add(resultRandNum);
        }
        Iterator<Integer> it = generatedRandNumSet.iterator();
        Integer currentElem = null;
        while(it.hasNext()) {
            currentElem = it.next();
            selectedImg.add(imgNumbers[currentElem.intValue()]);
        }
        model.addAttribute("randNumResult", selectedImg.toArray());
        return "result"; //result.html
    }
}
