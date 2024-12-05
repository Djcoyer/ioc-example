package v3;

import v3.impl.SqlRepository;

public class Controller {
    private Service service;

    public Controller() {
        this.service = new Service(new SqlRepository());
    }
}
