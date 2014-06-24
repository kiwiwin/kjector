# Kjector

JSR 330 compatible Dependency Injection Library (in development)


#Examples
##@Inject

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

##@Named

#### Boy.java
```
public class Boy {
    @Inject
    private
    @Named("lucy")
    Girl girlfriend;


    public String showGirlFriend() {
        return girlfriend.getName() + " is my girlfriend";
    }
}
```

#### Girl.java
```
public class Girl {
    private final String name;

    public Girl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

#### KjectorApp.java
```
public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .registerByName("lucy", new Girl("lucy"))
                .register(Boy.class)
                .build();

        final Boy boy = container.resolve(Boy.class);

        System.out.println(boy.showGirlFriend());
    }
}
```

##@Qualifier
#### Painter.java
```
@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@javax.inject.Qualifier
public @interface Painter {
    Color backgroundColor() default Color.TAN;

    Color color() default Color.TAN;

    public enum Color {RED, BLACK, TAN}
}
```

#### Farmer.java
```
public class Farmer {
    @Inject
    @org.kiwi.kjector.container.sample.Painter(color = org.kiwi.kjector.container.sample.Painter.Color.RED, backgroundColor = org.kiwi.kjector.container.sample.Painter.Color.BLACK)
    String uglyPainter;

    public Farmer() {

    }

    public String getUglyPainter() {
        return uglyPainter;
    }
}
```

#### KjectorApp.java
```
public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .register(Farmer.class)
                .registerByQualifier(new QualifierMeta(org.kiwi.kjector.container.sample.Painter.class).meta("color", Painter.Color.RED).meta("backgroundColor", Painter.Color.BLACK), "Messi")
                .build();

        final Farmer farmer = container.resolve(Farmer.class);

        System.out.println(farmer.getUglyPainter());
    }
}
```

