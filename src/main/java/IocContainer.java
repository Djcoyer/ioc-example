import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import util.Inject;
import util.Injectable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class IocContainer {
    // Track all injectable classes and their corresponding instances
    public Map<Class<?>, Object> classMap;

    public IocContainer() {
        this.classMap = new HashMap<>();

        // Instantiate all Injectable classes
        this.instantiateInjectableClasses();

        // After creating all instances, inject them where needed
        this.injectFieldValues();
    }

    public void registerClass(Class<?> classToRegister) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (this.classMap.containsKey(classToRegister))
            return;

        var instance = this.initializeClass(classToRegister);
        this.classMap.put(classToRegister, instance);
    }

    private void registerInterface(Class<?> interfaceType, Class<?> implementationType) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (this.classMap.containsKey(interfaceType)) {
            return;
        }
        var instance = this.initializeClass(implementationType);
        this.classMap.put(interfaceType, instance);
    }

    public <T> T getInstance(Class<T> classToGet) {
        try {
            if (classToGet.isInterface()) {
                return this.getInterfaceInstance(classToGet);
            }
            else if (!this.classMap.containsKey(classToGet)) {
                this.registerClass(classToGet);
            }
        } catch(Exception e) {}

        return classToGet.cast(this.classMap.get(classToGet));
    }

    private <T> T initializeClass(Class<T> classToInitialize) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var constructor = classToInitialize.getDeclaredConstructor();
        List<Object> parameterInstances = new ArrayList<>();

        if (constructor.isAnnotationPresent(Injectable.class)) {
            var parameters = constructor.getParameters();
            if (parameters.length > 0) {
                for (Parameter parameter : parameters) {
                    parameterInstances.add(this.getInstance(parameter.getType()));
                }
            }
        }

        constructor.setAccessible(true);
        return constructor.newInstance(parameterInstances.toArray());
    }

    private void initializeInjectableFields(Class<?> classType, Object instance) {
        try {
            Arrays.stream(classType.getDeclaredFields()).filter(f -> f.isAnnotationPresent(Inject.class)).forEach((field) -> {
                field.setAccessible(true);
                try {
                    var fieldInstance = this.getInstance(field.getType());
                    field.set(instance, fieldInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch(Exception e) {
            System.out.println("Failed to initialize class " + classType.getName());
        }
    }

    private <T> T getInterfaceInstance(Class<T> interfaceToGet) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AtomicReference<Class<?>> foundClass = new AtomicReference<>();
        this.classMap.forEach((clazz, index) -> {
            if (Arrays.asList(clazz.getInterfaces()).contains(interfaceToGet)) {
                foundClass.set(clazz);
            }
        });

        if (foundClass.get() != null) {
            this.registerInterface(interfaceToGet, foundClass.get());
        }

        return interfaceToGet.cast(this.classMap.get(interfaceToGet));
    }

    private void instantiateInjectableClasses() {
        Set<Class<?>> injectableClasses = new Reflections(ClasspathHelper.forJavaClassPath()).getTypesAnnotatedWith(Injectable.class);

        injectableClasses.forEach(clazz -> {
            try {
                this.registerClass(clazz); // Register the class if it's annotated with @Injectable
            } catch (Exception e) {
                e.printStackTrace(); // Handle any registration errors
            }
        });
    }

    private void injectFieldValues() {
        Map<Class<?>, Object> classMapClone = new HashMap<>(this.classMap);
        classMapClone.forEach(this::initializeInjectableFields);
    }
}
