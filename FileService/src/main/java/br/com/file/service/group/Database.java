package br.com.file.service.group;

import br.com.file.service.model.RemoteFile;
import br.com.file.service.model.RemoteFileOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 9/14/15.
 */
public class Database implements RemoteFileOperations {

    private List<RemoteFile> files;

    private static Database instance;

    private Database() {
        files = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    @Override
    public RemoteFile viewFile(RemoteFile file) {
        return files.get(files.indexOf(file));
    }

    @Override
    public void removeFile(Integer id) {
        for (RemoteFile f : files) {
            if (f.getId() == Long.valueOf(id))
                files.remove(files.indexOf(f));
        }
    }

    @Override
    public void editFile(RemoteFile file) {
        for (RemoteFile f : files) {
            if (f.getId() == file.getId())
                files.set(files.indexOf(f), file);
        }
    }

    @Override
    public void createFile(RemoteFile file) {
        this.files.add(file);
        System.out.println("FILE CREATED");
    }
}
