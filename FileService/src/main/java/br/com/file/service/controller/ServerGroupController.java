package br.com.file.service.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import br.com.file.service.component.ServerGroup;
import br.com.file.service.group.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.file.service.model.RemoteFile;

import java.util.List;

@Controller
public class ServerGroupController {

    @Autowired
    ServerGroup serverGroup;

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

	@RequestMapping(value="/shutdown/{index}", method=RequestMethod.PUT)
    @ResponseBody
	public String shutdownServer(@PathVariable int index) throws Exception {
		int size = serverGroup.getServers().size();

		if(index > size-1 || index < 0)
			return "Index out of bound of the servers list";

		serverGroup.getServers().remove(index).leave();

		// Trigger another ELECTION, if there's any Server to attend.
		if(size-1 > 0) {
            for(Server s : serverGroup.getServers())
                s.setIsMaster(false);

			serverGroup.getServers().get(0).sendElection();
			init();
            return "SERVER GROUP: " + serverGroup.showGroup() +
                   "MASTER IS: " + serverGroup.getMaster().toString();
		}
		else
			return "We have no servers.";
	}

}
