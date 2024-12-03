package services;

import services.inter.IInjectableService;

public class OtherInjectableService implements IInjectableService {
    @Override
    public String getMessage() {
        return "Hello from other service!";
    }
}
