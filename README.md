# Kjector

JSR 330 compatible Dependency Injection Library (in development)

#@Inject

#### Animal.java

```
public class Animal {
    @Inject
    @Named("name")
    protected String name;
}
```

#### Dog.java

```
public class Dog extends Animal {
    @Inject
    @Named("nickname")
    String nickname;
}
```

#### KjectorApp.java

```
public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .registerByName("name", "dog")
                .registerByName("nickname", "doggy")
                .register(Dog.class)
                .register(Animal.class)
                .build();

        final Dog dog = container.resolve(Dog.class);

        System.out.println(dog.name);
        System.out.println(dog.nickname);
    }
}
```