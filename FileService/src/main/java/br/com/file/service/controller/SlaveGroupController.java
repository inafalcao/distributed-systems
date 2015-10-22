package br.com.file.service.controller;

import br.com.file.service.component.SlaveGroup;
import br.com.file.service.group.Slave;
import br.com.file.service.model.RemoteFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SlaveGroupController {

    @Autowired
    SlaveGroup slaveGroup;

    public Slave getSlave(RemoteFile file) throws Exception {
        System.out.println("Waiting for SLAVE ELECTION ");

        Slave slave = null;
        while(slave == null) {
            slave = slaveGroup.getAvaliableSlave(file);
        }

        System.out.println("AVALIABLE SLAVE: " + slave.toString());
        return slave;
    }

    @RequestMapping(value = "/slave/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void custom(@RequestBody RemoteFile file) {

        try {
            getSlave(file).createFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/slave/view", method = RequestMethod.GET)
    @ResponseBody
    public RemoteFile view(RemoteFile file) {
        RemoteFile ff = null;
        try {
            ff = getSlave(file).viewFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ff;
    }

}
