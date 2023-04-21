package com.contactBook.controller;

import com.contactBook.model.Contact;
import com.contactBook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping({"/",""})
    public String showHomepage(Model model){
        List<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "index";
    }

    @GetMapping("/new")
    public String registerForm(Model model){
        model.addAttribute("contact", new Contact());
        return "new";
    }

    @PostMapping("/new")
    public String saveContact(@Validated Contact contact,BindingResult bindingResult, RedirectAttributes redirect,  Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("contact",contact);
            return "new";
        }

        contactRepository.save(contact);
        redirect.addFlashAttribute("msgSuccess","The contact has been saved");
        return "redirect:/";
    }

    @GetMapping("/{id}/edit/")
    public String showContactForm(@PathVariable Integer id, Model model){
        Contact contact = contactRepository.getReferenceById(id);
        model.addAttribute("contact", contact);
        return "new";
    }

    @PostMapping("/{id}/edit/")
    public String updateContact(@PathVariable Integer id, @Validated Contact contact,BindingResult bindingResult, RedirectAttributes redirect,  Model model){
        Contact contactDB = contactRepository.getReferenceById(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("contact",contact);
            return "new";
        }

        contactDB.setName(contact.getName());
        contactDB.setPhoneNumber(contact.getPhoneNumber());
        contactDB.setEmail(contact.getEmail());
        contactDB.setBirthday(contact.getBirthday());

        contactRepository.save(contactDB);
        redirect.addFlashAttribute("msgSuccess","The contact has been updated");
        return "redirect:/";
    }

    @PostMapping("/{id}/delete/")
    public String deleteContactById(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Contact contact = contactRepository.getReferenceById(id);
        contactRepository.delete(contact);
        redirectAttributes.addFlashAttribute("msgSuccess","The contact has been deleted");
        return "redirect:/";
    }
}
