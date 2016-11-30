package com.ironyard.controller.mvc;

import com.ironyard.data.Goal;
import com.ironyard.repos.GoalRepository;
import com.ironyard.repos.GoalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by reevamerchant on 11/7/16.
 */
@Controller
@RequestMapping(path = "/mvc/secure/goals")
public class MvcGoalController {

    @Autowired
    GoalUserRepository goalUserRepository;

    @Autowired
    GoalRepository goalRepository;

    private void addGoalsList(Model model){
        Iterable<Goal> goals = goalRepository.findAll();
        model.addAttribute("goals", goals);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        String destination = "/secure/goals";
        addGoalsList(model);
        return destination;
    }



    @RequestMapping(value = "all", method = RequestMethod.GET)
    public String allMovies(Model model){

        Iterable<Goal> aPageOfCompletedGoals = goalRepository.findByAccomplished(true);

        Iterable<Goal> aPageOfIncompleteGoals = goalRepository.findByAccomplished(false);


        model.addAttribute("completed_goals", aPageOfCompletedGoals.iterator());
        model.addAttribute("notcompleted_goals", aPageOfIncompleteGoals.iterator());

        return "/secure/goals";
    }



    @RequestMapping(value = "list/create", method = RequestMethod.POST)
    public String save(@RequestParam("item") String item,
                         @RequestParam("dateToBeCompleted") String dateToBeCompleted,
                         @RequestParam("comments") String comments) {

        String destination = "redirect:/mvc/secure/goals/all";

        Goal newGoal = new Goal();
        newGoal.setItem(item);
        newGoal.setDateToBeCompleted(dateToBeCompleted);
        newGoal.setComments(comments);

        goalRepository.save(newGoal);

        return destination;
    }



    @RequestMapping(value = "list/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Long id) {
        String destination = "redirect:/mvc/secure/goals/all";

        Goal foundGoal = goalRepository.findOne(id);

        goalRepository.delete(foundGoal);

        return destination;
    }



    @RequestMapping(value = "list/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id") Long id,
                       Model model) {
        String destination = "/secure/edit";

        Goal editGoal = goalRepository.findOne(id);
        model.addAttribute("item", editGoal.getItem());
        model.addAttribute("dateToBeCompleted", editGoal.getDateToBeCompleted());
        model.addAttribute("comments", editGoal.getComments());
        model.addAttribute("id", editGoal.getId());

        return destination;
    }



    @RequestMapping(value = "list/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") Long id,
                       @RequestParam("item") String item,
                       @RequestParam("dateToBeCompleted") String dateToBeCompleted,
                       @RequestParam("comments") String comments) {

        String destination = "redirect:/mvc/secure/goals/all";

        Goal editGoal = goalRepository.findOne(id);
        editGoal.setItem(item);
        editGoal.setDateToBeCompleted(dateToBeCompleted);
        editGoal.setComments(comments);

        goalRepository.save(editGoal);

        return destination;
    }

}
