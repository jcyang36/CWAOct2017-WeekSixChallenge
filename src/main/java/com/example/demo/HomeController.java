package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {
    @Autowired
    TransactionRepository transactionRepository;


    @RequestMapping("/")
    public String goIndex(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "index";
    }

    @RequestMapping("/list")
    public String goList(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "list";
    }

    @GetMapping("/deposit")
    public String goDepositForm(Model model){
        model.addAttribute("transaction", new Transaction());
        return "deposit";
    }

    @GetMapping("/withdrawal")
    public String goWithdrawalForm(Model model){
        model.addAttribute("transaction", new Transaction());
        return "withdrawal";
    }
    @PostMapping("/processdeposit")
    public String processForm(@Valid Transaction transaction, BindingResult result)
    {
        if(result.hasErrors()){
            return "deposit";

        }
        transactionRepository.save(transaction);
        return "redirect:/list";
    }
    @RequestMapping("/detail/{id}")
    public String showTransaction(@PathVariable("id") long id, Model model){
        model.addAttribute("transaction", transactionRepository.findOne(id));
        return "show";
    }
    @RequestMapping("/balance")
    public String goBalance(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "balance";
    }

}
