import controllers.InjectableController;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        IocContainer container = new IocContainer();
        InjectableController controller;
        try {
            controller = container.getInstance(InjectableController.class);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return;
        }

        String message = controller.getServiceMessage();
        System.out.println(message);
    }
}
