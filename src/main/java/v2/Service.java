package v2;

public class Service {
    private Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public Service() {
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
