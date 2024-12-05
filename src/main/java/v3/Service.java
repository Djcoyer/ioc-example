package v3;

import v3.inter.IDataRepository;

public class Service {
    private final IDataRepository dataRepository;

    public Service(IDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public String getData(Long id) {
        return this.dataRepository.getDataById(id);
    }
}
