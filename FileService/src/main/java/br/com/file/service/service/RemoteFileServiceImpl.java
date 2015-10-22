package br.com.file.service.service;

import javax.annotation.Resource;

import br.com.file.service.model.RemoteFile;
import br.com.file.service.repository.RemoteFileRepository;
import br.com.file.service.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("remoteFileService")
public class RemoteFileServiceImpl extends ServiceImpl<RemoteFile> implements RemoteFileService {

    private static final long serialVersionUID = -7579434482940907523L;

    @Resource(name = "remoteFileRepository")
    private RemoteFileRepository repository;

    @Override
    public Repository<RemoteFile> getRepository() {
        return repository;
    }

}
