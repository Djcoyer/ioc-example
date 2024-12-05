import controllers.InjectableController;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        IocContainer container = new IocContainer();
        InjectableController controller = container.getInstance(InjectableController.class);
        System.out.println(controller.getServiceMessage());
    }
}
