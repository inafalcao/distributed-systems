package br.com.file.service.model;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by inafalcao on 10/20/15.
 */
public interface RemoteFileOperations {

    RemoteFile viewFile(RemoteFile f);

    RemoteFile removeFile(Integer id);

    RemoteFile editFile(RemoteFile file);

    RemoteFile createFile(RemoteFile file);

}
