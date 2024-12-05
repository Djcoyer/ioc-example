package v2;

public class Controller {
    private Service service;

    public Controller() {
        var repository = new Repository();
        this.service = new Service();
        this.service.setRepository(repository);

        // OR
        this.service = new Service(repository);
    }
}
