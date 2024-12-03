package controllers;

import services.inter.IInjectableService;
import util.Inject;
import util.Injectable;

@Injectable
public class InjectableController {
    @Inject
    private IInjectableService service;

    public String getServiceMessage() {
        return this.service.getMessage();
    }
}
