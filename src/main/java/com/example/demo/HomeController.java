package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    TransactionRepository transactionRepository;


    @RequestMapping("/")
    public String goIndex(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        return "redirect:/register";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
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

@GetMapping("/register")
public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";

}

@PostMapping("/register")
public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
if(bindingResult.hasErrors()){
    return "register";
}else{
    userService.saveUser(user);
    model.addAttribute("message", "User Account Successfully Created");
    return "index";
}

}
    @RequestMapping("/balance")
    public String goBalance(Model model){
        model.addAttribute("transactions", transactionRepository.findAll());
        long balance=0;
        for (Transaction transaction: transactionRepository.findAll()) {
            if (transaction.getAction().equalsIgnoreCase("deposit")) {
                balance=balance+transaction.getAmount();

            }
            else if (transaction.getAction().equalsIgnoreCase("withdrawal")){
                balance=balance-transaction.getAmount();
            }
        }
        model.addAttribute("balance", balance);
        return "balance";
    }

}
