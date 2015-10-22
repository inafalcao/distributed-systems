package br.com.file.service.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import br.com.file.service.component.ServerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.file.service.model.RemoteFile;

import java.util.List;

@Controller
public class ServerGroupController {

    @Autowired
    ServerGroup serverGroup;

	/*@InitBinder("remoteFile")
	private void initBinderOrderItem(WebDataBinder binder) {
		binder.setValidator(orderItemValidator);
	}*/
	
    @PostConstruct
    public void init() throws Exception {
        System.out.println("Waiting for MASTER ELECTION ");

        Thread.sleep(5000);
        while(serverGroup.getMaster() == null) {}

        System.out.println("SERVER GROUP: " + serverGroup.showGroup());
        System.out.println("MASTER IS: " + serverGroup.getMaster().toString());
    }

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		//orderItem = new OrderItem();
		//ModelAndView mav = new ModelAndView("custom", "orderItem", orderItem);


        //slaveGroup.getMaster().getFileList();

		return null;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
	public ModelAndView custom(@ModelAttribute @Valid RemoteFile file,
								BindingResult result, 
								final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return new ModelAndView("list");
		}

        serverGroup.getMaster().createFile(file);

		return new ModelAndView("redirect:/list");
	}

	@RequestMapping(value="/end", method=RequestMethod.GET)
	public String newEndPage() {
		return "end";
	}

}
