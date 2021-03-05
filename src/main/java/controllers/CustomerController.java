package controllers;


import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.customer.ICustomerService;

@Controller
@RequestMapping("/home")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/show")
    public ModelAndView showList() {
        return new ModelAndView("list", "customerList", customerService.findAll());
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Integer id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("editForm", "customer", customer);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute Customer customer) {
        customer.setId(id);
        customerService.update(customer);
        return "redirect:/home/show";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Integer id) {
        Customer customer = customerService.findById(id);
        return new ModelAndView("deleteForm", "customer", customer);
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/home/show";
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        Customer customer = new Customer();
        modelAndView.addObject(customer);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Customer customer){
        customer.setId((int) Math.round(Math.random()*1000000));
        customerService.create(customer);
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("mess","Added success!");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;

    }



}

