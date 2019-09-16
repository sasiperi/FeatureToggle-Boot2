package com.sasip.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.spring.autowire.FF4JFeature;
import org.ff4j.strategy.ClientFilterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sasip.form.PersonForm;
import com.sasip.model.Person;
import com.sasip.service.CalculationsService;

@Controller
@RequestScope
public class PersonController
{

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @FF4JFeature(value = "sasi-f1")
    private boolean feature_X;

    @Autowired
    private FF4j ff4j;

    @Autowired
    @Qualifier("caluculationServiceDecimal")
    CalculationsService calcSvc;

    private static List<Person> persons = new ArrayList<Person>();

    static
    {
        persons.add(new Person("Dennis", "Ritchie", "C-Lang"));
        persons.add(new Person("Sabeer", "Bhatia", "Hot Mail"));
        persons.add(new Person("Ken", "Thomson", "Bell Labs, Unix"));
        persons.add(new Person("Steve", "Jobs", "Apple, CEO"));
        persons.add(new Person("Vinod", "Koshla", "Sun Mircro Systems, CEO"));
        persons.add(new Person("Sundar", "Pichai", "Google, CEO"));
        persons.add(new Person("Sam", "Ramji", "Cloud Foundry, CEO"));
        persons.add(new Person("Vivek", "Ranadive", "TIBCO, Founder"));
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model)
    {

        // model.addAttribute("message", message);

        return "index";
    }

    @GetMapping("/login")
    public String login()
    {
        return "/login";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model, HttpServletRequest request)
    {
        /*
         * if(ff4j.check("featurename")) {
         * 
         * }
         */
        if (feature_X)
        {
            LOG.info(" SASI-F1 ON");
        } else
        {
            LOG.info(" SASI-F1 OFF");
        }

        setClientInFlipContext(model, request);

        model.addAttribute("persons", persons);

        return "personList";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model)
    {

        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personForm)
    {

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        String who = personForm.getWhoIs();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0)
        {
            Person newPerson = new Person(firstName, lastName, who);
            persons.add(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", true);
        return "addPerson";
    }

    @RequestMapping(value = { "/removePerson/{index}" }, method = RequestMethod.GET)
    public String removePerson(Model model, @PathVariable int index)
    {
        persons.remove(index);
        return "redirect:/personList";

    }

    @RequestMapping(value = { "/addNumbers" }, method = RequestMethod.POST)
    public String addNumbers(RedirectAttributes redirectModel, @RequestParam("numOne") int numOne, @RequestParam("numTwo") int numTwo)
    {
        String sum = calcSvc.addNumbers(numOne, numTwo);
        redirectModel.addFlashAttribute("sum", sum);
        return "redirect:/personList";

    }

    @RequestMapping(value = { "/modifyPerson/{firstName}/{lastName}/{whoIs}" }, method = RequestMethod.POST)
    public String modifyPerson(Model model, @PathVariable String firstName, @PathVariable String lastName, @PathVariable String whoIs)
    {

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0)
        {
            Person newPerson = new Person(firstName, lastName, whoIs);
            persons.add(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", true);
        return "addPerson";
    }

    private void setClientInFlipContext(Model model, HttpServletRequest request)
    {
        FlippingExecutionContext fex = new FlippingExecutionContext();
        fex.addValue(ClientFilterStrategy.CLIENT_HOSTNAME, getClientIp(request));
        if (ff4j.check("client-feature", fex))
        {
            model.addAttribute("changeLook", true);
        }

    }

    private String getClientIp(HttpServletRequest request)
    {

        String remoteAddr = "";

        if (request != null)
        {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            LOG.info("X-FORWARDED-FOR : " + remoteAddr);
            if (remoteAddr == null || "".equals(remoteAddr))
            {
                remoteAddr = request.getRemoteAddr();

                LOG.info("Remote Addr : " + remoteAddr);
            }
        }

        return remoteAddr;
    }

}
