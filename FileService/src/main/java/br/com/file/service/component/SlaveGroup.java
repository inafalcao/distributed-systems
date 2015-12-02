package br.com.file.service.component;

import br.com.file.service.group.Slave;
import br.com.file.service.model.RemoteFile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 10/20/15.
 */
@Component("slaveGroup")
public class SlaveGroup {

    public static List<Slave> slaves;

    public SlaveGroup() {
        // Adding slaves

        slaves = new ArrayList<>();

        Slave s1 = new Slave();

        Slave s2 = new Slave();

        Slave s3 = new Slave();

        slaves.add(s1);
        slaves.add(s2);
        slaves.add(s3);
    }

}
