package services;

import services.inter.IInjectableService;
import util.Injectable;

@Injectable
public class InjectableService implements IInjectableService {
    @Override
    public String getMessage() {
        return "Hello from service!";
    }
}